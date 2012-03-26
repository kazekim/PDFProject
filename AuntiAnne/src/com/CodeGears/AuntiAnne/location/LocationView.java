package com.CodeGears.AuntiAnne.location;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.CodeGears.AuntiAnne.MainApp;
import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.TabActivity;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.function.DialogFunction;
import com.CodeGears.AuntiAnne.function.DocumentFunction;
import com.CodeGears.AuntiAnne.map.MyItemizedOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class LocationView extends LinearLayout {

	private MapController mapController; 
	
	private LocationTask locationTask;
	private static Context context;
	private  static ArrayList<LocationData> locationDataList;
	
	static List<Overlay> mapOverlays;
	static MyItemizedOverlay itemizedOverlay;
	static Drawable drawable;

	private MapView mapView;
	private static MapActivity activity;
	
	private static boolean isLoadLocation;
	
	public LocationView(Context _context,MapActivity _activity) {
		super(_context);
		createView();
		
		context=_context;
		activity = _activity;
		
		mapView = (MapView) findViewById(R.id.map_view);
		mapController = mapView.getController();
		
		mapOverlays = mapView.getOverlays();
		
		startLocationService();
	
		locationDataList= new ArrayList<LocationData>();
		
		isLoadLocation=false;
	}
	
	private final LocationListener locationListener = new LocationListener() {
	    public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		    	mapController.setCenter(new GeoPoint((int) (location.getLatitude() * 1E6),
		                (int) (location.getLongitude() * 1E6)));
		    	//System.out.println(location.getLatitude()+" "+location.getLongitude());
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };

	public void startLocationService(){
		// Start loction service
		LocationManager locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);

        Criteria locationCritera = new Criteria();
        locationCritera.setAccuracy(Criteria.ACCURACY_COARSE);
        locationCritera.setAltitudeRequired(false);
        locationCritera.setBearingRequired(false);
        locationCritera.setCostAllowed(true);
        locationCritera.setPowerRequirement(Criteria.NO_REQUIREMENT);

        String providerName = locationManager.getBestProvider(locationCritera, true);

        if (providerName != null && locationManager.isProviderEnabled(providerName)) {
            // Provider is enabled
            locationManager.requestLocationUpdates(providerName, 20000, 100, locationListener);
            
    		locationManager.requestLocationUpdates(providerName, 0, 0, locationListener);
    		
            Location lastKnownLocation = locationManager.getLastKnownLocation(providerName);
       	 
    		
    		
    		// Centre on Exmouth
            try{
            	mapController.setCenter(new GeoPoint((int) (lastKnownLocation.getLatitude() * 1E6), (int) (lastKnownLocation.getLongitude() * 1E6)));
            	mapController.setZoom(15);
            }catch(Exception e){
            	mapController.setCenter(new GeoPoint((int) (13.745513 * 1E6), (int) (100.534769 * 1E6)));
            	mapController.setZoom(10);
            }
    		
    		
            locationManager.removeUpdates(this.locationListener);
        } else {
            // Provider not enabled, prompt user to enable it
            Toast.makeText(activity, "Pleae turn on GPS", Toast.LENGTH_LONG).show();
            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            activity.startActivity(myIntent);
        }
	}
	
	public void loadLocation(){
		if(!isLoadLocation){
			locationTask= new LocationTask();
			locationTask.execute( context );
		}
		// first overlay
		drawable = getResources().getDrawable(R.drawable.marker);
		itemizedOverlay = new MyItemizedOverlay(drawable, mapView,activity);
	}
	
	public void setOnClickListener(OnClickListener listener){
		
		
	}

	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.locationlayout, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	protected static class LocationTask extends AsyncTask<Context, Integer, String>
	{
		private int status;

		public LocationTask(){

		}
		
		@Override
		protected String doInBackground( Context... params ) 
		{
			//-- on every iteration
			//-- runs a while loop that causes the thread to sleep for 50 milliseconds 
			//-- publishes the progress - calls the onProgressUpdate handler defined below
			//-- and increments the counter variable i by one
			/** Handling XML */
			try{
				URL urls = new URL(MainApp.LOCATION_URL);
				HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
				/*connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setUseCaches(false);
				*/
				// Send Post Variable through these Commands
				
				String response= "";

				Scanner inStream = new Scanner(connection.getInputStream());
				while(inStream.hasNextLine()){
					response+=(inStream.nextLine());
				}
				
				Document d = DocumentFunction.stringToDocument(response);
				connection.disconnect();

				NodeList nodeList = d.getElementsByTagName("location_list");
				Node item = nodeList.item(0);
				
				NodeList couponNodeList = item.getChildNodes();
				   System.out.println("NodeName "+couponNodeList.item(0).getNodeName());
				//couponDataList= new ArrayList<EcouponData>();
				
				
				
				for (int j = 0; j < couponNodeList.getLength(); j++) {
					locationDataList.add(new LocationData());
					Element element =(Element)couponNodeList.item(j);
					locationDataList.get(j).setName(element.getAttribute("name"));
					locationDataList.get(j).setDescription(element.getAttribute("description"));
					locationDataList.get(j).setLatitude(Double.parseDouble(element.getAttribute("latitude")));
					locationDataList.get(j).setLongtitude(Double.parseDouble(element.getAttribute("longitude")));
					locationDataList.get(j).setTel(element.getAttribute("tel"));
				}
				//InitVal.appSettings.setUsername(usernameEditText.getText().toString());
				status=InitVal.SUCCESS;
			}
			catch (UnknownHostException e){
				status=InitVal.CONNECTIONERROR;
			}
			catch (IOException e) {
				status=InitVal.CONNECTIONERROR;
			//	e.printStackTrace();
			}
			catch (FactoryConfigurationError e) {
				status=InitVal.DATAERROR;
			//	e.printStackTrace();
			} catch (SAXException e) {
				status=InitVal.DATAERROR;
			//	e.printStackTrace();
			} catch (ParserConfigurationException e) {
				status=InitVal.DATAERROR;
			//	e.printStackTrace();
			}
		
			
			return "COMPLETE!";
		}
		
		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			
			
		}
		
		// -- called from the publish progress 
		// -- notice that the datatype of the second param gets passed to this method
		@Override
		protected void onProgressUpdate(Integer... values) 
		{
			super.onProgressUpdate(values);
		}
		
		// -- called if the cancel button is pressed
		@Override
		protected void onCancelled()
		{
			super.onCancelled();
			
		}

		// -- called as soon as doInBackground method completes
		// -- notice that the third param gets passed to this method
		@Override
		protected void onPostExecute( String result ) 
		{
			super.onPostExecute(result);
			
			//System.out.println("InitVal Complete!!");
			if(status==InitVal.SUCCESS){
				for(int i=0;i<locationDataList.size();i++){
					LocationData locationData = locationDataList.get(i);
					GeoPoint point = new GeoPoint((int)(locationData.getLatitude()*1E6),(int)(locationData.getLongtitude()*1E6));
					OverlayItem overlayItem = new OverlayItem(point, locationData.getName(), 
						locationData.getDescription()+"\nTel : "+locationData.getTel());
					itemizedOverlay.addOverlay(overlayItem);
				}
			
				mapOverlays.add(itemizedOverlay);
				isLoadLocation=true;
			}else{
				DialogFunction.showError(context,status);
			}
		}
	}	
	
	
}
