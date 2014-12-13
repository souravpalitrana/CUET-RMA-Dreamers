package com.dreamers.imedikit;

import javax.security.auth.Destroyable;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Int2;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class EmergencyActivity extends Activity {
	
	
	LinearLayout tab1,tab2;
	ToggleButton statusButton;
	TextView description;
	boolean deviceTab=true,shakeTab=false;

	String shakeDescription,deviceDescription;
	boolean deviceStatus=false, shakeStatus=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.emergency);
		
		 tab1=(LinearLayout)findViewById(R.id.device);
         tab2=(LinearLayout)findViewById(R.id.shake);
         statusButton=(ToggleButton)findViewById(R.id.statusButton);
         description=(TextView) findViewById(R.id.description);
         
         deviceDescription="This is a device which can monitor hart rate. This device uses Bluetooth technology to connect with another device like android mobile. It consist of 3 key . 1st one is for the alert service on/off.2nd one is for option key 3rd  one is for buzzer on/off At the starting the sensor part has to be pleased at the fingertip correctly so that it synchronized with pulse. Than it needs to press option key than the device will start data sending. The option key will be also be used to set the normal range. to set that it to be pressed and data has to be set by the master device(smart phone, tab).after completing the data entry the button has to be relished.The 3rd key needs a long press to start or stop buzzer. If buzzer system in turned on it will create a bip sound for 5ms after the key relished.";
         shakeDescription="You have to turn 'Shaking Service' ON to get the advantages of shaking. Afetr turning 'Shaking Service' ON you can close the application even you can turn screen power OFF. You will get the advantages of shaking in every case only if the 'Shaking Service' is turned ON. Internet connection of your mobile device should also be turned ON when the 'Shaking Service' is turned ON. If you face any sexual harassment you will shake the device. Then your problem and location will be informed to your relatives. You must add your relative's phone number after you installed the application. Your problem and location will also be informed to the police station that is nearest from your current location. If you feel uneasy for the action of this application against shaking of your device you can turn the 'Shaking Service' OFF. But you must turn the 'Shaking Service' ON when you think that the situation is not safe for you.";

			SharedPreferences pref=getSharedPreferences("service_status",0);
			String value1=pref.getString("device", "false");
         String value2=pref.getString("shake", "false");
         description.setText(deviceDescription);
         tab1.setBackgroundResource(R.drawable.tab_border);
         if(!value1.equals("false"))
         {
        	 deviceStatus=true;
         }
         else
         {
        	 deviceStatus=false;
         }
         
         if(!value2.equals("false"))
         {
        	 shakeStatus=true;
         }
         else
         {
        	 shakeStatus=false;
         }
         
         statusButton.setChecked(deviceStatus);
         
	tab1.setOnClickListener(new OnClickListener() {  // when reselect
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				tab1.setBackgroundResource(R.drawable.tab_border);
				tab2.setBackgroundResource(R.drawable.unselected);
				
				deviceTab=true;
				shakeTab=false;
				
				statusButton.setChecked(deviceStatus);
				description.setText(deviceDescription);
			
	    		//-------------------------------
				
			}
		});
		
	
	
	
	tab2.setOnClickListener(new OnClickListener() {  // when reselect
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			tab1.setBackgroundResource(R.drawable.unselected);
			tab2.setBackgroundResource(R.drawable.tab_border);
		
			deviceTab=false;
			shakeTab=true;
			
			statusButton.setChecked(shakeStatus);
    		
			description.setText(shakeDescription);
		}
	});
	
	
	statusButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		
			//Toast.makeText(getApplicationContext(), "Toggole button cliked", Toast.LENGTH_LONG).show();
			
			
			if(deviceTab==true && shakeTab==false)  // device service tab
			{			
				
				if(deviceStatus)   // on ache off kora lagbe
				{
					deviceStatus=false;
					statusButton.setChecked(deviceStatus);					
					
					//-----------saving service------------------------					
					
					SharedPreferences pref=getSharedPreferences("service_status",0);
					SharedPreferences.Editor  editor=pref.edit();
					
					editor.putString("device",deviceStatus+"");
					editor.putString("shake",shakeStatus+"");
					editor.commit();
					//--------------------------------------
					
					
					
					
					
					//-----------------Akhane baki code korte hobe----Device service off korte hobe-----------------
					
				//	Toast.makeText(getApplicationContext(), "Device service is off", Toast.LENGTH_LONG).show();
					Intent in=new Intent(EmergencyActivity.this,BluetoothService.class);
					stopService(in);
					
					//--------------------------------------------------------------------
										
					
				}
				else                // off ache on kora lagbe
				{
					if(shakeStatus==false)  // jodi shake service bondho thake tahole device service on kora jabe ta na hole age shake ta bondho korte hobe pore device ta on korte hobe
					{
					deviceStatus=true;
					statusButton.setChecked(deviceStatus);
					
					
					
					
					//------------- saving the service status
					
					SharedPreferences pref=getSharedPreferences("service_status",0);
					SharedPreferences.Editor  editor=pref.edit();
					
					editor.putString("device",deviceStatus+"");
					editor.putString("shake",shakeStatus+"");
					editor.commit();
					//--------------------------------------
					
					
					
					
					
					
					
                    //-----------------Akhane baki code korte hobe---device service on korte hobe------------------
					
					//Toast.makeText(getApplicationContext(), "Device service is On", Toast.LENGTH_LONG).show();
					
					Intent in=new Intent(EmergencyActivity.this,BluetoothService.class);
					startService(in);
					//--------------------------------------------------------------------
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Your shake service is ON. Please turn it OFF. Then try again", Toast.LENGTH_LONG).show();
						 statusButton.setChecked(false);
					}
				}
			}
			else     // shake service tab
			{				
				
				
				if(shakeStatus)   // on ache off kora lagbe
				{
					shakeStatus=false;
					statusButton.setChecked(shakeStatus);
					
					
					//---------------- saving service status----------------
					
					SharedPreferences pref=getSharedPreferences("service_status",0);
					SharedPreferences.Editor  editor=pref.edit();
					
					editor.putString("device",deviceStatus+"");
					editor.putString("shake",shakeStatus+"");
					editor.commit();
					
					
					
					//-----------------------------------------------------
					
					
					
					
					
					
					//-----------------Akhane baki code korte hobe----shake service off korte hobe-----------------
					
					//Toast.makeText(getApplicationContext(), "Shake service is Off", Toast.LENGTH_LONG).show();
					Intent in=new Intent(EmergencyActivity.this,ShakeService.class);
					stopService(in);
					
					//--------------------------------------------------------------------
					
					
					
					
					
					
				}
				else                // off ache on kora lagbe
				{
					if(deviceStatus==false)  // jodi shake service bondho thake tahole device service on kora jabe ta na hole age shake ta bondho korte hobe pore device ta on korte hobe
					{
					shakeStatus=true;
					statusButton.setChecked(shakeStatus);
                    //-----------------Akhane baki code korte hobe---device service on korte hobe------------------

					SharedPreferences pref=getSharedPreferences("service_status",0);
					SharedPreferences.Editor  editor=pref.edit();
					
					editor.putString("device",deviceStatus+"");
					editor.putString("shake",shakeStatus+"");
					editor.commit();
					
				//	Toast.makeText(getApplicationContext(), "Shake service is on", Toast.LENGTH_LONG).show();
					Intent in=new Intent(EmergencyActivity.this,ShakeService.class);
					startService(in);
					
					//--------------------------------------------------------------------
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Your device service is ON. Please turn it OFF. Then try again", Toast.LENGTH_LONG).show();
					 statusButton.setChecked(false);
					
					}
				}
				
				
				
				
				
				
				
				
				
			}
			
			
		}
	});
	
	
	
	
	
	
	}

}
