package com.example.ballonatable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    public static long x=0;
    public static long y=0;
    public static long z=0;
    private SensorManager sensorManager; // менеджер сенсоров
    private float[] rotationMatrix; // матрица поворота
    private float[] accelerometer;  // данные с акселерометра
    private float[] geomagnetism;   // данные геомагнитного датчика
    private float[] orientation;    // матрица положения в пространстве
    // текстовые поля для вывода информации
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE); //получаем объект менеджера датчиков
        rotationMatrix = new float[16];
        accelerometer = new float[3];
        geomagnetism = new float[3];
        orientation = new float[3];
        Random random = new Random();
        setContentView(new MyDraw(this));
        //setContentView(R.layout.activity_main);

    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI );
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_UI );
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    private void loadSensorData(SensorEvent event) {
        final int type = event.sensor.getType(); //определяем тип датчика
        if (type == Sensor.TYPE_ACCELEROMETER) { //если акселерометр
            accelerometer = event.values.clone();
        }
        if (type == Sensor.TYPE_MAGNETIC_FIELD) { //если геомагнитный датчик
            geomagnetism = event.values.clone();
        }
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        loadSensorData(event); // получаем данные с датчика
        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometer, geomagnetism); //получаем матрицу поворота
        SensorManager.getOrientation(rotationMatrix, orientation); //получаем данные ориентации устройства в пространстве
        x=Math.round(Math.toDegrees(orientation[0]));
        y=Math.round(Math.toDegrees(orientation[1]));
        z=Math.round(Math.toDegrees(orientation[2]));
        /*TextView tx=findViewById(R.id.tx);
        tx.setText(String.valueOf(Math.round(Math.toDegrees(orientation[0])))+" "+String.valueOf(Math.round(Math.toDegrees(orientation[1])))+" "+String.valueOf(Math.round(Math.toDegrees(orientation[2]))));
        <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/tx"/>*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}