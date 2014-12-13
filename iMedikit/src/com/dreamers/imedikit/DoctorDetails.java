package com.dreamers.imedikit;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.activeandroid.query.Select;
import com.dreamers.gsonlibrary.District;
import com.dreamers.gsonlibrary.Special;
import com.dreamers.model.Generic;
import com.dreamers.model.Trade;
import com.google.gson.Gson;

public class DoctorDetails extends Activity {
	
	String lat,lon;
	TextView name,qualification,chember,speciality,visit,working_day;
	Button map;



	
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
			Intent intent=new Intent(DoctorDetails.this,DoctorInMap.class);
			intent.putExtra("lat", lat);
			intent.putExtra("lon", lon);
			startActivity(intent);
		//	finish();
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
	});
		
	
		
	
		
		
       
		
	













	}
	
	
	
	
	//--------------------------------------------------------------
	
		
			//-------
	
	//------------------------------------------------------------
	
	
	
	
	

	
	
	
	
	
	

}
