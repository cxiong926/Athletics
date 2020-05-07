@file:Suppress("DEPRECATION")

package uwsp.athletics.ui.socialmedia

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SocialPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    //This method sets the tab positions
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                Twitter()
            }
            1 -> {
                Facebook()
            }
            else -> {
                Instagram()
            }
        }
    }

    //This method returns 3 tabs
    override fun getCount(): Int {
        return 3
    }

    //This method sets the tab titles
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Twitter"
            1 -> "Facebook"
            else -> "Instagram"
        }
    }
}