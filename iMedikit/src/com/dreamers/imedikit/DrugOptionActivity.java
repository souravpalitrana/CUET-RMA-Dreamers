package com.dreamers.imedikit;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.dreamers.model.Generic;


public class DrugOptionActivity extends Activity {
	LinearLayout genericSearch,tradeSearch,genericDose,tradeDose;
	boolean data=false;
	
	
	ArrayList<String> name=new ArrayList<String>();
	ArrayList<String> code=new ArrayList<String>();
	
	
	@Override
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.medicine_option);
		
		genericSearch=(LinearLayout)findViewById(R.id.generic_search);
		genericDose=(LinearLayout)findViewById(R.id.generic_dose);
		
		
		
		tradeSearch=(LinearLayout)findViewById(R.id.search_phonetic);
		tradeDose=(LinearLayout)findViewById(R.id.add);
		
		genericSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new AsyncTaskRunnerGeneric().execute();
			}
		});
		
	genericDose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
		
		
	tradeSearch.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
		}
	});
	tradeDose.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			
		}
	});
	
	
	


		
	}


	
	
	
	
	
	
	
	
	//----------------- Generic Search---------------------------
	
	


	public class	AsyncTaskRunnerGeneric extends AsyncTask<String, String, String>
	{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
	
		try
		{
		
				ArrayList<Generic> fav = new Select("generic_name").all().from(Generic.class).execute();
				
			
			int size=fav.size();
			
			if(size==0)
			{
				data=false;
			}
			else
			{
				data=true;
			name.clear();
			
			 for (Generic word : fav) {
		        	
		        	//favouriteList.add(id.code);
		           
		                 
		               
		              name.add(word.genericName);
		             
		             
		        }
			 
			}
		}
		
		catch(Exception e)
		{
			Log.d("ERRORRRRRRRRR", e.toString());
		}
		return null;
	}
	
	protected void onPostExecute(String string)
	{
		
		
		if(data)
		{
		
			Intent intent= new Intent(DrugOptionActivity.this,SearchGeneric.class);
			intent.putExtra("name", name);
			
		startActivity(intent);
	
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No data available", Toast.LENGTH_LONG).show();
		}
		
	
	}
	
	
	
	}

	
	
	
	
	
	
	//------------------------------------------------------------
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
