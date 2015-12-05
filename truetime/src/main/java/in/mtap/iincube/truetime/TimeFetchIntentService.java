package in.mtap.iincube.truetime;

import android.app.IntentService;
import android.content.Intent;

public class TimeFetchIntentService extends IntentService {
  public TimeFetchIntentService() {
    super("TimeFetchIntentService");
  }

  @Override protected void onHandleIntent(Intent intent) {
    TrueTime.fetchTimeSync(new TrueClock(this));
  }
}
