package ragalik.baraxolka.other_logic.entrance;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.R;
import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * A simple {@link Fragment} subclass.
 */
public class SignIn extends Fragment implements View.OnClickListener {

    private static final String NICKNAME = "[а-яА-Яa-zA-Z0-9#&_.+@]{1,18}";
    private static final String EMAIL = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String PHONE_NUMBER = "[0-9+]{1,20}";
    private static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[a-zA-Z])(?=\\S+$).{8,18}$";

    private TextInputLayout nickname = null;
    private TextInputLayout email = null;
    private TextInputLayout phoneNumber = null;
    private TextInputLayout password = null;
    private TextView town;
    private TextView region;

    private AppCompatButton accept;
    private static String nicknameFromEditText;
    private static String emailFromEditText;
    private static String phoneNumberFromEditText;
    private static String passwordFromEditText;
    private static String regionFromSpinner;
    private static String townFromSpinner;
    private static boolean isRegionSelected;
    private AppCompatSpinner regionSpinner;
    private Toolbar toolbar;
    private AppCompatSpinner townSpinner;
    private ArrayAdapter<CharSequence> adapterRegion;
    private ArrayAdapter<CharSequence> adapterTown;
    //private TextView privacyPolicyTW;
    //private PrivacyPolicy privacyPolicyFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);

        toolbar = v.findViewById(R.id.toolbar_registration);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
            toolbar.setTitle("Регистрация");
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MainActivity.drawer.addDrawerListener(toggle);
        toggle.syncState();

        MainActivity.isActualFragment = false;
        MainActivity.invalidateSearchMenu();
        MainActivity.fab.hide();

        nickname = v.findViewById(R.id.si_nickname);
        email = v.findViewById(R.id.si_email);
        phoneNumber = v.findViewById(R.id.si_phone_number);
        password = v.findViewById(R.id.si_password);
        accept = v.findViewById(R.id.Registration_button);
        town = v.findViewById(R.id.SITown);
        region = v.findViewById(R.id.SIRegion);

        regionSpinner = v.findViewById(R.id.SIRegionSpinner);
        townSpinner = v.findViewById(R.id.SITownSpinner);

