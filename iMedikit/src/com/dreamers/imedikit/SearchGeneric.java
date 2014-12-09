package com.dreamers.imedikit;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import com.dreamers.model.Generic;
import com.dreamers.model.Trade;

public class SearchGeneric extends Activity {
	ListView list;
	LinearLayout search;
	AutoCompleteTextView searchBar;
String selected="";
	MyAdapter adapter;ArrayAdapter<String>suggest;
	boolean data=false;
	ArrayList<String> suggestion=new ArrayList<String>();
	
	ArrayList<String> name=new ArrayList<String>();
	ArrayList<String> trade=new ArrayList<String>();
	ArrayList<String> brand=new ArrayList<String>();
	ArrayList<String> genCode=new ArrayList<String>();
	ArrayList<String> res=new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search);
		
		search=(LinearLayout)findViewById(R.id.search);
		searchBar=(AutoCompleteTextView)findViewById(R.id.searchBar);
	
		list=(ListView) findViewById(R.id.listView1);
	
		
	
		
		
        adapter=new MyAdapter(this, trade,brand);
		
		list.setAdapter(adapter);
		
name=(ArrayList<String>) getIntent().getSerializableExtra("name");
	
		suggest  = new ArrayAdapter<String>(this, R.layout.custom_item, R.id.autoCompleteItem, name);
		
		searchBar.setAdapter(suggest);
		
search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
			String gen=searchBar.getText().toString();
			
			if(gen.equals(""))
			{
			Toast.makeText(getApplicationContext(), "Please write the generic name you want to search.", Toast.LENGTH_LONG).show();	
			}
			
			else
			{
				//String genCode=code.get(name.indexOf(gen));
				
				//-----------Searching the word---------
				
				 Select select = new Select();
				//	BanglaWord bng=	 select.from(BanglaWord.class).where("phonetic LIKE '"+word+"'").executeSingle();
					List<Generic> genCodeList=	 select.from(Generic.class).where("generic_name LIKE '"+gen+"'").execute();
				    //Toast.makeText(getApplicationContext(), bng.size()+"", Toast.LENGTH_LONG).show();
				    String id="";
				    String banglaText="";
					if(genCodeList.size()>0)
					{
					
				    for (Generic single : genCodeList) {
			        	
				    	 id=single.genericCode;
					    
					  
			        
			                    
			          
			        }
				    
				   trade.clear();
				   brand.clear();
				
					ArrayList<Trade> td = new Select("trade_name","generic_code","brand_name").from(Trade.class).where("generic_code LIKE '"+id+"'").execute();
					
					
					 for (Trade single : td) {
				        	
				        	trade.add(single.tradeName);
				        	brand.add(single.brandName);
				        	genCode.add(single.genericCode);
				           
				                    
				          
				        }
				  
					
				//-------------------------------------
				
				
				
				
				
				
				
				
				
				
				adapter.notifyDataSetChanged();
			}
					else
					{
						Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();	
							
					}
				
				
				
			} // else
			
			
			
			
			}
		});
		
	






list.setOnItemClickListener(new OnItemClickListener() {
	
  	 @Override
       public void onItemClick(AdapterView<?> adapter, View v, int position,
             long arg3) 
       {
  		 
        selected=genCode.get(position);
  //Toast.makeText(getApplicationContext(), selectedProductID, Toast.LENGTH_LONG).show();			
  AsyncTaskRunnerDetails runner =new AsyncTaskRunnerDetails();
  runner.execute();

       }
	});



	}
	
	
	
	

	//----------------- Generic Search---------------------------
	
	


	public class	AsyncTaskRunnerDetails extends AsyncTask<String, String, String>
	{
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
	
		try
		{
		

			ArrayList<Trade> td = new Select().from(Trade.class).where("generic_code LIKE '"+selected+"'").execute();
			
			
			 for (Trade single : td) {
		        	
		        	res.add(single.tradeName);
		        	res.add(single.brandName);
		        	res.add(single.description);
		        	res.add(single.doseInformation);
		           
		                    
		          
		        }
		  
			
			int size=td.size();
			
			if(size==0)
			{
				data=false;
			}
			else
			{
				data=true;
			
			 
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
		
			Intent intent= new Intent(SearchGeneric.this,GenericDetails.class);
			intent.putExtra("data",res);
			
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
