package com.example.rosaccelpublisher;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerationListener implements SensorEventListener {
    private final Context context;

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        //
    }

    private float[] acceleration = new float[]{0f, 0f, 0f};

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            acceleration[0] = event.values[0];
            acceleration[1] = event.values[1];
            acceleration[2] = event.values[2];
        }
    }

    public float[] getSensorValue()
    {
        return acceleration;
    }

    public AccelerationListener(Context c)
    {
        this.context = c ;
    }

    protected void onResume()
    {
        SensorManager sm = (SensorManager)
                context.getSystemService(Context.SENSOR_SERVICE);
        Sensor accel = sm.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        sm.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onPause()
    {
        SensorManager sm = (SensorManager)
                context.getSystemService(Context.SENSOR_SERVICE);
        sm.unregisterListener(this);
    }
}
