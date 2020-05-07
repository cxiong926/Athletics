package uwsp.athletics.ui.wtennis

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import okhttp3.*

import uwsp.athletics.R
import java.io.IOException

class WTennisNews : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wtennis_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mbasketlin: LinearLayout = view.findViewById(R.id.wTennisNewsLinear)
        fetchWTennisHomeNews(mbasketlin)

    }

    private fun fetchWTennisHomeNews(layout: LinearLayout) {

        // Hardcoded url for 10 general athletics stories
        val url = "https://athletics.uwsp.edu/services/get_stories.ashx?sport_id=16&num_stories=10"

        //builds the request
        val request = Request.Builder().url(url).build()

        // creates the request object
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {

                // The unparsed return
                val body = response.body?.string()


                //creates the gson builder
                val gson = GsonBuilder().create()

                // Parses the return based on chosen parameters/classes.  Used Array<TestNewsArray> since the first array is unnamed
                val newsReturn =
                    gson.fromJson(body, Array<NewsArray>::class.java)

                // gets the context of the activity
                val currentContext = context

                //references the linear layout on the home news page
                //var lin: LinearLayout = view!!.findViewById(R.id.homeNewsLinear)

                // runOnUiThread required to change the ui after initial creation
                activity?.runOnUiThread(java.lang.Runnable {

                    // for loop that creates a card for every index
                    for (i in newsReturn.indices) {

                        // creates a textview for the headline
                        var headline: TextView = TextView(currentContext)

                        // Sets the styles of the textview
                        headline.textSize = 18.0f
                        headline.setTextColor(Color.parseColor("#000000"))
                        headline.setPadding(32, 8, 32, 0)

                        // creates a textview
                        var date: TextView = TextView(currentContext)

                        // Sets the styles of the textview
                        date.setBackgroundResource(R.drawable.border)
                        date.textSize = 14.0f
                        date.setTextColor(Color.parseColor("#61000000"))
                        date.setPadding(32, 0, 32, 64)


                        //tv.setBackgroundColor(Color.parseColor("#f5f5f5"))

                        //creates an imageview
                        var imgview: ImageView = ImageView(currentContext)

                        // sets the padding of the imageview
                        imgview.setPadding(16, 16, 16, 0)


                        //  Sets an onclicklistener to open link when image is clicked
                        imgview.setOnClickListener {
                            val openURL = Intent(Intent.ACTION_VIEW)
                            openURL.data =
                                Uri.parse("https://athletics.uwsp.edu" + newsReturn[i].story_path)
                            startActivity(openURL)
                        }
                        headline.setOnClickListener {
                            val openURL = Intent(Intent.ACTION_VIEW)
                            openURL.data =
                                Uri.parse("https://athletics.uwsp.edu" + newsReturn[i].story_path)
                            startActivity(openURL)
                        }
                        date.setOnClickListener {
                            val openURL = Intent(Intent.ACTION_VIEW)
                            openURL.data =
                                Uri.parse("https://athletics.uwsp.edu" + newsReturn[i].story_path)
                            startActivity(openURL)
                        }

                        // Loads the image from the returned URL
                        Glide.with(this@WTennisNews)
                            .load("https://athletics.uwsp.edu" + newsReturn[i].image_source)
                            .into(imgview)

                        // appends the headline to the textview
                        headline.append(newsReturn[i].headline)
                        date.append(newsReturn[i].date)

                        //appends the imgview, textview, and date to the linear layout
                        layout.addView(imgview)
                        layout.addView(headline)
                        layout.addView(date)

                    }
                })
            }

            // Runs if the request fails
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

    //Class used to parse the json
    class NewsArray(
        val headline: String,
        val date: String,
        val image_source: String,
        val story_path: String
    )
}
