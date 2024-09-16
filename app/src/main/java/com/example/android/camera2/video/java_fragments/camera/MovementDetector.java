package com.example.android.camera2.video.java_fragments.camera;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MovementDetector implements SensorEventListener {

    private final SensorManager sensorManager;
    private Sensor gyroscopeSensor;
    private Sensor accelerometerSensor;
    private final MovementListener listener;

    private static final float RIGHT_TO_LEFT_THRESHOLD = 1.0f; // Threshold for detecting right-to-left movement
    private static final float FAST_MOVEMENT_THRESHOLD = 5.0f; // Threshold for fast movement

    public MovementDetector(Context context, MovementListener listener) {
        this.listener = listener;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // Initialize the gyroscope sensor
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        }

        // Initialize the accelerometer sensor
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    public void start() {
        if (gyroscopeSensor != null) {
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Handle gyroscope sensor for detecting direction of movement
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            detectRightToLeft(event);
        }

        // Handle accelerometer sensor for detecting fast movement
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            detectFastMovement(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ignore this for now
    }

    private void detectRightToLeft(SensorEvent event) {
        float rotationY = event.values[1]; // Rotation around Y-axis

        if (rotationY > RIGHT_TO_LEFT_THRESHOLD && listener != null) {
            listener.onRightToLeftMovement();
        }
    }

    private void detectFastMovement(SensorEvent event) {
        float acceleration = event.values[0]; // Acceleration on X-axis

        if (Math.abs(acceleration) > FAST_MOVEMENT_THRESHOLD && listener != null) {
            listener.onFastMovement();
        }
    }

    // Interface for detecting movements
    public interface MovementListener {
        void onRightToLeftMovement();

        void onFastMovement();
    }
}
