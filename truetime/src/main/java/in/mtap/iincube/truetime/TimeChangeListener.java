package in.mtap.iincube.truetime;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.util.Date;

public class TimeChangeListener implements TrueTime.Listener {
  private HttpURLConnection httpURLConnection;

  public TimeChangeListener(URLConnection urlConnection) {
    this.httpURLConnection = (HttpURLConnection) urlConnection;
  }

  @Override public void onTimeChanged() {
    try {
      httpURLConnection.setRequestMethod("GET");
      long serverTime = httpURLConnection.getHeaderFieldDate("Date", 0);
      if (serverTime != 0) {
        long offset = new Date().getTime() - serverTime;
        TrueTime.getSharedPrefClient().setOffset(offset);
        TrueTime.setPending(false);
      }
    } catch (IOException e) {
      e.printStackTrace();
      TrueTime.setPending(true);
    }
  }
}
