package com.dreamers.imedikit;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.dreamers.model.Generic;
import com.dreamers.model.Trade;

public class MainActivity extends Activity {
	
	boolean update=false;
	String value1="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		
		SharedPreferences pref=getSharedPreferences("database",0);
		 value1=pref.getString("load", "no");
		if(value1.equals("no"))
		{
			update=true;
		}
		else 
			update=false;
		
		AsyncTaskRunner runner = new AsyncTaskRunner();
		runner.execute();
	}

	
	
	
	
	public class	AsyncTaskRunner extends AsyncTask<String, String, String>
	{
		 ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
		
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		try {
			
			
			
		
			if(update)
			{
				
				
				  ActiveAndroid.beginTransaction();
				
				
				  
				  
				  Generic gen=new Generic("Aceclofenac","gen_00001","asd");
					gen.save();
					Generic gen2=new Generic("Paracetamol","gen_00002","asd");
					gen2.save();
					
					Trade td=new Trade("Flexi","gen_00001","Square","This is description","100mg twice daily");
					td.save();
					
					Trade td2=new Trade("Ace","gen_00002","Square","This is description","100mg twice daily");
					td2.save();
					
					Trade td3=new Trade("Napa","gen_00002","Square","This is description","100mg twice daily");
					td3.save();
					//Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_LONG).show();
				  
					
				  
				
				  ActiveAndroid.setTransactionSuccessful();
				  
				  SharedPreferences pref=getSharedPreferences("database",0);
					SharedPreferences.Editor editor=pref.edit();
					
					editor.putString("load","yes");
				
					editor.commit();
				  
				  Thread.sleep(1000);
			}
			else
			{
				Thread.sleep(1000);
			}
			
			
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String string)
	{
		if(update)
		{
			progressDialog.dismiss();
		}
		
		
		Intent intent= new Intent(MainActivity.this,OptionActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		
	
	}
	
	protected void onPreExecute()
	{
		if(update)
		{
		progressDialog.setMessage("Please wait. Loading the database for the first time.");
		progressDialog.setCancelable(false);
		progressDialog.show();
		}
		else
		{
			
		}
	}
	
	
	
	}
}
