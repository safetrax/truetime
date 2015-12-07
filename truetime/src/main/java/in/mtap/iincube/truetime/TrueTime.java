package in.mtap.iincube.truetime;

import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.util.Date;

public class TrueTime {
  private static final String DEFAULT_URL = "http://google.com";

  private Context context;
  private static TimeFetcher timeFetcher;
  private Clock clockInstance;

  TrueTime(Context context, TimeFetcher timeFetcher) {
    this(context, timeFetcher, new TrueClock(context));
  }

  TrueTime(Context context, TimeFetcher timeFetcher, Clock clock) {
    this.context = context;
    TrueTime.timeFetcher = timeFetcher;
    this.clockInstance = clock;
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
    return clockInstance.isTimeSet();
  }

  public long getTimeInMillis() {
    return clockInstance.getTime();
  }

  public Date getDate() {
    return new Date(getTimeInMillis());
  }

  public Date toSystemTime(Date date) {
    return new Date(date.getTime() + getTimeInMillis() - new Date().getTime());
  }

  public void downloadTimeInfoAsync() {
    downloadTimeInfoAsync(context);
  }

  public void downloadTimeInfo() throws IOException {
    downloadTimeInfo(clockInstance);
  }

  static void downloadTimeInfo(Clock clock) throws IOException {
    clock.unset();
    long serverTime = timeFetcher.fetchTime();
    clock.setTime(serverTime);
  }

  static void downloadTimeInfoAsync(Context context) {
    Intent intent = new Intent(context, TimeFetchIntentService.class);
    context.startService(intent);
  }
}
