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

    private float[] orientation = new float[]{0f, 0f, 0f};

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float[] mGravity = new float[3];
        float[] mGeomagnetic = new float[3];
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        float R[] =  new float[9];
        float I[] =  new float[9];
        boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
        if (success)
        {
            SensorManager.getOrientation(R, orientation);
        }

    }

    public float[] getSensorValue()
    {
        return orientation;
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
        Sensor magnet = sm.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0); //added
        sm.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
        sm.registerListener(this, magnet, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onPause()
    {
        SensorManager sm = (SensorManager)
                context.getSystemService(Context.SENSOR_SERVICE);
        sm.unregisterListener(this);
    }
}
