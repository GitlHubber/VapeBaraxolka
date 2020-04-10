// Generated by view binder compiler. Do not edit!
package ragalik.baraxolka.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import ragalik.baraxolka.R;

public final class ContentMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout constrLayout;

  private ContentMainBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout constrLayout) {
    this.rootView = rootView;
    this.constrLayout = constrLayout;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ContentMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ContentMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.content_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ContentMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      ConstraintLayout constrLayout = rootView.findViewById(R.id.constrLayout);
      if (constrLayout == null) {
        missingId = "constrLayout";
        break missingId;
      }
      return new ContentMainBinding((ConstraintLayout) rootView, constrLayout);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
