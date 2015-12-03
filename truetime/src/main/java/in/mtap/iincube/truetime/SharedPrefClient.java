package in.mtap.iincube.truetime;

import android.content.Context;
import android.content.SharedPreferences;

class SharedPrefClient {
  private static final String PREF_NAME = "true_time";
  private static final String IS_SYNCED = "is_synced";
  private static final String SERVER_TIME = "server_time";
  private static final String ELAPSED_OFFSET = "elapsed_offset";

  private SharedPreferences sharedPreferences;
  private static SharedPrefClient sharedPrefClient = null;

  private SharedPrefClient(Context context) {
    sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
  }

  static SharedPrefClient get(Context context) {
    if (sharedPrefClient == null) {
      sharedPrefClient = new SharedPrefClient(context);
    }
    return sharedPrefClient;
  }

  public boolean isTimeSynced() {
    return sharedPreferences.getBoolean(IS_SYNCED, false);
  }

  public void setTimeSynced(boolean isSynced) {
    sharedPreferences.edit().putBoolean(IS_SYNCED, isSynced).apply();
  }

  public long getServerTimeInMillis() {
    return sharedPreferences.getLong(SERVER_TIME, 0);
  }

  public void setServerTimeInMillis(long elapsedOffset) {
    sharedPreferences.edit().putLong(SERVER_TIME, elapsedOffset).apply();
  }

  public long getElapsedOffsetInMillis() {
    return sharedPreferences.getLong(ELAPSED_OFFSET, 0);
  }

  public void setElapsedOffsetInMillis(long elapsedOffset) {
    sharedPreferences.edit().putLong(ELAPSED_OFFSET, elapsedOffset).apply();
  }
}
