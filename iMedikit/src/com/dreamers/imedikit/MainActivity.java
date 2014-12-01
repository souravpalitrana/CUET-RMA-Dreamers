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
