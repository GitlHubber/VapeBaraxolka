package ragalik.baraxolka.view.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_administrator.*
import ragalik.baraxolka.R
import ragalik.baraxolka.view.ui.fragment.EditorsFragment
import ragalik.baraxolka.view.ui.fragment.ReportsFragment

class AdministratorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrator)

        activity = this

        setSupportActionBar(administratorToolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        Slidr.attach(this)

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener)
        bottomNavigationView.selectedItemId = R.id.editors_item
    }

    companion object {
        lateinit var activity : AppCompatActivity
    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.editors_item -> selectedFragment = EditorsFragment()
            R.id.report_item -> selectedFragment = ReportsFragment()
        }
        if (selectedFragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment).commit()
        }
        true
    }
}