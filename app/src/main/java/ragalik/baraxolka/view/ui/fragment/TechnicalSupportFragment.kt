package ragalik.baraxolka.view.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R
import ragalik.baraxolka.utils.APP_ACTIVITY


class TechnicalSupportFragment : BaseFragment(R.layout.fragment_technical_support) {

    private lateinit var link: TextView

    //private AdView mAdView;
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.fab.hide()
        MainActivity.isActualFragment = false
        MainActivity.invalidateSearchMenu()

//        Toolbar toolbar = view.findViewById(R.id.supportToolbar);
//        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
//        if (appCompatActivity != null) {
//            appCompatActivity.setSupportActionBar(toolbar);
//            toolbar.setTitle("Техническая поддержка");
//        }
        APP_ACTIVITY.mToolbar.title = "Техническая поддержка"

//        mAdView = getActivity().findViewById(R.id.SupportAdView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        
        link = view.findViewById(R.id.VKGroupLink)
        link.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/v_baraxolka"))
            startActivity(intent)
        }
    }
}