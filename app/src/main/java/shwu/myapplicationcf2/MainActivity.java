package shwu.myapplicationcf2;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.hardware.SensorManager;
import android.widget.ListView;

import java.util.List;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    private SensorManager mSensorManager;
    private List<Sensor> deviceSensors = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = ((ListView) findViewById(R.id.lista));
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert mSensorManager != null;
        deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);

        final ArrayList<String> sensor_str_list = new ArrayList<String>();
        for(Sensor s : deviceSensors) {
            sensor_str_list.add(s.getName());

        }

        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sensor_str_list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String sensorType= String.valueOf(deviceSensors.get(position).getType());
                switch (sensorType){
                    case "8": // sensor de proximidad
                        Intent intent = new Intent(MainActivity.this, proximidad.class);
                        intent.putExtra("DATO_NAME", sensor_str_list.get(position));
                        intent.putExtra("DATO_TYPE", sensorType);
                        startActivity(intent);
                        break;
                    case "1": //sensor acelererometro
                        intent = new Intent(MainActivity.this, acelerometro.class);
                        intent.putExtra("DATO_NAME", sensor_str_list.get(position));
                        intent.putExtra("DATO_TYPE", sensorType);
                        startActivity(intent);
                        break;
                    case "5": //sensor luz
                        intent = new Intent(MainActivity.this, luz.class);
                        intent.putExtra("DATO_NAME", sensor_str_list.get(position));
                        intent.putExtra("DATO_TYPE", sensorType);
                        startActivity(intent);
                        break;
                }

            }
        });

    }

}


//                Toast.makeText(getApplicationContext(),"Position"+position,Toast.LENGTH_SHORT).show();