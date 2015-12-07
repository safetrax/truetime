package in.mtap.iincube.truetime;

import android.app.IntentService;
import android.content.Intent;

import java.io.IOException;

public class TimeFetchIntentService extends IntentService {
  public TimeFetchIntentService() {
    super("TimeFetchIntentService");
  }

  @Override protected void onHandleIntent(Intent intent) {
    try {
      TrueTime.downloadTimeInfo(new TrueClock(this));
    } catch (IOException e) {
      // fail silently without any retry
    }
  }
}
