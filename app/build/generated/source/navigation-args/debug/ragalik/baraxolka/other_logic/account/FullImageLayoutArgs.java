package ragalik.baraxolka.other_logic.account;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;

public class FullImageLayoutArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private FullImageLayoutArgs() {
  }

  private FullImageLayoutArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static FullImageLayoutArgs fromBundle(@NonNull Bundle bundle) {
    FullImageLayoutArgs __result = new FullImageLayoutArgs();
    bundle.setClassLoader(FullImageLayoutArgs.class.getClassLoader());
    if (bundle.containsKey("position")) {
      int position;
      position = bundle.getInt("position");
      __result.arguments.put("position", position);
    } else {
      throw new IllegalArgumentException("Required argument \"position\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("urls")) {
      ArrayList urls;
      if (Parcelable.class.isAssignableFrom(ArrayList.class) || Serializable.class.isAssignableFrom(ArrayList.class)) {
        urls = (ArrayList) bundle.get("urls");
      } else {
        throw new UnsupportedOperationException(ArrayList.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (urls == null) {
        throw new IllegalArgumentException("Argument \"urls\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("urls", urls);
    } else {
      throw new IllegalArgumentException("Required argument \"urls\" is missing and does not have an android:defaultValue");
    }
    return __result;
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

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
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
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    FullImageLayoutArgs that = (FullImageLayoutArgs) object;
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
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getPosition();
    result = 31 * result + (getUrls() != null ? getUrls().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "FullImageLayoutArgs{"
        + "position=" + getPosition()
        + ", urls=" + getUrls()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(FullImageLayoutArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(int position, @NonNull ArrayList urls) {
      this.arguments.put("position", position);
      if (urls == null) {
        throw new IllegalArgumentException("Argument \"urls\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("urls", urls);
    }

    @NonNull
    public FullImageLayoutArgs build() {
      FullImageLayoutArgs result = new FullImageLayoutArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setPosition(int position) {
      this.arguments.put("position", position);
      return this;
    }

    @NonNull
    public Builder setUrls(@NonNull ArrayList urls) {
      if (urls == null) {
        throw new IllegalArgumentException("Argument \"urls\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("urls", urls);
      return this;
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
  }
}
