package in.mtap.iincube.truetime;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.verify;

public class TrueTimeTest {

  private void setUpTrueTime(long millis, Clock clock) throws IOException {
    TimeFetcher timeFetcher = Mockito.mock(TimeFetcher.class);
    Mockito.when(timeFetcher.fetchTime()).thenReturn(millis);
    TrueTime.initWith(null, timeFetcher, clock);
  }

  @Test public void fetchAndSetTime() throws IOException {
    Clock clock = Mockito.mock(Clock.class);
    setUpTrueTime(1000L, clock);
    TrueTime.downloadTimeInfo();
    verify(clock).setTime(1000L);
  }
}
