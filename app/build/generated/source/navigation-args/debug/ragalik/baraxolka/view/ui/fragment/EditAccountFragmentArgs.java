package ragalik.baraxolka.view.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class EditAccountFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private EditAccountFragmentArgs() {
  }

  private EditAccountFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static EditAccountFragmentArgs fromBundle(@NonNull Bundle bundle) {
    EditAccountFragmentArgs __result = new EditAccountFragmentArgs();
    bundle.setClassLoader(EditAccountFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("edit_flag")) {
      String editFlag;
      editFlag = bundle.getString("edit_flag");
      if (editFlag == null) {
        throw new IllegalArgumentException("Argument \"edit_flag\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("edit_flag", editFlag);
    } else {
      throw new IllegalArgumentException("Required argument \"edit_flag\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getEditFlag() {
    return (String) arguments.get("edit_flag");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("edit_flag")) {
      String editFlag = (String) arguments.get("edit_flag");
      __result.putString("edit_flag", editFlag);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    EditAccountFragmentArgs that = (EditAccountFragmentArgs) object;
    if (arguments.containsKey("edit_flag") != that.arguments.containsKey("edit_flag")) {
      return false;
    }
    if (getEditFlag() != null ? !getEditFlag().equals(that.getEditFlag()) : that.getEditFlag() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getEditFlag() != null ? getEditFlag().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "EditAccountFragmentArgs{"
        + "editFlag=" + getEditFlag()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(EditAccountFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull String editFlag) {
      if (editFlag == null) {
        throw new IllegalArgumentException("Argument \"edit_flag\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("edit_flag", editFlag);
    }

    @NonNull
    public EditAccountFragmentArgs build() {
      EditAccountFragmentArgs result = new EditAccountFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setEditFlag(@NonNull String editFlag) {
      if (editFlag == null) {
        throw new IllegalArgumentException("Argument \"edit_flag\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("edit_flag", editFlag);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getEditFlag() {
      return (String) arguments.get("edit_flag");
    }
  }
}
