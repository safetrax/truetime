package in.mtap.iincube.truetime;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;

public class TimeFetchIntentService extends IntentService {
  public TimeFetchIntentService() {
    super("TimeFetchIntentService");
  }

  @Override protected void onHandleIntent(Intent intent) {
    TimeFetcher timeFetcher = TrueTime.getTimeFetcher();
    if (timeFetcher == null)
      return;
    long serverTime = timeFetcher.fetchTime();
    if (serverTime > 0) {
      SharedPrefClient sharedPrefClient = SharedPrefClient.get(this);
      sharedPrefClient.setServerTimeInMillis(serverTime);
      sharedPrefClient.setElapsedOffsetInMillis(SystemClock.elapsedRealtime());
      sharedPrefClient.setTimeAvailable(true);
    }
  }
}
