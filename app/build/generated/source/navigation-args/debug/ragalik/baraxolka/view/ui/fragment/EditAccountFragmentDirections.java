package ragalik.baraxolka.view.ui.fragment;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import ragalik.baraxolka.R;

public class EditAccountFragmentDirections {
  private EditAccountFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionEditAccountFragmentToAccountFragment() {
    return new ActionOnlyNavDirections(R.id.action_editAccountFragment_to_accountFragment);
  }
}
