package in.mtap.iincube.truetime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;

final class TrueClock implements Clock {
  private static final String PREF_NAME = "in.mtap.iincube.truetime.PREF_TRUE_TIME";
  private static final String IS_TIME_AVAILABLE = "is_time_available";
  private static final String SERVER_TIME = "server_time";
  private static final String ELAPSED_OFFSET = "elapsed_offset";

  private SharedPreferences sharedPreferences;

  public TrueClock(Context context) {
    this(context.getSharedPreferences(PREF_NAME, 0));
  }

  TrueClock(SharedPreferences sharedPreferences) {
    this.sharedPreferences = sharedPreferences;
  }

  @Override public boolean isTimeSet() {
    return sharedPreferences.getBoolean(IS_TIME_AVAILABLE, false);
  }

  @Override public void setTime(long millis) {
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putBoolean(IS_TIME_AVAILABLE, true);
    editor.putLong(ELAPSED_OFFSET, SystemClock.elapsedRealtime());
    editor.putLong(SERVER_TIME, millis);
    editor.apply();
  }

  @Override public void unset() {
    sharedPreferences.edit().clear().apply();
  }

  @Override public long getTime() {
    return SystemClock.elapsedRealtime() + sharedPreferences.getLong(SERVER_TIME, 0)
        - sharedPreferences.getLong(ELAPSED_OFFSET, 0);
  }
}
