package com.dreamers.imedikit;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;


public class DrugOptionActivity extends Activity {
	LinearLayout genericSearch,tradeSearch,genericDose,tradeDose;
	boolean data=false;
	
	
	ArrayList<String> banglaWordList=new ArrayList<String>();
	ArrayList<Integer> bngId=new ArrayList<Integer>();
	
	
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
				Intent intent= new Intent(DrugOptionActivity.this,SearchGeneric.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	

}