//        privacyPolicyFragment = new PrivacyPolicy();
//        privacyPolicyTW = v.findViewById(R.id.SIPrivacyPolicy);
//        privacyPolicyTW.setOnClickListener(this);

        if (getContext() != null) {
            adapterRegion = ArrayAdapter.createFromResource(getContext(), R.array.Spinner_region_items, R.layout.text_color);
            adapterRegion.setDropDownViewResource(R.layout.dropdown_text_color);
            regionSpinner.setAdapter(adapterRegion);
            regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String text = adapterView.getItemAtPosition(i).toString();
                    isRegionSelected = true;
                    region.setText("Область");
                    town.setText("Город");
                    if (getContext() != null) {
                        switch (text) {
                            case ("Не указано") : adapterTown = ArrayAdapter.createFromResource(getContext(), R.array.Nothing, R.layout.text_color);
                                isRegionSelected = false;
                                break;
                            case ("Минск") : adapterTown = ArrayAdapter.createFromResource(getContext(), R.array.Minsk, R.layout.text_color);
                                region.setText("Город");
                                town.setText("Район");
                                break;
                            case ("Брестская обл.") : adapterTown = ArrayAdapter.createFromResource(getContext(), R.array.BrestRegion, R.layout.text_color);
                                break;
                            case ("Витебская обл.") : adapterTown = ArrayAdapter.createFromResource(getContext(), R.array.VitebskRegion, R.layout.text_color);
                                break;
                            case ("Гомельская обл.") : adapterTown = ArrayAdapter.createFromResource(getContext(), R.array.GomelRegion, R.layout.text_color);
                                break;
                            case ("Гродненская обл.") : adapterTown = ArrayAdapter.createFromResource(getContext(), R.array.GrodnoRegion, R.layout.text_color);
                                break;
                            case ("Минская обл.") : adapterTown = ArrayAdapter.createFromResource(getContext(), R.array.MinskRegion, R.layout.text_color);
                                break;
                            case ("Могилевская обл.") : adapterTown = ArrayAdapter.createFromResource(getContext(), R.array.MogilevRegion, R.layout.text_color);
                                break;
                            default : break;
                        }
                        regionFromSpinner = text;
                        adapterTown.setDropDownViewResource(R.layout.dropdown_text_color);
                        townSpinner.setAdapter(adapterTown);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });
        }

        townSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                townFromSpinner = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        accept.setOnClickListener(this);
        return v;
    }

    private String dataValidate () {
        nicknameFromEditText = nickname.getEditText().getText().toString().trim();
        emailFromEditText = email.getEditText().getText().toString().trim();
        phoneNumberFromEditText = phoneNumber.getEditText().getText().toString().trim();
        passwordFromEditText = password.getEditText().getText().toString().trim();
        String str = "";
        if (!nicknameFromEditText.matches(NICKNAME) || nicknameFromEditText.isEmpty()) {
            str += "Имя пользователя  ";
        }
        if (!emailFromEditText.matches(EMAIL) || emailFromEditText.isEmpty()) {
            str += "Электронная почта  ";
        }
        if (!phoneNumberFromEditText.matches(PHONE_NUMBER) && !phoneNumberFromEditText.isEmpty()) {
            str += "Номер телефона ";
        }
        if (!passwordFromEditText.matches(PASSWORD) || passwordFromEditText.isEmpty()) {
            str += "Пароль";
        }
        if (!str.equals("")) {
            return str;
        }
        return "1";
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case (R.id.Registration_button) :
                String str = dataValidate();
                if (str.equals("1")) {
                    registration();
                } else {
                    Toast.makeText(getContext(), "Проверьте поля: " + str, Toast.LENGTH_LONG).show();
                }
                if(getActivity() != null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                break;
//            case (R.id.SIPrivacyPolicy) :
//                if (getActivity() != null) {
//                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
//                    fragmentTransaction.replace(R.id.constrLayout, privacyPolicyFragment).commit();
//                    getActivity().setTitle("Политика конфиденциальности");
//                    InputMethodManager inputMM = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//                    if (inputMM != null) {
//                        inputMM.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                    }
//                }
//                break;
            default : break;
        }
    }

    private void registration () {
        ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Создание вашего аккаунта");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        nicknameFromEditText = nickname.getEditText().getText().toString();
        emailFromEditText = email.getEditText().getText().toString();
        phoneNumberFromEditText = phoneNumber.getEditText().getText().toString();
        if (phoneNumberFromEditText.equals("")) {
            phoneNumberFromEditText = "0";
        }
        if (!isRegionSelected) {
            regionFromSpinner = "0";
            townFromSpinner = "0";
        }
        passwordFromEditText = password.getEditText().getText().toString();

        Call<RegisterResponse> call = ApiClient.getApi().register(nicknameFromEditText, emailFromEditText,
                phoneNumberFromEditText, regionFromSpinner, townFromSpinner, passwordFromEditText);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                pDialog.dismiss();
                if (getActivity() != null && response.body().getSuccess()) {
                    MainActivity.createUserData(response.body().getIdUser(), nicknameFromEditText, emailFromEditText, phoneNumberFromEditText, regionFromSpinner, townFromSpinner, "1");
                    MainActivity.setNavHeaderText(getActivity());

                    FragmentTransaction fragmentTransaction;
                    if (getActivity() != null) {
                        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
                        fragmentTransaction.replace(R.id.constrLayout, MainActivity.adsFragment).commit();
                        MainActivity.showItemsNavigationDrawer(R.id.MY_ADS, R.id.FAVOURITES);
                        SharedPreferences.Editor editor = MainActivity.sp.edit();
                        editor.putString("image", "null");
                        editor.apply();
                    }
                    Toast.makeText(getContext(), "Регистрация прошла успешно!", Toast.LENGTH_LONG).show();
                    getActivity().setTitle("Объявления ");
                } else if (!response.body().getSuccess() && response.body().isEmailExist()) {
                    Toast.makeText(getContext(), "Аккаунт с таким email существует! ", Toast.LENGTH_LONG).show();
                } else if (!response.body().getSuccess() && response.body().isNumberExist()) {
                    Toast.makeText(getContext(), "Аккаунт с таким номером телефона существует! ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                pDialog.dismiss();
                Log.d("", t.getMessage());
                Toast.makeText(getContext(), "Ошибка регистрации.", Toast.LENGTH_LONG).show();
            }
        });
    }
}