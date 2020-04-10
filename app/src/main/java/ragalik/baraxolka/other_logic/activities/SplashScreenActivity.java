package ragalik.baraxolka.other_logic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.R;
import ragalik.baraxolka.other_logic.main_fragments.NoInternetConnection;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SLEEP_TIMER = 2;
    private TextView retry;
    public static LogoLauncher logoLauncher;
    private NoInternetConnection noEnternetConnection;
    public static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        preferences = getPreferences(MODE_PRIVATE);
        if (preferences.getString("theme", "").equals("Night")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        noEnternetConnection = new NoInternetConnection();
        logoLauncher = new LogoLauncher();
        if (checkInternetConnection()) {
            startAnimations(this);
            logoLauncher.start();
        } else {
            Toast.makeText(this, "Проверьте интернет соединение!", Toast.LENGTH_LONG).show();

            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.splashLayout, noEnternetConnection).commit();
        }
      //  getSupportActionBar().hide();
    }

    public boolean checkInternetConnection() {
        boolean connected;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }

    public class LogoLauncher extends Thread {
        public void run() {
            try{
                sleep(800 * SLEEP_TIMER);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public static void startAnimations (Activity activity) {
        ImageView logoImg = activity.findViewById(R.id.logotypeImg);
        TextView vapeText = activity.findViewById(R.id.splashVape);
        TextView baraxolkaText = activity.findViewById(R.id.splashBaraxolka);
        Animation imgAnim = AnimationUtils.loadAnimation(activity, R.anim.fade);
        Animation vapeAnim = AnimationUtils.loadAnimation(activity, R.anim.for_splash_text);
        Animation baraxolkaAnim = AnimationUtils.loadAnimation(activity, R.anim.for_splash_text);
        logoImg.startAnimation(imgAnim);
        vapeText.startAnimation(vapeAnim);
        baraxolkaText.startAnimation(baraxolkaAnim);
    }
}


