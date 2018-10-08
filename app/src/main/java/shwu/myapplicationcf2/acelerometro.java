package shwu.myapplicationcf2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class acelerometro extends AppCompatActivity  implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private AnimatedView mAnimatedView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String type_num = intent.getStringExtra("DATO_TYPE");

        int myNum = 0;
        try {
            myNum = Integer.parseInt(type_num);
        }
        catch(NumberFormatException ignored) {
        }

        if (myNum!=1){
            finish();
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        assert mSensorManager != null;
        mAccelerometer = mSensorManager.getDefaultSensor(myNum);
        mAnimatedView = new AnimatedView(this);
        mAnimatedView.setBackgroundResource(R.drawable.estadio2);

        setContentView(mAnimatedView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) { }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAnimatedView.onSensorEvent(event);
        }
    }

    public class AnimatedView extends View {
        private int CIRCLE_RADIUS = 100;
        public int contador = 0;
        private Paint mPaint;

        private int x;
        private int y;
        private int z;

        private int viewWidth;
        private int viewHeight;

        public AnimatedView(Context context) {
            super(context);
            mPaint = new Paint();
            mPaint.setColor(Color.YELLOW);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewWidth = w;
            viewHeight = h;
        }

        public void onSensorEvent (SensorEvent event) {
            x = x - (int) event.values[0];
            y = y + (int) event.values[1];
            z = (int) event.values[2];

            if (x <= 0 + CIRCLE_RADIUS) {
                x = 0 + CIRCLE_RADIUS;
                mPaint.setColor(Color.BLUE);
                contador++;
                if(contador%10==0){
                    CIRCLE_RADIUS = CIRCLE_RADIUS-1;
                }
             }
            if (x >= viewWidth - CIRCLE_RADIUS) {
                x = viewWidth - CIRCLE_RADIUS;
                mPaint.setColor(Color.BLUE);
                contador++;
                if(contador%10==0){
                    CIRCLE_RADIUS = CIRCLE_RADIUS-1;
                }
            }
            if (y <= 0 + CIRCLE_RADIUS) {
                y = 0 + CIRCLE_RADIUS;
                mPaint.setColor(Color.RED);
                contador++;
                if(contador%10==0){
                    CIRCLE_RADIUS = CIRCLE_RADIUS+1;
                }
            }
            if (y >= viewHeight - CIRCLE_RADIUS) {
                y = viewHeight - CIRCLE_RADIUS;
                mPaint.setColor(Color.RED);
                contador++;
                if(contador%10==0){
                    CIRCLE_RADIUS = CIRCLE_RADIUS+1;
                }
            }
        }
        @Override
        protected void onDraw(Canvas canvas) {
//            System.out.printf("Valor de z: %d\n",z);
            if(z>=0){
                canvas.drawCircle(x+5, y+5, CIRCLE_RADIUS, mPaint);
                invalidate();
            }
            else {
                canvas.drawCircle(x-5, y-5, CIRCLE_RADIUS, mPaint);
                invalidate();
            }


        }
    }
}
