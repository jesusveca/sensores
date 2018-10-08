package shwu.myapplicationcf2;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class luz extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor lightSensor;
    private SensorEventListener lightEventListener;
    private View root;
    private float maxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luz);

        root = findViewById(R.id.root);

        Intent intent = getIntent();
        String extras = intent.getStringExtra("DATO_NAME");
        String type_num = intent.getStringExtra("DATO_TYPE");

        int myNum = 0;
        try {
            myNum = Integer.parseInt(type_num);
        }
        catch(NumberFormatException ignored) {
        }

        if (myNum==5){
            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            assert sensorManager != null;
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
        else{
            finish();
        }


        if (lightSensor == null) {
            Toast.makeText(this, "El dispositivo no tiene sensor de luz!", Toast.LENGTH_SHORT).show();
            finish();
        }

        maxValue = lightSensor.getMaximumRange();

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float value = sensorEvent.values[0];
                getSupportActionBar().setTitle("Luminosidad : " + value + " lx");
                int newValue = (int) (255f * value / maxValue);
                root.setBackgroundColor(Color.rgb(newValue, newValue, newValue));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }
}