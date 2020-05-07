@file:Suppress("DEPRECATION")

package uwsp.athletics.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    //This method sets the tab positions
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeNewsFragment()
            }
            else -> {
                HomeTweetsFragment()
            }
        }
    }

    //This method returns 3 tabs
    override fun getCount(): Int {
        return 2
    }

    //This method sets the tab titles
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "News"
            else -> "Tweets"
        }
    }

}