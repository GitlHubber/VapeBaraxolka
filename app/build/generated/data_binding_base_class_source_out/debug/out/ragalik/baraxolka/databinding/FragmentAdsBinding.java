// Generated by view binder compiler. Do not edit!
package ragalik.baraxolka.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.chip.Chip;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import ragalik.baraxolka.R;

public final class FragmentAdsBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final RecyclerView ADSRecyclerView;

  @NonNull
  public final Toolbar adsToolbar;

  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final ProgressBar progressAds;

  @NonNull
  public final SwipeRefreshLayout refresherAds;

  @NonNull
  public final Chip searchChip;

  private FragmentAdsBinding(@NonNull CoordinatorLayout rootView,
      @NonNull RecyclerView ADSRecyclerView, @NonNull Toolbar adsToolbar,
      @NonNull AppBarLayout appBarLayout, @NonNull ProgressBar progressAds,
      @NonNull SwipeRefreshLayout refresherAds, @NonNull Chip searchChip) {
    this.rootView = rootView;
    this.ADSRecyclerView = ADSRecyclerView;
    this.adsToolbar = adsToolbar;
    this.appBarLayout = appBarLayout;
    this.progressAds = progressAds;
    this.refresherAds = refresherAds;
    this.searchChip = searchChip;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAdsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAdsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_ads, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAdsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      RecyclerView ADSRecyclerView = rootView.findViewById(R.id.ADSRecyclerView);
      if (ADSRecyclerView == null) {
        missingId = "ADSRecyclerView";
        break missingId;
      }
      Toolbar adsToolbar = rootView.findViewById(R.id.ads_toolbar);
      if (adsToolbar == null) {
        missingId = "adsToolbar";
        break missingId;
      }
      AppBarLayout appBarLayout = rootView.findViewById(R.id.appBarLayout);
      if (appBarLayout == null) {
        missingId = "appBarLayout";
        break missingId;
      }
      ProgressBar progressAds = rootView.findViewById(R.id.progress_ads);
      if (progressAds == null) {
        missingId = "progressAds";
        break missingId;
      }
      SwipeRefreshLayout refresherAds = rootView.findViewById(R.id.refresherAds);
      if (refresherAds == null) {
        missingId = "refresherAds";
        break missingId;
      }
      Chip searchChip = rootView.findViewById(R.id.search_chip);
      if (searchChip == null) {
        missingId = "searchChip";
        break missingId;
      }
      return new FragmentAdsBinding((CoordinatorLayout) rootView, ADSRecyclerView, adsToolbar,
          appBarLayout, progressAds, refresherAds, searchChip);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
