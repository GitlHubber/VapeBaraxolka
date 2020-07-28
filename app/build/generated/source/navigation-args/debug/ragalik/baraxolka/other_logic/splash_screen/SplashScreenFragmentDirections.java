package ragalik.baraxolka.other_logic.splash_screen;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import ragalik.baraxolka.R;

public class SplashScreenFragmentDirections {
  private SplashScreenFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionSplashScreenFragmentToAdsFragment() {
    return new ActionOnlyNavDirections(R.id.action_splashScreenFragment_to_adsFragment);
  }
}
