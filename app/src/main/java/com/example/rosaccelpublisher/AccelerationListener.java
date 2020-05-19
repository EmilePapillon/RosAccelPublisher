package com.example.rosaccelpublisher;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class AccelerationListener implements SensorEventListener {
    private final Context context;

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        //
    }

    private float[] orientation = new float[]{0f, 0f, 0f};
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private boolean newAValue = false;
    private boolean newMValue = false;
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        // Log.d("onSensorChanged", "Method called");
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = event.values;
            /*
            Log.d("onSensorChanged", "mGravity new value: " +
                    String.valueOf(mGravity[0]) + " " +
                    String.valueOf(mGravity[1]) + " " +
                    String.valueOf(mGravity[2]) + " ");

             */
            newAValue=true;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mGeomagnetic = event.values;
            /*
            Log.d("onSensorChanged", "mGeomagnetic new value: " +
                    String.valueOf(mGeomagnetic[0]) + " " +
                    String.valueOf(mGeomagnetic[1]) + " " +
                    String.valueOf(mGeomagnetic[2]) + " ");
                    */

            newMValue=true;
        }

        if(newAValue && newMValue)
        {
            float R[] =  new float[9];
            float I[] =  new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success)
            {
               // Log.d("onSensorChanged", "getRotationMatrix Success");
                SensorManager.getOrientation(R, orientation);
            }
            newAValue=false;
            newMValue=false;
        }


    }

    public float[] getSensorValue()
    {
       //Log.d("getSensorValue", String.valueOf(orientation[0]) + " " + String.valueOf(orientation[1]) + " " + String.valueOf(orientation[2]));
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
