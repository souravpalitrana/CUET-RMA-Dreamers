package com.dreamers.imedikit;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter2 extends ArrayAdapter<String>{

	ArrayList <String> word;
	ArrayList <String> parts;
	private Activity context;
	int viewCount;
	int count=0;
	Typeface banglaFont;
	public MyAdapter2(Activity context, ArrayList<String> word) {
		super(context, R.layout.item, word);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.word=word;
		
		

		
		
	}
	
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		View view= convertView;
		
		
			 
			 LayoutInflater inflater= context.getLayoutInflater();
			 
			 
			 view= inflater.inflate(R.layout.item, parent,false);
			 
			  
		 count=position;
			 
		 TextView wordView = (TextView)view.findViewById(R.id.district);
		
		
		 wordView.setText(word.get(position));
		 
		 
		 
		 
		 
		/* view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				     
				
				Toast.makeText(context, "item="+count, Toast.LENGTH_LONG).show();
			}
		});*/
		 
		 
		 
		 
		 
		
		return view;
	}

}
