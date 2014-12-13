package com.dreamers.imedikit;

import java.util.ArrayList;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Process;
import android.os.Vibrator;
import android.os.PowerManager.WakeLock;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class ShakeService extends Service implements SensorEventListener{

    public static final String TAG = ShakeService.class.getName();
    public static final int SCREEN_OFF_RECEIVER_DELAY = 500;

    private SensorManager mSensorManager = null;
    private WakeLock mWakeLock = null;
    
    private ToneGenerator tg;
	Vibrator vibe;
	
	  private static final int FORCE_THRESHOLD = 350;
	  private static final int TIME_THRESHOLD = 100;
	  private static final int SHAKE_TIMEOUT = 500;
	  private static final int SHAKE_DURATION = 1000;
	  private static final int SHAKE_COUNT = 3;
	  
	  private float mLastX=-1.0f, mLastY=-1.0f, mLastZ=-1.0f;
	  private long mLastTime;	  
	  private int mShakeCount = 0;
	  private long mLastShake;
	  private long mLastForce;

	    /*
	     * Register this as a sensor event listener.
	     */
	    private void registerListener() {
	        mSensorManager.registerListener(this,
	                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	                SensorManager.SENSOR_DELAY_GAME);
	    }

	    /*
	     * Un-register this as a sensor event listener.
	     */
	    private void unregisterListener() {
	        mSensorManager.unregisterListener(this);
	    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive("+intent+")");

            if (!intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                return;
            }
             
            Runnable runnable = new Runnable() {
                public void run() {
                    Log.i(TAG, "Runnable executing.");
                    unregisterListener();
                    registerListener();
                }
            };

            new Handler().postDelayed(runnable, SCREEN_OFF_RECEIVER_DELAY);
        }
    };
    
    
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
		if(event.sensor!=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)) return;
        long now = System.currentTimeMillis();
	    if ((now - mLastForce) > SHAKE_TIMEOUT) {
	      mShakeCount = 0;
	    }

	    if ((now - mLastTime) > TIME_THRESHOLD) {
	      long diff = now - mLastTime;
	    //  float speed = Math.abs(values[SensorManager.DATA_X] + values[SensorManager.DATA_Y] + values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ) / diff * 10000;
	      float speed = Math.abs(event.values[0] + event.values[1] + event.values[2] - mLastX - mLastY - mLastZ) / diff * 10000;
	      if (speed > FORCE_THRESHOLD) {
	        if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
	          mLastShake = now;
	          mShakeCount = 0;
	          
	          LocationFinder finder=new LocationFinder();
	          
	        }
	        mLastForce = now;
	      }
	      mLastTime = now;
	      mLastX = event.values[0];
	      mLastY = event.values[1];
	      mLastZ = event.values[2];
	    }
	} 
	
	
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
		vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        PowerManager manager =(PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);

        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        startForeground(Process.myPid(), new Notification());
        registerListener();
        mWakeLock.acquire();
        
        Toast.makeText(getApplicationContext(), "Shaking Service is started", Toast.LENGTH_LONG).show();

        return START_STICKY;
    }
    
    
    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        unregisterListener();
        mWakeLock.release();
        Toast.makeText(this, "Shaking Service Destroyed", Toast.LENGTH_LONG).show();
        stopForeground(true);
    }
    
    
    class LocationFinder implements GooglePlayServicesClient.ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener,LocationListener
    {
    	
    	private LocationClient locationClient;
    	int count=0;
    	ArrayList<String> numbers=new ArrayList<String>();
    	
    	public LocationFinder() {
    		// TODO Auto-generated constructor stub
    		//GetAllNumber obj=new GetAllNumber();
    		//numbers=obj.getNumber();
    		numbers.add("01725793938");
    		locationClient=new LocationClient(getApplicationContext(), this, this);
    		locationClient.connect();
    	}

    	@Override
    	public void onLocationChanged(Location arg0) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void onProviderDisabled(String arg0) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void onProviderEnabled(String arg0) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void onConnected(Bundle arg0) {
    		// TODO Auto-generated method stub
    		count++;
    		Location currentLocation = locationClient.getLastLocation();
    		double lat1=0,lon1=0;
    		try{
    			lat1 = currentLocation.getLatitude();
    			lon1 = currentLocation.getLongitude();
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();				
    		}
    		if(lat1==0||lon1==0)
    		{
    			if(count<=50)
    			{
    				locationClient.disconnect();
    				locationClient.connect();
    			}
    			else
    			{
    		    /*    KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE); 
    		        final KeyguardManager.KeyguardLock kl = km .newKeyguardLock(TAG); 
    		        kl.disableKeyguard(); 

    		        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE); 
    		        WakeLock lock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
    		                                         | PowerManager.ACQUIRE_CAUSES_WAKEUP
    		                                         | PowerManager.ON_AFTER_RELEASE, TAG);
    		        lock.acquire();  */
    		        
        			vibe.vibrate(2000);
    	            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
    	    	   // Toast.makeText(getApplicationContext(), "Your present location not found, try again.",Toast.LENGTH_LONG).show();
    	            SmsManager manager=SmsManager.getDefault();
    	            for(int ind=0;ind<numbers.size();ind++)
    	            {
    	            	String number=numbers.get(ind);
    	            	manager.sendTextMessage(number, null,"harassment-"+lat1+":"+lon1, null, null);
    	            }
    	            
    	          //  lock.release();
    			}
    		}
    		else
    		{
		    /*    KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE); 
		        final KeyguardManager.KeyguardLock kl = km .newKeyguardLock(TAG); 
		        kl.disableKeyguard(); 

		        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE); 
		        WakeLock lock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
		                                         | PowerManager.ACQUIRE_CAUSES_WAKEUP
		                                         | PowerManager.ON_AFTER_RELEASE, TAG);
		        lock.acquire();  */
    			
    			vibe.vibrate(2000);
                tg.startTone(ToneGenerator.TONE_PROP_BEEP);
        	   // Toast.makeText(getApplicationContext(), "Latitude: "+lat1+"\nLOngitude: "+lon1, Toast.LENGTH_LONG).show();
                SmsManager manager=SmsManager.getDefault();
	            for(int ind=0;ind<numbers.size();ind++)
	            {
	            	String number=numbers.get(ind);
	            	manager.sendTextMessage(number, null,"harassment-"+lat1+":"+lon1, null, null);
	            }
	            
	          //  lock.release();
    		}
    	}

    	@Override
    	public void onDisconnected() {
    		// TODO Auto-generated method stub
    		
    	}

    	@Override
    	public void onConnectionFailed(ConnectionResult arg0) {
    		// TODO Auto-generated method stub
    		
    	}
    	
    }

}
