package ragalik.baraxolka.view.ui.fragment

import androidx.fragment.app.Fragment


open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        //APP_ACTIVITY.mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()
        //APP_ACTIVITY.mAppDrawer.enableDrawer()
    }

}