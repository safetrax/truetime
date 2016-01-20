package in.mtap.iincube.safetrax.truetime.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import in.mtap.iincube.truetime.TrueTime;

public class ExampleActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.example_layout);
    TextView txtTrueTime = (TextView) findViewById(R.id.txt_true_time);
    txtTrueTime.setText(String.format("%s is %s", TrueTime.getDate().toString(),
        TrueTime.isAvailable()));
  }
}
