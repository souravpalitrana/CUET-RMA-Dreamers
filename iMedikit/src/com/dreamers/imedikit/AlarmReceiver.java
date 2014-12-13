package com.dreamers.imedikit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver{
	
	private static final String ALARM_ACTION_NAME = "com.dreamers.imedikit.ALARM";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
        // Handle the alarm broadcast
        if (ALARM_ACTION_NAME.equals(intent.getAction()))
        {
            // Launch the alarm popup dialog
           // Intent alarmIntent = new Intent("android.intent.action.MAIN"); 
           // alarmIntent.setClass(context, AlarmPopUp.class);
        	Intent alarmIntent = new Intent(context,AlarmPopUp.class); 
            alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Pass on the alarm ID as extra data
            alarmIntent.putExtra("AlarmID", intent.getIntExtra("AlarmID", -1));
           // alarmIntent.putExtra("event", intent.getExtras().getString("event"));
            // Start the popup activity
            context.startActivity(alarmIntent);
        }
	}

}
