package ragalik.baraxolka.other_logic.entrance;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import java.util.regex.Pattern;

import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.User;
import ragalik.baraxolka.network.entities.UserResponse;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ragalik.baraxolka.paging_feed.ads.ADS.adViewModel;


public class LogIn extends Fragment implements View.OnClickListener {

    private static final Pattern EMAIL_START = Pattern.compile("^[A-Za-z][A-Za-z0-9-]{1,40}$");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z][A-Za-z0-9_.-]{2,25}@[a-zA-Z]{3,10}\\.[a-z]{2,4}$");

    private static final Pattern PHONE_NUMBER_START = Pattern.compile("(^80|^\\+375)");
    private static final Pattern PHONE_NUMBER = Pattern.compile("(^80\\d{9}|^\\+375\\d{9})");
    //public static final Pattern PASSWORD = Pattern.compile("[a-zA-Z0-9_]{5,20}");

    private static final String TAG_USER = "user";
    private static final String TAG_NICKNAME = "nickname";


    private static TextInputLayout numberOrEmailLayout;
    private static TextInputLayout passwordLayout;
    private static EditText numberOrEmail;
    private static EditText password;

    private SignIn signInFragment;
    private Toolbar toolbar;
    private static String numberOrEmailStr;
    private static String passwordStr;
    private static String inputManager;
    private AppCompatButton acceptLoginButton;
    private AppCompatButton createAkkButton;

    //private TextView forgotPassword;
    //private ForgotPassword forgotPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_log_in, container, false);
        MainActivity.isActualFragment = false;
        MainActivity.invalidateSearchMenu();
        MainActivity.fab.hide();
        toolbar = v.findViewById(R.id.toolbar_authorization);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
            toolbar.setTitle("Вход");
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MainActivity.drawer.addDrawerListener(toggle);
        toggle.syncState();

        signInFragment = new SignIn();

        numberOrEmailLayout = v.findViewById(R.id.PhNumberOrEmailLayout);
        numberOrEmail = v.findViewById(R.id.LIPh_or_email);
        numberOrEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (numberOrEmail.getText().toString().matches(PHONE_NUMBER_START.toString())) {
                    numberOrEmailLayout.setCounterEnabled(true);
                    if (numberOrEmail.getText().charAt(0) == '+') {
                        numberOrEmailLayout.setCounterMaxLength(13);
                    } else {
                        numberOrEmailLayout.setCounterMaxLength(11);
                    }

                    inputManager = "phoneNumber";
                } else if (numberOrEmail.getText().toString().matches(EMAIL_START.toString())) {
                    numberOrEmailLayout.setCounterEnabled(true);
                    numberOrEmailLayout.setCounterMaxLength(40);
                    inputManager = "email";
                }
            }
        });
        password = v.findViewById(R.id.LIPassword);
        passwordLayout = v.findViewById(R.id.LIPasswordLayout);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                passwordLayout.setCounterEnabled(true);
                passwordLayout.setCounterMaxLength(18);
            }
        });

//        forgotPassword = v.findViewById(R.id.forgotPass);
//        forgotPass = new ForgotPassword();
//        forgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        acceptLoginButton = v.findViewById(R.id.AcceptLogInButton);
        acceptLoginButton.setOnClickListener(this);
        createAkkButton = v.findViewById(R.id.SignInLogInButton);
        createAkkButton.setOnClickListener(this);

        return v;
    }

    private void hideKeyboard (View v) {
        if (getActivity() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case (R.id.forgotPass) :
//                if (getActivity() != null) {
//                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
//                    fragmentTransaction.replace(R.id.constrLayout, forgotPass).commit();
//                    getActivity().setTitle("Восстановление пароля");
//                    hideKeyboard(v);
//                }
//                break;
            case (R.id.SignInLogInButton) :
                numberOrEmail.setText("");
                password.setText("");
                numberOrEmailLayout.setCounterEnabled(false);
                passwordLayout.setCounterEnabled(false);
                if (getActivity() != null) {
                    FragmentTransaction fragmentTrans = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTrans.setCustomAnimations(R.anim.enter_from_up, R.anim.exit_to_up);
                    fragmentTrans.replace(R.id.constrLayout, signInFragment).addToBackStack(null).commit();
                    getActivity().setTitle("Регистрация");
                    hideKeyboard(v);
                }
                break;
            case (R.id.AcceptLogInButton) :
                numberOrEmailStr = numberOrEmail.getText().toString();
                passwordStr = password.getText().toString();
                if (nickPassValidate()) {
                    logIn();
                }
                hideKeyboard(v);
                break;
        }
    }

    private boolean nickPassValidate () {
        String nick = numberOrEmail.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (nick.isEmpty() || pass.isEmpty()) {
            return false;
        } else if (inputManager.equals("phoneNumber") && (!numberOrEmail.getText().toString().matches(PHONE_NUMBER.toString()))) {
            Toast.makeText(getContext(), "Номер телефона указан неверно!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (inputManager.equals("email") && (!numberOrEmail.getText().toString().matches(EMAIL.toString()))) {
            Toast.makeText(getContext(), "Адрес электронной почты указан неверно!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void logIn () {
        ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Поиск вашего аккаунта");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Call<UserResponse> call = ApiClient.getApi().auth(numberOrEmailStr, inputManager, passwordStr);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                pDialog.dismiss();

                if (response.body().getSuccess()) {
                    User user = response.body().getUser().get(0);
                    MainActivity.createUserData(user.getId(), user.getNickname(), user.getEmail(), user.getPhoneNumber(), user.getRegion(), user.getTown(), user.getStatusName());

                    if (getActivity() != null) {
                        MainActivity.setNavHeaderText(getActivity());
                    }

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right);
                    fragmentTransaction.replace(R.id.constrLayout, MainActivity.adsFragment).commit();
                    getActivity().setTitle("Объявления");
                    SharedPreferences.Editor editor = MainActivity.sp.edit();
                    adViewModel.getLiveDataSource().getValue().invalidate();

                    editor.putString("image", user.getImage());
                    editor.apply();

                    Toast.makeText(getContext(), "Добро пожаловать, " + user.getNickname() + "!", Toast.LENGTH_SHORT).show();
                    MainActivity.showItemsNavigationDrawer(R.id.MY_ADS, R.id.FAVOURITES);
                } else if (!response.body().getSuccess() && response.body().getCorrectEmail()) {
                    password.setError("Неверно введен пароль!");
                    Toast.makeText(getContext(), "Неверно введен пароль!", Toast.LENGTH_LONG).show();
                } else if (!response.body().getSuccess() && !response.body().getCorrectEmail()) {
                    if (inputManager.equals("email")) {
                        numberOrEmail.setError("Неверно введена почта");
                        Toast.makeText(getContext(), "Неверно введена почта", Toast.LENGTH_LONG).show();
                    } else {
                        numberOrEmail.setError("Неверно введен номер телефона!");
                        Toast.makeText(getContext(), "Неверно введен номер телефона", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка!", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });
    }
}

