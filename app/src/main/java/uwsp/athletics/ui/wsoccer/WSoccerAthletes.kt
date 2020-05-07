package uwsp.athletics.ui.wsoccer

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import okhttp3.*
import uwsp.athletics.R
import java.io.IOException

class WSoccerAthletes : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wsoccer_athletes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        val lin: LinearLayout = view.findViewById(R.id.wSoccerAthletesLinear)
        fetchWSoccerRoster(lin)

        super.onViewCreated(view, savedInstanceState)
    }

    // Function to fetch and create the roster
    private fun fetchWSoccerRoster(layout: LinearLayout) {

        // Request URL for  the men's baseball roster
        val url = "https://athletics.uwsp.edu/services/roster_xml.aspx?format=json&path=wsoc"

        //  Sets the client and creates the request with the url
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        //  Calls the request
        client.newCall(request).enqueue(object : Callback {

            // On successful call
            override fun onResponse(call: Call, response: Response) {

                // Sets unparsed response to val body
                val body = response.body?.string()

                // Creates the gson object for parsing
                val gson = GsonBuilder().create()

                // Parses the return using the RosterReturnClass
                val sidearmReturn = gson.fromJson(body, RosterReturnClass::class.java)

                // Context set to a variable because "this" doesn't work when creating elements
                val currentContext = context

                // runOnUiThread required to change the ui after initial creation
                activity?.runOnUiThread(java.lang.Runnable {


                    // for loop to create a card for every index
                    for (i in sidearmReturn.roster.indices) {

                        //creates a horizontal linear layout and sets padding
                        var pictureLinearLayout: LinearLayout = LinearLayout(currentContext)
                        var infoLinearLayout: LinearLayout = LinearLayout(currentContext)
                        pictureLinearLayout.setPadding(0, 0, 0, 0)

                        // creates a textview for the description
                        var title: TextView = TextView(currentContext)
                        var tv: TextView = TextView(currentContext)

                        infoLinearLayout.orientation = LinearLayout.VERTICAL

                        // Sets the styles of the textview
                        title.textSize = 18.0f
                        title.setTextColor(Color.parseColor("#000000"))
                        title.setPadding(24, 16, 0, 8)
                        title.setTypeface(null, Typeface.BOLD)

                        // Sets the styles of the textview
                        tv.textSize = 16.0f
                        tv.setTextColor(Color.parseColor("#000000"))
                        tv.setPadding(24, 16, 0, 8)

                        // Creates a textview that works as a link for the full bio
                        var link: TextView = TextView(currentContext)
                        link.setBackgroundResource(R.drawable.border)

                        // Sets the text and styles of the link
                        link.textSize = 14.0f
                        link.setTextColor(Color.parseColor("#1a0dab"))
                        link.setPadding(0, 0, 0, 20)
                        link.gravity = Gravity.CENTER
                        link.append("View Bio")

                        //creates an imageview for the athlete
                        var imgview: ImageView = ImageView(currentContext)

                        // sets the size and padding of the imageview
                        imgview.setPadding(16, 20, 16, 0)
                        imgview.layoutParams = ViewGroup.LayoutParams(525, 680)

                        // Sets on click function for link
                        link.setOnClickListener {
                            val openURL = Intent(Intent.ACTION_VIEW)
                            openURL.data =
                                Uri.parse(sidearmReturn.roster[i].playerinfo.biolink)
                            startActivity(openURL)
                        }

                        // Loads the image using the returned URL
                        Glide.with(this@WSoccerAthletes)
                            // uses photo[0] because the image is always at index zero rather than i
                            .load(sidearmReturn.roster[i].photos[0].roster)
                            .into(imgview)

                        // Changes the returned height to a more common format
                        val splitHeight = sidearmReturn.roster[i].playerinfo.height.split("-")

                        // Appends the applicable information to create the athlete's description
                        title.append(sidearmReturn.roster[i].name)
                        tv.append("Position: " + sidearmReturn.roster[i].playerinfo.pos_short + "\n")
                        tv.append("Jersey : #" + sidearmReturn.roster[i].playerinfo.uni + "\n")
                        tv.append("Year: " + sidearmReturn.roster[i].playerinfo.year_long + "\n")
                        tv.append("Height: " + splitHeight[0] + "'" + splitHeight[1] + "\"" + "\n")
                        tv.append("Hometown: " + sidearmReturn.roster[i].playerinfo.hometown + "\n")

//                        appends the imgview and textview to the linear layout
                        infoLinearLayout.addView(title)
                        infoLinearLayout.addView(tv)

                        pictureLinearLayout.addView(imgview)
                        pictureLinearLayout.addView(infoLinearLayout)

                        // Appends the linear layout to the parent linear layout
                        layout.addView(pictureLinearLayout)
                        layout.addView(link)

                    }
                })
            }

            override fun onFailure(call: Call, e: IOException) {
                val currentContext = context

                // creates a textview for the error
                var error: TextView = TextView(currentContext)

                // Sets the styles of the textview
                error.textSize = 18.0f
                error.setTextColor(Color.parseColor("#000000"))
                error.setPadding(32, 8, 32, 0)

                error.append("There was a connection error. Please try again.")

                layout.addView(error)
            }
        })

    }


    class RosterReturnClass(val roster: List<Roster>)

    class Roster(val name: String, val playerinfo: PlayerInformation, val photos: List<PlayerPhoto>)

    class PlayerInformation(
        val pos_short: String,
        val uni: String,
        val height: String,
        val weight: String,
        val year_long: String,
        val hometown: String,
        val highschool: String,
        val biolink: String
    )

    class PlayerPhoto(val roster: String)

}