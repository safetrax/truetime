package in.mtap.iincube.truetime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    SharedPrefClient sharedPrefClient = SharedPrefClient.get(context);
    sharedPrefClient.setTimeSynced(false);
    Intent timeSyncIntent = new Intent(context, TimeSyncIntentService.class);
    context.startService(timeSyncIntent);
  }
}
