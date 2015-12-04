package in.mtap.iincube.truetime;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Date;

public class TrueTime {
  private static final String DEFAULT_URL = "http://in.pool.ntp.org";

  private Context context;
  private TimeFetcher timeFetcher;
  private SharedPrefClient sharedPrefClient;

  private static TrueTime instance = null;

  TrueTime(Context context, TimeFetcher timeFetcher) {
    this.context = context;
    this.sharedPrefClient = SharedPrefClient.get(context);
    this.timeFetcher = timeFetcher;
    TrueTime.instance = this;
  }

  public static TrueTime init(Context context) {
    return new TrueTime(context, new DefaultTimeFetcher(DEFAULT_URL));
  }

  public static TrueTime initWith(Context context, String fetchFromRestUrl) {
    return new TrueTime(context, new DefaultTimeFetcher(fetchFromRestUrl));
  }

  public static TrueTime initWith(Context context, TimeFetcher timeFetcher) {
    return new TrueTime(context, timeFetcher);
  }

  public boolean isAvailable() {
    return sharedPrefClient.isTimeAvailable();
  }

  public long getTimeInMillis() {
    return SystemClock.elapsedRealtime() + sharedPrefClient.getServerTimeInMillis()
        - sharedPrefClient.getElapsedOffsetInMillis();
  }

  public Date getDate() {
    return new Date(getTimeInMillis());
  }

  public Date toSystemTime(Date date) {
    return new Date(date.getTime() + getTimeInMillis() - new Date().getTime());
  }

  public void forceSync() {
    Intent intent = new Intent(context, TimeFetchIntentService.class);
    context.startService(intent);
  }

  static TimeFetcher getTimeFetcher() {
    if (instance == null)
      return null;
    return instance.timeFetcher;
  }
}
