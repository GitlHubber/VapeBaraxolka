// Generated by view binder compiler. Do not edit!
package ragalik.baraxolka.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import ragalik.baraxolka.R;

public final class FragmentTechnicalSupportBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final TextView Header;

  @NonNull
  public final Toolbar supportToolbar;

  @NonNull
  public final TextView title1TS;

  @NonNull
  public final TextView title2TS;

  @NonNull
  public final TextView title3;

  private FragmentTechnicalSupportBinding(@NonNull FrameLayout rootView, @NonNull TextView Header,
      @NonNull Toolbar supportToolbar, @NonNull TextView title1TS, @NonNull TextView title2TS,
      @NonNull TextView title3) {
    this.rootView = rootView;
    this.Header = Header;
    this.supportToolbar = supportToolbar;
    this.title1TS = title1TS;
    this.title2TS = title2TS;
    this.title3 = title3;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentTechnicalSupportBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentTechnicalSupportBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_technical_support, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentTechnicalSupportBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      TextView Header = rootView.findViewById(R.id.Header);
      if (Header == null) {
        missingId = "Header";
        break missingId;
      }
      Toolbar supportToolbar = rootView.findViewById(R.id.supportToolbar);
      if (supportToolbar == null) {
        missingId = "supportToolbar";
        break missingId;
      }
      TextView title1TS = rootView.findViewById(R.id.title1TS);
      if (title1TS == null) {
        missingId = "title1TS";
        break missingId;
      }
      TextView title2TS = rootView.findViewById(R.id.title2TS);
      if (title2TS == null) {
        missingId = "title2TS";
        break missingId;
      }
      TextView title3 = rootView.findViewById(R.id.title3);
      if (title3 == null) {
        missingId = "title3";
        break missingId;
      }
      return new FragmentTechnicalSupportBinding((FrameLayout) rootView, Header, supportToolbar,
          title1TS, title2TS, title3);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
