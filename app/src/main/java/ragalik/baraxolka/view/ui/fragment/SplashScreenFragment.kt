package ragalik.baraxolka.view.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_splash_screen.*
import ragalik.baraxolka.R


class SplashScreenFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        startAnimations()
        Handler().postDelayed({
            context?.let {
                val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
                fragmentTransaction?.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right)
                fragmentTransaction?.replace(R.id.constrLayout, AdsFragment())?.commit()
                activity?.title = "Объявления"
                activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }, 2000)
    }


    private fun startAnimations() {
        val imgAnim = AnimationUtils.loadAnimation(activity, R.anim.up_to_bottom)
        val vapeAnim = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_up)
        val baraxolkaAnim = AnimationUtils.loadAnimation(activity, R.anim.bottom_to_up)
        logotypeImg.startAnimation(imgAnim)
        splashVape.startAnimation(vapeAnim)
        splashBaraxolka.startAnimation(baraxolkaAnim)
    }
}