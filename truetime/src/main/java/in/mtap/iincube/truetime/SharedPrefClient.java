package in.mtap.iincube.truetime;

import android.content.Context;
import android.content.SharedPreferences;

class SharedPrefClient {
  static final String PREF_NAME = "offset_pref";
  static final String OFFSET = "offset";

  SharedPreferences sharedPreferences;

  SharedPrefClient(Context context) {
    sharedPreferences = context.getSharedPreferences(PREF_NAME, 0);
  }

  Long getOffset() {
    return sharedPreferences.getLong(OFFSET, 0);
  }

  void setOffset(long offset) {
    sharedPreferences.edit().putLong(OFFSET, offset).apply();
  }
}
