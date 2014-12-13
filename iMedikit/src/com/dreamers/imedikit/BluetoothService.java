package com.dreamers.imedikit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;


import android.app.KeyguardManager;
import android.app.Notification;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Process;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class BluetoothService extends Service{
	public static final String TAG = BluetoothService.class.getName();

	
	
	Spinner range;
	Button connect,send,close;
	String value="";
	boolean stop=false;
	boolean press=false;
	boolean notStoped=false;
	boolean count=true;
	boolean initiated=false;
	boolean change=false;
	Button stopSound;
	MediaPlayer player;
	 BluetoothAdapter mBluetoothAdapter;
	    BluetoothSocket mmSocket;
	    BluetoothDevice mmDevice;
	    OutputStream mmOutputStream;
	    InputStream mmInputStream;
	    Thread workerThread;
	    byte[] readBuffer;
	    int readBufferPosition;
	    int counter;
	    volatile boolean stopWorker;
	    ArrayList<String> data=new ArrayList<String>();
	
	HashMap<String,String> map=new HashMap<String, String>();
	
	
	
	
	
	
	
	

    private WakeLock mWakeLock = null;
    
    private ToneGenerator tg;
	Vibrator vibe;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
        tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
		vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		player=MediaPlayer.create(this, R.raw.alert);
        PowerManager manager =(PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		Toast.makeText(getApplicationContext(), "Device Service statrted", Toast.LENGTH_LONG).show();
        startForeground(Process.myPid(), new Notification());
        mWakeLock.acquire();
        
        
        try 
        {
            findBT();
            openBT();
        }
        catch (Exception ex) { }

		return START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		  mWakeLock.release();
		Toast.makeText(getApplicationContext(), "Device Service destroyed", Toast.LENGTH_LONG).show();
		stopForeground(true);
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
	 void findBT()
	    {
	        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	        if(mBluetoothAdapter == null)
	        {
	           // myLabel.setText("No bluetooth adapter available");
	            Toast.makeText(getApplicationContext(), "No bluetooth adapter available", Toast.LENGTH_LONG).show();
	  	      
	        }
	        
	        if(!mBluetoothAdapter.isEnabled())
	        {
	            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	         //   startActivityForResult(enableBluetooth, 0);
	          //  myLabel.setText("Bluetooth Device Found");
	            Toast.makeText(getApplicationContext(), "Bluetooth Device Found", Toast.LENGTH_LONG).show();
	  	      
	        }
	        
	        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
	        if(pairedDevices.size() > 0)
	        {
	        	
	            for(BluetoothDevice device : pairedDevices)
	            {
	            	Toast.makeText(getApplicationContext(), device.getName(),Toast.LENGTH_LONG).show();
	                if(device.getName().equals("RMA_DREAMERS")) 
	                {
	                    mmDevice = device;
	                    break;
	                    
	                }
	            }
	        }
	       
	    }
	    
	    void openBT() throws IOException
	    {
	    	
	    	try
	    	{
	    	
	        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
	       
	    		// UUID uuid = UUID.fromString("00001101-0000-1000-8000-98D331801551");	
	    		mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);    
	    		 // Make a connection to the BluetoothSocket 
	    		if (mBluetoothAdapter.isEnabled()) mBluetoothAdapter.cancelDiscovery();
	        mmSocket.connect();
	        mmOutputStream = mmSocket.getOutputStream();
	        mmInputStream = mmSocket.getInputStream();
	        
	        beginListenForData();
	        
	       // myLabel.setText("Bluetooth Opened");
	    	Toast.makeText(getApplicationContext(), "Bluetooth Opened", Toast.LENGTH_LONG).show();
	        
	    	}
	    	
	    	catch(Exception e)
	    	
	    	{
	    		Toast.makeText(getApplicationContext(), e+"", Toast.LENGTH_LONG).show();
	    	}
	    }
	
	    void sendData() throws IOException
	    {
	    	
	    	if(change==true)
	    	{
	    	String val= data.get(range.getSelectedItemPosition());
	    	
	    	String msg=map.get(val);
	    	value=msg;
	     //   String msg = myTextbox.getText().toString();
	        msg += "\n";
	        mmOutputStream.write(msg.getBytes());
	       // myLabel.setText("Data Sent");
	  		Toast.makeText(getApplicationContext(), "Send", Toast.LENGTH_LONG).show();
	  	  
	    	}
	    	else
	    	{
	    		Toast.makeText(getApplicationContext(), "Range Already Changed", Toast.LENGTH_LONG).show();
	    		
	    	}
	           
	    }
	    
	    void closeBT() throws IOException
	    {
	        stopWorker = true;
	        mmOutputStream.close();
	        mmInputStream.close();
	        mmSocket.close();
	        //myLabel.setText("Bluetooth Closed");
	        Toast.makeText(getApplicationContext(), "Bluetooth Closed", Toast.LENGTH_LONG).show();
	        player.stop();  
	    }
	

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    void beginListenForData()
	    {
	        final Handler handler = new Handler(); 
	        final byte delimiter = 10; //This is the ASCII code for a newline character
	        
	        stopWorker = false;
	        readBufferPosition = 0;
	        readBuffer = new byte[1024];
	        workerThread = new Thread(new Runnable()
	        {
	            public void run()
	            {                
	               while(!Thread.currentThread().isInterrupted() && !stopWorker)
	               {
	                    try 
	                    {
	                        int bytesAvailable = mmInputStream.available();                        
	                        if(bytesAvailable > 0)
	                        {
	                            byte[] packetBytes = new byte[bytesAvailable];
	                            mmInputStream.read(packetBytes);
	                            for(int i=0;i<bytesAvailable;i++)
	                            {
	                                byte b = packetBytes[i];
	                                if(b == delimiter)
	                                {
	                                    byte[] encodedBytes = new byte[readBufferPosition];
	                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
	                                    final String data = new String(encodedBytes, "US-ASCII");
	                                    readBufferPosition = 0;
	                                   
	                                    handler.post(new Runnable()
	                                    {
	                                        public void run()
	                                        {
	                                          //  myLabel.setText(data);
	                                           // Toast.makeText(getApplicationContext(), data +" code="+data.codePointAt(0)+" length= "+data.length()+"  "+"  second="+data.codePointAt(1), Toast.LENGTH_SHORT).show();
	                                        	Toast.makeText(getApplicationContext(), data , Toast.LENGTH_SHORT).show();
	                                            
	                                 	      
	                                 	       if(data.equals(value))
	                                 	       {
	                                 	    	   Toast.makeText(getApplicationContext(), "Range Changed Succesfully", Toast.LENGTH_SHORT).show();
	  	                                 	     	   change=false;
	                                 	       }
	                                 	       
	                                 	       else if(data.codePointAt(0)==120)
	                                 	       {
	                                 	    		
	                                 	    	 // Toast.makeText(getApplicationContext(), "inside x", Toast.LENGTH_SHORT).show();
		                                          
	                                 	    	  if(count==true)
	                                 	    	  {
	                                 	    		 AsyncTaskRunner task=new AsyncTaskRunner();
	                                 	    		 task.execute();
	                                 	    		 count=false;
	                                 	    		 initiated=true;
	                                 	    		 stop=false;
	                                 	    	  }
	                                 	    	 
	                                 	    	   
	                                 	       }
	                                 	       else if(data.codePointAt(0)==121)
	                                 	       {
	                                 	    	//  Toast.makeText(getApplicationContext(), "End", Toast.LENGTH_SHORT).show();
	                                 	    		if(initiated)
	                                 	    		{
	                                 	    			notStoped=false;
	                                 	    			initiated=false;
	                                 	    			stop=true;
	                                 	    		}
	                                 	    	   
	                                 	       }
	                                 	       
	                                 	       else if(notStoped==true && stop==false)
	                                 	       {
	                                 	    	   
	                                 	    	//  Toast.makeText(getApplicationContext(), "The user is victimized", Toast.LENGTH_SHORT).show();
		                                 	    	stop=true;
		                                 	    //	player.start();
		                      // ekhan theke sms send korte hobe
		                                 	    	
		                                 	    	LocationFinder finder=new LocationFinder();
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	 Toast.makeText(getApplicationContext(), "The user is victimized", Toast.LENGTH_SHORT).show();
				                                 	    
			                                 	    	try
			                                 	    	{
			                                 	    		stop=true;
				                                 	    	player.start();
				                                 	    	stopWorker = true;
				                                 	    	notStoped=true;
				                                 	    	stop=false;
				                                 	    	count=true;
			                                 	    	mmOutputStream.close();
			                                	        mmInputStream.close();
			                                	        mmSocket.close();
			                                	       
			                                 	    	}
			                                 	    	catch(Exception e)
			                                 	    	{
			                                 	    		
			                                 	    	}
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
		                                 	    	
	                                 	       }
	                                 	       else
	                                 	       {
	                                 	    	   
	                                 	    	//  Toast.makeText(getApplicationContext(), "Last a dukece", Toast.LENGTH_SHORT).show();
	                                 	    		
		                                 	    	
	                                 	       }
	                                 	       
	                                 	       
	                                 	       
	                                 	 /*      else if(change)
	                                 	       {
	                                 	    	  try 
	                          	                {
	                          	                    sendData();
	                          	                }
	                          	                catch (IOException ex) { }
	                                 	       }*/
	                                        }
	                                    });
	                                }
	                                else
	                                {
	                                    readBuffer[readBufferPosition++] = b;
	                                }
	                            }
	                        }
	                    } 
	                    catch (IOException ex) 
	                    {
	                        stopWorker = true;
	                    }
	               }
	            }
	        });

	        workerThread.start();
	    }
	    
	    
	    
	    
	    
	    
  
	
	
	
	
	
	
	//----------- Fetching Current Project
	class AsyncTaskRunner extends AsyncTask<String, String, String>
	{
		// ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
		 
		
		 
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generimated method stub
                         
          
          
          try {
			Thread.sleep(5000);
			notStoped=true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
          
			
			return null;
		}
		
		
		
		protected void onPostExecute(String result)
		{
			
		
			
			
			
		
		
		}
		
	
				
		
			}
	
	
	
	
	
	// Location finder class
	
    class LocationFinder implements GooglePlayServicesClient.ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener,LocationListener
    {
    	
    	private LocationClient locationClient;
    	int count=0;
    	ArrayList<String> numbers=new ArrayList<String>();
    	
    	public LocationFinder() {
    		// TODO Auto-generated constructor stub
    	//	GetAllNumber obj=new GetAllNumber();
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
    	           // lock.release();
    			}
    		}
    		else
    		{	
		     /*   KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE); 
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
	           // lock.release();
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
