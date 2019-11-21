package com.example.hp.sencer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    TextView textView,textView1;
    Sensor lightSensor,proxySensor,tempSensor,accSensor,magSensor;
    float[] accarr=new float[3];
    float[] magnetic=new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.tvsensor);
        textView1=findViewById(R.id.text1);
        sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);

        lightSensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proxySensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        tempSensor=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        accSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magSensor=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    protected void onResume(){

        super.onResume();
        if(lightSensor!=null){
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(proxySensor!=null){
            sensorManager.registerListener(this,proxySensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(tempSensor!=null){
            sensorManager.registerListener(this,tempSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(accSensor!=null){
            sensorManager.registerListener(this,accSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(magSensor!=null){
            sensorManager.registerListener(this,magSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    public void dothis(View view) {

        List<Sensor> sensorList=sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sb=new StringBuilder();
        for(Sensor s:sensorList){
            String s1="Name: "+s.getName()+" \n Vendor: "+ s.getVendor()+ " \n Motion:"+s.getVersion();
            sb.append(s1+"\n");
        }
        textView.setText(sb);
    }
    protected void onPause(){
        super.onPause();

        sensorManager.unregisterListener(this,proxySensor);
        sensorManager.unregisterListener(this,lightSensor);
        sensorManager.unregisterListener(this,accSensor);
        sensorManager.unregisterListener(this,tempSensor);
        sensorManager.unregisterListener(this,magSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        int sensortype=event.sensor.getType();
        switch (sensortype){
            case Sensor.TYPE_LIGHT:
                break;

            case Sensor.TYPE_PROXIMITY:

                break;

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                break;

            case Sensor.TYPE_ACCELEROMETER:
                accarr=event.values.clone();
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetic=event.values.clone();
                break;
        }
        float[] rotationMatrix=new float[9];
        boolean rotationOK=SensorManager.getRotationMatrix(rotationMatrix,null,accarr,magnetic);

        if(rotationOK){
            float orientation[]=new float[3];
            SensorManager.getOrientation(rotationMatrix,orientation);
            float azimut=orientation[0];
            float pitch=orientation[1];
            float roll=orientation[2];

            textView1.setText("  Azimut: "+azimut+" \n  pitch "+pitch+"  \n  roll "+roll);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
