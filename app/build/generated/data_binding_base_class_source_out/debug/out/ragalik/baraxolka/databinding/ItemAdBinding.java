// Generated by view binder compiler. Do not edit!
package ragalik.baraxolka.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import ragalik.baraxolka.R;

public final class ItemAdBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView adPreviewDateTime;

  @NonNull
  public final TextView adPreviewPrice;

  @NonNull
  public final AppCompatCheckBox bookmarkButtonAds;

  @NonNull
  public final ImageView ivAvatar;

  @NonNull
  public final Button moderatorAcceptButton;

  @NonNull
  public final Button moderatorRejectButton;

  @NonNull
  public final TextView tvDescription;

  @NonNull
  public final TextView tvTitle;

  private ItemAdBinding(@NonNull CardView rootView, @NonNull TextView adPreviewDateTime,
      @NonNull TextView adPreviewPrice, @NonNull AppCompatCheckBox bookmarkButtonAds,
      @NonNull ImageView ivAvatar, @NonNull Button moderatorAcceptButton,
      @NonNull Button moderatorRejectButton, @NonNull TextView tvDescription,
      @NonNull TextView tvTitle) {
    this.rootView = rootView;
    this.adPreviewDateTime = adPreviewDateTime;
    this.adPreviewPrice = adPreviewPrice;
    this.bookmarkButtonAds = bookmarkButtonAds;
    this.ivAvatar = ivAvatar;
    this.moderatorAcceptButton = moderatorAcceptButton;
    this.moderatorRejectButton = moderatorRejectButton;
    this.tvDescription = tvDescription;
    this.tvTitle = tvTitle;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemAdBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemAdBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_ad, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemAdBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    String missingId;
    missingId: {
      TextView adPreviewDateTime = rootView.findViewById(R.id.ad_preview_date_time);
      if (adPreviewDateTime == null) {
        missingId = "adPreviewDateTime";
        break missingId;
      }
      TextView adPreviewPrice = rootView.findViewById(R.id.ad_preview_price);
      if (adPreviewPrice == null) {
        missingId = "adPreviewPrice";
        break missingId;
      }
      AppCompatCheckBox bookmarkButtonAds = rootView.findViewById(R.id.bookmark_button_ads);
      if (bookmarkButtonAds == null) {
        missingId = "bookmarkButtonAds";
        break missingId;
      }
      ImageView ivAvatar = rootView.findViewById(R.id.iv_avatar);
      if (ivAvatar == null) {
        missingId = "ivAvatar";
        break missingId;
      }
      Button moderatorAcceptButton = rootView.findViewById(R.id.moderator_accept_button);
      if (moderatorAcceptButton == null) {
        missingId = "moderatorAcceptButton";
        break missingId;
      }
      Button moderatorRejectButton = rootView.findViewById(R.id.moderator_reject_button);
      if (moderatorRejectButton == null) {
        missingId = "moderatorRejectButton";
        break missingId;
      }
      TextView tvDescription = rootView.findViewById(R.id.tv_description);
      if (tvDescription == null) {
        missingId = "tvDescription";
        break missingId;
      }
      TextView tvTitle = rootView.findViewById(R.id.tv_title);
      if (tvTitle == null) {
        missingId = "tvTitle";
        break missingId;
      }
      return new ItemAdBinding((CardView) rootView, adPreviewDateTime, adPreviewPrice,
          bookmarkButtonAds, ivAvatar, moderatorAcceptButton, moderatorRejectButton, tvDescription,
          tvTitle);
    }
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
