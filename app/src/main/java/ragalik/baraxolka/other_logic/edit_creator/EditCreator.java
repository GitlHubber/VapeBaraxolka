package ragalik.baraxolka.other_logic.edit_creator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.R;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.CategoryResponse;
import ragalik.baraxolka.network.entities.ServerResponse;
import ragalik.baraxolka.network.entities.Subcategories;
import ragalik.baraxolka.other_logic.account.PathUtils;
import ragalik.baraxolka.network.entities.Ad;
import ragalik.baraxolka.network.entities.FullAdResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCreator extends AppCompatActivity implements View.OnClickListener {

    private static final long MAX_SIZE_PHOTO = 3145728;

    private TextInputLayout titleEditText;
    private AppCompatAutoCompleteTextView categorySpinner;
    private AppCompatAutoCompleteTextView subcategorySpinner;
    private TextInputLayout descriptionEditText;
    private TextInputLayout priceEditText;
    private AppCompatButton addAdButton;
    private String categoryFromSpinner = "";
    private String subcategoryFromSpinner = "";
    public static ArrayList<ImageView> adImages;
    private static int choosedIndex = 0;
    public static int imageUploadCount;
    public static ArrayList<File> files;
    private TextInputLayout subcategoryLayout;
    private TextInputLayout categoryLayout;
    //private AppCompatRadioButton newState;
    //private AppCompatRadioButton secondaryState;
    //private String goodsState;
    // private boolean state;
    public static TextView uploadAmountTW;
    public static AppCompatTextView mainLabel;
    public static String uploadedCounterStr;
    public static AppCompatActivity activity;
    public static ArrayList<String> uris;
    static Uri selectedImageUri;
    public static Uri outputFileUri;
    public static AppCompatActivity appCompatActivity;

    public static File fileWithUri;
    private ProgressDialog pDialog;
    private static ArrayList<MultipartBody.Part> parts = new ArrayList<>();
    public int adId;

    ArrayAdapter<String> adapterSubcategory;
    ArrayAdapter<String> categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_creator);
        appCompatActivity = this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            adId = (Integer)bundle.get("adId");
        }

        Toolbar toolbar = findViewById(R.id.editCreatorToolbar);
        toolbar.setTitle("Изменение объявления");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        subcategorySpinner = findViewById(R.id.subcategorySpinner);
        categorySpinner = findViewById(R.id.categorySpinner);
        mainLabel = findViewById(R.id.edit_creator_main_label);
        subcategoryLayout = findViewById(R.id.subcategoryLayout);
        categoryLayout = findViewById(R.id.categoryLayout);

        activity = this;
        uploadAmountTW = findViewById(R.id.uploadPhotoCounterEditCreator);
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

