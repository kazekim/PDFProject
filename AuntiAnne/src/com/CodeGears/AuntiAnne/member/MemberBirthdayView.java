package com.CodeGears.AuntiAnne.member;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

import com.CodeGears.AuntiAnne.MainApp;
import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.function.DialogFunction;

public class MemberBirthdayView  extends LinearLayout{
	
	private Button confirmButton;
	private Button cancelButton;
	private Button useBirthdayButton;
	
	private RelativeLayout confirmCouponBox;

	private TextView birthdayTitleText;
	private TextView birthdayTitleText2;
	private TextView birthdayNameText;
	private TextView birthdayPromotionText;
	private TextView birthdayPromotionDescriptText;
	
	private TextView confirmDialogText;

	//private Context context;
	private int useCouponStep=0;
	private int USERSTEP=0;
	private int SHOPSTEP=1;

	
	public MemberBirthdayView(Context context) {
		super(context);
		createView();
		
		//this.context = context;
		
		birthdayTitleText = (TextView) findViewById(R.id.birthdaytitletext);
		birthdayTitleText2 = (TextView) findViewById(R.id.birthdaytitletext2);
		birthdayNameText = (TextView) findViewById(R.id.birthdaynametext);
		confirmButton = (Button) findViewById(R.id.confirmbutton);
		cancelButton = (Button) findViewById(R.id.cancelbutton);
		useBirthdayButton = (Button) findViewById(R.id.usebirthdaybutton);
		confirmCouponBox = (RelativeLayout) findViewById(R.id.confirmdialog);
		confirmDialogText = (TextView) findViewById(R.id.confirmcoupontext);
		birthdayPromotionText = (TextView) findViewById(R.id.birthdaypromotion);
		birthdayPromotionDescriptText = (TextView) findViewById(R.id.birthdaypromotiondescript);
		
		birthdayTitleText.setTypeface(InitVal.FontBold);
		birthdayTitleText.setTextSize(InitVal.fontSizeMember);
		birthdayTitleText.setTextColor(InitVal.BLUECOLOR);
		
		birthdayTitleText2.setTypeface(InitVal.FontBold);
		birthdayTitleText2.setTextSize(InitVal.fontSizeMember);
		birthdayTitleText2.setTextColor(InitVal.BLUECOLOR);
		
		birthdayNameText.setTypeface(InitVal.FontBold);
		birthdayNameText.setTextSize(InitVal.fontSizeMember);
		birthdayNameText.setTextColor(InitVal.BLUECOLOR);
		
		birthdayPromotionText.setTypeface(InitVal.FontBold);
		birthdayPromotionText.setTextSize(InitVal.fontSizeMember);
		birthdayPromotionText.setTextColor(InitVal.BLUECOLOR);
		
		birthdayPromotionDescriptText.setTypeface(InitVal.FontBold);
		birthdayPromotionDescriptText.setTextSize(InitVal.fontSizeMemberDetail);
		birthdayPromotionDescriptText.setTextColor(InitVal.BLUECOLOR);
		
		confirmDialogText.setTypeface(InitVal.Font);
		confirmDialogText.setTextSize(InitVal.fontSizeConfirmCouponDialog);
		confirmDialogText.setTextColor(InitVal.BLUECOLOR);
		
		birthdayNameText.setText(InitVal.userData.getName());
		
		confirmCouponBox.setVisibility(View.GONE);
	}
	
	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.memberbirthday, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	public void setOnClickListener(OnClickListener listener){
		confirmButton.setOnClickListener(listener);
		cancelButton.setOnClickListener(listener);
		useBirthdayButton.setOnClickListener(listener);
	}
	
	public boolean confirmButtonIsClick(){
		return confirmButton.isPressed();
	}
	
	public boolean cancelButtonIsClick(){
		return cancelButton.isPressed();
	}
	
	public boolean useBirthdayButtonIsClick(){
		return useBirthdayButton.isPressed();
	}
	
	public void closeConfirmCouponBox(){
		confirmCouponBox.setVisibility(View.GONE);
	}
	
	public void useCoupon(){
		if(useCouponStep==USERSTEP){
			useCouponStep=SHOPSTEP;
			
			confirmDialogText.setText(getContext().getText(R.string.confirmusecouponshop));
		}else{
			confirmDialogText.setText(getContext().getText(R.string.confirmusecoupon));
			closeConfirmCouponBox();
			useBirthday();
		}
		
		
	}
	
	public void showConfirmBox(){
		useCouponStep=USERSTEP;
		confirmCouponBox.setVisibility(View.VISIBLE);
	}
	
	public void useBirthday(){
		try {
			URL url = new URL( MainApp.BIRTHDAYUSE_URL );
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput( true );
			connection.setUseCaches( false );
			String data = "phone="+InitVal.userData.getPhone();
				connection.setRequestMethod( "POST" );
				connection.setDoOutput( true );
				DataOutputStream wr = new DataOutputStream( connection.getOutputStream() );
				wr.writeBytes( data );
				wr.flush();
				wr.close();
			
			connection.connect();
			String response= "";

			Scanner inStream = new Scanner(connection.getInputStream());
			while(inStream.hasNextLine()){
				response+=(inStream.nextLine());

			}
			System.out.println(response);
			if(response.equals("success")){
				useBirthdayButton.setVisibility(View.GONE);
			}else{
				DialogFunction.showAlertDialog(getContext(), response);
			}
			connection.disconnect();

		} catch ( MalformedURLException e ) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
}
