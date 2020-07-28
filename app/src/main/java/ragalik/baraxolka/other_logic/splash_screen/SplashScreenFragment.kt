package ragalik.baraxolka.other_logic.splash_screen

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_splash_screen.*
import androidx.navigation.fragment.findNavController

import ragalik.baraxolka.R


class SplashScreenFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    companion object {
        private val SLEEP_TIMER = 2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startAnimations()
        Handler().postDelayed({
            context?.let {
                findNavController().navigate(R.id.action_splashScreenFragment_to_adsFragment)
            }
        }, 2500)
    }


    fun startAnimations() {
        val imgAnim = AnimationUtils.loadAnimation(activity, R.anim.fade)
        val vapeAnim = AnimationUtils.loadAnimation(activity, R.anim.fade)
        val baraxolkaAnim = AnimationUtils.loadAnimation(activity, R.anim.fade)
        logotypeImg.startAnimation(imgAnim)
        splashVape.startAnimation(vapeAnim)
        splashBaraxolka.startAnimation(baraxolkaAnim)
    }
}