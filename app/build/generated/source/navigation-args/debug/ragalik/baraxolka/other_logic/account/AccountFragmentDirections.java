package ragalik.baraxolka.other_logic.account;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import ragalik.baraxolka.R;

public class AccountFragmentDirections {
  private AccountFragmentDirections() {
  }

  @NonNull
  public static ActionAccountFragmentToFullImageLayout actionAccountFragmentToFullImageLayout(
      int position, @NonNull ArrayList urls) {
    return new ActionAccountFragmentToFullImageLayout(position, urls);
  }

  @NonNull
  public static ActionAccountFragmentToEditAccountFragment actionAccountFragmentToEditAccountFragment(
      @NonNull String editFlag) {
    return new ActionAccountFragmentToEditAccountFragment(editFlag);
  }

  public static class ActionAccountFragmentToFullImageLayout implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionAccountFragmentToFullImageLayout(int position, @NonNull ArrayList urls) {
      this.arguments.put("position", position);
      if (urls == null) {
        throw new IllegalArgumentException("Argument \"urls\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("urls", urls);
    }

    @NonNull
    public ActionAccountFragmentToFullImageLayout setPosition(int position) {
      this.arguments.put("position", position);
      return this;
    }

    @NonNull
    public ActionAccountFragmentToFullImageLayout setUrls(@NonNull ArrayList urls) {
      if (urls == null) {
        throw new IllegalArgumentException("Argument \"urls\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("urls", urls);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("position")) {
        int position = (int) arguments.get("position");
        __result.putInt("position", position);
      }
      if (arguments.containsKey("urls")) {
        ArrayList urls = (ArrayList) arguments.get("urls");
        if (Parcelable.class.isAssignableFrom(ArrayList.class) || urls == null) {
          __result.putParcelable("urls", Parcelable.class.cast(urls));
        } else if (Serializable.class.isAssignableFrom(ArrayList.class)) {
          __result.putSerializable("urls", Serializable.class.cast(urls));
        } else {
          throw new UnsupportedOperationException(ArrayList.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
        }
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_accountFragment_to_fullImageLayout;
    }

    @SuppressWarnings("unchecked")
    public int getPosition() {
      return (int) arguments.get("position");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public ArrayList getUrls() {
      return (ArrayList) arguments.get("urls");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionAccountFragmentToFullImageLayout that = (ActionAccountFragmentToFullImageLayout) object;
      if (arguments.containsKey("position") != that.arguments.containsKey("position")) {
        return false;
      }
      if (getPosition() != that.getPosition()) {
        return false;
      }
      if (arguments.containsKey("urls") != that.arguments.containsKey("urls")) {
        return false;
      }
      if (getUrls() != null ? !getUrls().equals(that.getUrls()) : that.getUrls() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + getPosition();
      result = 31 * result + (getUrls() != null ? getUrls().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionAccountFragmentToFullImageLayout(actionId=" + getActionId() + "){"
          + "position=" + getPosition()
          + ", urls=" + getUrls()
          + "}";
    }
  }

  public static class ActionAccountFragmentToEditAccountFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionAccountFragmentToEditAccountFragment(@NonNull String editFlag) {
      if (editFlag == null) {
        throw new IllegalArgumentException("Argument \"edit_flag\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("edit_flag", editFlag);
    }

    @NonNull
    public ActionAccountFragmentToEditAccountFragment setEditFlag(@NonNull String editFlag) {
      if (editFlag == null) {
        throw new IllegalArgumentException("Argument \"edit_flag\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("edit_flag", editFlag);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("edit_flag")) {
        String editFlag = (String) arguments.get("edit_flag");
        __result.putString("edit_flag", editFlag);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_accountFragment_to_editAccountFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getEditFlag() {
      return (String) arguments.get("edit_flag");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionAccountFragmentToEditAccountFragment that = (ActionAccountFragmentToEditAccountFragment) object;
      if (arguments.containsKey("edit_flag") != that.arguments.containsKey("edit_flag")) {
        return false;
      }
      if (getEditFlag() != null ? !getEditFlag().equals(that.getEditFlag()) : that.getEditFlag() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getEditFlag() != null ? getEditFlag().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionAccountFragmentToEditAccountFragment(actionId=" + getActionId() + "){"
          + "editFlag=" + getEditFlag()
          + "}";
    }
  }
}
