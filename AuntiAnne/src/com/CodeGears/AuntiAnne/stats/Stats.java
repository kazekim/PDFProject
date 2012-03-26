package com.CodeGears.AuntiAnne.stats;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import javax.xml.parsers.FactoryConfigurationError;

import com.CodeGears.AuntiAnne.MainApp;
import com.CodeGears.AuntiAnne.appdata.InitVal;

import android.content.Context;
import android.os.AsyncTask;

public class Stats {

	private String userID;
	private int action;
	private String parameter1;
	private String parameter2;//
	
	private StatsTask statsTask;
	
	public Stats(Context context,String userID,int action){
		this.action=action;
		this.userID=userID;
		
		statsTask = new StatsTask();
		statsTask.execute( context );
	}
	
	public Stats(Context context, String userID,int action,String parameter1, String parameter2){
		this.parameter1=parameter1;
		this.parameter2=parameter2;
	
		statsTask = new StatsTask();
		statsTask.execute( context );
	}
	
	
	public class StatsTask extends AsyncTask<Context, Integer, String>
	{
		private String data;
		public StatsTask(){
			data="action="+action+"&user_id="+userID;
			if(parameter1!=""){
				data+="&parameter1="+parameter1;	
			}
			if(parameter2!=""){
				data+="&parameter2="+parameter2;
			}
			
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
				URL urls = new URL(MainApp.STATS_URL);
				HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setUseCaches(false);
				
				// Send Post Variable through these Commands
				
				OutputStream outStream = connection.getOutputStream();
				Writer writer = new OutputStreamWriter(outStream, "UTF-8");
				writer.write(data);
				writer.flush();
				writer.close();
				outStream.close();
			
				connection.disconnect();
					
			}
			catch (UnknownHostException e){
				
			}
			catch (IOException e) {
				//e.printStackTrace();
			}
			catch (FactoryConfigurationError e) {
				//e.printStackTrace();
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
			
			//System.out.println("Stats Complete!!");
		}
		
		
	}  
}
