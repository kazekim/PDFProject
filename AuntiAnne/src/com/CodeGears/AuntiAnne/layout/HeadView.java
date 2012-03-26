package com.CodeGears.AuntiAnne.layout;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HeadView extends LinearLayout{
	private Button backButton;
	private Button homeButton;
	private ImageView logo;
	private TextView headText;
	
	public HeadView(Context context) {
		super(context);

		createView();
		
		backButton = (Button) findViewById(R.id.backbutton);
		homeButton =(Button) findViewById(R.id.homebutton);
		logo = (ImageView) findViewById(R.id.logo);
		headText = (TextView) findViewById(R.id.headtext);
		
		headText.setTypeface(InitVal.FontBold);
		headText.setTextSize(InitVal.fontSizeCoreMenuTitle);
		headText.setTextColor(InitVal.BLUECOLOR);
		
		
	}
	
	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.head, this, true);
		
	}
	
	public void hideBackButton(){
		backButton.setVisibility(View.GONE);
	}
	
	public void hideHomeButton(){
		homeButton.setVisibility(View.GONE);
	}
	
	public boolean backButtonisClick(){
		return backButton.isPressed();
	}
	
	public boolean homeButtonisClick(){
		return homeButton.isPressed();
	}
	
	public void setHeadText(String title){
		headText.setText(title);
		logo.setVisibility(View.GONE);
	}
	
	public void setListener(OnClickListener listener){
		backButton.setOnClickListener(listener);
		homeButton.setOnClickListener(listener);
	}
	

}
