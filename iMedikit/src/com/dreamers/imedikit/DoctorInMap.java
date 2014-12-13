package com.dreamers.imedikit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class DoctorInMap extends FragmentActivity implements GooglePlayServicesClient.ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener, LocationListener{
	
	String latStr,lonStr;
	double lat,lon,myLat,myLon;
	
String selected="";
boolean availableProduct=false;

private LocationClient locationClient;
private GoogleMap googleMap;
boolean draw;	
Marker m,m1;
LatLng loc,loc1;

	boolean data=false;
	
	ArrayList<String> info=new ArrayList<String>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.doctor_map);
	
        Intent in=getIntent();
        latStr=in.getExtras().getString("lat");
        lonStr=in.getExtras().getString("lon");
   
        lat=Double.valueOf(latStr);
        lon=Double.valueOf(lonStr);
        
        
        
		if (isMapAvailable())
		{
			googleMap=null;
			myLat=0;
			myLon=0;
			loc1=null;
			m=null;
			m1=null;
			draw=false;
			
	        try {
	            // Loading map
	    		googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	    		
	    		if(googleMap==null)
	    		{
	    			Toast.makeText(getApplicationContext(), "Unable to create map",Toast.LENGTH_LONG).show();
	    		}
	    		else
	    		{
	    			googleMap.setMyLocationEnabled(true);
	    			
	    			loc = new LatLng(lat, lon);
					googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 13.0f));
					m =googleMap.addMarker(new MarkerOptions().position(loc).title("Doctor is here").snippet("Click to get direction from you"));
					m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
					m.showInfoWindow();
	    			
	       			locationClient = new LocationClient(this, this, this);
	    		}
	 
	        } catch (Exception e) {
	            e.printStackTrace();
	        	Toast.makeText(getApplicationContext(), e+"", Toast.LENGTH_LONG).show();
	        }
	        
	        
	        googleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker marker) {
					// TODO Auto-generated method stub
					
					if(marker.getTitle().equals("You are here"))
					{
						if(m.isInfoWindowShown())
						{
							m.hideInfoWindow();
							m1.showInfoWindow();
						}
					}
					else
					{
						if(m1.isInfoWindowShown())
						{
							m1.hideInfoWindow();
							m.showInfoWindow();
						}
					}
					return false;
					
				}
			});
	        
	        googleMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
				
				@Override
				public void onInfoWindowClick(Marker marker) {
					// TODO Auto-generated method stub
					
					if(marker.getTitle().equals("Doctor is here"))
					{
						if(myLat==0||myLon==0)
						{
							locationClient.disconnect();
							locationClient.connect();
						}
						else
						{
							if(draw==false)
							{
									try
									{
										draw=true;
										Toast.makeText(getApplicationContext(), "Wait to get the direction",Toast.LENGTH_LONG).show();
										try{
											 final LatLngBounds bounds = new LatLngBounds.Builder().include(loc).include(loc1).build();
										     googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 65));
										}
										catch(Exception e)
										{
											Toast.makeText(getApplicationContext(), "Direction can not be shown, please try again.", Toast.LENGTH_LONG).show();
										}
										String str_origin = "origin="+lat+","+lon;
								        // Destination of route
								        String str_dest = "destination="+myLat+","+myLon; 
								        // Sensor enabled
								        String sensor = "sensor=false"; 
								        // Building the parameters to the web service
								        String parameters = str_origin+"&"+str_dest+"&"+sensor;	 
								        // Output format
								        String output = "json";	 
								        // Building the url to the web service
								        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
								        DownloadTask downloadTask = new DownloadTask();	        
							         // Start downloading json data from Google Directions API
							            downloadTask.execute(url); 
									}
									catch(Exception e)
									{
										e.printStackTrace();
										draw=false;
										Toast.makeText(getApplicationContext(), "Direction can not be shown, please try again.", Toast.LENGTH_LONG).show();
									}
									
									if(m.isInfoWindowShown()) m.hideInfoWindow();
									m1.showInfoWindow();
									
							}
							else  Toast.makeText(getApplicationContext(), "Wait to get the direction",Toast.LENGTH_LONG).show();
						}
					}
					
				}
			});
	        
			
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Map is not available in your device",Toast.LENGTH_LONG).show();
			finish();
		} 
        
        
       
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		locationClient.connect();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		locationClient.disconnect();
	}
	
	
	public boolean isMapAvailable() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		// resultCode=ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED;
		if (ConnectionResult.SUCCESS == resultCode) {
			return true;
		} else if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
			// Service Missing
			// Service needs update
			// Service disabled
			Dialog d = GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					1);
			d.show();

		} else {
			Toast.makeText(getApplicationContext(), "Google Maps API V2 is not supported in your device!",Toast.LENGTH_LONG).show();
			finish();
		}

		return false;
	}
	
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
	
	

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
		Location currentLocation = locationClient.getLastLocation();
		
		try{
			myLat = currentLocation.getLatitude();
			myLon = currentLocation.getLongitude();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Your present location not found, try again.",Toast.LENGTH_LONG).show();
			
		}
		
		if(myLat==0||myLon==0)
		{
			Toast.makeText(getApplicationContext(), "Your present location not found, try again.",Toast.LENGTH_LONG).show();
		}
		else
		{  	
			if(googleMap==null)
			{
				Toast.makeText(getApplicationContext(),"Map is not created",Toast.LENGTH_LONG).show();
			}
			else
			{
				loc1 = new LatLng(myLat, myLon);
				m1 =googleMap.addMarker(new MarkerOptions().position(loc1).title("You are here"));
				m1.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
			}
			
		}
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	//--------------------------------------------------------------
	
		
			//-------
	
	//------------------------------------------------------------
	
	
	 private class DownloadTask extends AsyncTask<String, Void, String>{

	        // Downloading data in non-ui thread
	        @Override
	        protected String doInBackground(String... url) {

	            // For storing data from web service
	            String data = "";

	            try{
	                // Fetching the data from web service
	                data = downloadUrl(url[0]);
	            }catch(Exception e){
	                Log.d("Background Task",e.toString());
	            }
	            return data;
	        }

	        // Executes in UI thread, after the execution of
	        // doInBackground()
	        @Override
	        protected void onPostExecute(String result) {
	            super.onPostExecute(result);

	            ParserTask parserTask = new ParserTask();

	            // Invokes the thread for parsing the JSON data
	            parserTask.execute(result);
	        }
	    }
	    
	    
	    /** A class to parse the Google Places in JSON format */
	    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

	        // Parsing the data in non-ui thread
	        @Override
	        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

	            JSONObject jObject;
	            List<List<HashMap<String, String>>> routes = null;

	            try{
	                jObject = new JSONObject(jsonData[0]);
	                DirectionsJSONParser parser = new DirectionsJSONParser();

	                // Starts parsing data
	                routes = parser.parse(jObject);
	            }catch(Exception e){
	                e.printStackTrace();
	            }
	            return routes;
	        }

	        // Executes in UI thread, after the parsing process
	        @Override
	        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
	            ArrayList<LatLng> points = null;
	            PolylineOptions lineOptions = null;
	            MarkerOptions markerOptions = new MarkerOptions();

	            // Traversing through all the routes
	            for(int i=0;i<result.size();i++){
	                points = new ArrayList<LatLng>();
	                lineOptions = new PolylineOptions();

	                // Fetching i-th route
	                List<HashMap<String, String>> path = result.get(i);

	                // Fetching all the points in i-th route
	                for(int j=0;j<path.size();j++){
	                    HashMap<String,String> point = path.get(j);

	                    double lat = Double.parseDouble(point.get("lat"));
	                    double lng = Double.parseDouble(point.get("lng"));
	                    LatLng position = new LatLng(lat, lng);

	                    points.add(position);
	                }

	                // Adding all the points in the route to LineOptions
	                lineOptions.addAll(points);
	                lineOptions.width(8);
	                lineOptions.color(Color.RED);
	            }

	            // Drawing polyline in the Google Map for the i-th route
	            googleMap.addPolyline(lineOptions);
	        }

	    }	
	
	

	
	
	
	
	
	

}
