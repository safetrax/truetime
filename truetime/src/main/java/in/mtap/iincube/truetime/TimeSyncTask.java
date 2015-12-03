package in.mtap.iincube.truetime;

import android.content.Context;
import android.os.SystemClock;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class TimeSyncTask implements TrueTime.SyncTask {
  private URI uri;

  public TimeSyncTask(URI uri) {
    this.uri = uri;
  }

  @Override public void doSync(Context context) {
    try {
      URL url = uri.toURL();
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestMethod("GET");
      long serverTime = httpURLConnection.getHeaderFieldDate("Date", 0);
      if (serverTime != 0) {
        SharedPrefClient sharedPrefClient = SharedPrefClient.get(context);
        sharedPrefClient.setServerTimeInMillis(serverTime);
        sharedPrefClient.setElapsedOffsetInMillis(SystemClock.elapsedRealtime());
        sharedPrefClient.setTimeSynced(true);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
