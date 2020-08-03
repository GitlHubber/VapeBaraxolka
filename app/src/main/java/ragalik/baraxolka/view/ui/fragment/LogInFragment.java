package ragalik.baraxolka.view.ui.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import ragalik.baraxolka.network.ApiClient;
import ragalik.baraxolka.network.entities.User;
import ragalik.baraxolka.network.entities.UserResponse;
import ragalik.baraxolka.MainActivity;
import ragalik.baraxolka.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ragalik.baraxolka.view.ui.fragment.AdsFragment.adViewModel;


public class LogInFragment extends Fragment implements View.OnClickListener {

    private static final String EMAIL = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String EMAIL_START = "[a-zA-Z0-9._-]";
    private static final String PHONE_NUMBER_START = "(^\\+375)";
    private static final String PHONE_NUMBER = "(^(\\+37529|\\+37533|\\+37544)\\d{7})";
    //public static final Pattern PASSWORD = Pattern.compile("[a-zA-Z0-9_]{5,20}");

    private TextInputLayout numberOrEmail;
    private TextInputLayout password;

    private SignInFragment signInFragment;
    private Toolbar toolbar;
    private static String numberOrEmailStr;
    private static String passwordStr;
    private static String inputManager;
    private AppCompatButton acceptLoginButton;
    private TextView createAkkButton;

    private TextView forgotPassword;
    private ForgotPasswordFragment forgotPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.isActualFragment = false;
        MainActivity.invalidateSearchMenu();
        MainActivity.fab.hide();
        toolbar = view.findViewById(R.id.toolbar_authorization);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            appCompatActivity.setSupportActionBar(toolbar);
            toolbar.setTitle("Вход");
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), MainActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MainActivity.drawer.addDrawerListener(toggle);
        toggle.syncState();

        signInFragment = new SignInFragment();

        numberOrEmail = view.findViewById(R.id.PhNumberOrEmailLayout);
        numberOrEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches(PHONE_NUMBER_START)) {
                    numberOrEmail.setCounterEnabled(true);
                    numberOrEmail.setCounterMaxLength(13);
                    inputManager = "phoneNumber";
                } else if (s.toString().matches(EMAIL_START)) {
                    numberOrEmail.setCounterEnabled(true);
                    numberOrEmail.setCounterMaxLength(40);
                    inputManager = "email";
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        password = view.findViewById(R.id.LIPasswordLayout);
        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                password.setCounterEnabled(true);
                password.setCounterMaxLength(18);
            }
        });

        forgotPassword = view.findViewById(R.id.forgotPass);
        forgotPass = new ForgotPasswordFragment();
        forgotPassword.setOnClickListener(v1 -> {
            Navigation.findNavController(MainActivity.activity, R.id.fragment).navigate(R.id.forgotPasswordFragment);
            hideKeyboard(view);
        });

        acceptLoginButton = view.findViewById(R.id.AcceptLogInButton);
        acceptLoginButton.setOnClickListener(this);
        createAkkButton = view.findViewById(R.id.SignInLogInButton);
        createAkkButton.setOnClickListener(this);
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
//                    fragmentTransaction.setCustomAnimations(R.anim.up_to_bottom, R.anim.exit_to_right);
//                    fragmentTransaction.replace(R.id.constrLayout, forgotPass).commit();
//                    getActivity().setTitle("Восстановление пароля");
//                    hideKeyboard(v);
//                }
//                break;
            case (R.id.SignInLogInButton) :
                numberOrEmail.getEditText().setText("");
                password.getEditText().setText("");
                numberOrEmail.setCounterEnabled(false);
                password.setCounterEnabled(false);
                if (getActivity() != null) {
//                    FragmentTransaction fragmentTrans = getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTrans.setCustomAnimations(R.anim.bottom_to_up, R.anim.exit_to_up);
//                    fragmentTrans.replace(R.id.constrLayout, signInFragment).addToBackStack(null).commit();
//                    getActivity().setTitle("Регистрация");
                    Navigation.findNavController(getActivity(), R.id.fragment).navigate(R.id.signIn);
                    hideKeyboard(v);
                }
                break;
            case (R.id.AcceptLogInButton) :
                numberOrEmailStr = numberOrEmail.getEditText().getText().toString();
                passwordStr = password.getEditText().getText().toString();
                if (nickPassValidate()) {
                    logIn();
                }
                hideKeyboard(v);
                break;
        }
    }

    private boolean nickPassValidate () {
        String nick = numberOrEmail.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        if (nick.matches(PHONE_NUMBER)) {
            inputManager = "phoneNumber";
        } else if (nick.matches(EMAIL)) {
            inputManager = "email";
        }

        if (nick.isEmpty() || pass.isEmpty()) {
            return false;
        } else if (inputManager.equals("phoneNumber") && (!nick.matches(PHONE_NUMBER))) {
            Toast.makeText(getContext(), "Номер телефона указан неверно!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (inputManager.equals("email") && (!nick.matches(EMAIL))) {
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

//                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(R.anim.up_to_bottom, R.anim.exit_to_right);
//                    fragmentTransaction.replace(R.id.constrLayout, MainActivity.adsFragment).commit();
//                    getActivity().setTitle("Объявления");
                    Navigation.findNavController(MainActivity.activity, R.id.fragment).navigate(R.id.ADS);

                    if (!MainActivity.isEntranceFromDialog) {
                        adViewModel.getLiveDataSource().getValue().invalidate();
                    }

                    SharedPreferences.Editor editor = MainActivity.sp.edit();
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

