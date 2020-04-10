package ragalik.baraxolka.other_logic.ad_creator;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ragalik.baraxolka.R;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.ServerResponse;
import ragalik.baraxolka.other_logic.account.PathUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdCreator extends AppCompatActivity implements View.OnClickListener {

    private static final long MAX_SIZE_PHOTO = 3145728;

    private static TextInputLayout titleEditText;
    private Spinner categorySpinner;
    private Spinner subcategorySpinner;
    private static TextInputLayout descriptionEditText;
    private static TextInputLayout priceEditText;
    private AppCompatButton addAdButton;
    private String categoryFromSpinner;
    private static String subcategoryFromSpinner;
    public static ArrayList<ImageView> adImages;
    private int choosedIndex = 0;
    public static int imageUploadCount;
    public static ArrayList<File> files;
    private ArrayAdapter<CharSequence> adapterCategory;
    private ArrayAdapter<CharSequence> adapterSubcategory;
    private AppCompatRadioButton newState;
    private AppCompatRadioButton secondaryState;
    public static TextView uploadAmountTW;
    public static AppCompatTextView mainLabel;
    public static String uploadedCounterStr;
    public static AppCompatActivity activity;
    public static ArrayList<String> uris;
    static Uri selectedImageUri;
    private boolean state;
    public static Uri outputFileUri;
    public static AppCompatActivity appCompatActivity;

    public static File fileWithUri;

    private ProgressDialog pDialog;

    private static ArrayList<MultipartBody.Part> parts = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_ad_creator);
        appCompatActivity = this;

        Toolbar toolbar = findViewById(R.id.adCreatorToolbar);
        toolbar.setTitle("Добавить объявление");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subcategorySpinner = findViewById(R.id.subcategorySpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        mainLabel = findViewById(R.id.ad_creator_main_label);

        activity = this;
        uploadAmountTW = findViewById(R.id.uploadPhotoCounterAdCreator);
        uploadedCounterStr = "Загружено 0 из 5";

        adImages = new ArrayList<>();
        files = new ArrayList<>();
        uris = new ArrayList<>();

        imageUploadCount = 0;

        MultipartBody.Part image1 = null;
        MultipartBody.Part image2 = null;
        MultipartBody.Part image3 = null;
        MultipartBody.Part image4 = null;
        MultipartBody.Part image5 = null;
        parts.add(image1);
        parts.add(image2);
        parts.add(image3);
        parts.add(image4);
        parts.add(image5);

        adImages.add((ImageView) findViewById(R.id.adPhoto1));
        adImages.add((ImageView) findViewById(R.id.adPhoto2));
        adImages.add((ImageView) findViewById(R.id.adPhoto3));
        adImages.add((ImageView) findViewById(R.id.adPhoto4));
        adImages.add((ImageView) findViewById(R.id.adPhoto5));

        adImages.get(0).setBackground(getResources().getDrawable(R.drawable.ad_creator_main_photo_dark_theme));
        adImages.get(0).setImageDrawable(getResources().getDrawable(R.drawable.ic_circle_add));
        adImages.get(0).setOnClickListener(this);
        adImages.get(1).setOnClickListener(this);
        adImages.get(2).setOnClickListener(this);
        adImages.get(3).setOnClickListener(this);
        adImages.get(4).setOnClickListener(this);

        titleEditText = findViewById(R.id.titleEditText);

        adapterCategory = ArrayAdapter.createFromResource(this, R.array.Spinner_category_items, R.layout.text_color);
        adapterCategory.setDropDownViewResource(R.layout.dropdown_text_color);
        categorySpinner.setAdapter(adapterCategory);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                switch (text) {
                    case ("Не выбрано") : adapterSubcategory = ArrayAdapter.createFromResource(view.getContext(), R.array.NothingSelected, R.layout.text_color);
                        break;
                    case ("Vape") : adapterSubcategory = ArrayAdapter.createFromResource(view.getContext(), R.array.Spinner_vape_items, R.layout.text_color);
                        break;
                    case ("Кальяны") : adapterSubcategory = ArrayAdapter.createFromResource(view.getContext(), R.array.Spinner_kalyan_items, R.layout.text_color);
                        break;
                    case ("Vape комплектующие") : adapterSubcategory = ArrayAdapter.createFromResource(view.getContext(), R.array.Spinner_vape_components_items, R.layout.text_color);
                        break;
                    default : break;
                }

                categoryFromSpinner = text;
                adapterSubcategory.setDropDownViewResource(R.layout.dropdown_text_color);
                subcategorySpinner.setAdapter(adapterSubcategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        subcategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Не выбрано")) {
                    subcategoryFromSpinner = "";
                } else {
                    subcategoryFromSpinner = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        newState = findViewById(R.id.newState);
        secondaryState = findViewById(R.id.secondaryState);
        newState.setOnClickListener(this);
        secondaryState.setOnClickListener(this);

        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);

        addAdButton = findViewById(R.id.addAdButton);
        addAdButton.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                if (fileWithUri == null) {
                    fileWithUri = new File(PathUtils.getPath(this, data.getData()));

                    if (data.getData() != null) {
                        selectedImageUri = data.getData();
                    }
                } else {
                    selectedImageUri = outputFileUri;
                }

                ImageView imageView = new ImageView(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.ad_creator_image_style));

                //getResizeBitmap(selectedImageUri);
                addImage();
            }
        }
    }

    private void addImage() {
        if (fileWithUri.length() < MAX_SIZE_PHOTO) {
            adImages.get(choosedIndex).setImageURI(selectedImageUri);

            if (choosedIndex == imageUploadCount) {
                ++imageUploadCount;
            }
            uris.add(choosedIndex, selectedImageUri.toString());
            files.add(choosedIndex, fileWithUri);

            if (imageUploadCount < 5) {
                adImages.get(imageUploadCount).setBackground(getResources().getDrawable(R.drawable.ad_creator_image_style));
                adImages.get(imageUploadCount).setImageDrawable(getResources().getDrawable(R.drawable.ic_circle_add));
                if (imageUploadCount > 1) {
                    adImages.get(imageUploadCount - 1).setBackground(getResources().getDrawable(R.drawable.ad_creator_secondary_photo));
                }
            } else if (imageUploadCount == 5) {
                adImages.get(imageUploadCount - 1).setBackground(getResources().getDrawable(R.drawable.ad_creator_secondary_photo));
            }

            uploadedCounterStr = uploadedCounterStr.substring(0, 10) + imageUploadCount + uploadedCounterStr.substring(11, 16);
            uploadAmountTW.setText(uploadedCounterStr);

            if (imageUploadCount == 1) {
                mainLabel.setVisibility(View.VISIBLE);
            }

            fileWithUri = null;
        } else {
            Toast.makeText(activity, "Размер фото превышает 3 мб.", Toast.LENGTH_SHORT).show();
            fileWithUri = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(activity, "Отказано в доступе", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onClick(View v) {
        boolean flag = false;
        switch (v.getId()) {
            case R.id.newState :
                state = true;
                newState.setChecked(true);
                secondaryState.setChecked(false);
                break;
            case R.id.secondaryState :
                state = false;
                secondaryState.setChecked(true);
                newState.setChecked(false);
                break;
            case R.id.adPhoto1 :
                choosedIndex = 0;
                flag = true;
                break;
            case R.id.adPhoto2 :
                if (imageUploadCount >= 1) {
                    choosedIndex = 1;
                    flag = true;
                }
                break;
            case R.id.adPhoto3 :
                if (imageUploadCount >= 2) {
                    choosedIndex = 2;
                    flag = true;
                }
                break;
            case R.id.adPhoto4 :
                if (imageUploadCount >= 3) {
                    choosedIndex = 3;
                    flag = true;
                }
                break;
            case R.id.adPhoto5 :
                if (imageUploadCount >= 4) {
                    choosedIndex = 4;
                    flag = true;
                }
                break;
            case R.id.addAdButton:
                insertAd();
                if(this != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                break;
        }

        if (flag) {
            AdCreatorImageMenu adCreatorImageMenu = new AdCreatorImageMenu(imageUploadCount, choosedIndex, uris);
            adCreatorImageMenu.show(getSupportFragmentManager(), "");
        }
    }

    private void insertAd () {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Создание объявления");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("ru"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+3"));

        String title = titleEditText.getEditText().getText().toString();
        String description = descriptionEditText.getEditText().getText().toString();


        for (int i = 0; i < uris.size(); ++i) {
            parts.set(i, MultipartBody.Part.createFormData("image" + (i + 1), "-" + (i + 1) + ".png", RequestBody.create(MediaType.parse("image/*"), files.get(i))));
        }

        int price = Integer.parseInt(priceEditText.getEditText().getText().toString());
        String nickname = MainActivity.sp.getString("nickname", "");
        String datetime = dateFormat.format(new Date());
        String subcategory = subcategoryFromSpinner;


        ApiClient.getApi().insertAd(RequestBody.create(MediaType.parse("multipart/form-data"), title),
                RequestBody.create(MediaType.parse("multipart/form-data"), description),
                parts.get(0),
                parts.get(1),
                parts.get(2),
                parts.get(3),
                parts.get(4),
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(price)),
                RequestBody.create(MediaType.parse("multipart/form-data"), nickname),
                RequestBody.create(MediaType.parse("multipart/form-data"), datetime),
                RequestBody.create(MediaType.parse("multipart/form-data"), subcategory)).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.body() != null) {
                    Intent myIntent = new Intent(appCompatActivity, MainActivity.class);
                    appCompatActivity.startActivity(myIntent);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }
}




