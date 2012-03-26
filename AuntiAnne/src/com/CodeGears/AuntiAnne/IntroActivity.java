package com.CodeGears.AuntiAnne;


import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.function.Timer;
import com.CodeGears.AuntiAnne.member.LoginView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class IntroActivity extends Activity{
	
	private DataTask dataTask;
	
	private static int INTRO_TIME=4000;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introactivity);
        
        dataTask = new DataTask();
		dataTask.execute( this );
		
		if(!InitVal.appSettings.getUsername().equals("")){
			InitVal.canOpenMember=false;
			LoginView.status=InitVal.LOADFROMPREFERENCE;
			LoginView.doLogin(this,InitVal.appSettings.getUsername());
		
		}
    }

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
	    super.onWindowFocusChanged(hasFocus);

	}
	
	public class DataTask extends AsyncTask<Context, Integer, String>
	{
		
		public DataTask(){

		}
		
		@Override
		protected String doInBackground( Context... params ) 
		{
			//-- on every iteration
			//-- runs a while loop that causes the thread to sleep for 50 milliseconds 
			//-- publishes the progress - calls the onProgressUpdate handler defined below
			//-- and increments the counter variable i by one
			/** Handling XML */
			
			Timer timer = new Timer();
			
			timer.start();
			
			while(timer.getDuration()<INTRO_TIME){

				timer.update();
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
			

			Intent intent = new Intent( IntroActivity.this, MainActivity.class );
	        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
	        startActivity( intent );
		}
		
		
	}  
}
