package com.dreamers.imedikit;



import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class GenericDetails extends Activity {
	
	boolean update=false;
	
	TextView genericName,brandName,description,dose;
	ArrayList<String>data=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.generic_details);
		
		genericName=(TextView)findViewById(R.id.generic_name);
		brandName=(TextView)findViewById(R.id.brand_name);
		
		description=(TextView)findViewById(R.id.description);
		dose=(TextView)findViewById(R.id.dose);
		
		
		data=(ArrayList<String>) getIntent().getSerializableExtra("data");
		
		genericName.setText(data.get(0));
		brandName.setText(data.get(1));
		description.setText(data.get(2));
		dose.setText(data.get(3));
		
		
	
	}

	
	
	
	
	
}
