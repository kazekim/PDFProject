package com.CodeGears.AuntiAnne.eCard;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.CodeGears.AuntiAnne.MainApp;
import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.contact.ContactData;
import com.CodeGears.AuntiAnne.contact.ContactDetailView;
import com.CodeGears.AuntiAnne.function.DialogFunction;
import com.CodeGears.AuntiAnne.function.DocumentFunction;
import com.CodeGears.AuntiAnne.loadimage.ImageLoader;

public class EcardView extends LinearLayout{

	private static Context context;
	private static Activity activity;
	
	private ContactDetailView contactDetailView;
	
	private static ImageView ecardBGImage;
	private Button leftButton;
	private Button rightButton;
	private Button selectButton;
	
	private static ImageLoader imageLoader; 
	
	private static ArrayList<StickerData> stickerDataList;
	private static ArrayList<CardData> cardDataList;
	
	private static LoadEcardTask loadEcardTask;
	
	private static boolean canClick=false;
	
	
	public EcardView(Activity _activity,Context _context) {
		super(_context);
		createView();
		
		activity = _activity;
		context = _context;
		
		ecardBGImage = (ImageView) findViewById(R.id.ecardbgselect);
		leftButton = (Button) findViewById(R.id.ecardselectleftbutton);
		rightButton = (Button) findViewById(R.id.ecardselectrightbutton);
		selectButton = (Button) findViewById(R.id.ecardselectbutton);
		
		 imageLoader=new ImageLoader(activity.getApplicationContext());

		loadEcardTask= new LoadEcardTask();
		loadEcardTask.execute( context );
	}
	
	public void setOnClickListener(OnClickListener listener){
		leftButton.setOnClickListener(listener);
		rightButton.setOnClickListener(listener);
		selectButton.setOnClickListener(listener);
	}
	

	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.ecardlayout, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	public boolean leftButtonIsClick(){
		if(!canClick){
			return false;
		}
		return leftButton.isPressed();
	}
	
	public boolean rightButtonIsClick(){
		if(!canClick){
			return false;
		}
		return rightButton.isPressed();
	}

	public boolean selectButtonIsClick(){
		if(!canClick){
			return false;
		}
		return selectButton.isPressed();
	}
	
	public void moveBackgroundPrevious(){
		InitVal.cardIndex--;
		
		if(InitVal.cardIndex<0){
			InitVal.cardIndex=cardDataList.size()-1;
		}
		
		imageLoader.DisplayImage(cardDataList.get(InitVal.cardIndex).getPictureURL(), activity, ecardBGImage,true);
	}
	
	public void moveBackgroundForward(){
		InitVal.cardIndex++;
		
		if(InitVal.cardIndex>cardDataList.size()-1){
			InitVal.cardIndex=0;
		}
		
		imageLoader.DisplayImage(cardDataList.get(InitVal.cardIndex).getPictureURL(), activity, ecardBGImage,true);
	}
	
	public void recycleBitmap(){
    	for(int i=0;i<cardDataList.size();i++){
    		cardDataList.get(i).recycle();
    	}
    	
    	for(int i=0;i<stickerDataList.size();i++){
    		stickerDataList.get(i).recycle();
    	}

    }
	
	public void cancelTask(){
		loadEcardTask.cancel(true);
	}
	
	protected static class LoadEcardTask extends AsyncTask<Context, Integer, String>
	{
		private int status;
	
		public LoadEcardTask(){
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
				URL urls = new URL(MainApp.ECARD_URL);
				HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
				
				String response= "";

				Scanner inStream = new Scanner(connection.getInputStream());
				while(inStream.hasNextLine()){
					response+=(inStream.nextLine());
				}
			
				//System.out.println(response);
				if(!response.equals("fail")){

					Document d = DocumentFunction.stringToDocument(response);
					connection.disconnect();

					NodeList nodeList = d.getElementsByTagName("card_list");
					//System.out.println(nodeList.getLength());
					Node item = nodeList.item(0);
					
					NodeList ecardNodeList = item.getChildNodes();
					//   System.out.println("NodeName "+ecardNodeList.item(0).getNodeName());

					cardDataList= new ArrayList<CardData>();
					stickerDataList = new ArrayList<StickerData>();
					
					
					for (int j = 0; j < ecardNodeList.getLength(); j++) {
					//	EcouponData couponData =new EcouponData();
					//	couponDataList.add(couponData);
						
						Element element =(Element) ecardNodeList.item(j);
						
						if(element.getNodeName().equals("card")){
							CardData cardData = new CardData();
							cardDataList.add(cardData);
							cardData.setName(element.getAttribute("name"));
							cardData.setPictureURL(element.getAttribute("picture"));
							cardData.setColor1(element.getAttribute("color1"));
							cardData.setColor2(element.getAttribute("color2"));
							cardData.setFont(element.getAttribute("font"));
							cardData.setFontColor(element.getAttribute("font_color"));
							cardData.setFontPositionX(Double.parseDouble(element.getAttribute("font_positionx")));
							cardData.setFontPositionY(Double.parseDouble(element.getAttribute("font_positiony")));
							cardData.setFontSize(Integer.parseInt(element.getAttribute("font_size")));
							cardData.setFontAlign(element.getAttribute("font_align"));
							
							//System.out.println("id "+cardData.getName()+" "+cardData.getPictureURL());
						}else{
							StickerData stickerData = new StickerData();
							stickerDataList.add(stickerData);
							stickerData.setName(element.getAttribute("name"));
							stickerData.setPictureURL(element.getAttribute("picture"));
							
							//System.out.println("id "+stickerData.getName()+" "+stickerData.getPictureURL());
						}

	

					}
					InitVal.cardDataList = cardDataList;
					InitVal.stickerDataList = stickerDataList;
					
					status=InitVal.SUCCESS;
				}
			}
			catch (UnknownHostException e){
				status=InitVal.CONNECTIONERROR;
			}
			catch (IOException e) {
				status=InitVal.CONNECTIONERROR;
				e.printStackTrace();
			}
			catch (FactoryConfigurationError e) {
				status=InitVal.DATAERROR;
				e.printStackTrace();
			} catch (SAXException e) {
				status=InitVal.DATAERROR;
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				status=InitVal.DATAERROR;
				e.printStackTrace();
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
				if(cardDataList.size()>0){
					imageLoader.DisplayImage(cardDataList.get(InitVal.cardIndex).getPictureURL(), activity, ecardBGImage,true);
					canClick=true;
				}
			}else{
				DialogFunction.showError(context,status);
			}
			
		}
	}	
}