package in.mtap.iincube.truetime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class TrueTime {

  private Context context;
  private static Listener listener;
  private static SharedPrefClient sharedPrefClient;
  private static boolean isPending = false;
  private static TrueTime singleton = null;

  final TimeChangedReceiver timeChangedReceiver = new TimeChangedReceiver();
  final NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();

  private TrueTime(Context context) {
    TrueTime.sharedPrefClient = new SharedPrefClient(context);
    this.context = context;
    listen();
  }

  public static TrueTime with(Context context) {
    if (singleton == null) {
      singleton = new TrueTime(context);
    }
    return singleton;
  }

  private void listen() {
    IntentFilter timeFilter = new IntentFilter();
    timeFilter.addAction(Intent.ACTION_TIME_CHANGED);
    timeFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
    context.registerReceiver(timeChangedReceiver, timeFilter);
    IntentFilter networkFilter = new IntentFilter();
    networkFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    context.registerReceiver(networkStateReceiver, networkFilter);
  }

  public void shutDown() {
    try {
      context.unregisterReceiver(timeChangedReceiver);
      context.unregisterReceiver(networkStateReceiver);
    } catch (Exception e) {
      e.printStackTrace();
    }
    singleton = null;
  }

  public void setListener(@NonNull Listener listener) {
    this.listener = listener;
  }

  public static Long getOffset() {
    return sharedPrefClient.getOffset();
  }

  static Listener getListener() {
    return listener;
  }

  static SharedPrefClient getSharedPrefClient() {
    return sharedPrefClient;
  }

  static boolean isPending() {
    return isPending;
  }

  static void setPending(boolean isPending) {
    TrueTime.isPending = isPending;
  }

  private class TimeChangedReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
      doInBackground();
    }
  }

  private class NetworkStateReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
      ConnectivityManager manager = (ConnectivityManager) context.getSystemService(
          CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = manager.getActiveNetworkInfo();
      if (networkInfo != null && networkInfo.isConnected() && isPending)
        doInBackground();
    }
  }

  private void doInBackground() {
    Intent intent = new Intent(context, TimeChangeListener.class);
    context.startService(intent);
  }

  interface Listener {
    void onTimeChanged();
  }
}
