package com.dreamers.imedikit;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Reminder extends Activity {
	
	private SharedPreferences remindPreferences;
	private SharedPreferences.Editor remindPrefsEditor;
	
	ToggleButton reminderStatus;
	TextView remindTime;
	Button changeTime,setRingtone,setReminder;
	EditText edtEvent;
	
	Uri uri;
	String ring;
    private int hour;
    private int minute;
    String event;
    boolean status;
    static final int TIME_DIALOG_ID = 999;
    private TimePickerDialog.OnTimeSetListener timePickerListener;
    private static final String ALARM_ACTION_NAME = "com.dreamers.imedikit.ALARM";
    private AlarmManager m_alarmMgr;
    private int m_alarmId;
    PendingIntent alarmPI;
   // String day[]={"SATURDAY","SUNDAY","MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.reminder_new);
		reminderStatus=(ToggleButton)findViewById(R.id.remindStatus);
		remindTime=(TextView)findViewById(R.id.txtTime);
		changeTime=(Button)findViewById(R.id.changeTime);
		setRingtone=(Button)findViewById(R.id.btnRingtone);
		setReminder=(Button)findViewById(R.id.setReminder);
		edtEvent=(EditText)findViewById(R.id.edtEvent);
		
		m_alarmId = 0;
		m_alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		Intent alarmIntent = new Intent(ALARM_ACTION_NAME);
		// Create the corresponding PendingIntent object
        alarmPI = PendingIntent.getBroadcast(this, m_alarmId, alarmIntent, 0);
		
		remindPreferences=getSharedPreferences("remindPrefs", MODE_PRIVATE);
		remindPrefsEditor=remindPreferences.edit();
		
		event=remindPreferences.getString("event","");
		status=remindPreferences.getBoolean("status", false);
		hour=remindPreferences.getInt("hour", -1);
		minute=remindPreferences.getInt("minute", -1);
		ring=remindPreferences.getString("ringtone","");
		if(ring.equals(""))
		{
			uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
			ring = uri.toString();
            remindPrefsEditor.putString("ringtone", ring);
            remindPrefsEditor.commit();
		}
		reminderStatus.setChecked(status);
		edtEvent.setText(event);
		if(hour==-1||minute==-1)
		{
			Calendar calAlarm = Calendar.getInstance();
			hour=calAlarm.get(Calendar.HOUR_OF_DAY);
			minute=calAlarm.get(Calendar.MINUTE);
		}
		remindTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));
		
		timePickerListener =  new TimePickerDialog.OnTimeSetListener() {
	        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
	            hour = selectedHour;
	            minute = selectedMinute;
	            remindPrefsEditor.putInt("hour",hour);
	            remindPrefsEditor.putInt("minute",minute);
				remindPrefsEditor.commit();
                remindTime.setText(new StringBuilder().append(padding_str(hour)).append(":").append(padding_str(minute)));
	        }
        };
		
		reminderStatus.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					remindPrefsEditor.putBoolean("status", true);
					remindPrefsEditor.commit();
					createAlarm();
				}
				else
				{
					remindPrefsEditor.putBoolean("status", false);
					remindPrefsEditor.commit();
					m_alarmMgr.cancel(alarmPI);
				}
			}
		});
		
		changeTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(TIME_DIALOG_ID);
			}
		});
		
		setRingtone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
				intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
				startActivityForResult(intent, 5);
			}
		});
		
		setReminder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(reminderStatus.isChecked())
				{
					createAlarm();
				}
				else  Toast.makeText(getApplicationContext(), "Please turn on reminder status", Toast.LENGTH_LONG).show();
			}
		});
		
		
	}
	
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
        switch (id) {
    case TIME_DIALOG_ID:
            // set time picker as current time
            return new TimePickerDialog(this, timePickerListener, hour, minute,false);

        }
        return null;
	}
	
    private static String padding_str(int c) {
        if (c >= 10)
           return String.valueOf(c);
        else
           return "0" + String.valueOf(c);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	// TODO Auto-generated method stub
        if (resultCode == Activity.RESULT_OK && requestCode == 5)
        {
             uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

             if (uri != null)
             {
            	// ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
                 ring = uri.toString();
                 remindPrefsEditor.putString("ringtone", ring);
                 remindPrefsEditor.commit();
                 Toast.makeText(getApplicationContext(),"Ringtone is set properly",Toast.LENGTH_LONG).show();
             }
             else
             {
            	 Toast.makeText(getApplicationContext(),"Ringtone is not set properly",Toast.LENGTH_LONG).show();
             }
         } 
    }
    
    private void createAlarm()
    {
    	event=edtEvent.getText().toString();
    	if(event.equals("")) Toast.makeText(getApplicationContext(), "Please enter the event to remind", Toast.LENGTH_LONG).show();
    	else
    	{
    		remindPrefsEditor.putString("event", event);
    		remindPrefsEditor.commit();
        	String time=remindTime.getText().toString();
        	hour=0;
        	minute=0;
        	boolean flag=false;
        	for(int i=0;i<time.length();i++)
        	{
        		if(time.charAt(i)==':')
        		{
        			flag=true;
        			continue;
        		}
        		else
        		{
        			if(flag==false) hour=hour*10+(time.charAt(i)-48);
        			else minute=minute*10+(time.charAt(i)-48);
        		}
        	}
        /*    long userTime=((hour*60+minute)*60)*1000;                
            long currentTime=System.currentTimeMillis();
            long alarmTime;
            if(userTime>currentTime) alarmTime=userTime-currentTime;
            else alarmTime=(24*3600*1000-currentTime)+userTime;
            m_alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+alarmTime, alarmPI); */

            Calendar calAlarm = Calendar.getInstance(); 
            calAlarm.set(Calendar.HOUR_OF_DAY,hour);
            calAlarm.set(Calendar.MINUTE, minute);
            calAlarm.set(Calendar.SECOND, 0);

            long userTime=calAlarm.getTimeInMillis();
            long currentTime=System.currentTimeMillis();
            if(userTime<=currentTime)
            {
            	userTime=(24*3600*1000)+userTime;
            //	Toast.makeText(getApplicationContext(), "Please set the time properly", Toast.LENGTH_LONG).show();
            }
          //  else
          //  {
            	// Register the alarm with the alarm manager
                m_alarmMgr.set(AlarmManager.RTC_WAKEUP, userTime, alarmPI);              
                Toast.makeText(getApplicationContext(), "Reminder is set properly", Toast.LENGTH_LONG).show();
          //  }
    	}
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
