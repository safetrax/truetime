package in.mtap.iincube.truetime;

import java.io.IOException;

public interface TimeFetcher {
  long fetchTime() throws IOException;
}
