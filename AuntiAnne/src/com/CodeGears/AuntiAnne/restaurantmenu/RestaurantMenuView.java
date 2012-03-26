package com.CodeGears.AuntiAnne.restaurantmenu;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.CodeGears.AuntiAnne.MainApp;
import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.function.DialogFunction;
import com.CodeGears.AuntiAnne.function.DocumentFunction;

public class RestaurantMenuView extends LinearLayout {


	private RestaurantMenuTask restaurantMenuTask;
	private static Context context;
	private static Activity activity;
	
	private  static ArrayList<CategoryMenuData> restaurantMenuDataList;
	
	private  static LinearLayout menuLayout;
	//private TextView coreMenuTitle;
	//private static GridView coreMenuGridView;
	//private static CoreMenuGridView coreMenuAdaptor;
	
	public RestaurantMenuView(Context _context,Activity _activity) {
		super(_context);
		createView();
		
		activity=_activity;
		context=_context;
		
		restaurantMenuDataList = new ArrayList<CategoryMenuData>();
		
		restaurantMenuTask = new RestaurantMenuTask();
		restaurantMenuTask.execute( context );
		
		menuLayout = (LinearLayout) findViewById(R.id.menulayout);	
		
	}
	

	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.restaurantmenulayout, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	public void recycleBitmap(){
		for(int i=0;i<restaurantMenuDataList.size();i++){
			restaurantMenuDataList.get(i).recycle();
    	}
	}

	protected static class RestaurantMenuTask extends AsyncTask<Context, Integer, String>
	{

		private int status;
		
		public RestaurantMenuTask(){
			
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
				URL urls = new URL(MainApp.RESTAURANTMENU_URL);
				HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
				/*			 BufferedReader in = new BufferedReader(
			                         new InputStreamReader(
			                         connection.getInputStream()));
			 String inputLine;
			
			 while ((inputLine = in.readLine()) != null) 
			     System.out.println(inputLine);
			 in.close();*/
				
				String response= "";

				Scanner inStream = new Scanner(connection.getInputStream());
				while(inStream.hasNextLine()){
					response+=(inStream.nextLine());
				}
				
				Document d = DocumentFunction.stringToDocument(response);
				connection.disconnect();

				NodeList nodeList = d.getElementsByTagName("menu_list");
				Node item = nodeList.item(0);
				
				NodeList categoryNodeList = item.getChildNodes();
				 //  System.out.println("NodeName "+categoryNodeList.item(0).getNodeName());
				//couponDataList= new ArrayList<EcouponData>();
				
				
				
				for (int j = 0; j < categoryNodeList.getLength(); j++) {
					CategoryMenuData categoryData = new CategoryMenuData();
					restaurantMenuDataList.add(categoryData);
					Element element =(Element)categoryNodeList.item(j);
					categoryData.setCategoryName(element.getAttribute("name"));
					
					Node categoryItem = categoryNodeList.item(j);
					
					NodeList foodNodeList = categoryItem.getChildNodes();
					
					for(int k=0;k<foodNodeList.getLength();k++){
						FoodData foodData = new FoodData();
						categoryData.addFood(foodData);
						Element foodElement = (Element)foodNodeList.item(k);
						foodData.setName(foodElement.getAttribute("name"));
						foodData.setDescription(foodElement.getAttribute("description"));
						foodData.setthumbURL(foodElement.getAttribute("picture"));
						
					}
					
				}
				
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
			
			if(status==InitVal.SUCCESS){
				for(int i=0;i<restaurantMenuDataList.size();i++){
					RestaurantCategoryMenuView categoryMenuView = new RestaurantCategoryMenuView(context, activity, restaurantMenuDataList.get(i));
					menuLayout.addView(categoryMenuView);
				}
				//coreMenuAdaptor = new CoreMenuGridView(context,restaurantMenuDataList);
				//coreMenuGridView.setAdapter(coreMenuAdaptor);
			}else{
				DialogFunction.showError(context,status);
			
			}
			
			
		}
	}	
}
