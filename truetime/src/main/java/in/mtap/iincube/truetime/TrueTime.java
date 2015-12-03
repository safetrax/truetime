package in.mtap.iincube.truetime;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Date;

public class TrueTime {
  private static TrueTime singleton = null;

  private static SyncTask listener = null;
  private SharedPrefClient sharedPrefClient;
  private Context context;

  private TrueTime(Context context) {
    this.context = context;
    this.sharedPrefClient = SharedPrefClient.get(context);
  }

  public static TrueTime with(Context context) {
    if (singleton == null) {
      singleton = new TrueTime(context);
    }
    return singleton;
  }

  public boolean isTrue() {
    return sharedPrefClient.isTimeSynced();
  }

  public Date getCurrentTime() {
    return new Date(SystemClock.elapsedRealtime() + sharedPrefClient.getServerTimeInMillis()
        - sharedPrefClient.getElapsedOffsetInMillis());
  }

  public Date getTrueDate(Date date) {
    return new Date(date.getTime() + getOffset());
  }

  static SyncTask getSyncTask() {
    return listener;
  }

  public void setSyncTask(SyncTask listener) {
    TrueTime.listener = listener;
  }

  public void forceSync() {
    Intent intent = new Intent(context, TimeSyncIntentService.class);
    context.startService(intent);
  }

  private long getOffset() {
    return (getCurrentTime().getTime() - new Date().getTime());
  }

  interface SyncTask {
    void doSync(Context context);
  }
}
