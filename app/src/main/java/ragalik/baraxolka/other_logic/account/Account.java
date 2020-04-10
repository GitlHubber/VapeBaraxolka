package ragalik.baraxolka.other_logic.account;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.network.IApi;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.User;
import ragalik.baraxolka.network.entities.UserResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Account extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1000;

    private IApi apiClient;
    private Uri selectedUri;
    private ProgressDialog pDialog;

    public static ImageView accountPhoto;
    public static ImageView navigationPhoto;
    public static AppCompatActivity activity;

    public static Uri outputFileUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_ad_loader);
        apiClient = ApiClient.getApi();
        activity = this;

        getUserInfo();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_FILE_REQUEST) {
                if (AccountImageMenu.fileWithUri == null) {
                    AccountImageMenu.fileWithUri = new File(PathUtils.getPath(this, data.getData()));

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

            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Загрузка фото...");
            pDialog.show();

            MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", filename, RequestBody.create(MediaType.parse("image/*"), AccountImageMenu.fileWithUri));

            apiClient.uploadAccountImage(body, RequestBody.create(MediaType.parse("multipart/form-data"), MainActivity.sp.getString("email", ""))).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    pDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Изображение загружено!", Toast.LENGTH_LONG).show();
                    AccountImageMenu.fileWithUri = null;
                    Picasso.get().invalidate(selectedUri);
                    Picasso.get().load(selectedUri).into(accountPhoto);
                    Picasso.get().load(selectedUri).into(navigationPhoto);

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
                    AccountImageMenu.takePicture(activity);
                } else {
                    Toast.makeText(activity, "Отказано в доступе", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public static void startAccountView() {
        activity.setContentView(R.layout.activity_account);
        Toolbar toolbar = activity.findViewById(R.id.toolbarAccount);
        final AppCompatActivity appCompatActivity = activity;
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbar.setTitle(MainActivity.sp.getString("nickname", ""));
        }

        navigationPhoto = MainActivity.activity.findViewById(R.id.navigationDrawerPhoto);

        Button signOut = activity.findViewById(R.id.signOutButtonAccount);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (this != null) {
                    MainActivity.removeData(MainActivity.activity);
                    MainActivity.removeGroupFromNV(0, MainActivity.activity);
                    MainActivity.removeSignOut(MainActivity.activity);

                    Intent myIntent = new Intent(activity, MainActivity.class);
                    v.getContext().startActivity(myIntent);

                    MainActivity.hideItemsNavigationDrawer(R.id.MY_ADS, R.id.FAVOURITES);

                    Toast.makeText(appCompatActivity, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
                }

            }
        });

        String emailStr = MainActivity.sp.getString("email", "");
        String phoneNumberStr = MainActivity.sp.getString("phoneNumber", "");
        TextView emailTV = activity.findViewById(R.id.account_email);
        TextView phoneNumberTV = activity.findViewById(R.id.account_phone);
        accountPhoto = activity.findViewById(R.id.profilePhoto);
        emailTV.setText(emailStr);
        if (phoneNumberStr.equals("0")) {
            phoneNumberTV.setText("Номер телефона не указан!");
        } else {
            phoneNumberTV.setText(phoneNumberStr);
        }

        TextView townTV = activity.findViewById(R.id.account_town);
        String townStr = MainActivity.sp.getString("region", "") + " | г. " + MainActivity.sp.getString("town", "");
        if (MainActivity.sp.getString("region", "").equals("0")) {
            townTV.setText("Город не указан!");
        } else {
            townTV.setText(townStr);
        }

        accountPhoto.setOnClickListener(v -> {
            AccountImageMenu accountImageMenu = new AccountImageMenu();
            accountImageMenu.show(activity.getSupportFragmentManager(), "");
        });

        if (!MainActivity.sp.getString("image", "").equals("null")) {
            String temp = MainActivity.sp.getString("image", "");
            Picasso.get().invalidate(temp);
            Picasso.get().load(temp).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(accountPhoto);
            Picasso.get().load(temp).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(navigationPhoto);
        } else {
            accountPhoto.setImageResource(R.drawable.gradient_navigation);
            navigationPhoto.setImageResource(R.drawable.gradient_navigation);
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
                    editor.putString("image", user.getImage());
                    editor.apply();
                    startAccountView();
                } else {
                    Toast.makeText(getApplicationContext(), "Ошибка загрузки профиля", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Account.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
