package shwu.myapplicationcf2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class proximidad extends AppCompatActivity {
    TextView texto;
    SensorManager sensorManager;
    Sensor proximitySensor ;

    SensorEventListener proximitySensorListener;

    ImageView iv;

    private SensorManager mSensorManager;
    private List<Sensor> deviceSensors = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximidad);
        iv = (ImageView) findViewById(R.id.icono);

        Intent intent = getIntent();
        String extras = intent.getStringExtra("DATO_NAME");
        String type_num = intent.getStringExtra("DATO_TYPE");

        int myNum = 0;
        try {
            myNum = Integer.parseInt(type_num);
            }
        catch(NumberFormatException ignored) {
        }

        if (myNum==8){
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            assert sensorManager != null;
            proximitySensor  = sensorManager.getDefaultSensor(myNum);
        }
        else{
            finish();
        }

        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                float value = sensorEvent.values[0];
                getSupportActionBar().setTitle("Proximidad : " + value + " cm");
                float value2 = value; //10-value;
                int newValue = (int) (255f * value2)/10;
                getWindow().getDecorView().setBackgroundColor(Color.rgb(newValue, newValue, 0));
                iv.setMinimumWidth((int) (100*value2));
                iv.setMinimumHeight((int) (100*value2));
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(proximitySensorListener, proximitySensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }
}
