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
    String missingId;
    missingId: {
      AppCompatCheckBox bookmarkButtonFullAd = rootView.findViewById(R.id.bookmark_button_full_ad);
      if (bookmarkButtonFullAd == null) {
        missingId = "bookmarkButtonFullAd";
        break missingId;
      }
      TextView categoryTextFullAd = rootView.findViewById(R.id.categoryTextFullAd);
      if (categoryTextFullAd == null) {
        missingId = "categoryTextFullAd";
        break missingId;
      }
      CircleIndicator circleIndicatorFullAd = rootView.findViewById(R.id.circleIndicatorFullAd);
      if (circleIndicatorFullAd == null) {
        missingId = "circleIndicatorFullAd";
        break missingId;
      }
      TextView dTextFullAd = rootView.findViewById(R.id.dTextFullAd);
      if (dTextFullAd == null) {
        missingId = "dTextFullAd";
        break missingId;
      }
      TextView dateTextFullAd = rootView.findViewById(R.id.dateTextFullAd);
      if (dateTextFullAd == null) {
        missingId = "dateTextFullAd";
        break missingId;
      }
      TextView descriptionTextFullAd = rootView.findViewById(R.id.descriptionTextFullAd);
      if (descriptionTextFullAd == null) {
        missingId = "descriptionTextFullAd";
        break missingId;
      }
      CoordinatorLayout fullAdCoordinator = rootView.findViewById(R.id.fullAdCoordinator);
      if (fullAdCoordinator == null) {
        missingId = "fullAdCoordinator";
        break missingId;
      }
      LinearLayout fullAdLinearLayout = rootView.findViewById(R.id.fullAdLinearLayout);
      if (fullAdLinearLayout == null) {
        missingId = "fullAdLinearLayout";
        break missingId;
      }
      ViewPager ivFullScreenAd = rootView.findViewById(R.id.ivFullScreenAd);
      if (ivFullScreenAd == null) {
        missingId = "ivFullScreenAd";
        break missingId;
      }
      TextView nTextFullAd = rootView.findViewById(R.id.nTextFullAd);
      if (nTextFullAd == null) {
        missingId = "nTextFullAd";
        break missingId;
      }
      TextView numberAdTextFullAd = rootView.findViewById(R.id.numberAdTextFullAd);
      if (numberAdTextFullAd == null) {
        missingId = "numberAdTextFullAd";
        break missingId;
      }
      TextView priceTextFullAd = rootView.findViewById(R.id.priceTextFullAd);
      if (priceTextFullAd == null) {
        missingId = "priceTextFullAd";
        break missingId;
      }
      TextView sellerName = rootView.findViewById(R.id.sellerName);
      if (sellerName == null) {
        missingId = "sellerName";
        break missingId;
      }
      LinearLayout sellerProfileLayout = rootView.findViewById(R.id.seller_profile_layout);
      if (sellerProfileLayout == null) {
        missingId = "sellerProfileLayout";
        break missingId;
      }
      TextView stateTextFullAd = rootView.findViewById(R.id.stateTextFullAd);
      if (stateTextFullAd == null) {
        missingId = "stateTextFullAd";
        break missingId;
      }
      TextView textView12 = rootView.findViewById(R.id.textView12);
      if (textView12 == null) {
        missingId = "textView12";
        break missingId;
      }
      TextView textView17 = rootView.findViewById(R.id.textView17);
      if (textView17 == null) {
        missingId = "textView17";
        break missingId;
      }
      TextView textView18 = rootView.findViewById(R.id.textView18);
      if (textView18 == null) {
        missingId = "textView18";
        break missingId;
      }
      TextView textView20 = rootView.findViewById(R.id.textView20);
      if (textView20 == null) {
        missingId = "textView20";
        break missingId;
      }
      TextView textView26 = rootView.findViewById(R.id.textView26);
      if (textView26 == null) {
        missingId = "textView26";
        break missingId;
      }
      TextView textView28 = rootView.findViewById(R.id.textView28);
      if (textView28 == null) {
        missingId = "textView28";
        break missingId;
      }
      TextView titleTextFullAd = rootView.findViewById(R.id.titleTextFullAd);
      if (titleTextFullAd == null) {
        missingId = "titleTextFullAd";
        break missingId;
      }
      TextView townTextFullAd = rootView.findViewById(R.id.townTextFullAd);
      if (townTextFullAd == null) {
        missingId = "townTextFullAd";
        break missingId;
      }
      TextView typeTextFullAd = rootView.findViewById(R.id.typeTextFullAd);
      if (typeTextFullAd == null) {
        missingId = "typeTextFullAd";
        break missingId;
      }
      CircleImageView userImageFullAd = rootView.findViewById(R.id.userImageFullAd);
      if (userImageFullAd == null) {
        missingId = "userImageFullAd";
        break missingId;
      }
      TextView viewsTextFullAd = rootView.findViewById(R.id.viewsTextFullAd);
      if (viewsTextFullAd == null) {
        missingId = "viewsTextFullAd";
        break missingId;
      }
      return new ContentFullAdBinding((CoordinatorLayout) rootView, bookmarkButtonFullAd,
          categoryTextFullAd, circleIndicatorFullAd, dTextFullAd, dateTextFullAd,
          descriptionTextFullAd, fullAdCoordinator, fullAdLinearLayout, ivFullScreenAd, nTextFullAd,
          numberAdTextFullAd, priceTextFullAd, sellerName, sellerProfileLayout, stateTextFullAd,
          textView12, textView17, textView18, textView20, textView26, textView28, titleTextFullAd,
          townTextFullAd, typeTextFullAd, userImageFullAd, viewsTextFullAd);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
