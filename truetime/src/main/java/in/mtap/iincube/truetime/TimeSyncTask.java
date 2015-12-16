package in.mtap.iincube.truetime;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.io.IOException;

public class TimeSyncTask extends GcmTaskService {

  @Override public int onRunTask(TaskParams taskParams) {
    try {
      TrueTime.downloadTimeInfo(new TrueClock(this));
      return GcmNetworkManager.RESULT_SUCCESS;
    } catch (IOException e) {
      return GcmNetworkManager.RESULT_RESCHEDULE;
    }
  }
}
