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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dreamers.gsonlibrary.District;
import com.dreamers.gsonlibrary.Herbal;
import com.dreamers.gsonlibrary.HotTopic;
import com.dreamers.model.Generic;
import com.dreamers.model.Trade;
import com.google.gson.Gson;



public class OptionActivity extends Activity {
	LinearLayout drugSearch,hot,doctorSearch,manual,reminder,herbal,emergency,others;
	boolean data=false;
	int success=5;
	String res="";
	ArrayList<String> district=new ArrayList<String>();
	ArrayList<String> code=new ArrayList<String>();
	
	boolean availableProduct=false;
	//JSONParser jParser = new JSONParser();

	@Override
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.options);
		
		drugSearch=(LinearLayout)findViewById(R.id.search_bangla);
		hot=(LinearLayout)findViewById(R.id.update);
		reminder=(LinearLayout)findViewById(R.id.favourite);
		herbal=(LinearLayout)findViewById(R.id.history);
		others=(LinearLayout)findViewById(R.id.others);
		
		
		doctorSearch=(LinearLayout)findViewById(R.id.doctor);
		emergency=(LinearLayout)findViewById(R.id.add);
		
		drugSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(OptionActivity.this,DrugOptionActivity.class);
				startActivity(intent);
				
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				
			}
		});
		
	emergency.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent in=new Intent(OptionActivity.this,EmergencyActivity.class);
				startActivity(in);
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
			}
		});
		
		
		
		
hot.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AsyncTaskRunnerHotTopic().execute();
			}
		});

doctorSearch.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	
	
		
		new AsyncTaskRunnerDistrict().execute();
		
		
		
		
		
		
		
		
		
		
	}
});

others.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	Toast.makeText(getApplicationContext(), "Not yet implemented", Toast.LENGTH_LONG).show();
		
	}
});
		

reminder.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent in=new Intent(OptionActivity.this,Reminder.class);
		startActivity(in);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}
});

