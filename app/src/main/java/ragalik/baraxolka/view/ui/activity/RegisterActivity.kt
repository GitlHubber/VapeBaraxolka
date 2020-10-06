package ragalik.baraxolka.view.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_register.*
import ragalik.baraxolka.R
import ragalik.baraxolka.databinding.ActivityRegisterBinding
import ragalik.baraxolka.utils.initFirebase
import ragalik.baraxolka.utils.replaceFragment
import ragalik.baraxolka.view.ui.fragment.EnterPhoneNumberFragment

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(registerToolbar)
        title = "Ваш телефон"
        replaceFragment(EnterPhoneNumberFragment(), false)
    }
}