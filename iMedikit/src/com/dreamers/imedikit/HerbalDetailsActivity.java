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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.activeandroid.query.Select;
import com.dreamers.gsonlibrary.District;
import com.dreamers.gsonlibrary.Special;
import com.dreamers.model.Generic;
import com.dreamers.model.Trade;
import com.google.gson.Gson;

public class HerbalDetailsActivity extends Activity {
	
	ListView list;
	LinearLayout search;
	AutoCompleteTextView searchBar;
String selected="";
boolean availableProduct=false;
	MyAdapter3 adapter;
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
	
adapter=new MyAdapter3(this, name,code);
list.setAdapter(adapter);




		
	
		
		
       
		
	













	}
	
	
	
	
	//--------------------------------------------------------------
	
		
			//-------
	
	//------------------------------------------------------------
	
	
	
	
	

	
	
	
	
	
	

}
