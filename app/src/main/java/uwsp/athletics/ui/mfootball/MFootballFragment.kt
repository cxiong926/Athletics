package uwsp.athletics.ui.mfootball

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_mfootball.*
import okhttp3.*
import org.w3c.dom.Text
import uwsp.athletics.R
import java.io.IOException

class MFootballFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mfootball, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Adds the viewpager to the fragment
        val mViewPager = view.findViewById(R.id.mFootballViewPager) as ViewPager
        mViewPager.adapter = MFootballPagerAdapter(childFragmentManager)

        //Adds the tab to the fragment
        mFootballTabLayout.setupWithViewPager(mViewPager)
        fetchMFootballScore()
        super.onViewCreated(view, savedInstanceState)

    }

    private fun fetchMFootballScore() {

        // Request URL for most recent game
        val url =
            "https://athletics.uwsp.edu/services/gameday.ashx?type=get-schedule-games&sport_id=8&take=1"

        // builds the request for OKHttp to use
        val request = Request.Builder().url(url).build()

        // creates the request object
        val client = OkHttpClient()

        // Send the request
        client.newCall(request).enqueue(object : Callback {

            // Successful Request
            override fun onResponse(call: Call, response: Response) {

                // The unparsed return
                val body = response.body?.string()

                // Creates the gson builder used for parsing
                val gson = GsonBuilder().create()

                // Parses the return based on chosen parameters/classes.  Used Array<Score> since the first array is unnamed
                val scoreReturn = gson.fromJson(body, Array<Score>::class.java)

                // runOnUiThread required to change the ui after initial creation
                activity?.runOnUiThread(java.lang.Runnable {

                    // 77-126 changes returned date to easier to read format
                    val delimiter = "-"
                    var month: String = ""
                    val returnedDate = scoreReturn[0].date.substringBefore("T")
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

                    // References the date and time textviews
                    val date: TextView? = view?.findViewById(R.id.mFootballDate)

                    // Sets the text for the date and time textviews
                    date?.text = formattedDate
//                    time?.text = scoreReturn[0].time

                    // References both teams' score textviews
                    val homeScore: TextView? = view?.findViewById(R.id.mFootballUWSPScore)
                    val awayScore: TextView? = view?.findViewById(R.id.mFootballOppScore)

                    // Sets text for both teams' scores
                    if(scoreReturn[0].result_team_score.isNullOrBlank()){
                        homeScore?.text = "-"
                        awayScore?.text = "-"
                    }
                    else{
                        homeScore?.text = scoreReturn[0].result_team_score
                        awayScore?.text = scoreReturn[0].result_opponent_score
                    }


                    // References the team name textviews
                    val awayTeam: TextView? = view?.findViewById(R.id.mFootballOppTeam)
                    val homeTeam: TextView? = view?.findViewById(R.id.mFootballUWSPTeam)

                    // Sets text for teams' names
                    homeTeam?.text = getString(R.string.uwsp)
                    awayTeam?.text = scoreReturn[0].opponent

                    // References both teams' imageviews
                    val awayTeamPic: ImageView = view!!.findViewById(R.id.mFootballOppPic)
                    val homeTeamPic: ImageView = view!!.findViewById(R.id.mFootballUWSPPic)

                    // Loads the away team's image from the returned URL
                    Glide.with(this@MFootballFragment)
                        .load("https://athletics.uwsp.edu" + scoreReturn[0].opponent_image)
                        .into(awayTeamPic)

                    // Loads the home team's image from the returned URL
                    Glide.with(this@MFootballFragment)
                        .load("https://athletics.uwsp.edu/images/logos/UW_Stevens_Point.png")
                        .into(homeTeamPic)

                    // References the away/home textview
                    val uwspIndicator: TextView? = view?.findViewById(R.id.mFootballUWSPIndicator)
                    val oppIndicator: TextView? = view?.findViewById(R.id.mFootballOppIndicator)

                    // Sets both teams' away/home textview based on the away team's location indicator from the API return
                    if (scoreReturn[0].location_indicator == "H") {
                        uwspIndicator?.text = getString(R.string.home)
                        oppIndicator?.text = getString(R.string.away)
                    } else {
                        uwspIndicator?.text = getString(R.string.away)
                        oppIndicator?.text = getString(R.string.home)
                    }

                    val recentUpcoming: TextView? = view?.findViewById(R.id.mFootballRecent)

                    if (scoreReturn[0].game_status == "A"){
                        recentUpcoming?.text = "Upcoming"
                    }else{
                        recentUpcoming?.text = "Recent"
                    }



                })
            }

            // Failed Request
            override fun onFailure(call: Call, e: IOException) {
//                Toast.makeText(this, "Hi there! This is a Toast.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Class used to parse the json
    class Score(
        val date: String,
        val time: String,
        val opponent: String,
        val game_status: String,
        val location_indicator: String,
        val opponent_image: String,
        val result_team_score: String,
        val result_opponent_score: String
    )
}



