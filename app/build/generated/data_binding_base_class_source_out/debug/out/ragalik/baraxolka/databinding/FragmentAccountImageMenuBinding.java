// Generated by view binder compiler. Do not edit!
package ragalik.baraxolka.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import ragalik.baraxolka.R;

public final class FragmentAccountImageMenuBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final AppCompatButton accountDeleteImage;

  @NonNull
  public final AppCompatButton accountLoadImage;

  @NonNull
  public final LinearLayout accountMenuLinearLayout;

  @NonNull
  public final AppCompatButton accountShowImage;

  @NonNull
  public final AppCompatButton accountTakeImage;

  @NonNull
  public final AppCompatTextView textView5;

  private FragmentAccountImageMenuBinding(@NonNull LinearLayout rootView,
      @NonNull AppCompatButton accountDeleteImage, @NonNull AppCompatButton accountLoadImage,
      @NonNull LinearLayout accountMenuLinearLayout, @NonNull AppCompatButton accountShowImage,
      @NonNull AppCompatButton accountTakeImage, @NonNull AppCompatTextView textView5) {
    this.rootView = rootView;
    this.accountDeleteImage = accountDeleteImage;
    this.accountLoadImage = accountLoadImage;
    this.accountMenuLinearLayout = accountMenuLinearLayout;
    this.accountShowImage = accountShowImage;
    this.accountTakeImage = accountTakeImage;
    this.textView5 = textView5;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAccountImageMenuBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAccountImageMenuBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_account_image_menu, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAccountImageMenuBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      AppCompatButton accountDeleteImage = rootView.findViewById(R.id.account_delete_image);
      if (accountDeleteImage == null) {
        missingId = "accountDeleteImage";
        break missingId;
      }
      AppCompatButton accountLoadImage = rootView.findViewById(R.id.account_load_image);
      if (accountLoadImage == null) {
        missingId = "accountLoadImage";
        break missingId;
      }
      LinearLayout accountMenuLinearLayout = rootView.findViewById(R.id.accountMenuLinearLayout);
      if (accountMenuLinearLayout == null) {
        missingId = "accountMenuLinearLayout";
        break missingId;
      }
      AppCompatButton accountShowImage = rootView.findViewById(R.id.account_show_image);
      if (accountShowImage == null) {
        missingId = "accountShowImage";
        break missingId;
      }
      AppCompatButton accountTakeImage = rootView.findViewById(R.id.account_take_image);
      if (accountTakeImage == null) {
        missingId = "accountTakeImage";
        break missingId;
      }
      AppCompatTextView textView5 = rootView.findViewById(R.id.textView5);
      if (textView5 == null) {
        missingId = "textView5";
        break missingId;
      }
      return new FragmentAccountImageMenuBinding((LinearLayout) rootView, accountDeleteImage,
          accountLoadImage, accountMenuLinearLayout, accountShowImage, accountTakeImage, textView5);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
