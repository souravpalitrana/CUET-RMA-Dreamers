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
	LinearLayout banglaSearch,update,phonetic,others,favourite,history,addWord;
	boolean data=false;
	
	
	ArrayList<String> banglaWordList=new ArrayList<String>();
	ArrayList<Integer> bngId=new ArrayList<Integer>();
	
	
	@Override
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.options);
		
		banglaSearch=(LinearLayout)findViewById(R.id.search_bangla);
		update=(LinearLayout)findViewById(R.id.update);
		favourite=(LinearLayout)findViewById(R.id.favourite);
		history=(LinearLayout)findViewById(R.id.history);
		others=(LinearLayout)findViewById(R.id.others);
		
		
		phonetic=(LinearLayout)findViewById(R.id.search_phonetic);
		addWord=(LinearLayout)findViewById(R.id.add);
		
		banglaSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	addWord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			}
		});
		
		
		
		
update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});

phonetic.setOnClickListener(new OnClickListener() {
	
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
		

favourite.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
});

history.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	}
});



		//AsyncTaskRunner runner = new AsyncTaskRunner();
		//runner.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	

}
