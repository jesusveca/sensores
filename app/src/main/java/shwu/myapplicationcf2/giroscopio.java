package shwu.myapplicationcf2;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class giroscopio extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;
    int whip = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giroscopio);

        sensorManager= (SensorManager)getSystemService(SENSOR_SERVICE);
        assert sensorManager != null;
        sensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensor==null)
            finish();

        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorevent) {
                float x = sensorevent.values[0];
                float y = sensorevent.values[1];
                float z = sensorevent.values[2];
                System.out.println("Valor GiroX"  + x);
                if (x<-5 && whip ==0){
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    whip++;
                } else if(x>-5 && whip ==1) {
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.RED);

                }

                if(whip ==2){
                    sound();
                    whip=0;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        Start();
    }


    private void sound(){
        MediaPlayer mediaPlayer=MediaPlayer.create(this, R.raw.latigo);
        mediaPlayer.start();

    }

    private void Start(){
        sensorManager.registerListener(sensorEventListener,sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void Stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Start();
    }
}

