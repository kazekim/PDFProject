package com.CodeGears.AuntiAnne.contact;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;

public class ContactDetailView  extends LinearLayout{

	private TextView telText;
	private TextView telValue;
	private TextView telText2;
	private TextView telValue2;
	private TextView faxText;
	private TextView faxValue;
	private TextView addressText;
	private TextView addressValue;
	private TextView linkText;
	private Button telButton;
	private Button telButton2;
	private ArrayList<Button> linkButtons;
	
	private LinearLayout linkLayout;
	
	public ContactDetailView(Context context,ContactData contactData) {
		super(context);
		createView();

		telText = (TextView) findViewById(R.id.teltext);
		telValue = (TextView) findViewById(R.id.telvalue);
		telText2 = (TextView) findViewById(R.id.teltext2);
		telValue2 = (TextView) findViewById(R.id.telvalue2);
		faxText = (TextView) findViewById(R.id.faxtext);
		faxValue = (TextView) findViewById(R.id.faxvalue);
		addressText = (TextView) findViewById(R.id.addresstext);
		addressValue = (TextView) findViewById(R.id.addressvalue);
		linkText = (TextView) findViewById(R.id.linktext);
		telButton = (Button) findViewById(R.id.telbutton);
		telButton2 = (Button) findViewById(R.id.telbutton2);
		
		
		telText.setTypeface(InitVal.FontBold);
		telText.setTextSize(InitVal.fontSizeContact);
		telText.setTextColor(InitVal.BLUECOLOR);
		telValue.setTypeface(InitVal.Font);
		telValue.setTextSize(InitVal.fontSizeContact2);
		telValue.setTextColor(InitVal.BLUECOLOR);
		telText2.setTypeface(InitVal.FontBold);
		telText2.setTextSize(InitVal.fontSizeContact);
		telText2.setTextColor(InitVal.BLUECOLOR);
		telValue2.setTypeface(InitVal.Font);
		telValue2.setTextSize(InitVal.fontSizeContact2);
		
		telValue2.setTextColor(InitVal.BLUECOLOR);
		faxText.setTypeface(InitVal.FontBold);
		faxText.setTextSize(InitVal.fontSizeContact);
		faxText.setTextColor(InitVal.BLUECOLOR);
		faxValue.setTypeface(InitVal.Font);
		faxValue.setTextSize(InitVal.fontSizeContact);
		faxValue.setTextColor(InitVal.BLUECOLOR);
		addressText.setTypeface(InitVal.FontBold);
		addressText.setTextSize(InitVal.fontSizeContact);
		addressText.setTextColor(InitVal.BLUECOLOR);
		addressValue.setTypeface(InitVal.Font);
		addressValue.setTextSize(InitVal.fontSizeContact);
		addressValue.setTextColor(InitVal.BLUECOLOR);
		linkText.setTypeface(InitVal.FontBold);
		linkText.setTextSize(InitVal.fontSizeContact);
		linkText.setTextColor(InitVal.BLUECOLOR);
		
		telValue.setText(contactData.getTel()+" ("+contactData.getTelDescript()+")");
		telValue2.setText(contactData.getTel2()+" ("+contactData.getTelDescript2()+")");
		faxValue.setText(contactData.getFax());
		addressValue.setText(contactData.getAddress());
	
		linkLayout = (LinearLayout) findViewById(R.id.linklayout);
		linkButtons = new ArrayList<Button>();
		
		for(int i=0;i<contactData.getLinksNum();i++){
			TextView tv = new TextView(context);

			tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 35, 1f));

			tv.setTypeface(InitVal.Font);
			
			if(InitVal.screenType==InitVal.MDPI){
				tv.setTextSize(InitVal.fontSizeContact);
			}else{
				tv.setTextSize(InitVal.fontSizeContact2);
			}
			tv.setTextColor(InitVal.BLUECOLOR);
			tv.setText(contactData.getLinkAtIndex(i));
			tv.setGravity(Gravity.CENTER_VERTICAL);
			     
			Button button = new Button(context);
			if(InitVal.screenType==InitVal.MDPI){
				button.setLayoutParams(new LinearLayout.LayoutParams(39, 16));
			}else if(InitVal.screenType==InitVal.HDPI){
				button.setLayoutParams(new LinearLayout.LayoutParams(85, 33));
			}else{
				button.setLayoutParams(new LinearLayout.LayoutParams(85, 33));	
			}
			
			LinearLayout.LayoutParams lllp2=(LinearLayout.LayoutParams)button.getLayoutParams(); 
			lllp2.gravity=Gravity.CENTER_VERTICAL; 
			button.setLayoutParams(lllp2); 
			
			button.setBackgroundResource(R.drawable.buttonopenhd);
			
			linkButtons.add(button);
			
			LinearLayout lo = new LinearLayout(context);
			lo.setOrientation(LinearLayout.HORIZONTAL);
			lo.addView(tv);
			lo.addView(button);
			linkLayout.addView(lo);
		}
	}
	
	public void setOnClickListener(OnClickListener listener){
		telButton.setOnClickListener(listener);
		telButton2.setOnClickListener(listener);
		for(int i=0;i<linkButtons.size();i++){
			linkButtons.get(i).setOnClickListener(listener);
		}
	}
	
	public int checkLinkButtonIsClick(){
		int valueret = -1;
		for(int i=0;i<linkButtons.size();i++){
			if(linkButtons.get(i).isPressed()){
				return i;
			}
		}
		
		return valueret;
	}
	
	public boolean checkTelButtonIsClick(){
		return telButton.isPressed();
	}
	
	public boolean checkTelButton2IsClick(){
		return telButton2.isPressed();
	}
	
	
	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.contact, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	
 
}