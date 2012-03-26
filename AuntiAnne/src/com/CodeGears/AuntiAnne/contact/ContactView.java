package com.CodeGears.AuntiAnne.contact;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.view.LayoutInflater;
import android.widget.LinearLayout;


import com.CodeGears.AuntiAnne.R;


public class ContactView extends LinearLayout{

	private Context context;
	private ContactData contactData;
	
	private ContactDetailView contactDetailView;
	private LinearLayout detailView;
	
	public ContactView(Context context) {
		super(context);
		createView();
		
		this.context = context;
		
		contactData = new ContactData();
		loadContact();
		
		contactDetailView = new ContactDetailView(context, contactData);
		
		detailView = (LinearLayout) findViewById(R.id.detailview);
		detailView.addView(contactDetailView);
		
	}
	
	public void setOnClickListener(OnClickListener listener){
		contactDetailView.setOnClickListener(listener);
	}
	
	public int checkLinkButtonIsClick(){
		return contactDetailView.checkLinkButtonIsClick();	
	}
	
	public boolean checkTelButtonIsClick(){
		return contactDetailView.checkTelButtonIsClick();
	}
	
	public boolean checkTelButton2IsClick(){
		return contactDetailView.checkTelButtonIsClick();
	}
	
	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.detail, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	public String getLinkURLAtIndex(int index){
		return contactData.getLinkAtIndex(index);
	}
	
	public String getTel(){
		return contactData.getTel();
	}
	
	public String getTel2(){
		return contactData.getTel2();
	}
	
	public void loadContact(){
		
		try{
			Resources res = context.getResources();
		   XmlResourceParser xpp = res.getXml(R.xml.contacts);
		   xpp.next();
		   int eventType = xpp.getEventType();
		   
		   
		   
		   while (eventType != XmlPullParser.END_DOCUMENT)
		   {
		    if(eventType == XmlPullParser.START_TAG)
		    {

		    	if(xpp.getName().equals("tel1")){
		    		contactData.setTel(xpp.getAttributeValue(null, "value"));
		    		contactData.setTelDescript(xpp.getAttributeValue(null, "descript"));
		    	}else if(xpp.getName().equals("tel2")){
		    		contactData.setTel2(xpp.getAttributeValue(null, "value"));
		    		contactData.setTelDescript2(xpp.getAttributeValue(null, "descript"));
		    	}else if(xpp.getName().equals("fax")){
		    		contactData.setFax(xpp.getAttributeValue(null, "value"));
		    	}else if(xpp.getName().equals("address")){
		    		contactData.setAddress(xpp.getAttributeValue(null, "value"));
		    	}else if(xpp.getName().equals("link")){
		    		contactData.addLinks(xpp.getAttributeValue(null, "url"));
		    	}
		    }
		    eventType = xpp.next();
		   }
		   
		} catch (XmlPullParserException e) {
	  		// TODO Auto-generated catch block
	  		e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		}
}