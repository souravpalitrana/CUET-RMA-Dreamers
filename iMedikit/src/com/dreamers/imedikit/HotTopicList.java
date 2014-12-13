package com.dreamers.imedikit;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.activeandroid.query.Select;
import com.dreamers.gsonlibrary.District;
import com.dreamers.gsonlibrary.HotTopicDetails;
import com.dreamers.gsonlibrary.Special;
import com.dreamers.model.Generic;
import com.dreamers.model.Trade;
import com.google.gson.Gson;

public class HotTopicList extends Activity {
	ListView list;
	LinearLayout search;
	AutoCompleteTextView searchBar;
	String hotName="";
String selected="";
boolean availableProduct=false;
	MyAdapter2 adapter;
	boolean data=false;
	
	ArrayList<String> name=new ArrayList<String>();
	ArrayList<String> code=new ArrayList<String>();
	ArrayList<String> spec_name=new ArrayList<String>();
	ArrayList<String> spec_code=new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.district_list);
		
		
	
		list=(ListView) findViewById(R.id.listView1);
	
		
	
		
		
       
		
	
		
name=(ArrayList<String>) getIntent().getSerializableExtra("name");
code=(ArrayList<String>) getIntent().getSerializableExtra("code");	
	
adapter=new MyAdapter2(this, name);
list.setAdapter(adapter);







list.setOnItemClickListener(new OnItemClickListener() {
	
  	 @Override
       public void onItemClick(AdapterView<?> adapter, View v, int position,
             long arg3) 
       {
  		 
        selected=code.get(position);
        hotName=name.get(position);
  //Toast.makeText(getApplicationContext(), selectedProductID, Toast.LENGTH_LONG).show();			
  AsyncTaskRunnerSpec runner =new  AsyncTaskRunnerSpec();
  runner.execute();

       }
	});



	}
	
	
	
	
	//--------------------------------------------------------------
	
	
			class AsyncTaskRunnerSpec extends AsyncTask<String, String, String>
			{
				 ProgressDialog progressDialog = new ProgressDialog(HotTopicList.this);
				 private static final String TAG = "PostFetcher";
					public  String SERVER_URL = "http://192.168.43.27:8080/iMedikitWebService/GetHotTopicDetails";
					
				 int success=5;
				  String x="";
				String error="";
				int statusCode;
				@Override
				protected String doInBackground(String... params) {
					// TODO Auto-generated method stub

					try
					{
						
						
						
                               List<NameValuePair> pair= new ArrayList<NameValuePair>();
						
						pair.add(new BasicNameValuePair("code", selected));
						
						DefaultHttpClient client = new DefaultHttpClient();
						String paramString = URLEncodedUtils.format(pair, "utf-8");
						SERVER_URL += "?" + paramString;
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
								List<HotTopicDetails> posts = Arrays.asList(gson.fromJson(reader,HotTopicDetails.class));
								content.close();

								availableProduct=true;	
								spec_name.clear();
								
								// Need to get data from java obeject
								
								for(HotTopicDetails res : posts) {
								
									 spec_name=(ArrayList<String>) res.description;
									
					
											
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
						Intent intent=new Intent(HotTopicList.this,HotTopicDescription.class);
						intent.putExtra("name", spec_name);
						intent.putExtra("hot_name", hotName);
						
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
			
			
			
			//-------
	
	//------------------------------------------------------------
	
	
	
	
	

	
	
	
	
	
	

}
