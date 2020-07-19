package ragalik.baraxolka.paging_feed.administrator

import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_administrator.*
import ragalik.baraxolka.R
import ragalik.baraxolka.paging_feed.administrator.editors.EditorsFragment
import ragalik.baraxolka.paging_feed.administrator.reports.ReportsFragment

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