//        newState = findViewById(R.id.newState);
//        secondaryState = findViewById(R.id.secondaryState);
//        newState.setOnClickListener(this);
//        secondaryState.setOnClickListener(this);

        descriptionEditText = findViewById(R.id.descriptionEditText);
        priceEditText = findViewById(R.id.priceEditText);

        addAdButton = findViewById(R.id.addAdButton);
        addAdButton.setOnClickListener(this);

        getFullAdInfo(adId);

    }

    private void getFullAdInfo (int adId) {
        int user_id = MainActivity.sp.getInt("id", 0);

        String phoneIdentificator = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);


        Call<FullAdResponse> call = ApiClient.getApi().getFullAd(adId, user_id, phoneIdentificator);
        call.enqueue(new Callback<FullAdResponse>() {
            @Override
            public void onResponse(Call<FullAdResponse> call, Response<FullAdResponse> response) {
                Ad ad = response.body().getAd().get(0);
                ArrayList<String> urls = new ArrayList<>();

                titleEditText.getEditText().setText(ad.getTitle());
                descriptionEditText.getEditText().setText(ad.getDescription());
                priceEditText.getEditText().setText(String.valueOf(ad.getPrice()));

                if (!ad.getImage1url().equals("null")){
                    selectedImageUri = Uri.parse(ad.getImage1url());
                    urls.add(ad.getImage1url());
                }

                if (!ad.getImage2url().equals("null")){
                    selectedImageUri = Uri.parse(ad.getImage2url());
                    urls.add(ad.getImage2url());
                }

                if (!ad.getImage3url().equals("null")){
                    selectedImageUri = Uri.parse(ad.getImage3url());
                    urls.add(ad.getImage3url());
                }

                if (!ad.getImage4url().equals("null")){
                    selectedImageUri = Uri.parse(ad.getImage4url());
                    urls.add(ad.getImage4url());
                }

                if (!ad.getImage5url().equals("null")){
                    selectedImageUri = Uri.parse(ad.getImage5url());
                    urls.add(ad.getImage5url());
                }

                addImageFromInternet(urls);

                createCategorySpinner(ad.getCategoryName(), ad.getSubcategoryName());
            }

            @Override
            public void onFailure(Call<FullAdResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });
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

                addImage();
            }
        }
    }

    private void createCategorySpinner(String category, String subcategory) {

        ApiClient.getApi().getCategoriesWithSubcategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {

                HashMap<String, ArrayList<Subcategories>> subcategoriesHashMap = new HashMap<>();
                ArrayList<String> categories = new ArrayList<>();
                categories.add("Не выбрано");

                ArrayList<String> subcategories = new ArrayList<>();
                subcategories.add("Не выбрано");

                for (int i = 0; i < response.body().getCategories().size(); ++i) {
                    categories.add(response.body().getCategories().get(i).getCategory_name());
                    subcategoriesHashMap.put(response.body().getCategories().get(i).getCategory_name(), response.body().getCategories().get(i).getSubcategories());
                }

                categoryAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_text_color, categories);
                categorySpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_text_color, categories));
                categorySpinner.setOnClickListener(v -> categorySpinner.showDropDown());
                categorySpinner.setText(category, false);
                subcategoryLayout.setVisibility(View.VISIBLE);
                categorySpinner.setOnItemClickListener((parent, view, position, id) -> {

                    if (subcategoriesHashMap.get(parent.getItemAtPosition(position).toString()) != null) {
                        if (position != 0) {
                            subcategoryLayout.setVisibility(View.VISIBLE);
                            subcategories.clear();

                            for (int i = 0; i < subcategoriesHashMap.get(parent.getItemAtPosition(position).toString()).size(); ++i) {
                                subcategories.add(subcategoriesHashMap.get(parent.getItemAtPosition(position).toString()).get(i).getSubcategory_name());
                            }

                            categoryFromSpinner = parent.getItemAtPosition(position).toString();
                            if (categoryFromSpinner.equals("Не выбрано")) {
                                categoryFromSpinner = "";
                            }

                            adapterSubcategory = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_text_color, subcategories);
                            subcategorySpinner.setAdapter(adapterSubcategory);
                            subcategorySpinner.setOnClickListener(v -> subcategorySpinner.showDropDown());
                            subcategoryFromSpinner = "";
                            subcategorySpinner.setText(adapterSubcategory.getItem(0), false);
                            if (subcategoriesHashMap.get(parent.getItemAtPosition(position).toString()).size() == 1) {
                                subcategoryFromSpinner = adapterSubcategory.getItem(1);
                                subcategorySpinner.setText(subcategoryFromSpinner, false);
                            }
                        } else {
                            subcategoryLayout.setVisibility(View.GONE);
                        }
                    } else {
                        subcategoryLayout.setVisibility(View.GONE);
                    }
                });

                for (int i = 0; i < subcategoriesHashMap.get(category).size(); ++i) {
                    subcategories.add(subcategoriesHashMap.get(category).get(i).getSubcategory_name());
                }
                adapterSubcategory = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_text_color, subcategories);
                subcategorySpinner.setAdapter(adapterSubcategory);
                subcategorySpinner.setText(subcategory, false);
                subcategoryFromSpinner = subcategory;

                categoryFromSpinner = category;
                subcategoryFromSpinner = subcategory;

                subcategorySpinner.setOnItemClickListener((parent, view, position, id) -> {
                    if (parent.getItemAtPosition(position).toString().equals("Не выбрано")) {
                        subcategoryFromSpinner = "";
                    } else {
                        subcategoryFromSpinner = parent.getItemAtPosition(position).toString();
                    }
                });

            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Toast.makeText(EditCreator.this, "Проверьте интернет соединение.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static void addImageFromInternet(ArrayList<String> urls) {

        ArrayList<String> temp = urls;

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... urls) {

                try {

                    for (int i = 0; i < temp.size(); ++i) {
                        files.add(i, Glide.with(activity)
                                .asFile()
                                .load(temp.get(i))
                                .submit().get());

                        uris.add(i, temp.get(i));
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void file) {
                super.onPostExecute(file);

                for (int i = 0; i < temp.size(); ++i) {
                    adImages.get(choosedIndex).setImageURI(Uri.fromFile(files.get(i)));

                    if (choosedIndex == imageUploadCount) {
                        ++imageUploadCount;
                    }

                    if (imageUploadCount < 5) {
                        adImages.get(imageUploadCount).setBackground(activity.getDrawable(R.drawable.ad_creator_image_style));
                        adImages.get(imageUploadCount).setImageDrawable(activity.getDrawable(R.drawable.ic_circle_add));
                        if (imageUploadCount > 1) {
                            adImages.get(imageUploadCount - 1).setBackground(activity.getDrawable(R.drawable.ad_creator_secondary_photo));
                        }
                    } else if (imageUploadCount == 5) {
                        adImages.get(imageUploadCount - 1).setBackground(activity.getDrawable(R.drawable.ad_creator_secondary_photo));
                    }

                    uploadedCounterStr = uploadedCounterStr.substring(0, 10) + imageUploadCount + uploadedCounterStr.substring(11, 16);
                    uploadAmountTW.setText(uploadedCounterStr);

                    if (imageUploadCount == 1) {
                        mainLabel.setVisibility(View.VISIBLE);
                    }

                    choosedIndex++;
                }
            }
        }.execute();

    }

    private void addImage() {
        if (fileWithUri.length() < MAX_SIZE_PHOTO) {
            adImages.get(choosedIndex).setImageURI(selectedImageUri);

            if (choosedIndex == imageUploadCount) {
                uris.add(choosedIndex, selectedImageUri.toString());
                files.add(choosedIndex, fileWithUri);

                ++imageUploadCount;
            } else {
                uris.set(choosedIndex, selectedImageUri.toString());
                files.set(choosedIndex, fileWithUri);
            }

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

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {} else {
                Toast.makeText(activity, "Отказано в доступе", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        boolean flag = false;
        switch (v.getId()) {
//            case R.id.newState :
//                state = true;
//                newState.setChecked(true);
//                secondaryState.setChecked(false);
//                goodsState = "Новое";
//                break;
//            case R.id.secondaryState :
//                state = false;
//                secondaryState.setChecked(true);
//                newState.setChecked(false);
//                goodsState = "Б/У";
//                break;
            case R.id.adPhoto1:
                choosedIndex = 0;
                flag = true;
                break;
            case R.id.adPhoto2:
                if (imageUploadCount >= 1) {
                    choosedIndex = 1;
                    flag = true;
                }
                break;
            case R.id.adPhoto3:
                if (imageUploadCount >= 2) {
                    choosedIndex = 2;
                    flag = true;
                }
                break;
            case R.id.adPhoto4:
                if (imageUploadCount >= 3) {
                    choosedIndex = 3;
                    flag = true;
                }
                break;
            case R.id.adPhoto5:
                if (imageUploadCount >= 4) {
                    choosedIndex = 4;
                    flag = true;
                }
                break;
            case R.id.addAdButton:
                if (isAdCorrect()) {
                    insertAd();
                }

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }

        if (flag) {
            EditCreatorImageMenu editCreatorImageMenu = new EditCreatorImageMenu(imageUploadCount, choosedIndex, uris);
            editCreatorImageMenu.setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialogTheme);
            editCreatorImageMenu.show(getSupportFragmentManager(), "");
        }
    }

    private boolean isAdCorrect () {

        boolean isAdCorrect = true;

        titleEditText.setErrorEnabled(false);
        categoryLayout.setErrorEnabled(false);
        subcategoryLayout.setErrorEnabled(false);
        descriptionEditText.setErrorEnabled(false);
        priceEditText.setErrorEnabled(false);

        if (files.size() == 0) {
            Toast.makeText(activity, "Отсутствуют фотографии. Добавьте хотя бы одну.", Toast.LENGTH_LONG).show();
            isAdCorrect = false;
        }
        if (categoryFromSpinner.equals("")) {
            categoryLayout.setError("Укажите категорию");
            isAdCorrect = false;
        } else if (subcategoryFromSpinner.equals("")) {
            subcategoryLayout.setError("Укажите подкатегорию");
            isAdCorrect = false;
        }
        if (titleEditText.getEditText().getText().toString().equals("")) {
            titleEditText.setError("Укажите заголовок");
            isAdCorrect = false;
        }
        if (descriptionEditText.getEditText().getText().toString().equals("")) {
            descriptionEditText.setError("Укажите описание");
            isAdCorrect = false;
        }
        if (priceEditText.getEditText().getText().toString().equals("")) {
            priceEditText.setError("Укажите стоимость");
            isAdCorrect = false;
        }
        return isAdCorrect;
    }


    private void insertAd () {
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Сохранение объявления");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("ru"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+3"));

        String title = titleEditText.getEditText().getText().toString();
        String description = descriptionEditText.getEditText().getText().toString();


        for (int i = 0; i < uris.size(); ++i) {
            if (files.get(i) != null) {
                parts.set(i, MultipartBody.Part.createFormData("image" + (i + 1), "-" + (i + 1) + ".png", RequestBody.create(MediaType.parse("image/*"), files.get(i))));
            }
        }

        double price = Double.parseDouble(priceEditText.getEditText().getText().toString());
        String datetime = dateFormat.format(new Date());
        String subcategory = subcategoryFromSpinner;

        ApiClient.getApi().editCreator(RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(adId)),
                RequestBody.create(MediaType.parse("multipart/form-data"), title),
                RequestBody.create(MediaType.parse("multipart/form-data"), description),
                parts.get(0),
                parts.get(1),
                parts.get(2),
                parts.get(3),
                parts.get(4),
                RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(price)),
                RequestBody.create(MediaType.parse("multipart/form-data"), datetime),
                RequestBody.create(MediaType.parse("multipart/form-data"), subcategory)).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                pDialog.dismiss();
                if (response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Обьявление сохранено!", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(appCompatActivity, MainActivity.class);
                    appCompatActivity.startActivity(myIntent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                pDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        choosedIndex = 0;
        imageUploadCount = 0;
        uris.clear();
        parts.clear();
        fileWithUri = null;
        outputFileUri = null;

        Glide.get(activity).clearMemory();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                Glide.get(activity).clearDiskCache();
            }
        });

    }

}
