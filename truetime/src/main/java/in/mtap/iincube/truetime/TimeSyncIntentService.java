package in.mtap.iincube.truetime;

import android.app.IntentService;
import android.content.Intent;

public class TimeSyncIntentService extends IntentService {
  public TimeSyncIntentService() {
    super("TimeSyncIntentService");
  }

  @Override protected void onHandleIntent(Intent intent) {
    if (TrueTime.isPending() && TrueTime.getListener() != null)
      TrueTime.getListener().onTimeChanged();
  }
}
