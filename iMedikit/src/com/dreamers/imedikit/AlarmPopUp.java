package com.dreamers.imedikit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;

public class AlarmPopUp extends Activity
{
    // An ID of the alarm dialog
    private static final int DIALOG_ALARM = 0;
 
    // The alarm ID
    private int m_alarmId;
    MediaPlayer player;
    KeyguardManager keyguardManager;
    KeyguardLock lock;
	WakeLock wakeLock;
    String event;
    Uri uri;
    Ringtone ringtone;
    private SharedPreferences remindPreferences;
    //private ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);   
        
     //   getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        
        setContentView(R.layout.popup);
        
    //    keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE); 
     //   lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
      //  lock.disableKeyguard();
        
        remindPreferences=getSharedPreferences("remindPrefs", MODE_PRIVATE);
        event=remindPreferences.getString("event","");
        String ring=remindPreferences.getString("ringtone","");
        uri=Uri.parse(ring);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
        
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE); 
        final KeyguardManager.KeyguardLock kl = km .newKeyguardLock("MyKeyguardLock"); 
        kl.disableKeyguard(); 

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE); 
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                                         | PowerManager.ACQUIRE_CAUSES_WAKEUP
                                         | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
        wakeLock.acquire();
       
 
        // Get the alarm ID from the intent extra data
       Intent intent = getIntent();
        Bundle extras = intent.getExtras();
 
        if (extras != null) {
            m_alarmId = extras.getInt("AlarmID", -1);
        } else {
            m_alarmId = -1;
        }
 
        // Show the popup dialog
        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
       // tg.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD);
        ringtone.play();
        showDialog(DIALOG_ALARM);  
    }
    
 
   @Override
    protected Dialog onCreateDialog(int id)
    {
        super.onCreateDialog(id);
 
        // Build the dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
 
        alert.setTitle("MEDIKIT");
        alert.setMessage("Its time to " + event);
        alert.setCancelable(false);
 
        alert.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	// getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            	//lock.reenableKeyguard();
				 wakeLock.release();
            	ringtone.stop();
                AlarmPopUp.this.finish();
            }
        });
 
        // Create and return the dialog
        AlertDialog dlg = alert.create();
 
        return dlg;
    }  
}