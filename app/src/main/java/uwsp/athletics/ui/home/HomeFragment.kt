package uwsp.athletics.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_home.*
import uwsp.athletics.R


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //sets the home page to fragment_home
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Adds the viewpager to the fragment
        val mViewPager = view.findViewById(R.id.homeViewPager) as ViewPager
        mViewPager.adapter = HomePagerAdapter(childFragmentManager)

        val homeLinear: LinearLayout = view.findViewById(R.id.homeImage)

    // gets the context of the activity
        val currentContext = context
        //creates an imageview
        var imgview: ImageView = ImageView(currentContext)

        // sets the padding of the imageview
        imgview.setPadding(0, 0, 0, 0)
        // Loads the image from the returned URL
        Glide.with(this@HomeFragment)
            .load("https://athletics.uwsp.edu/images/2018/1/26/AthleticsHomePage.png")
            .into(imgview)

        homeLinear.addView(imgview)

        //Adds the tab to the fragment
        tabLayout.setupWithViewPager(mViewPager)



        super.onViewCreated(view, savedInstanceState)


    }

}