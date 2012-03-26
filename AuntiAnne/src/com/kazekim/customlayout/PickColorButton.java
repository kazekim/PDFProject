package com.kazekim.customlayout;

import java.util.ArrayList;

import com.CodeGears.AuntiAnne.R;

import android.R.attr;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;

public class PickColorButton extends RelativeLayout implements OnClickListener{
	
	private Button[] colorButton;
	private ImageView pickColorBGImageView;
	
	private OnChooseColorListener listener;
	private static final int MAXCOLOR = 15;
	private ArrayList<String> colorList;

	public PickColorButton(Context context) {
		super(context);
		
		createView();
		
	}
	
	public PickColorButton(Context context,AttributeSet attrs){
		super(context,attrs);
		
	}

	
	public void setBackgroundDrawable(Drawable pickColorBG){
		
		pickColorBGImageView.setBackgroundDrawable(pickColorBG);
	}
	
	public void addColor(String colorCode){
		try{
			if(colorList.size()==MAXCOLOR){
				exceedMaxColor();
				return;
			}
			int color= Color.parseColor("#"+colorCode);
			colorButton[colorList.size()].setBackgroundColor(color);
			colorButton[colorList.size()].setVisibility(View.VISIBLE);
			colorButton[colorList.size()].setOnClickListener(this);
			
			colorList.add(colorCode);
			
		}catch (ColorLimitException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void setOnChooseColorListener(OnChooseColorListener listener){
		this.listener = listener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		for(int i=0;i<colorButton.length;i++){
			if(v == colorButton[i]){
				listener.onChooseColor(this,colorList.get(i));
				//System.out.println("Pressssssss!!!!!!!!");
			}
		}
	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layoutInflater.inflate(R.layout.pickcolorbutton, this);
		setupViewItems();
	}
 
	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout l = (RelativeLayout)layoutInflater.inflate(R.layout.pickcolorbutton, this, true);
		l.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		setupViewItems();
	}
	
	private void setupViewItems() {
		colorButton = new Button[15];
		colorButton[0] = (Button) findViewById(R.id.colorbutton1);
		colorButton[1] = (Button) findViewById(R.id.colorbutton2);
		colorButton[2] = (Button) findViewById(R.id.colorbutton3);
		colorButton[3] = (Button) findViewById(R.id.colorbutton4);
		colorButton[4] = (Button) findViewById(R.id.colorbutton5);
		colorButton[5] = (Button) findViewById(R.id.colorbutton6);
		colorButton[6] = (Button) findViewById(R.id.colorbutton7);
		colorButton[7] = (Button) findViewById(R.id.colorbutton8);
		colorButton[8] = (Button) findViewById(R.id.colorbutton9);
		colorButton[9] = (Button) findViewById(R.id.colorbutton10);
		colorButton[10] = (Button) findViewById(R.id.colorbutton11);
		colorButton[11] = (Button) findViewById(R.id.colorbutton12);
		colorButton[12] = (Button) findViewById(R.id.colorbutton13);
		colorButton[13] = (Button) findViewById(R.id.colorbutton14);
		colorButton[14] = (Button) findViewById(R.id.colorbutton15);
		
		pickColorBGImageView = (ImageView) findViewById(R.id.pickcolorbuttonbg);
		
		colorList = new ArrayList<String>();
	}
 

	private void exceedMaxColor() throws ColorLimitException {
        throw new ColorLimitException("Color Limit Exceed Exception");
    }
	
	public class ColorLimitException extends Exception {
		private static final long serialVersionUID = -1187717227380353321L;

		ColorLimitException(String message) {
	        super(message);

	    }
	}
	
	
}
