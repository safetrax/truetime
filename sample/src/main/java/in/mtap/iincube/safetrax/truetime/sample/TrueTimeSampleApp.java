package in.mtap.iincube.safetrax.truetime.sample;

import android.app.Application;

import in.mtap.iincube.truetime.TrueTime;

public class TrueTimeSampleApp extends Application {

  @Override public void onCreate() {
    super.onCreate();
    TrueTime.init(getApplicationContext());
    if (!TrueTime.isAvailable()) {
      TrueTime.downloadTimeInfoAsync();
    }
  }
}
