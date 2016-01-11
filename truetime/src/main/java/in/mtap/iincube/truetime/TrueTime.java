package in.mtap.iincube.truetime;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;
import com.google.android.gms.gcm.Task;

import java.io.IOException;
import java.util.Date;

public class TrueTime {
  private static final String DEFAULT_URL = "http://google.com";

  private static Context context;
  private static TimeFetcher timeFetcher;
  private static Clock clockInstance;

  private TrueTime() {
    throw new AssertionError("No instances allowed");
  }

  public static void init(Context context) {
    initWith(context, DEFAULT_URL);
  }

  public static void initWith(Context context, String fetchFromRestUrl) {
    initWith(context, new DefaultTimeFetcher(fetchFromRestUrl));
  }

  static void initWith(Context context, TimeFetcher timeFetcher, Clock clock) {
    TrueTime.context = context;
    TrueTime.timeFetcher = timeFetcher;
    TrueTime.clockInstance = clock;
  }

  public static void initWith(Context context, TimeFetcher timeFetcher) {
    initWith(context, timeFetcher, new TrueClock(context));
  }

  public static boolean isAvailable() {
    return clockInstance.isTimeSet();
  }

  public static long getTimeInMillis() {
    return clockInstance.getTime();
  }

  public static Date getDate() {
    return new Date(getTimeInMillis());
  }

  public static Date toSystemTime(Date date) {
    return new Date(date.getTime() + getTimeInMillis() - new Date().getTime());
  }

  public static void downloadTimeInfoAsync() {
    downloadTimeInfoAsync(context);
  }

  public static void downloadTimeInfo() throws IOException {
    downloadTimeInfo(clockInstance);
  }

  static void downloadTimeInfo(Clock clock) throws IOException {
    clock.unset();
    long serverTime = timeFetcher.fetchTime();
    clock.setTime(serverTime);
  }

  static void downloadTimeInfoAsync(Context context) {
    Task timeSyncTask = new OneoffTask.Builder().setService(TimeSyncTask.class)
        .setPersisted(false)
        .setExecutionWindow(5, 60)
        .setRequiresCharging(false)
        .setTag("in.mtap.iincube.truetime_sync").build();
    GcmNetworkManager.getInstance(context).schedule(timeSyncTask);
  }
}
