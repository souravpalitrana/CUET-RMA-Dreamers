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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.activeandroid.query.Select;
import com.dreamers.model.Generic;
import com.dreamers.model.Trade;

public class SearchTrade extends Activity {
	ListView list;
	LinearLayout search;
	AutoCompleteTextView searchBar;
	ArrayList<String>result=new ArrayList<String>();
String selected="";
TextView genericName,brandName,description,dose;
	MyAdapter adapter;ArrayAdapter<String>suggest;
	boolean data=false;
	ArrayList<String> suggestion=new ArrayList<String>();
	
	ArrayList<String> name=new ArrayList<String>();
	ArrayList<String> trade=new ArrayList<String>();
	ArrayList<String> brand=new ArrayList<String>();
	ArrayList<String> genCode=new ArrayList<String>();
	ArrayList<String> res=new ArrayList<String>();
	
	ScrollView container;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_trade);
		
		search=(LinearLayout)findViewById(R.id.search);
		searchBar=(AutoCompleteTextView)findViewById(R.id.searchBar);
		genericName=(TextView)findViewById(R.id.generic_name);
		brandName=(TextView)findViewById(R.id.brand_name);
		
		description=(TextView)findViewById(R.id.description);
		dose=(TextView)findViewById(R.id.dose);
		container=(ScrollView)findViewById(R.id.scrollView1);
	
		
	
		
		
  
		
		
		
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
			Toast.makeText(getApplicationContext(), "Please write the Trade name you want to search.", Toast.LENGTH_LONG).show();	
			}
			
			else
			{
				//String genCode=code.get(name.indexOf(gen));
				
				//-----------Searching the word---------
				result.clear();
				 Select select = new Select();
				//	BanglaWord bng=	 select.from(BanglaWord.class).where("phonetic LIKE '"+word+"'").executeSingle();
					List<Trade> genCodeList=	 select.from(Trade.class).where("trade_name LIKE '"+gen+"'").execute();
				    //Toast.makeText(getApplicationContext(), bng.size()+"", Toast.LENGTH_LONG).show();
				    String id="";
				    String banglaText="";
					if(genCodeList.size()>0)
					{
					
				    for (Trade single : genCodeList) {
			        	
				    	 id=single.genericCode;
					    result.add(single.tradeName);
					    result.add(single.brandName);
					    result.add(single.description);
					    result.add(single.doseInformation);
					  
			        
			                    
			          
			        }
			
				
				
					ArrayList<Generic> td = new Select("generic_name").from(Generic.class).where("generic_code LIKE '"+id+"'").execute();
					
					String genc="";
					 for (Generic single : td) {
				        	
				        
				        	genc=single.genericName;
				        	//Toast.makeText(getApplicationContext(),single.genericName+" "+result.get(0), Toast.LENGTH_LONG).show();	
							   
				                    
				          
				        }
				  
					
				//-------------------------------------
				
				container.setVisibility(View.VISIBLE);
				
				
				genericName.setText(genc);
				brandName.setText(result.get(1));
				description.setText(result.get(2));
				dose.setText(result.get(3));
				
				
				
				
				
				
			}
					else
					{
						Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();	
						container.setVisibility(View.INVISIBLE);
					}
				
				
				
			} // else
			
			
			
			
			}
		});
		
	










	}
	
	
	
	

	//----------------- Generic Search---------------------------
	
	



	
	
	
	
	
	

}
