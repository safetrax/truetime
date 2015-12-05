package in.mtap.iincube.truetime;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

final class DefaultTimeFetcher implements TimeFetcher {
  private String restUrl;

  public DefaultTimeFetcher(String restUrl) {
    this.restUrl = restUrl;
  }

  @Override public long fetchTime() throws IOException {
    URL url = new URL(restUrl);
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    return httpURLConnection.getHeaderFieldDate("Date", 0);
  }
}
