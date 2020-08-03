package ragalik.baraxolka.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R


class ForgotPasswordFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appCompatActivity = activity as AppCompatActivity?
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar_forgot_pass)
            toolbar_forgot_pass.title = "Восстановление пароля"
            appCompatActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        MainActivity.isActualFragment = false
        MainActivity.invalidateSearchMenu()
        MainActivity.fab.hide()
    }
}