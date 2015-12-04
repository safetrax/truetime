package in.mtap.iincube.truetime;

import java.net.HttpURLConnection;
import java.net.URL;

public class DefaultTimeFetcher implements TimeFetcher {
  private String restUrl;

  public DefaultTimeFetcher(String restUrl) {
    this.restUrl = restUrl;
  }

  @Override public long fetchTime() {
    try {
      URL url = new URL(restUrl);
      HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
      return httpURLConnection.getHeaderFieldDate("Date", 0);
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }
}
