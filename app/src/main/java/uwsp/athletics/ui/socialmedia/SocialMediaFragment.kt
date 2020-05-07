package uwsp.athletics.ui.socialmedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_social_media.*
import uwsp.athletics.R

class SocialMediaFragment : Fragment() {

    private lateinit var socialMediaViewModel: SocialMediaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        socialMediaViewModel =
            ViewModelProviders.of(this).get(SocialMediaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_social_media, container, false)
        socialMediaViewModel.text.observe(this, Observer {
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Adds the viewpager to the fragment
        val mViewPager = view.findViewById(R.id.socialViewPager) as ViewPager
        mViewPager.adapter = SocialPagerAdapter(childFragmentManager)

        //Adds the tab to the fragment
        socialTabLayout.setupWithViewPager(mViewPager)

//        (activity as AppCompatActivity).supportActionBar?.setIcon(R.drawable.sp)

        super.onViewCreated(view, savedInstanceState)
    }
}