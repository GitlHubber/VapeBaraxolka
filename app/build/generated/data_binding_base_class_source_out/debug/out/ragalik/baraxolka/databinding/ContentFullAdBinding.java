// Generated by view binder compiler. Do not edit!
package ragalik.baraxolka.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import me.relex.circleindicator.CircleIndicator;
import ragalik.baraxolka.R;

public final class ContentFullAdBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final AppCompatCheckBox bookmarkButtonFullAd;

  @NonNull
  public final TextView categoryTextFullAd;

  @NonNull
  public final CircleIndicator circleIndicatorFullAd;

  @NonNull
  public final TextView dTextFullAd;

  @NonNull
  public final TextView dateTextFullAd;

  @NonNull
  public final TextView descriptionTextFullAd;

  @NonNull
  public final CoordinatorLayout fullAdCoordinator;

  @NonNull
  public final LinearLayout fullAdLinearLayout;

  @NonNull
  public final ViewPager ivFullScreenAd;

  @NonNull
  public final TextView nTextFullAd;

  @NonNull
  public final TextView numberAdTextFullAd;

  @NonNull
  public final TextView priceTextFullAd;

  @NonNull
  public final TextView sellerName;

  @NonNull
  public final LinearLayout sellerProfileLayout;

  @NonNull
  public final TextView stateTextFullAd;

  @NonNull
  public final TextView textView12;

  @NonNull
  public final TextView textView17;

  @NonNull
  public final TextView textView18;

  @NonNull
  public final TextView textView20;

  @NonNull
  public final TextView textView26;

  @NonNull
  public final TextView textView28;

  @NonNull
  public final TextView titleTextFullAd;

  @NonNull
  public final TextView townTextFullAd;

  @NonNull
  public final TextView typeTextFullAd;

  @NonNull
  public final CircleImageView userImageFullAd;

  @NonNull
  public final TextView viewsTextFullAd;

  private ContentFullAdBinding(@NonNull CoordinatorLayout rootView,
      @NonNull AppCompatCheckBox bookmarkButtonFullAd, @NonNull TextView categoryTextFullAd,
      @NonNull CircleIndicator circleIndicatorFullAd, @NonNull TextView dTextFullAd,
      @NonNull TextView dateTextFullAd, @NonNull TextView descriptionTextFullAd,
      @NonNull CoordinatorLayout fullAdCoordinator, @NonNull LinearLayout fullAdLinearLayout,
      @NonNull ViewPager ivFullScreenAd, @NonNull TextView nTextFullAd,
      @NonNull TextView numberAdTextFullAd, @NonNull TextView priceTextFullAd,
      @NonNull TextView sellerName, @NonNull LinearLayout sellerProfileLayout,
      @NonNull TextView stateTextFullAd, @NonNull TextView textView12, @NonNull TextView textView17,
      @NonNull TextView textView18, @NonNull TextView textView20, @NonNull TextView textView26,
      @NonNull TextView textView28, @NonNull TextView titleTextFullAd,
      @NonNull TextView townTextFullAd, @NonNull TextView typeTextFullAd,
      @NonNull CircleImageView userImageFullAd, @NonNull TextView viewsTextFullAd) {
    this.rootView = rootView;
    this.bookmarkButtonFullAd = bookmarkButtonFullAd;
    this.categoryTextFullAd = categoryTextFullAd;
    this.circleIndicatorFullAd = circleIndicatorFullAd;
    this.dTextFullAd = dTextFullAd;
    this.dateTextFullAd = dateTextFullAd;
    this.descriptionTextFullAd = descriptionTextFullAd;
    this.fullAdCoordinator = fullAdCoordinator;
    this.fullAdLinearLayout = fullAdLinearLayout;
    this.ivFullScreenAd = ivFullScreenAd;
    this.nTextFullAd = nTextFullAd;
    this.numberAdTextFullAd = numberAdTextFullAd;
    this.priceTextFullAd = priceTextFullAd;
    this.sellerName = sellerName;
    this.sellerProfileLayout = sellerProfileLayout;
    this.stateTextFullAd = stateTextFullAd;
    this.textView12 = textView12;
    this.textView17 = textView17;
    this.textView18 = textView18;
    this.textView20 = textView20;
    this.textView26 = textView26;
    this.textView28 = textView28;
    this.titleTextFullAd = titleTextFullAd;
    this.townTextFullAd = townTextFullAd;
    this.typeTextFullAd = typeTextFullAd;
    this.userImageFullAd = userImageFullAd;
    this.viewsTextFullAd = viewsTextFullAd;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ContentFullAdBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ContentFullAdBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.content_full_ad, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ContentFullAdBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bookmark_button_full_ad;
      AppCompatCheckBox bookmarkButtonFullAd = rootView.findViewById(id);
      if (bookmarkButtonFullAd == null) {
        break missingId;
      }

      id = R.id.categoryTextFullAd;
      TextView categoryTextFullAd = rootView.findViewById(id);
      if (categoryTextFullAd == null) {
        break missingId;
      }

      id = R.id.circleIndicatorFullAd;
      CircleIndicator circleIndicatorFullAd = rootView.findViewById(id);
      if (circleIndicatorFullAd == null) {
        break missingId;
      }

      id = R.id.dTextFullAd;
      TextView dTextFullAd = rootView.findViewById(id);
      if (dTextFullAd == null) {
        break missingId;
      }

      id = R.id.dateTextFullAd;
      TextView dateTextFullAd = rootView.findViewById(id);
      if (dateTextFullAd == null) {
        break missingId;
      }

      id = R.id.descriptionTextFullAd;
      TextView descriptionTextFullAd = rootView.findViewById(id);
      if (descriptionTextFullAd == null) {
        break missingId;
      }

      CoordinatorLayout fullAdCoordinator = (CoordinatorLayout) rootView;

      id = R.id.fullAdLinearLayout;
      LinearLayout fullAdLinearLayout = rootView.findViewById(id);
      if (fullAdLinearLayout == null) {
        break missingId;
      }

      id = R.id.ivFullScreenAd;
      ViewPager ivFullScreenAd = rootView.findViewById(id);
      if (ivFullScreenAd == null) {
        break missingId;
      }

      id = R.id.nTextFullAd;
      TextView nTextFullAd = rootView.findViewById(id);
      if (nTextFullAd == null) {
        break missingId;
      }

      id = R.id.numberAdTextFullAd;
      TextView numberAdTextFullAd = rootView.findViewById(id);
      if (numberAdTextFullAd == null) {
        break missingId;
      }

      id = R.id.priceTextFullAd;
      TextView priceTextFullAd = rootView.findViewById(id);
      if (priceTextFullAd == null) {
        break missingId;
      }

      id = R.id.sellerName;
      TextView sellerName = rootView.findViewById(id);
      if (sellerName == null) {
        break missingId;
      }

      id = R.id.seller_profile_layout;
      LinearLayout sellerProfileLayout = rootView.findViewById(id);
      if (sellerProfileLayout == null) {
        break missingId;
      }

      id = R.id.stateTextFullAd;
      TextView stateTextFullAd = rootView.findViewById(id);
      if (stateTextFullAd == null) {
        break missingId;
      }

      id = R.id.textView12;
      TextView textView12 = rootView.findViewById(id);
      if (textView12 == null) {
        break missingId;
      }

      id = R.id.textView17;
      TextView textView17 = rootView.findViewById(id);
      if (textView17 == null) {
        break missingId;
      }

      id = R.id.textView18;
      TextView textView18 = rootView.findViewById(id);
      if (textView18 == null) {
        break missingId;
      }

      id = R.id.textView20;
      TextView textView20 = rootView.findViewById(id);
      if (textView20 == null) {
        break missingId;
      }

      id = R.id.textView26;
      TextView textView26 = rootView.findViewById(id);
      if (textView26 == null) {
        break missingId;
      }

      id = R.id.textView28;
      TextView textView28 = rootView.findViewById(id);
      if (textView28 == null) {
        break missingId;
      }

      id = R.id.titleTextFullAd;
      TextView titleTextFullAd = rootView.findViewById(id);
      if (titleTextFullAd == null) {
        break missingId;
      }

      id = R.id.townTextFullAd;
      TextView townTextFullAd = rootView.findViewById(id);
      if (townTextFullAd == null) {
        break missingId;
      }

      id = R.id.typeTextFullAd;
      TextView typeTextFullAd = rootView.findViewById(id);
      if (typeTextFullAd == null) {
        break missingId;
      }

      id = R.id.userImageFullAd;
      CircleImageView userImageFullAd = rootView.findViewById(id);
      if (userImageFullAd == null) {
        break missingId;
      }

      id = R.id.viewsTextFullAd;
      TextView viewsTextFullAd = rootView.findViewById(id);
      if (viewsTextFullAd == null) {
        break missingId;
      }

      return new ContentFullAdBinding((CoordinatorLayout) rootView, bookmarkButtonFullAd,
          categoryTextFullAd, circleIndicatorFullAd, dTextFullAd, dateTextFullAd,
          descriptionTextFullAd, fullAdCoordinator, fullAdLinearLayout, ivFullScreenAd, nTextFullAd,
          numberAdTextFullAd, priceTextFullAd, sellerName, sellerProfileLayout, stateTextFullAd,
          textView12, textView17, textView18, textView20, textView26, textView28, titleTextFullAd,
          townTextFullAd, typeTextFullAd, userImageFullAd, viewsTextFullAd);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
