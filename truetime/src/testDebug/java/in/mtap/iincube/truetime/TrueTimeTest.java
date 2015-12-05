package in.mtap.iincube.truetime;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.Mockito.verify;

public class TrueTimeTest {

  private TrueTime getTrueTimeWithFetchTime(long millis, Clock clock) throws IOException {
    TimeFetcher timeFetcher = Mockito.mock(TimeFetcher.class);
    Mockito.when(timeFetcher.fetchTime()).thenReturn(millis);
    return new TrueTime(null, timeFetcher, clock);
  }

  @Test public void fetchAndSetTime() throws IOException {
    Clock clock = Mockito.mock(Clock.class);
    TrueTime trueTime = getTrueTimeWithFetchTime(1000L, clock);
    trueTime.fetchTime();
    verify(clock).setTime(1000L);
  }
}
