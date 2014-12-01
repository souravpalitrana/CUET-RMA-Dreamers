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


public class OptionActivity extends Activity {
	LinearLayout drugSearch,others,doctorSearch,manual,reminder,herbal,emergency;
	boolean data=false;
	
	
	ArrayList<String> banglaWordList=new ArrayList<String>();
	ArrayList<Integer> bngId=new ArrayList<Integer>();
	
	
	@Override
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.options);
		
		drugSearch=(LinearLayout)findViewById(R.id.search_bangla);
		others=(LinearLayout)findViewById(R.id.update);
		reminder=(LinearLayout)findViewById(R.id.favourite);
		herbal=(LinearLayout)findViewById(R.id.history);
		others=(LinearLayout)findViewById(R.id.others);
		
		
		doctorSearch=(LinearLayout)findViewById(R.id.search_phonetic);
		emergency=(LinearLayout)findViewById(R.id.add);
		
		drugSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(OptionActivity.this,DrugOptionActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				
			}
		});
		
	emergency.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
		
		
		
		
others.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});

doctorSearch.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	
	}
});

others.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	
		
	}
});
		

reminder.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
});

herbal.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
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
