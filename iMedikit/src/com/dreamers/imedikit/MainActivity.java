package com.dreamers.imedikit;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.Window;

public class MainActivity extends Activity {
	
	boolean update=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		
		
		
		
		AsyncTaskRunner runner = new AsyncTaskRunner();
		runner.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	
	public class	AsyncTaskRunner extends AsyncTask<String, String, String>
	{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		try {
			
			
			
		
			
			
			
			
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(String string)
	{
		
		Intent intent= new Intent(MainActivity.this,OptionActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
		
	
	}
	
	
	
	}
}
