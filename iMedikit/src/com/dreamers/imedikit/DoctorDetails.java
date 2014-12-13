package com.dreamers.imedikit;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory.Options;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorDetails extends Activity {
	
	String lat,lon;
	TextView name,qualification,chember,speciality,visit,working_day;
	Button map;
   Context context;
   boolean statusOfGPS,statusOfInternet,statusOfNetwork;


	
	ArrayList<String> info=new ArrayList<String>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.doctor_details);
		
		name=(TextView)findViewById(R.id.name);
		qualification=(TextView)findViewById(R.id.qualification);
		chember=(TextView)findViewById(R.id.chember);
		speciality=(TextView)findViewById(R.id.speciality);
		visit=(TextView)findViewById(R.id.visit);
		working_day=(TextView)findViewById(R.id.working_day);
		
		context=this;
		
info=(ArrayList<String>) getIntent().getSerializableExtra("name");
	  map=(Button)findViewById(R.id.map);
	  
	  
	  name.setText(info.get(0));
	  qualification.setText(info.get(1));
	  chember.setText(info.get(2));
	  speciality.setText(info.get(3));
visit.setText(info.get(4));
working_day.setText(info.get(5));
lat=info.get(6);
lon=info.get(7);
	  
	  
	  map.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			
	        try {
	            // Loading map
					LocationManager manager = (LocationManager) getSystemService(context.LOCATION_SERVICE );
					statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
					statusOfNetwork=manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
					statusOfInternet=isConnectingToInternet();
					
					if((statusOfGPS==false&&statusOfNetwork==false)||statusOfInternet==false)
					{
						LayoutInflater li = LayoutInflater.from(context);
						View promptsView;
						AlertDialog.Builder builder;
						final AlertDialog alertDialog;
						Button btnOk=null;
						Button btnContinue=null;
						if(statusOfGPS==false&&statusOfInternet==false&&statusOfNetwork==false)
						{
							Toast.makeText(getApplicationContext(), "Please turn your GPS and Internet connection ON",Toast.LENGTH_LONG).show();
						}
						else if(statusOfInternet==false&&(statusOfGPS==true||statusOfNetwork==true))
						{
							Toast.makeText(getApplicationContext(), "Please turn your Internet connection ON",Toast.LENGTH_LONG).show();
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Please turn your GPS ON",Toast.LENGTH_LONG).show();
						}
					}
				
					else
					{
						if(statusOfNetwork==false)
						{
							Toast.makeText(getApplicationContext(), "Please turn your network status ON",Toast.LENGTH_LONG).show();
						}
						Intent intent=new Intent(DoctorDetails.this,DoctorInMap.class);
						intent.putExtra("lat", lat);
						intent.putExtra("lon", lon);
						startActivity(intent);
					//	finish();
						overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					}
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	            Toast.makeText(getApplicationContext(), "Internet status and GPS status of device can not be accesed",Toast.LENGTH_LONG).show();
	        }
		}
	});
		
	
		
	
		
		
       
		
	













	}
	
	
	
	
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
	
	
	
	
	//--------------------------------------------------------------
	
		
			//-------
	
	//------------------------------------------------------------
	
	
	
	
	

	
	
	
	
	
	

}
