package com.CodeGears.AuntiAnne.eCard;

import com.CodeGears.AuntiAnne.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

public class ColorPickBox extends LinearLayout{
	
	private LinearLayout color;
	private Button button;
	
	private String colorText;

	public ColorPickBox(Context context,String colorText) {
		super(context);
		createView();
		
		this.colorText=colorText;
		
		color = (LinearLayout) findViewById(R.id.ecardcolorpickcolor);
		button = (Button) findViewById(R.id.ecardcolorpickbutton);
		
		color.setBackgroundColor(Color.parseColor("#"+colorText));
	}
	
	public void setOnClickListener(OnClickListener listener){
		button.setOnClickListener(listener);
		System.out.println("dsadasda");
	}
	
	public boolean buttonIsClicked(){
		return button.isPressed();
	}
	
	public String getColorText(){
		return colorText;
	}
	
	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.colorpickbox, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
}