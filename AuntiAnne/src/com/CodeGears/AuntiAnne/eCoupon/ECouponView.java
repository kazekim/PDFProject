package com.CodeGears.AuntiAnne.eCoupon;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.CodeGears.AuntiAnne.MainApp;
import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.function.DatabaseHelper;
import com.CodeGears.AuntiAnne.function.DialogFunction;
import com.CodeGears.AuntiAnne.function.DocumentFunction;

public class ECouponView extends LinearLayout{

	private static final String AnimationUtils = null;
	//private ScrollView couponScrollView;
	private static GridView couponGridView;
	private static EcouponGridView couponAdaptor;
	
	private Button confirmButton;
	private Button cancelButton;
	private Button confirmButton2;
	
	private TextView confirmDialogText;
	private TextView couponDialogText;
	private TextView couponDialogDate;
	private TextView couponDialogCode;
	
	private RelativeLayout confirmCouponBox;
	private RelativeLayout couponBox;
	
	private static ArrayList<EcouponData> couponDataList;
	
	private static Context context;
	private static Activity activity;
	private static DatabaseHelper dh;
	
	private static LoadCouponTask loadCouponTask;
	
	private static int categoryType;
	private static String dbname;
	private static String insertQuery;
	
	private int clickedItem;
	
	private int useCouponStep=0;
	private int USERSTEP=0;
	private int SHOPSTEP=1;
	
	private static boolean canClick=false;
	
	public ECouponView(Context _context,Activity _activity,int _categoryType) {
		super(_context);
		createView();
		context=_context;
		activity=_activity;
		
		categoryType = _categoryType;
		
		//System.out.println("asdasd"+categoryType);
		/*
		if(categoryType==InitVal.MEMBERCOUPON){
			dbname=EcouponData.MEMBERCOUPONDBTABLENAME;
			insertQuery = EcouponData.MEMBERINSERTQUERY;
		}else{*/
			dbname=EcouponData.COUPONDBTABLENAME;
			insertQuery = EcouponData.INSERTQUERY;
		//}
		
		//couponScrollView = (ScrollView) findViewById(R.id.ecouponscroll);
	
		couponGridView = (GridView) findViewById(R.id.coupongridview);
		
		dh = new DatabaseHelper(context);
		dh.setInsertStatement(insertQuery);
		
		confirmButton = (Button) findViewById(R.id.confirmbutton);
		cancelButton = (Button) findViewById(R.id.cancelbutton);
		confirmButton2 = (Button) findViewById(R.id.confirmbutton2);
		confirmCouponBox = (RelativeLayout) findViewById(R.id.confirmdialog);
		couponBox = (RelativeLayout) findViewById(R.id.coupondialog);
		
		confirmDialogText = (TextView) findViewById(R.id.confirmcoupontext);
		couponDialogText = (TextView) findViewById(R.id.coupondialogtext);
		couponDialogDate = (TextView) findViewById(R.id.coupondialogdate);
		couponDialogCode= (TextView) findViewById(R.id.coupongeneratecode);
		
		
		confirmCouponBox.setVisibility(View.GONE);
		couponBox.setVisibility(View.GONE);
		
		confirmDialogText.setTypeface(InitVal.Font);
		confirmDialogText.setTextSize(InitVal.fontSizeConfirmCouponDialog);
		confirmDialogText.setTextColor(InitVal.BLUECOLOR);
		
		couponDialogText.setTypeface(InitVal.Font);
		couponDialogText.setTextSize(InitVal.fontSizeCouponDialog);
		couponDialogText.setTextColor(InitVal.BLUECOLOR);
		
		couponDialogDate.setTypeface(InitVal.Font);
		couponDialogDate.setTextSize(InitVal.fontSizeCouponDialog);
		couponDialogDate.setTextColor(InitVal.BLUECOLOR);
		couponDialogDate.setText(InitVal.todayDate);
		
		couponDialogCode.setTypeface(InitVal.Font);
		couponDialogCode.setTextSize(InitVal.fontSizeCouponDialogCode);
		couponDialogCode.setTextColor(InitVal.BLUECOLOR);
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		couponGridView.setOnItemClickListener(listener);
	}
	
	public void setOnClickListener(OnClickListener listener){
		confirmButton.setOnClickListener(listener);
		confirmButton2.setOnClickListener(listener);
		cancelButton.setOnClickListener(listener);
	}
	
	public boolean canClick(){
		return canClick;
	}
	
