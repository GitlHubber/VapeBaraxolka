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

public final class FragmentRulesBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final TextView Header;

  @NonNull
  public final Toolbar rulesToolbar;

  @NonNull
  public final TextView title1R;

  @NonNull
  public final TextView title2R;

  @NonNull
  public final TextView title3R;

  private FragmentRulesBinding(@NonNull FrameLayout rootView, @NonNull TextView Header,
      @NonNull Toolbar rulesToolbar, @NonNull TextView title1R, @NonNull TextView title2R,
      @NonNull TextView title3R) {
    this.rootView = rootView;
    this.Header = Header;
    this.rulesToolbar = rulesToolbar;
    this.title1R = title1R;
    this.title2R = title2R;
    this.title3R = title3R;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRulesBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRulesBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_rules, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRulesBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Header;
      TextView Header = rootView.findViewById(id);
      if (Header == null) {
        break missingId;
      }

      id = R.id.rulesToolbar;
      Toolbar rulesToolbar = rootView.findViewById(id);
      if (rulesToolbar == null) {
        break missingId;
      }

      id = R.id.title1R;
      TextView title1R = rootView.findViewById(id);
      if (title1R == null) {
        break missingId;
      }

      id = R.id.title2R;
      TextView title2R = rootView.findViewById(id);
      if (title2R == null) {
        break missingId;
      }

      id = R.id.title3R;
      TextView title3R = rootView.findViewById(id);
      if (title3R == null) {
        break missingId;
      }

      return new FragmentRulesBinding((FrameLayout) rootView, Header, rulesToolbar, title1R,
          title2R, title3R);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
