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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import androidx.core.view.GravityCompat;

import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ragalik.baraxolka.paging_feed.search.SearchActivity;
import ragalik.baraxolka.other_logic.entrance.LogInFragment;
import ragalik.baraxolka.other_logic.entrance.SignInFragment;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.AdsCount;
import ragalik.baraxolka.paging_feed.ads.ADS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String SERVER_URL = "http://ketrovsky.iam.by/scripts/";

    private LogInFragment logInFragment;
    public static ADS adsFragment;
    private SignInFragment signInFragment;
    private Dialog myDialog;
    public static SharedPreferences sp;
    private static List<String> listItem;
    public static FloatingActionButton fab;
    private static TextView moderator_count;
    public static Activity activity;
    public static NavigationView navigationView;
    private Toolbar toolbar;
    private int entrance_count = 0;
    public static boolean isActualFragment = false;
    public static DrawerLayout drawer;
    public static boolean isEntranceFromDialog = false;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    public static ImageView navigationPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getPreferences(MODE_PRIVATE);
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        if (sp.getString("theme", "").equals("Night")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        activity = this;

        listItem = new ArrayList<>();
        myDialog = new Dialog(this);
        logInFragment = new LogInFragment();
        adsFragment = new ADS();
        moderator_count = new TextView(this);

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("entrance_counter", sp.getInt("entrance_counter", 0) + 1);
        editor.apply();

        fab = findViewById(R.id.mainActivityFab);
        fab.setOnClickListener(view -> {
            //Navigation.findNavController(this, R.id.fragment).navigate(R.id.adCreatorActivity);
//            Intent myIntent = new Intent(MainActivity.this, AdCreatorActivity.class);
//            startActivity(myIntent);
        });

        navController = Navigation.findNavController(this, R.id.fragment);
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
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(drawer)
                .build();
        navigationView = findViewById(R.id.nav_view);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

        });


        //navigationView.setNavigationItemSelectedListener(this);
        //navigationView.setCheckedItem(R.id.ADS);

        openStartFragment();
    }

    private void openStartFragment () {
        SharedPreferences.Editor editor = sp.edit();
        entrance_count = sp.getInt("entrance_counter", 0);
        if (entrance_count == 1 && !isEntered()) {
            myDialog.setContentView(R.layout.start_login_window);
            myDialog.show();
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            AppCompatButton log = myDialog.findViewById(R.id.logButton);
            AppCompatButton sign = myDialog.findViewById(R.id.signButton);
            this.setTitle("Все объявления");
            myDialog.setOnCancelListener(dialog -> {
                //newTransaction(adsFragment, "Все объявления");
                myDialog.dismiss();
            });
            log.setOnClickListener(v -> {
                isEntranceFromDialog = true;
                myDialog.dismiss();
                //newTransaction(logInFragment, "Вход");
            });
            sign.setOnClickListener(v -> {
                isEntranceFromDialog = true;
                signInFragment = new SignInFragment();
                myDialog.dismiss();
                //newTransaction(signInFragment, "Регистрация");
            });
            hideItemsNavigationDrawer(R.id.MY_ADS, R.id.FAVOURITES);
        } else if (isEntered()) {
            // newTransaction(adsFragment, "Все объявления");
            showItemsNavigationDrawer(R.id.MY_ADS, R.id.FAVOURITES);
        } else if (entrance_count > 1) {
            if (entrance_count == 10) {
                editor.putInt("entrance_counter", 0);
                editor.apply();
            }
            //newTransaction(adsFragment, "Все объявления");
            hideItemsNavigationDrawer(R.id.MY_ADS, R.id.FAVOURITES);
        }
    }

    private boolean isEntered () {
        return (!sp.getString("nickname", "").equals(""));
    }

    public void onClickImage(View v){
        if (!isEntered()) {
            Navigation.findNavController(this, R.id.fragment).navigate(R.id.logIn);
        } else {
            Navigation.findNavController(this, R.id.fragment).navigate(R.id.accountFragment);
        }
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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
        MenuItem searchItem = menu.findItem(R.id.option_search);

        if (isActualFragment) {
            searchItem.setVisible(true);
        } else {
            searchItem.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public static void setNavHeaderText (Activity activity) {
        TextView data = activity.findViewById(R.id.drawer_nickname);
        data.setText(sp.getString("nickname", ""));
    }

//    private void newTransaction (Fragment fragment, String fragmentName) {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//       // fragmentTransaction.setCustomAnimations(R.anim.bottom_to_up, R.anim.exit_to_up);
//        fragmentTransaction.replace(R.id.constrLayout, fragment).commit();
//        this.setTitle(fragmentName);
//    }

    private void readData () {
        TextView nickname = findViewById(R.id.drawer_nickname);
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

        TextView nickname = activity.findViewById(R.id.drawer_nickname);
        nickname.setText("Войти/Регистрация");
        editor.apply();
    }

    @Override
    public void onBackPressed() {

        // var backPressedOnce = false
//        var navController = findNavController(R.id.fragNavHost)
//
//        // Check if the current destination is actually the start sestination (Home screen)
//        if (navController.graph.startDestination == navController.currentDestination?.id)
//        {
//            // Check if back is already pressed. If yes, then exit the app.
//            if (backPressedOnce)
//            {
//                super.onBackPressed()
//                return
//            }
//
//            backPressedOnce = true
//            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()
//
//            Handler().postDelayed(2000) {
//            backPressedOnce = false
//        }
//        }
//    else {
//            super.onBackPressed()
//        }


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        readData();

        navigationPhoto = findViewById(R.id.navigationDrawerPhoto);

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getPrimaryNavigationFragment().getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
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

