package uwsp.athletics.ui.wtennis

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

class WTennisSchedule : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wtennis_schedule, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val lin: LinearLayout = view.findViewById(R.id.wTennisSchedLinear)
        fetchWTennisSchedule(lin)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun fetchWTennisSchedule(layout: LinearLayout) {

        val url =
            "https://athletics.uwsp.edu/services/schedule_xml_2.aspx?format=json&path=wten&take=100"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()


        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()
                val sidearmReturn = gson.fromJson(body, ScheduleReturnClass::class.java)


                // Context set to a variable because "this" doesn't work when creating elements
                val currentContext = context
                activity?.runOnUiThread(java.lang.Runnable {

//                    for loop to create card for each game
                    for (i in sidearmReturn.schedule.indices) {

//                        variables to make the date/time return more readable for users
                        var gameCompleted = false
                        val delimiter = "-"
                        var month: String = ""
                        val returnedDate =
                            sidearmReturn.schedule[i].datetime_utc.substringBefore("T")
                        val splitReturnedDate = returnedDate.split(delimiter)

                        // Determines the month base on the number from the return
                        when {
                            splitReturnedDate[1] == "01" -> {
                                month = "January "
                            }
                            splitReturnedDate[1] == "02" -> {
                                month = "February "
                            }
                            splitReturnedDate[1] == "03" -> {
                                month = "March "
                            }
                            splitReturnedDate[1] == "04" -> {
                                month = "April "
                            }
                            splitReturnedDate[1] == "05" -> {
                                month = "May "
                            }
                            splitReturnedDate[1] == "06" -> {
                                month = "June "
                            }
                            splitReturnedDate[1] == "07" -> {
                                month = "July "
                            }
                            splitReturnedDate[1] == "08" -> {
                                month = "August "
                            }
                            splitReturnedDate[1] == "09" -> {
                                month = "September "
                            }
                            splitReturnedDate[1] == "10" -> {
                                month = "October "
                            }
                            splitReturnedDate[1] == "11" -> {
                                month = "November "
                            }
                            splitReturnedDate[1] == "12" -> {
                                month = "December "
                            }
                        }

                        // Removes a zero from the day if it's the first digit (ex: 01, 02, etc) and sets it to val day
                        val day = splitReturnedDate[2].substringAfter("0")

                        // Concatenates the formatted date
                        val formattedDate = month + day + ", " + splitReturnedDate[0]


                        //creates a horizontal linear layout for the logo/score on one side and the date, opponent name, and location on the other
                        val infoLinearLayout: LinearLayout = LinearLayout(currentContext)
                        infoLinearLayout.setPadding(0, 0, 0, 0)

                        // creates a horizontal linear layout for the box score and recap links
                        val linksLinearLayout: LinearLayout = LinearLayout(currentContext)
                        linksLinearLayout.setPadding(0, 0, 0, 0)
                        linksLinearLayout.gravity = Gravity.CENTER
                        linksLinearLayout.setBackgroundResource(R.drawable.border)

                        // creates a vertical linear layout for the game details
                        val gameDetailsLinearLayout: LinearLayout = LinearLayout(currentContext)
                        gameDetailsLinearLayout.orientation = LinearLayout.VERTICAL
                        gameDetailsLinearLayout.setPadding(16, 0, 0, 0)

//                        creates a vertical linear layout for the logo and score
                        val logoScoreLinearLayout: LinearLayout = LinearLayout(currentContext)
                        logoScoreLinearLayout.orientation = LinearLayout.VERTICAL
                        logoScoreLinearLayout.minimumWidth = 400

                        // creates the different textviews for the information to be displayed
                        val gameDate: TextView = TextView(currentContext)
                        val opponentName: TextView = TextView(currentContext)
                        val location: TextView = TextView(currentContext)
                        val scoreAndStatus: TextView = TextView(currentContext)


                        // Sets the styles of the different textviews
                        gameDate.textSize = 12.0f
//                        gameDate.setTextColor(Color.parseColor("#000000"))
                        gameDate.setPadding(24, 16, 0, 8)

                        opponentName.textSize = 24.0f
                        opponentName.setTextColor(Color.parseColor("#000000"))
                        opponentName.setTypeface(null, Typeface.BOLD)
                        opponentName.setPadding(24, 16, 0, 0)

                        scoreAndStatus.textSize = 18.0f
                        scoreAndStatus.setTextColor(Color.parseColor("#000000"))
                        scoreAndStatus.setPadding(24, 0, 0, 0)

                        location.textSize = 14.0f
                        location.setTextColor(Color.parseColor("#000000"))
                        location.setPadding(24, 0, 0, 16)

                        // Checks if status or prescore_info is not empty.  If not empty, assumed that the game was completed.
                        // Adds the boxscore and recap links for the completed game.
                        // If either is empty, assumed that the game has not been completed.  Skips adding the two links.
                        // There was no way to test if this worked for future games i.e. skipping building these links.
                        // The Great Quarantine of 2020 cancelled/postponed the rest of the season for every sport,
                        // so I couldn't test it.
                        // I assumed that status and prescore_info would be empty if the game hasn't been played yet.
                        if (sidearmReturn.schedule[i].results.isNotEmpty()) {


                            val recap: TextView = TextView(currentContext)
                            gameCompleted = true


                            // Sets the styles of the textview
                            recap.textSize = 14.0f
                            recap.setTextColor(Color.parseColor("#1a0dab"))
                            recap.setPadding(32, 0, 0, 20)
                            recap.append("\u25BA View Recap")

                            // sets the onclicklistener for the game recap
                            recap.setOnClickListener {
                                val openURL = Intent(Intent.ACTION_VIEW)
                                openURL.data =
                                    Uri.parse(sidearmReturn.schedule[i].links.postgame.url)
                                startActivity(openURL)
                            }

                            //  appends the link textviews to their appropriate linear layout

                            linksLinearLayout.addView(recap)

                            // checks the result status.  If != to W or L, assumed that prescore_info will have the appropriate info
                            if (sidearmReturn.schedule[i].results[0].status == "W" || sidearmReturn.schedule[i].results[0].status == "L") {
                                scoreAndStatus.append("Result: " + sidearmReturn.schedule[i].results[0].status + ", " + sidearmReturn.schedule[i].results[0].team_score + " - " + sidearmReturn.schedule[i].results[0].opponent_score)
                            } else {
                                scoreAndStatus.append("Result: " + sidearmReturn.schedule[i].results[0].prescore_info)
                            }
                        } else {
                            infoLinearLayout.setBackgroundResource(R.drawable.border)
                        }


                        //creates an imageview for the logo
                        val imgview: ImageView = ImageView(currentContext)

                        // sets the padding of the imageview
                        imgview.setPadding(16, 20, 16, 0)
                        imgview.layoutParams = ViewGroup.LayoutParams(400, 400)


                        // appends the date, opponent, and location to the appropriate textviews.  Also adds the "place" icon for the location
                        gameDate.append(formattedDate + " @ " + sidearmReturn.schedule[i].time)
                        opponentName.append(sidearmReturn.schedule[i].opponent.name)
                        location.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_place,
                            0,
                            0,
                            0
                        )
                        location.append(sidearmReturn.schedule[i].location.location)


                        // Loads the image from the returned URL. The url is edited for an image with 200 width instead of the default 100
                        Glide.with(this@WTennisSchedule)
                            .load(sidearmReturn.schedule[i].opponent.logo_image.substringBefore("width") + "width=200")
                            .into(imgview)

                        // appends all the textviews to their appropriate linear layouts
                        // 221-233 are all to create the desired format using a combination of horizontal/vertical linear layouts
                        // and textviews
                        gameDetailsLinearLayout.addView(gameDate)
                        gameDetailsLinearLayout.addView(opponentName)
                        if (gameCompleted) {
                            gameDetailsLinearLayout.addView(scoreAndStatus)
                        }
                        gameDetailsLinearLayout.addView(location)
                        logoScoreLinearLayout.addView(imgview)
//                        logoScoreLinearLayout.addView(scoreAndStatus)

                        //  appends the previous linear layouts to a parent linear layout
                        infoLinearLayout.addView(logoScoreLinearLayout)
                        infoLinearLayout.addView(gameDetailsLinearLayout)

                        // appends the two linear layouts to the main parent linear layout
                        layout.addView(infoLinearLayout)
                        layout.addView(linksLinearLayout)

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

    //    , val record: Array<TeamRecord>
    class ScheduleReturnClass(val schedule: List<TeamSchedule>, val record: TeamRecord)

    class TeamSchedule(
        val datetime_utc: String,
        val time: String,
        val location: LocationInfo,
        val links: Links,
        val opponent: Opponent,
        val sponsor: String,
        val results: List<GameResult>
    )


    class LocationInfo(val location: String)

    class Links(val boxscore: ScoreLink, val postgame: PostGameLink)

    class ScoreLink(val url: String)
    class PostGameLink(val url: String)

    class Opponent(val name: String, val logo_image: String, val opponent_website: String)

    class GameResult(
        val team_score: String,
        val opponent_score: String,
        val status: String,
        val prescore_info: String
    )


    class TeamRecord(
        val overall_percentage: String
    )
}