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

public final class RejectedAdsBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final RecyclerView RejectedAdsView;

  @NonNull
  public final ProgressBar progressRejected;

  private RejectedAdsBinding(@NonNull CoordinatorLayout rootView,
      @NonNull RecyclerView RejectedAdsView, @NonNull ProgressBar progressRejected) {
    this.rootView = rootView;
    this.RejectedAdsView = RejectedAdsView;
    this.progressRejected = progressRejected;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static RejectedAdsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static RejectedAdsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.rejected_ads, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static RejectedAdsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.RejectedAdsView;
      RecyclerView RejectedAdsView = rootView.findViewById(id);
      if (RejectedAdsView == null) {
        break missingId;
      }

      id = R.id.progress_rejected;
      ProgressBar progressRejected = rootView.findViewById(id);
      if (progressRejected == null) {
        break missingId;
      }

      return new RejectedAdsBinding((CoordinatorLayout) rootView, RejectedAdsView,
          progressRejected);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
