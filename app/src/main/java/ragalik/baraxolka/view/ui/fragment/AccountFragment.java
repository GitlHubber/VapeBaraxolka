package ragalik.baraxolka.view.ui.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.apache.http.auth.AUTH;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.network.IApi;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.ServerResponse;
import ragalik.baraxolka.network.entities.User;
import ragalik.baraxolka.utils.AppConstantsKt;
import ragalik.baraxolka.utils.AppDatabaseHelperKt;
import ragalik.baraxolka.utils.FuncsKt;
import ragalik.baraxolka.view.ui.image_menu.AccountImageMenu;
import ragalik.baraxolka.utils.PathUtils;
import ragalik.baraxolka.view.ui.image_menu.FullImageLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AccountFragment extends BaseFragment {

    private static final int PICK_FILE_REQUEST = 1000;

    private IApi apiClient;
    private Uri selectedUri;
    private ProgressDialog pDialog;

    public static ImageView accountPhoto;

    private Toolbar toolbar;
    public static Uri outputFileUri;
    private ViewStub accountViewStub;
    private AppCompatButton signOut;

    private TextView emailTV;
    private TextView phoneNumberTV;
    private TextView accountPNInfo;
    private TextView tw_hide_phone;
    private AppCompatCheckBox hidePhone;
    private LinearLayout hidePhoneLayout;
    private TextView townTV;
    private FloatingActionButton accountFab;

    private LinearLayout pnLayout;
    private LinearLayout regionLayout;
    private LinearLayout reportsLayout;

    public AccountFragment() {
        super(R.layout.fragment_account);
    }

    // private AdView mAdView;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.isActualFragment = false;
        MainActivity.invalidateSearchMenu();
        MainActivity.fab.hide();


//        toolbar = view.findViewById(R.id.toolbarAccount);
//        final AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
//        if (appCompatActivity != null) {
//            appCompatActivity.setSupportActionBar(toolbar);
//            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }
//

        accountViewStub = view.findViewById(R.id.account_view_stub);
        accountViewStub.inflate();

        pnLayout = view.findViewById(R.id.account_pn_layout);
        regionLayout = view.findViewById(R.id.account_region_layout);
        reportsLayout = view.findViewById(R.id.account_reports_layout);
        accountFab = view.findViewById(R.id.account_fab);

        pnLayout.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().
                    addToBackStack(null).replace(R.id.constrLayout, new EditAccountFragment("phone")).commit();
        });

        regionLayout.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().
                    addToBackStack(null).replace(R.id.constrLayout, new EditAccountFragment("region")).commit();
        });

        signOut = view.findViewById(R.id.signOutButtonAccount);
        signOut.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Выход из аккаунта");
            builder.setMessage("Вы действительно хотите выйти из аккаунта?");
            builder.setPositiveButton("Да", (dialog, which) -> {
                dialog.dismiss();
//                MainActivity.removeData(MainActivity.activity);
                //MainActivity.removeGroupFromNV(0, MainActivity.activity);

                AppDatabaseHelperKt.getAUTH().signOut();

                getActivity().getSupportFragmentManager().beginTransaction().
                        addToBackStack(null).replace(R.id.constrLayout, new AdsFragment()).commit();

//                MainActivity.hideItemsNavigationDrawer(R.id.MY_ADS, R.id.FAVOURITES, R.id.MODERATOR, R.id.ADMIN);
//
//                Toast.makeText(appCompatActivity, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
            });
            builder.setNegativeButton("Нет", ( dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        emailTV = view.findViewById(R.id.account_email);
        phoneNumberTV = view.findViewById(R.id.account_phone);
        accountPhoto = view.findViewById(R.id.profilePhoto);

        accountPNInfo = view.findViewById(R.id.tw_account_info);
        tw_hide_phone = view.findViewById(R.id.tw_hide_phone);
        hidePhone = view.findViewById(R.id.cb_showHidePN);
        hidePhoneLayout = view.findViewById(R.id.hide_phone_layout);
        townTV = view.findViewById(R.id.account_town);

        apiClient = ApiClient.getApi();

        getUserInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (AccountImageMenu.fileWithUri == null) {
                    AccountImageMenu.fileWithUri = new File(PathUtils.getPath(getContext(), data.getData()));

                    if (data.getData() != null) {
                        selectedUri = data.getData();
                    }
                } else {
                    selectedUri = outputFileUri.normalizeScheme();
                }

                uploadFile();
            }
        }
    }

    private void uploadFile() {
            String filename = MainActivity.sp.getString("email", "") + ".png"; //user email to set filename

            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Загрузка фото...");
            pDialog.show();

            MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", filename, RequestBody.create(MediaType.parse("image/*"), AccountImageMenu.fileWithUri));

            apiClient.uploadAccountImage(body, RequestBody.create(MediaType.parse("multipart/form-data"), MainActivity.sp.getString("email", ""))).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    pDialog.dismiss();
                    Toast.makeText(getContext(), "Изображение загружено!", Toast.LENGTH_LONG).show();
                    AccountImageMenu.fileWithUri = null;
                    Picasso.get().invalidate(selectedUri);
                    Picasso.get().load(selectedUri).into(accountPhoto);
                    Picasso.get().load(selectedUri).into(MainActivity.navigationPhoto);

                    SharedPreferences.Editor editor = MainActivity.sp.edit();
                    editor.putString("image", MainActivity.SERVER_URL + "upload/" + MainActivity.sp.getString("email", "") + ".png");
                    editor.apply();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    pDialog.dismiss();
                }
            });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AccountImageMenu.takePicture((AppCompatActivity) MainActivity.activity);
                } else {
                    Toast.makeText(getActivity(), "Отказано в доступе", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void startAccountView(User user) {
        AppConstantsKt.APP_ACTIVITY.mToolbar.setTitle(user.getNickname());

//        mAdView = findViewById(R.id.AccountAdView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        if (user.isReportExists() == 1) {
            reportsLayout.setVisibility(View.VISIBLE);
            reportsLayout.setOnClickListener(v -> {
                getActivity().getSupportFragmentManager().beginTransaction().
                        addToBackStack(null).replace(R.id.constrLayout, new MyReportsFragment()).commit();
            });
        }

        String emailStr = user.getEmail();
        String phoneNumberStr = user.getPhoneNumber();

        emailTV.setText(emailStr.substring(0, 1).toUpperCase() + emailStr.substring(1));
        if (phoneNumberStr.equals(emailStr)) {
            phoneNumberTV.setText("Номер телефона не указан");
        } else {
            String resultPN = phoneNumberStr.substring(0, 4) + " (" + phoneNumberStr.substring(4, 6) + ") " + phoneNumberStr.substring(6, 9) + "-"
                    + phoneNumberStr.substring(9, 11) + "-" + phoneNumberStr.substring(11, 13);
            phoneNumberTV.setText(resultPN);


            if (user.isPhoneHide() == 0) {
                tw_hide_phone.setText("Показывать номер телефона");
                hidePhone.setChecked(false);
            } else {
                tw_hide_phone.setText("Скрывать номер телефона");
                hidePhone.setChecked(true);
            }

            accountPNInfo.setText("Ваш номер телефона изменить нельзя");
            pnLayout.setClickable(false);

            hidePhoneLayout.setVisibility(View.VISIBLE);
            hidePhoneLayout.setOnClickListener(v -> {
                int isPhoneHide = 0;
                if (MainActivity.sp.getInt("isPhoneHide", -1) == 0) {
                    isPhoneHide = 1;
                }
                Call<ServerResponse> call = ApiClient.getApi().showHidePhone(isPhoneHide, user.getEmail());
                call.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        SharedPreferences.Editor editor = MainActivity.sp.edit();
                        if (MainActivity.sp.getInt("isPhoneHide", -1) == 1) {
                            tw_hide_phone.setText("Показывать номер телефона");
                            Snackbar.make(v, "Ваш номер виден пользователям", Snackbar.LENGTH_SHORT).show();
                            hidePhone.setChecked(false);
                            editor.putInt("isPhoneHide", 0);
                        } else {
                            tw_hide_phone.setText("Скрывать номер телефона");
                            Snackbar.make(v, "Ваш номер скрыт для пользователей", Snackbar.LENGTH_SHORT).show();
                            hidePhone.setChecked(true);
                            editor.putInt("isPhoneHide", 1);
                        }
                        editor.apply();
                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {}
                });
            });
        }

        String region = user.getRegion();
        String town = user.getTown();
        String townStr;
        if (region.equals("Минск")) {
            townStr = "г. " + region + " " + town + " р-н";
            townTV.setText(townStr);
        } else if (region.equals("0")) {
            townTV.setText("Город не указан");
        } else {
            townStr = region + " г. " + town;
            townTV.setText(townStr);
        }

        accountPhoto.setOnClickListener(v -> {
            if (!MainActivity.sp.getString("image", "").equals("null")) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(MainActivity.sp.getString("image", ""));

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.accountCoordinator, new FullImageLayout(0, arrayList)).addToBackStack("").commit();
            }
        });

        accountFab.setOnClickListener(v -> {
            AccountImageMenu accountImageMenu = new AccountImageMenu();
            accountImageMenu.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);
            accountImageMenu.show(getActivity().getSupportFragmentManager(), "");
        });

        if (!MainActivity.sp.getString("image", "null").equals("null")) {
            String temp = user.getImage();
            Picasso.get().invalidate(temp);
//            Picasso.get().load(temp).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(accountPhoto);
//            Picasso.get().load(temp).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(MainActivity.navigationPhoto);
        } else {
            accountPhoto.setImageResource(R.drawable.gradient_navigation);
            MainActivity.navigationPhoto.setImageResource(R.drawable.gradient_navigation);
        }
    }

    private void getUserInfo () {
        IApi iApi = ApiClient.getApi();
        Call<List<User>> call = iApi.getUserInfo(MainActivity.sp.getInt("id", 0));
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.body().isEmpty()) {
                    SharedPreferences.Editor editor = MainActivity.sp.edit();
                    User user = response.body().get(0);
                    editor.putString("nickname", user.getNickname());
                    editor.putString("email", user.getEmail());
                    editor.putString("phoneNumber", user.getPhoneNumber());
                    editor.putString("region", user.getRegion());
                    editor.putString("town", user.getTown());

                    if (user.isPhoneHide() != null) {
                        editor.putInt("isPhoneHide", user.isPhoneHide());
                    }

                    if (user.getImage() != null) {
                        editor.putString("image", user.getImage());
                    }
                    editor.apply();

                    accountViewStub.setVisibility(View.GONE);
                    accountFab.setVisibility(View.VISIBLE);
                    startAccountView(user);
                } else {
                    Toast.makeText(getContext(), "Ошибка загрузки профиля", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.activity, MainActivity.class);
                    getActivity().finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
