package uwsp.athletics.ui.swim

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

class SwimCoaches : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swim_coaches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val lin: LinearLayout = view.findViewById(R.id.swimCoachesLinear)
        fetchswimCoaches(lin)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun fetchswimCoaches(layout: LinearLayout) {

        // Request URL for  the men's baseball roster
        val url = "https://athletics.uwsp.edu/services/coaches_xml.aspx?format=json&path=swim "
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()


        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()
                val sidearmReturn = gson.fromJson(body, CoachReturnClass::class.java)


                // Context set to a variable because "this" doesn't work when creating elements
                val currentContext = context
                activity?.runOnUiThread(java.lang.Runnable {

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
                        imgview.layoutParams = ViewGroup.LayoutParams(400, 518)

                        // Sets on click function for link
                        link.setOnClickListener {
                            val openURL = Intent(Intent.ACTION_VIEW)
                            openURL.data =
                                Uri.parse(sidearmReturn.roster[i].staffinfo.biolink)
                            startActivity(openURL)
                        }

                        // Loads the image using the returned URL
                        Glide.with(this@SwimCoaches)
                            // uses photo[0] because the image is always at index zero rather than i
                            .load(sidearmReturn.roster[i].photos[0].roster)
                            .into(imgview)


                        // Appends the applicable information to create the athlete's description
                        title.append(sidearmReturn.roster[i].name)
                        tv.append(sidearmReturn.roster[i].staffinfo.title + "\n")
                        if (sidearmReturn.roster[i].staffinfo.email.isNotEmpty() || sidearmReturn.roster[i].staffinfo.phone.isNotEmpty()) {
                            tv.append("Contact:\n" + sidearmReturn.roster[i].staffinfo.email + "\n" + sidearmReturn.roster[i].staffinfo.phone)
                        }

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

    class CoachReturnClass(val roster: List<CoachRoster>)

    class CoachRoster(
        val name: String,
        val staffinfo: StaffInformation,
        val photos: List<CoachPhoto>
    )

    class StaffInformation(
        val title: String,
        val email: String,
        val phone: String,
        val biolink: String
    )

    class CoachPhoto(val roster: String)


}