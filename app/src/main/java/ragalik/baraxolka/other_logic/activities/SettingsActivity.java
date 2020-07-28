package ragalik.baraxolka.other_logic.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.r0adkll.slidr.Slidr;

import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        toolbar.setTitle("Настройки");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Slidr.attach(this);

        SwitchCompat nightThemeSwitcher = findViewById(R.id.nightThemeSwitcher);

        if (MainActivity.sp.getString("theme", "").equals("Night")) {
            nightThemeSwitcher.setChecked(true);
        } else {
            nightThemeSwitcher.setChecked(false);
        }

        nightThemeSwitcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = MainActivity.sp.edit();
            if (isChecked) {
                editor.putString("theme", "Night");
            } else {
                editor.putString("theme", "Day");
            }
            editor.apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }
}
