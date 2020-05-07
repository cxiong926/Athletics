package uwsp.athletics

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
//    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_donate,
                R.id.nav_notifications, R.id.nav_share, R.id.nav_send,
                R.id.nav_mBaseball, R.id.nav_mBasketball, R.id.nav_mFootball,
                R.id.nav_mHockey, R.id.nav_mWrestling, R.id.nav_wBasketball,
                R.id.nav_wGolf, R.id.nav_wHockey, R.id.nav_wSoccer, R.id.nav_wSoftball,
                R.id.nav_wTennis, R.id.nav_wVolleyball, R.id.nav_wWrestling,
                R.id.nav_cross, R.id.nav_swim, R.id.nav_track,
                R.id.nav_social
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    //  Sets an onclicklistener to open streams when icon is clicked

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.openAudioStream -> {

//                These do the same things
                val url = "https://athletics.uwsp.edu/coverage"
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))


//                val openURL = Intent(Intent.ACTION_VIEW)
//                openURL.data =
//                    Uri.parse("https://athletics.uwsp.edu/coverage")
//                startActivity(openURL)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onSupportNavigateUp(): Boolean {

//        val navController = findNavController(R.id.nav_host_fragment)
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    //    function for the checkboxes
    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.checkbox_general -> {
                    if (checked) {
//                        R.id.checkbox_general.jump
                        // Put some meat on the sandwich
                    } else {
                        // Remove the meat

                    }
                }

                R.id.checkbox_mBaseball -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_mCross -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_mFootball -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_mHockey -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_mSwim -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_mTrack -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_mWrestling -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_wBasketball -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_wCross -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_wGolf -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_wHockey -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_wSoccer -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_wSwim -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_wTennis -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_wTrack -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_wVolleyBall -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

                R.id.checkbox_wWrestling -> {
                    if (checked) {
                        // Cheese me
                    } else {
                        // I'm lactose intolerant
                    }
                }

            }
        }
    }
}
