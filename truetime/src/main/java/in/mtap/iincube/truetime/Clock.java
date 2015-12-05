package in.mtap.iincube.truetime;

interface Clock {
  boolean isTimeSet();

  void setTime(long millis);

  void unset();

  long getTime();
}