herbal.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		new AsyncTaskRunnerHerbal().execute();
	}
});



		
	}
	
		
		
		
		
		
		
		//--------------------------------------------------------------
		
		
		class AsyncTaskRunnerDistrict extends AsyncTask<String, String, String>
		{
			 ProgressDialog progressDialog = new ProgressDialog(OptionActivity.this);
			 private static final String TAG = "PostFetcher";
				public static final String SERVER_URL = "http://192.168.43.27:8080/iMedikitWebService/GetDistrict";
				
			 int success=5;
			  String x="";
			String error="";
			int statusCode;
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub

				try
				{
					
					
					
					HttpClient client = new DefaultHttpClient();
					//HttpPost post = new HttpPost(SERVER_URL);
					HttpGet get = new HttpGet(SERVER_URL);
					
					//Perform the request and check the status code
					HttpResponse response = client.execute(get);
					StatusLine statusLine = response.getStatusLine();
					statusCode=statusLine.getStatusCode();
					if(statusLine.getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						InputStream content = entity.getContent();
						
						
							//Read the server response and attempt to parse it as JSON
							Reader reader = new InputStreamReader(content);
							
							//GsonBuilder gsonBuilder = new GsonBuilder();
							//gsonBuilder.setDateFormat("M/d/yy hh:mm a");
							Gson gson = new Gson();
							List<District> posts = Arrays.asList(gson.fromJson(reader, District.class));
							content.close();
                            district.clear();
                            code.clear();
							availableProduct=true;	
							
							// Need to get data from java obeject
							
							for(District res : posts) {
							
								 district=(ArrayList<String>) res.district;
								code=(ArrayList<String>)res.district_code;
				
										
							availableProduct=true;		
								
							}
							
					}
						
							
				} 
				
				catch(Exception e)
				{
					error=error+e;
				}
							
							
							//------------------------------------------------
						
				
				
				return null;
			}
			
			
			
			protected void onPostExecute(String string)
			{
				
				progressDialog.dismiss();
				
				if(availableProduct==true)
				{
				//view.setText(data.toString()+" "+phonetic.toString());
					//Toast.makeText(getApplicationContext(), "  "+district, Toast.LENGTH_LONG).show();
					Intent intent=new Intent(OptionActivity.this,DistrictList.class);
					intent.putExtra("name", district);
					intent.putExtra("code", code);
					startActivity(intent);
				//	finish();
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					
				}
				
				else
				{
					Toast.makeText(getApplicationContext(), "There is no data available or server problem ", Toast.LENGTH_LONG).show();
					//view.setText(error);
					
				}
			
			}
			
			protected void onPreExecute()
			{
				
				progressDialog.setMessage("Please wait. Loading..");
				progressDialog.setCancelable(false);
				progressDialog.show();
			}
			
			
			
			
				}
		
		
		
		//------------------------------------------------------------------

		

		
		//--------------------------------------------------------------
		
		
		class AsyncTaskRunnerHerbal extends AsyncTask<String, String, String>
		{
			 ProgressDialog progressDialog = new ProgressDialog(OptionActivity.this);
			 private static final String TAG = "PostFetcher";
				public static final String SERVER_URL = "http://192.168.43.27:8080/iMedikitWebService/GetHerbal";
				
			 int success=5;
			  String x="";
			String error="";
			int statusCode;
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub

				try
				{
					
					
					
					HttpClient client = new DefaultHttpClient();
					//HttpPost post = new HttpPost(SERVER_URL);
					HttpGet get = new HttpGet(SERVER_URL);
					
					//Perform the request and check the status code
					HttpResponse response = client.execute(get);
					StatusLine statusLine = response.getStatusLine();
					statusCode=statusLine.getStatusCode();
					if(statusLine.getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						InputStream content = entity.getContent();
						
						
							//Read the server response and attempt to parse it as JSON
							Reader reader = new InputStreamReader(content);
							
							//GsonBuilder gsonBuilder = new GsonBuilder();
							//gsonBuilder.setDateFormat("M/d/yy hh:mm a");
							Gson gson = new Gson();
							List<Herbal> posts = Arrays.asList(gson.fromJson(reader, Herbal.class));
							content.close();
                            district.clear();
                            code.clear();
							availableProduct=true;	
							
							// Need to get data from java obeject
							
							for(Herbal res : posts) {
							
								 district=(ArrayList<String>) res.name;
								code=(ArrayList<String>)res.code;
				
										
							availableProduct=true;		
								
							}
							
					}
						
							
				} 
				
				catch(Exception e)
				{
					error=error+e;
				}
							
							
							//------------------------------------------------
						
				
				
				return null;
			}
			
			
			
			protected void onPostExecute(String string)
			{
				
				progressDialog.dismiss();
				
				if(availableProduct==true)
				{
				//view.setText(data.toString()+" "+phonetic.toString());
					//Toast.makeText(getApplicationContext(), "  "+district, Toast.LENGTH_LONG).show();
					Intent intent=new Intent(OptionActivity.this,HerbalList.class);
					intent.putExtra("name", district);
					intent.putExtra("code", code);
					startActivity(intent);
				//	finish();
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					
				}
				
				else
				{
					Toast.makeText(getApplicationContext(), "There is no data available or server problem ", Toast.LENGTH_LONG).show();
					//view.setText(error);
					
				}
			
			}
			
			protected void onPreExecute()
			{
				
				progressDialog.setMessage("Please wait. Loading..");
				progressDialog.setCancelable(false);
				progressDialog.show();
			}
			
			
			
			
				}
		
		
		
		//------------------------------------------------------------------

		
		

		//--------------------------------------------------------------
		
		
		class AsyncTaskRunnerHotTopic extends AsyncTask<String, String, String>
		{
			 ProgressDialog progressDialog = new ProgressDialog(OptionActivity.this);
			 private static final String TAG = "PostFetcher";
				public static final String SERVER_URL = "http://192.168.43.27:8080/iMedikitWebService/GetHotTopic";
				
			 int success=5;
			  String x="";
			String error="";
			int statusCode;
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub

				try
				{
					
					
					
					HttpClient client = new DefaultHttpClient();
					//HttpPost post = new HttpPost(SERVER_URL);
					HttpGet get = new HttpGet(SERVER_URL);
					
					//Perform the request and check the status code
					HttpResponse response = client.execute(get);
					StatusLine statusLine = response.getStatusLine();
					statusCode=statusLine.getStatusCode();
					if(statusLine.getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						InputStream content = entity.getContent();
						
						
							//Read the server response and attempt to parse it as JSON
							Reader reader = new InputStreamReader(content);
							
							//GsonBuilder gsonBuilder = new GsonBuilder();
							//gsonBuilder.setDateFormat("M/d/yy hh:mm a");
							Gson gson = new Gson();
							List<HotTopic> posts = Arrays.asList(gson.fromJson(reader, HotTopic.class));
							content.close();
                            district.clear();
                            code.clear();
							availableProduct=true;	
							
							// Need to get data from java obeject
							
							for(HotTopic res : posts) {
							
								 district=(ArrayList<String>) res.name;
								code=(ArrayList<String>)res.code;
				
										
							availableProduct=true;		
								
							}
							
					}
						
							
				} 
				
				catch(Exception e)
				{
					error=error+e;
				}
							
							
							//------------------------------------------------
						
				
				
				return null;
			}
			
			
			
			protected void onPostExecute(String string)
			{
				
				progressDialog.dismiss();
				
				if(availableProduct==true)
				{
				//view.setText(data.toString()+" "+phonetic.toString());
					//Toast.makeText(getApplicationContext(), "  "+district, Toast.LENGTH_LONG).show();
					Intent intent=new Intent(OptionActivity.this,HotTopicList.class);
					intent.putExtra("name", district);
					intent.putExtra("code", code);
					startActivity(intent);
				//	finish();
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					
				}
				
				else
				{
					Toast.makeText(getApplicationContext(), "There is no data available or server problem ", Toast.LENGTH_LONG).show();
					//view.setText(error);
					
				}
			
			}
			
			protected void onPreExecute()
			{
				
				progressDialog.setMessage("Please wait. Loading..");
				progressDialog.setCancelable(false);
				progressDialog.show();
			}
			
			
			
			
				}
		
		
		
		//------------------------------------------------------------------

		
		
		
		
		//---------------------------------------------------------------------
		
		

}
