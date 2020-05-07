package uwsp.athletics.ui.donate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import uwsp.athletics.R


class DonateFragment : Fragment() {

    private lateinit var donateViewModel: DonateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        donateViewModel =
            ViewModelProviders.of(this).get(DonateViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_donate, container, false)
        donateViewModel.text.observe(this, Observer {
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        Loads the donor website after view created
        val imageView: WebView = getView()!!.findViewById<View>(R.id.donorWebview) as WebView
        imageView.loadUrl("https://athletics.uwsp.edu/sports/2010/10/20/GEN_1020105157.aspx")

        super.onViewCreated(view, savedInstanceState)
    }
}