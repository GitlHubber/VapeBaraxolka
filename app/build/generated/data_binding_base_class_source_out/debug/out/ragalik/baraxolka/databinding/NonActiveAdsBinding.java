// Generated by view binder compiler. Do not edit!
package ragalik.baraxolka.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import ragalik.baraxolka.R;

public final class NonActiveAdsBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final RecyclerView NonActiveAdsView;

  @NonNull
  public final ProgressBar progressNonActive;

  private NonActiveAdsBinding(@NonNull CoordinatorLayout rootView,
      @NonNull RecyclerView NonActiveAdsView, @NonNull ProgressBar progressNonActive) {
    this.rootView = rootView;
    this.NonActiveAdsView = NonActiveAdsView;
    this.progressNonActive = progressNonActive;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NonActiveAdsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NonActiveAdsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.non_active_ads, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NonActiveAdsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      RecyclerView NonActiveAdsView = rootView.findViewById(R.id.NonActiveAdsView);
      if (NonActiveAdsView == null) {
        missingId = "NonActiveAdsView";
        break missingId;
      }
      ProgressBar progressNonActive = rootView.findViewById(R.id.progress_non_active);
      if (progressNonActive == null) {
        missingId = "progressNonActive";
        break missingId;
      }
      return new NonActiveAdsBinding((CoordinatorLayout) rootView, NonActiveAdsView,
          progressNonActive);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
