package in.mtap.iincube.truetime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver {
  @Override public void onReceive(Context context, Intent intent) {
    SharedPrefClient sharedPrefClient = SharedPrefClient.get(context);
    sharedPrefClient.setTimeAvailable(false);
    Intent timeFetchIntent = new Intent(context, TimeFetchIntentService.class);
    context.startService(timeFetchIntent);
  }
}