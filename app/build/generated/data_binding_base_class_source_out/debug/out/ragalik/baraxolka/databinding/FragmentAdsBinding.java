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
  public final Chip categoryChip;

  @NonNull
  public final Chip filterChip;

  @NonNull
  public final ProgressBar progressAds;

  @NonNull
  public final SwipeRefreshLayout refresherAds;

  @NonNull
  public final Chip searchChip;

  private FragmentAdsBinding(@NonNull CoordinatorLayout rootView,
      @NonNull RecyclerView ADSRecyclerView, @NonNull Toolbar adsToolbar,
      @NonNull AppBarLayout appBarLayout, @NonNull Chip categoryChip, @NonNull Chip filterChip,
      @NonNull ProgressBar progressAds, @NonNull SwipeRefreshLayout refresherAds,
      @NonNull Chip searchChip) {
    this.rootView = rootView;
    this.ADSRecyclerView = ADSRecyclerView;
    this.adsToolbar = adsToolbar;
    this.appBarLayout = appBarLayout;
    this.categoryChip = categoryChip;
    this.filterChip = filterChip;
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
    int id;
    missingId: {
      id = R.id.ADSRecyclerView;
      RecyclerView ADSRecyclerView = rootView.findViewById(id);
      if (ADSRecyclerView == null) {
        break missingId;
      }

      id = R.id.ads_toolbar;
      Toolbar adsToolbar = rootView.findViewById(id);
      if (adsToolbar == null) {
        break missingId;
      }

      id = R.id.appBarLayout;
      AppBarLayout appBarLayout = rootView.findViewById(id);
      if (appBarLayout == null) {
        break missingId;
      }

      id = R.id.category_chip;
      Chip categoryChip = rootView.findViewById(id);
      if (categoryChip == null) {
        break missingId;
      }

      id = R.id.filter_chip;
      Chip filterChip = rootView.findViewById(id);
      if (filterChip == null) {
        break missingId;
      }

      id = R.id.progress_ads;
      ProgressBar progressAds = rootView.findViewById(id);
      if (progressAds == null) {
        break missingId;
      }

      id = R.id.refresherAds;
      SwipeRefreshLayout refresherAds = rootView.findViewById(id);
      if (refresherAds == null) {
        break missingId;
      }

      id = R.id.search_chip;
      Chip searchChip = rootView.findViewById(id);
      if (searchChip == null) {
        break missingId;
      }

      return new FragmentAdsBinding((CoordinatorLayout) rootView, ADSRecyclerView, adsToolbar,
          appBarLayout, categoryChip, filterChip, progressAds, refresherAds, searchChip);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