	public void loadCoupon(){
		
		

		if(!InitVal.appSettings.getLastLoadCouponDate().equals(InitVal.todayDate) && categoryType==InitVal.NORMALCOUPON){
			
			couponBox.setVisibility(View.VISIBLE);
			
			

		}else if(!InitVal.appSettings.getLastLoadMemberCouponDate().equals(InitVal.todayDate) && categoryType==InitVal.MEMBERCOUPON){
			
			couponBox.setVisibility(View.VISIBLE);
			
			

		}else{

			couponDataList = EcouponData.createEcouponList(dh.selectAll(dbname, EcouponData.column
					, "couponType = '"+categoryType+"'", null, null,null, null));
			
			couponAdaptor = new EcouponGridView(couponDataList,context,activity);
			couponGridView.setAdapter(couponAdaptor);

		}
	}
	
	
	public void useCoupon(){
		System.out.println("dsadasd");
		if(useCouponStep==USERSTEP){
			useCouponStep=SHOPSTEP;
			
			confirmDialogText.setText(getContext().getText(R.string.confirmusecouponshop));
		}else{
			useCouponAtIndex(clickedItem);
			confirmDialogText.setText(getContext().getText(R.string.confirmusecoupon));
			closeConfirmCouponBox();
		}
		
	}
	public void useCouponAtIndex(int index){

		
		EcouponData couponData = couponDataList.get(index);
		couponData.setIsUsed(true);
		couponAdaptor.notifyDataSetChanged();
		couponGridView.invalidateViews();
		
		ContentValues args = new ContentValues();
		args.put(EcouponData.column[5], "1");
		couponAdaptor.notifyDataSetChanged();
		
		dh.update(dbname, args, EcouponData.column[1]+"='"+couponData.getCouponID()+"'" , null);

	}
	
	
	public boolean checkCouponIsUsed(int index){
		EcouponData couponData = couponDataList.get(index);
		
		return couponData.isUsed();
	}
	
	public void closeDatabase(){
		dh.closeDB();
	}
	
	public void clickOnGridNumber(int index){
		clickedItem=index;
		confirmCouponBox.setVisibility(View.VISIBLE);
		useCouponStep=USERSTEP;
		confirmDialogText.setText(getContext().getText(R.string.confirmusecoupon));
		couponDialogCode.setText(couponDataList.get(index).getCouponID());
	}
	
	public void closeConfirmCouponBox(){
		clickedItem=-1;
		confirmCouponBox.setVisibility(View.GONE);
	}
	
	public void closeCouponBox(){
		couponBox.setVisibility(View.GONE);
	}
	
	public boolean confirmButtonIsClick(){
		return confirmButton.isPressed();
	}
	
	public boolean confirmButton2IsClick(){
		
		return confirmButton2.isPressed();
	}
	
	public boolean cancelButtonIsClick(){
		return cancelButton.isPressed();
	}
	
	public void loadNewCoupon(){
		if(!InitVal.appSettings.getLastLoadCouponDate().equals(InitVal.todayDate) && !InitVal.appSettings.getLastLoadMemberCouponDate().equals(InitVal.todayDate)){
			dh.deleteAll(dbname);
		}		
		loadCouponTask = new LoadCouponTask(InitVal.todayDate);
		loadCouponTask.execute( context );
		
		
	}


	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.ecouponlayout, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	public void recycleBitmap(){
		for(int i=0;i<couponDataList.size();i++){
			couponDataList.get(i).recycle();
		}
	}

	protected static class LoadCouponTask extends AsyncTask<Context, Integer, String>
	{
		private String data;
		private String curDate;
		private int status;
	
		public LoadCouponTask(String curDate){
			this.curDate = curDate;
			data="date="+InitVal.todayDate+"&category="+categoryType;
			//data="date="+curDate+"&category="+categoryType;
		
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
				URL urls = new URL(MainApp.COUPON_URL);
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
				//System.out.println(response);
				if(!response.equals("fail")){

					Document d = DocumentFunction.stringToDocument(response);
					connection.disconnect();

					NodeList nodeList = d.getElementsByTagName("coupon_list");
					//System.out.println(nodeList.getLength());
					Node item = nodeList.item(0);
					
					NodeList couponNodeList = item.getChildNodes();
					//   System.out.println("NodeName "+couponNodeList.item(0).getNodeName());
					couponDataList= new ArrayList<EcouponData>();
					
					
					
					for (int j = 0; j < couponNodeList.getLength(); j++) {
						EcouponData couponData =new EcouponData();
						couponDataList.add(couponData);
						Element element =(Element)couponNodeList.item(j);
						couponData.setCouponID(element.getAttribute("id"));
						couponData.setCouponDescript(element.getAttribute("text"));
						couponData.setPictureURL(element.getAttribute("picture"));
						couponData.setCouponNotice(element.getAttribute("notice"));
	
						dh.insert(couponData.getCouponID(),couponData.getPictureURL(),couponData.getCouponDescript()
								,couponData.getCouponNotice(),"0",Integer.toString(categoryType));
						//System.out.println("id "+couponDataList.get(j).getCouponID()+" "+couponDataList.get(j).getPictureURL());
					}
					status=InitVal.SUCCESS;
				}else{
					status=InitVal.NODATA;
				}
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
				couponAdaptor = new EcouponGridView(couponDataList,context,activity);
				couponGridView.setAdapter(couponAdaptor);
				if(categoryType==InitVal.NORMALCOUPON){
					InitVal.appSettings.setLastLoadCouponDate(curDate);
				}else{
					InitVal.appSettings.setLastLoadMemberCouponDate(curDate);
				}
			}else{
				DialogFunction.showError(context,status);
			}
			canClick=true;
		}
	}	
}
