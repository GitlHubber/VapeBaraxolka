// Generated by view binder compiler. Do not edit!
package ragalik.baraxolka.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import ragalik.baraxolka.R;

public final class FragmentSignInBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final AppCompatButton RegistrationButton;

  @NonNull
  public final AppCompatAutoCompleteTextView SIRegionSpinner;

  @NonNull
  public final TextInputLayout SIRegionSpinnerLayout;

  @NonNull
  public final AppCompatAutoCompleteTextView SITownSpinner;

  @NonNull
  public final TextInputLayout SITownSpinnerLayout;

  @NonNull
  public final TextView appName;

  @NonNull
  public final TextView appName2;

  @NonNull
  public final TextInputLayout siEmail;

  @NonNull
  public final TextInputLayout siNickname;

  @NonNull
  public final TextInputLayout siPassword;

  @NonNull
  public final TextInputLayout siPhoneNumber;

  @NonNull
  public final Toolbar toolbarRegistration;

  private FragmentSignInBinding(@NonNull FrameLayout rootView,
      @NonNull AppCompatButton RegistrationButton,
      @NonNull AppCompatAutoCompleteTextView SIRegionSpinner,
      @NonNull TextInputLayout SIRegionSpinnerLayout,
      @NonNull AppCompatAutoCompleteTextView SITownSpinner,
      @NonNull TextInputLayout SITownSpinnerLayout, @NonNull TextView appName,
      @NonNull TextView appName2, @NonNull TextInputLayout siEmail,
      @NonNull TextInputLayout siNickname, @NonNull TextInputLayout siPassword,
      @NonNull TextInputLayout siPhoneNumber, @NonNull Toolbar toolbarRegistration) {
    this.rootView = rootView;
    this.RegistrationButton = RegistrationButton;
    this.SIRegionSpinner = SIRegionSpinner;
    this.SIRegionSpinnerLayout = SIRegionSpinnerLayout;
    this.SITownSpinner = SITownSpinner;
    this.SITownSpinnerLayout = SITownSpinnerLayout;
    this.appName = appName;
    this.appName2 = appName2;
    this.siEmail = siEmail;
    this.siNickname = siNickname;
    this.siPassword = siPassword;
    this.siPhoneNumber = siPhoneNumber;
    this.toolbarRegistration = toolbarRegistration;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentSignInBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentSignInBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_sign_in, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentSignInBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Registration_button;
      AppCompatButton RegistrationButton = rootView.findViewById(id);
      if (RegistrationButton == null) {
        break missingId;
      }

      id = R.id.SIRegionSpinner;
      AppCompatAutoCompleteTextView SIRegionSpinner = rootView.findViewById(id);
      if (SIRegionSpinner == null) {
        break missingId;
      }

      id = R.id.SIRegionSpinnerLayout;
      TextInputLayout SIRegionSpinnerLayout = rootView.findViewById(id);
      if (SIRegionSpinnerLayout == null) {
        break missingId;
      }

      id = R.id.SITownSpinner;
      AppCompatAutoCompleteTextView SITownSpinner = rootView.findViewById(id);
      if (SITownSpinner == null) {
        break missingId;
      }

      id = R.id.SITownSpinnerLayout;
      TextInputLayout SITownSpinnerLayout = rootView.findViewById(id);
      if (SITownSpinnerLayout == null) {
        break missingId;
      }

      id = R.id.appName;
      TextView appName = rootView.findViewById(id);
      if (appName == null) {
        break missingId;
      }

      id = R.id.appName2;
      TextView appName2 = rootView.findViewById(id);
      if (appName2 == null) {
        break missingId;
      }

      id = R.id.si_email;
      TextInputLayout siEmail = rootView.findViewById(id);
      if (siEmail == null) {
        break missingId;
      }

      id = R.id.si_nickname;
      TextInputLayout siNickname = rootView.findViewById(id);
      if (siNickname == null) {
        break missingId;
      }

      id = R.id.si_password;
      TextInputLayout siPassword = rootView.findViewById(id);
      if (siPassword == null) {
        break missingId;
      }

      id = R.id.si_phone_number;
      TextInputLayout siPhoneNumber = rootView.findViewById(id);
      if (siPhoneNumber == null) {
        break missingId;
      }

      id = R.id.toolbar_registration;
      Toolbar toolbarRegistration = rootView.findViewById(id);
      if (toolbarRegistration == null) {
        break missingId;
      }

      return new FragmentSignInBinding((FrameLayout) rootView, RegistrationButton, SIRegionSpinner,
          SIRegionSpinnerLayout, SITownSpinner, SITownSpinnerLayout, appName, appName2, siEmail,
          siNickname, siPassword, siPhoneNumber, toolbarRegistration);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
