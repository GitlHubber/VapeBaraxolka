package ragalik.baraxolka.view.ui.activity

import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import com.r0adkll.slidr.Slidr
import ragalik.baraxolka.MainActivity
import ragalik.baraxolka.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_settings)
        toolbar.title = "Настройки"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Slidr.attach(this)

        val nightThemeSwitcher = findViewById<SwitchCompat>(R.id.nightThemeSwitcher)
        nightThemeSwitcher.isChecked = MainActivity.sp.getString("theme", "") == "Night"
        nightThemeSwitcher.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            val editor = MainActivity.sp.edit()
            if (isChecked) {
                editor.putString("theme", "Night")
            } else {
                editor.putString("theme", "Day")
            }
            editor.apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}