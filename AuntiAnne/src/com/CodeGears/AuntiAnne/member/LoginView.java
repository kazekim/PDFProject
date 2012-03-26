package com.CodeGears.AuntiAnne.member;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import com.CodeGears.AuntiAnne.MainApp;
import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.TabActivity;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.function.DialogFunction;
import com.CodeGears.AuntiAnne.function.DocumentFunction;


import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class LoginView  extends LinearLayout {

	
	private Button loginButton;
	private Button registerButton;
	private EditText usernameEditText;
	private TextView loginDialogText;
	private TextView registerText;
	
	private static LoginTask loginTask;
	private static Context context;
	private static TabActivity activity;
	
	public static int status;
	
	public LoginView(Context _context,TabActivity _activity) {
		super(_context);
		createView();
		
		context=_context;
		activity=_activity;
		
		usernameEditText = (EditText) findViewById(R.id.username);
		loginButton = (Button) findViewById(R.id.loginbutton);
		loginDialogText = (TextView) findViewById(R.id.loginquestion);
		registerText = (TextView) findViewById(R.id.registerdesc);
		registerButton = (Button) findViewById(R.id.registerbutton);

		loginDialogText.setTypeface(InitVal.Font);
		loginDialogText.setTextSize(InitVal.fontSizeMember);
		loginDialogText.setTextColor(InitVal.BLUECOLOR);
		
		registerText.setTypeface(InitVal.Font);
		registerText.setTextSize(InitVal.fontSizeMember);
		registerText.setTextColor(InitVal.BLUECOLOR);
		
		//SettingData.save(context);
	}
	
	public void setOnClickListener(OnClickListener listener){
		loginButton.setOnClickListener(listener);
		usernameEditText.setOnClickListener(listener);
		registerButton.setOnClickListener(listener);
	}
	
	public Boolean loginButtonIsClick(){
		return loginButton.isPressed();
	}

	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.login, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	public void doLogin(){
		 InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE); 
		  inputManager.hideSoftInputFromWindow(usernameEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		  
		 doLogin(usernameEditText.getText().toString());
	}
	
	
	public static void doLogin(Context _context,String username){ 
			context=_context;
			 doLogin(username);
	}
	
	private static void doLogin(String username){ 
		if(!username.equals("")){
			loginTask= new LoginTask(username);
			loginTask.execute( context );
		}else{
			CharSequence text = context.getString(R.string.plzcorrecttel);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
	}
	
	public boolean registerButtonIsClick(){
		return registerButton.isPressed();
	}

	protected static class LoginTask extends AsyncTask<Context, Integer, String>
	{
		private String data;
	
		public LoginTask(String username){
			data="phone="+username;
			
		}
		
		@Override
		protected String doInBackground( Context... params ) 
		{
			//-- on every iteration
			//-- runs a while loop that causes the thread to sleep for 50 milliseconds 
			//-- publishes the progress - calls the onProgressUpdate handler defined below
			//-- and increments the counter variable i by one
			/** Handling XML */
			status=InitVal.CONNECTIONERROR;
			try{
				URL urls = new URL(MainApp.LOGIN_URL);
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
				
			String response= "";

				Scanner inStream = new Scanner(connection.getInputStream());
				while(inStream.hasNextLine()){
					response+=(inStream.nextLine());

				}
				
				if(!response.equals("fail")){

					Document d = DocumentFunction.stringToDocument(response);
					connection.disconnect();
				
					NodeList nodeList = d.getElementsByTagName("user");

					Element element =(Element)nodeList.item(0);
					
					InitVal.userData.setName(element.getAttribute("name"));
					InitVal.userData.setSurname(element.getAttribute("surname"));
					InitVal.userData.setPhone(element.getAttribute("phone"));
					InitVal.userData.setPoint(Integer.parseInt(element.getAttribute("point")));
					InitVal.userData.setQuota(Integer.parseInt(element.getAttribute("quota")));
					InitVal.userData.setIsBirthDay(element.getAttribute("isBirthday"));
					InitVal.userData.setIsRegister(element.getAttribute("isRegister"));
					
					InitVal.appSettings.setUsername(InitVal.userData.getPhone());
					if(status!=InitVal.LOADFROMPREFERENCE){
						status=InitVal.SUCCESS;
					}
				}else{
					status=InitVal.FAIL;
				}
			}catch (UnknownHostException e){
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
			
			InitVal.canOpenMember=true;
			if(status==InitVal.SUCCESS){
				if(InitVal.curPage==InitVal.MEMBER)
					activity.openPage(InitVal.curPage);
			}else if(status==InitVal.LOADFROMPREFERENCE){
				
			}else{
				DialogFunction.showError(context,status);
			}

		}
	}	
}