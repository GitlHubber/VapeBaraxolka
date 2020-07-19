package ragalik.baraxolka;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.View;
import androidx.core.view.GravityCompat;

import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ragalik.baraxolka.other_logic.account.Account;
import ragalik.baraxolka.other_logic.activities.SettingsActivity;
import ragalik.baraxolka.other_logic.ad_creator.AdCreatorActivity;
import ragalik.baraxolka.paging_feed.administrator.AdministratorActivity;
import ragalik.baraxolka.paging_feed.search.SearchActivity;
import ragalik.baraxolka.other_logic.entrance.LogIn;
import ragalik.baraxolka.other_logic.entrance.SignIn;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.AdsCount;
import ragalik.baraxolka.paging_feed.ads.ADS;
import ragalik.baraxolka.paging_feed.moderator.AdModerator;
import ragalik.baraxolka.paging_feed.favourites.FAVOURITES;

import ragalik.baraxolka.other_logic.main_fragments.TechnicalSUPPORT;
import ragalik.baraxolka.paging_feed.my_ads.MyADS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SERVER_URL = "http://ketrovsky.iam.by/scripts/";

    private LogIn logInFragment;
    public static ADS adsFragment;
    private SignIn signInFragment;
    private Dialog myDialog;
    public static SharedPreferences sp;
    private static List<String> listItem;
    public static FloatingActionButton fab;
    private static TextView moderator_count;
    public static Activity activity;
    public static NavigationView navigationView;
    private Toolbar toolbar;
    private int entrance_count = 0;
    public static Boolean isActualFragment = false;
    public static DrawerLayout drawer;
    private boolean doubleBackToExitPressedOnce;
    public static boolean isEntranceFromDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getPreferences(MODE_PRIVATE);
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        activity = this;

        listItem = new ArrayList<>();
        myDialog = new Dialog(this);
        logInFragment = new LogIn();
        adsFragment = new ADS();
        moderator_count = new TextView(this);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("entrance_counter", sp.getInt("entrance_counter", 0) + 1);
        editor.apply();

        SharedPreferences sharedPreferences = getSharedPreferences("MenuItems", MODE_PRIVATE);
        String resultString = sharedPreferences.getString("Menu", "[]");
        resultString = resultString.substring(1, resultString.length() - 1);
        if (!resultString.isEmpty()) {
            if (resultString.contains(",")) {
                String[] items = resultString.split(",");
                for (String item : items) {
                    listItem.add(item.trim());
                }
            } else {
                listItem.add(resultString);
            }
        }

        fab = findViewById(R.id.fab1);
        fab.setOnClickListener(view -> {
            Intent myIntent = new Intent(MainActivity.this, AdCreatorActivity.class);
            startActivity(myIntent);
        });

        drawer = findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                if(getCurrentFocus() != null)
                {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                updateNotify();
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {}

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {}

            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.ADS);

        entrance_count = sp.getInt("entrance_counter", 0);
        if (entrance_count == 1 && !isEntered()) {
            myDialog.setContentView(R.layout.start_login_window);
            myDialog.show();
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            AppCompatButton log = myDialog.findViewById(R.id.logButton);
            AppCompatButton sign = myDialog.findViewById(R.id.signButton);
            this.setTitle("Все объявления");
            myDialog.setOnCancelListener(dialog -> {
                newTransaction(adsFragment, "Все объявления");
                myDialog.dismiss();
            });
            log.setOnClickListener(v -> {
                isEntranceFromDialog = true;
                myDialog.dismiss();
                newTransaction(logInFragment, "Вход");
            });
            sign.setOnClickListener(v -> {
                isEntranceFromDialog = true;
                signInFragment = new SignIn();
                myDialog.dismiss();
                newTransaction(signInFragment, "Регистрация");
            });
            hideItemsNavigationDrawer(R.id.MY_ADS, R.id.FAVOURITES);
        } else if (isEntered()) {
            newTransaction(adsFragment, "Все объявления");
            showItemsNavigationDrawer(R.id.MY_ADS, R.id.FAVOURITES);
        } else if (entrance_count > 1) {
            if (entrance_count == 10) {
                editor.putInt("entrance_counter", 0);
                editor.apply();
            }
            newTransaction(adsFragment, "Все объявления");
            hideItemsNavigationDrawer(R.id.MY_ADS, R.id.FAVOURITES);
        }
    }

    private boolean isEntered () {
//        if (!sp.getString("nickname", "").equals("") && ) {
//
//        }
        return (!sp.getString("nickname", "").equals(""));
    }

    public void onClickImage(View v){
        if (!isEntered()) {
            newTransaction(logInFragment, "Вход");
        } else {
            Intent intent = new Intent(this, Account.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public static void createUserData(int id, String nickname, String email, String phoneNumber, String region, String town, String statusName) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", id);
        editor.putString("nickname", nickname);
        editor.putString("email", email);
        editor.putString("phoneNumber", phoneNumber);
        editor.putString("region", region);
        editor.putString("town", town);
        editor.putString("status_name", statusName);
        editor.apply();
    }

    public static void invalidateSearchMenu() {
        activity.invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.option_search);
        if (isActualFragment) {
            item.setVisible(true);
        } else {
            item.setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public static void setNavHeaderText (Activity activity) {
        TextView data = activity.findViewById(R.id.nickname);
        data.setText(sp.getString("nickname", ""));
    }

    private void newTransaction (Fragment fragment, String fragmentName) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_up, R.anim.exit_to_up);
        fragmentTransaction.replace(R.id.constrLayout, fragment).commit();
        this.setTitle(fragmentName);
    }

    private void readData () {
        TextView nickname = findViewById(R.id.nickname);
        if (isEntered()) {
            nickname.setText(sp.getString("nickname", ""));
        }
    }

    public static void removeData (Activity activity) {
        SharedPreferences.Editor editor = sp.edit();

        editor.putInt("id", 0);
        editor.putString("nickname", "");
        editor.putString("email", "");
        editor.putString("phoneNumber", "");
        editor.putString("image", "");
        editor.putString("status_name", "");

        TextView nickname = activity.findViewById(R.id.nickname);
        nickname.setText("Войти/Регистрация");
        editor.apply();
    }

    public static void removeSignOut (Activity activity) {
        applyChanges(activity);
    }

    private static void applyChanges (Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("MenuItems", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Menu", listItem.toString());
        editor.apply();
        activity.invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Нажмите 'назад' еще раз чтобы выйти", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        readData();

        ImageView navigationPhoto = this.findViewById(R.id.navigationDrawerPhoto);

        if (!MainActivity.sp.getString("image", "").equals("null") && !MainActivity.sp.getString("image", "").equals("")) {
            String temp = MainActivity.sp.getString("image", "");
            Picasso.get().invalidate(temp);
            Picasso.get().load(temp).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(navigationPhoto);
        } else {
            navigationPhoto.setImageResource(R.drawable.gradient_navigation);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_search:
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
       // Toolbar toolbar = findViewById(R.id.toolbar);

        int id = item.getItemId();

        if (id == R.id.ADS) {
            fragmentTransaction.replace(R.id.constrLayout, new ADS()).commit();
         //   toolbar.setTitle("Объявления");
        } else if (id == R.id.MY_ADS) {
            fragmentTransaction.replace(R.id.constrLayout, new MyADS()).commit();   //Нужно раскомментить при готовых май адс!
            //toolbar.stTitle("Мои объявления");
        } else if (id == R.id.FAVOURITES) {
            fragmentTransaction.replace(R.id.constrLayout, new FAVOURITES()).commit();
           // toolbar.setTitle("Закладки");
        }
//        else if (id == R.id.RULES) {
//            fragmentTransaction.replace(R.id.constrLayout, new RULES()).commit();
//           // toolbar.setTitle("Правила");
//        }
        else if (id == R.id.Technical_SUPPORT) {
            fragmentTransaction.replace(R.id.constrLayout, new TechnicalSUPPORT()).commit();
           // toolbar.setTitle("Техническая поддержка");
        } else if (id == R.id.SETTINGS) {
            Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(myIntent);
        } else if (id == R.id.MODERATOR) {
            fragmentTransaction.replace(R.id.constrLayout, new AdModerator()).commit();
        } else if (id == R.id.ADMIN) {
            //fragmentTransaction.replace(R.id.constrLayout, new Administrator()).commit();
            Intent myIntent = new Intent(MainActivity.this, AdministratorActivity.class);
            startActivity(myIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void checkUserStatus() {
        if (sp.getString("status_name", "").equals("АДМИНИСТРАТОР")) {
            createAdminField();
            createEditorField();
        } else if (sp.getString("status_name", "").equals("РЕДАКТОР")) {
            createEditorField();
        }
    }

    public static void removeGroupFromNV(int id, Activity activity) {
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.removeGroup(id);
    }

    private static void createAdminField() {
        NavigationView navigationView = MainActivity.activity.findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem administrator = menu.findItem(R.id.ADMIN);
        administrator.setVisible(true);
        createEditorField();
    }

    private static void createEditorField() {
        NavigationView navigationView = MainActivity.activity.findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        MenuItem moderator = menu.findItem(R.id.MODERATOR);
        moderator.setVisible(true);
        moderator_count = (TextView) moderator.getActionView();
    }

    public static void hideItemsNavigationDrawer(int... items) {
        Menu menu = navigationView.getMenu();

        for (int item : items) {
            menu.findItem(item).setVisible(false);
        }
    }

    public static void showItemsNavigationDrawer(int... items) {
        Menu menu = navigationView.getMenu();

        for (int item : items) {
            menu.findItem(item).setVisible(true);
        }
    }

    private void updateNotify () {
        Call<AdsCount> call = ApiClient.getApi().getAdsCount(3);
        call.enqueue(new Callback<AdsCount>() {
            @Override
            public void onResponse(Call<AdsCount> call, Response<AdsCount> response) {
                if (response.body() != null) {
                    int count = response.body().getCount();
                    moderator_count.setText("");
                    if (count != 0) {
                        moderator_count.setText(String.valueOf(count));
                    }
                }
            }

            @Override
            public void onFailure(Call<AdsCount> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "Ошибка в рассмотрение", Toast.LENGTH_LONG).show();
            }
        });
    }
}

