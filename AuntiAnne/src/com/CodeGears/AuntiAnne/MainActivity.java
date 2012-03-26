package com.CodeGears.AuntiAnne;



import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.layout.HeadView;
import com.CodeGears.AuntiAnne.member.LoginView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity  implements OnClickListener{
    /** Called when the activity is first created. */
	
	private Button newsButton;
	private Button memberButton;
	private Button ecouponButton;
	private Button ecardButton;
	private Button locationButton;
	private Button contactButton;
	private Button restaurantMenuButton;
	
	private TextView newsMainLabel;
	private TextView memberMainLabel;
	private TextView ecouponMainLabel;
	private TextView ecardMainLabel;
	private TextView locationMainLabel;
	private TextView contactMainLabel;
	private TextView coreMenuMainLabel;
	
	private HeadView headView;
	
	private FrameLayout headLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        if(MainApp.isPlayIntro){

			MainApp.isPlayIntro=false;
			Intent intent = new Intent( MainActivity.this, IntroActivity.class );
	        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
	        startActivity( intent );
	        
		}else{
			setContentView(R.layout.main);
			
			headView = new HeadView(this);
			headLayout = (FrameLayout) findViewById(R.id.headview);
			headLayout.addView(headView);
			headView.hideBackButton();
			headView.hideHomeButton();
			
			newsButton = (Button) findViewById(R.id.newsbutton);
			memberButton = (Button) findViewById(R.id.memberbutton);
			ecouponButton = (Button) findViewById(R.id.ecouponbutton);
			ecardButton = (Button) findViewById(R.id.ecardbutton);
			locationButton = (Button) findViewById(R.id.locationbutton);
			contactButton = (Button) findViewById(R.id.contactbutton);
			restaurantMenuButton = (Button) findViewById(R.id.auntieannemenubutton);
			
			newsMainLabel =(TextView) findViewById(R.id.newsmainlabel);
			memberMainLabel =(TextView) findViewById(R.id.membermainlabel);
			ecouponMainLabel =(TextView) findViewById(R.id.ecouponmainlabel);
			ecardMainLabel =(TextView) findViewById(R.id.ecardmainlabel);
			locationMainLabel =(TextView) findViewById(R.id.locationmainlabel);
			contactMainLabel =(TextView) findViewById(R.id.contactmainlabel);
			coreMenuMainLabel =(TextView) findViewById(R.id.coremenumainlabel);
			
			Typeface font = InitVal.FontBold;
			float fontSize = InitVal.fontSizeMainMenu;
			
			newsMainLabel.setTypeface(font);
			memberMainLabel.setTypeface(font);
			ecouponMainLabel.setTypeface(font);
			ecardMainLabel.setTypeface(font);
			locationMainLabel.setTypeface(font);
			ecardMainLabel.setTypeface(font);
			contactMainLabel.setTypeface(font);
			coreMenuMainLabel.setTypeface(font);
			
			newsMainLabel.setTextSize(fontSize);
			memberMainLabel.setTextSize(fontSize);
			ecouponMainLabel.setTextSize(fontSize);
			ecardMainLabel.setTextSize(fontSize);
			locationMainLabel.setTextSize(fontSize);
			contactMainLabel.setTextSize(fontSize);
			coreMenuMainLabel.setTextSize(fontSize);
			
			newsMainLabel.setTextColor(InitVal.BLUECOLOR);
			memberMainLabel.setTextColor(InitVal.BLUECOLOR);
			ecouponMainLabel.setTextColor(InitVal.BLUECOLOR);
			ecardMainLabel.setTextColor(InitVal.BLUECOLOR);
			locationMainLabel.setTextColor(InitVal.BLUECOLOR);
			contactMainLabel.setTextColor(InitVal.BLUECOLOR);
			coreMenuMainLabel.setTextColor(InitVal.BLUECOLOR);
			
			
			newsButton.setOnClickListener(this);
			memberButton.setOnClickListener(this);
			contactButton.setOnClickListener(this);
			ecouponButton.setOnClickListener(this);
			ecardButton.setOnClickListener(this);
			locationButton.setOnClickListener(this);
			restaurantMenuButton.setOnClickListener(this);
			
			
		}
			
			
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		InitVal.curPage=-1;
		if(newsButton.isPressed()){
			InitVal.curPage=InitVal.NEWS;
			
		}else if(memberButton.isPressed()){
			InitVal.curPage=InitVal.MEMBER;
		}else if(ecouponButton.isPressed()){
			InitVal.curPage=InitVal.ECOUPON;
		}else if(ecardButton.isPressed()){
			InitVal.curPage=InitVal.ECARD;
		}else if(locationButton.isPressed()){
			InitVal.curPage=InitVal.LOCATION;
		}else if(contactButton.isPressed()){
			InitVal.curPage=InitVal.CONTACT;
		}else if(restaurantMenuButton.isPressed()){
			InitVal.curPage=InitVal.COREMENU;
		}else{
			
		}
		
		if(InitVal.curPage>-1){
			Intent myIntent;
			myIntent = new Intent(MainActivity.this,
				TabActivity.class);

			startActivityForResult(myIntent,1);
			overridePendingTransition (R.anim.fadeout, R.anim.fadein);
		}
	}
    
    
}