@file:Suppress("DEPRECATION")

package uwsp.athletics.ui.track

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TrackPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    //This method sets the tab positions
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                TrackNews()
            }
            1 -> {
                TrackSchedule()
            }
            2 -> {
                TrackAthletes()
            }
            else -> {
                TrackCoaches()
            }
        }
    }

    //This method returns 4 tabs
    override fun getCount(): Int {
        return 4
    }

    //This method sets the tab titles
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "News"
            1 -> "Schedule"
            2 -> "Athletes"
            else -> "Coaches"
        }
    }
}