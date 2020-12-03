package com.example.mysensors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.github.nisrulz.sensey.LightDetector;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;
import com.github.nisrulz.sensey.TouchTypeDetector;

public class MainActivity extends AppCompatActivity {
    Switch s1,s2,s3;
    TextView tv;
    SensorManager manager;
    Sensor sensor,acsensor;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = findViewById(R.id.ssensor);
        s2 = findViewById(R.id.lsensor);
        s3 = findViewById(R.id.tsensor);
        tv = findViewById(R.id.sd);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        acsensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        layout = findViewById(R.id.llayout);

        /*This code is with 3party dependencies*/
        /*Initialise Sensey*/
        Sensey.getInstance().init(MainActivity.this);

        /*Shake Sensor*/
        ShakeDetector.ShakeListener slistener = new ShakeDetector.ShakeListener() {
            @Override
            public void onShakeDetected() {
                tv.setText("Shake Sensor Detected");
            }

            @Override
            public void onShakeStopped() {
                tv.setText("Shake Sensor Stopped");
            }
        };
        /*Implement click event for shake sensor using switch*/
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Sensey.getInstance().startShakeDetection(slistener);
                }else{
                    Sensey.getInstance().stopShakeDetection(slistener);
                }
            }
        });

        /*Light Sensor (Automatic Brightness)*/
        LightDetector.LightListener llistener = new LightDetector.LightListener() {
            @Override
            public void onDark() {
                tv.setText("It is dark room");
                layout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                        R.color.purple_700));
            }

            @Override
            public void onLight() {
                tv.setText("It is light room");
                layout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                        R.color.teal_200));
            }
        };
        /*Implement click event for light sensor using switch*/
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Sensey.getInstance().startLightDetection(llistener);
                }else{
                    Sensey.getInstance().stopLightDetection(llistener);
                }
            }
        });
        /*Touch Sensor*/
        TouchTypeDetector.TouchTypListener tlistener = new TouchTypeDetector.TouchTypListener() {
            @Override
            public void onDoubleTap() {
                tv.setText("Double Tapped");
            }

            @Override
            public void onLongPress() {
                tv.setText("Long Pressed");
            }

            @Override
            public void onScroll(int i) {
                tv.setText("Scrolled");
            }

            @Override
            public void onSingleTap() {
                tv.setText("Single Tap");
            }

            @Override
            public void onSwipe(int i) {
                tv.setText("Swiped");
            }

            @Override
            public void onThreeFingerSingleTap() {
                tv.setText("Tapped with 3 fingers");
            }

            @Override
            public void onTwoFingerSingleTap() {
                tv.setText("Tapped with 2 fingers");
            }
        };
        /*Implement click event for touch sensor using switch*/
        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Sensey.getInstance().startTouchTypeDetection(MainActivity.this,tlistener);
                }else{
                    Sensey.getInstance().stopTouchTypeDetection();
                }
            }
        });
        /*Third party dependency code ends here*/

        /*Proxmity Sensor*/
        SensorEventListener plistener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (sensorEvent.values[0] == 0) {
                        tv.setText("Near");
                        layout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.red));
                    } else {
                        tv.setText("Far");
                        layout.setBackgroundColor(ContextCompat.getColor(MainActivity.this,
                                R.color.teal_200));
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        if (sensor==null){
            tv.setText("No proximity Sensor");
        }else{
            manager.registerListener(plistener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Sensey.getInstance().setupDispatchTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Sensey.getInstance().stop();
    }
}