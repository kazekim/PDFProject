package com.CodeGears.AuntiAnne.member;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.News.NewsCell;
import com.CodeGears.AuntiAnne.appdata.CategoryData;
import com.CodeGears.AuntiAnne.appdata.InitVal;

public class MemberDetailView  extends LinearLayout{

	private TextView memberNameTitleText;
	private TextView memberNameText;
	private TextView checkQuotaText;
	private TextView checkQuotaDetailText;
	private TextView checkQuotaDetailText2;
	private TextView checkQuotaDetailText3;
	private TextView checkQuotaDetailText4;
	private TextView checkQuotaDetailText5;
	private TextView checkPointText;
	private TextView checkPointDetailText;
	
	private ListView memberListView;
	
	private Context context;
	
	private ArrayList<MemberMenuData> memberMenuList;
	
	public MemberDetailView(Context context) {
		super(context);
		createView();
		
		this.context = context;
		
		memberNameTitleText = (TextView) findViewById(R.id.membernametitle);
		memberNameText = (TextView) findViewById(R.id.membernamesilverbox);
		checkQuotaText = (TextView) findViewById(R.id.checkquota);
		checkQuotaDetailText = (TextView) findViewById(R.id.checkquotadetail);
		checkQuotaDetailText2 = (TextView) findViewById(R.id.checkquotadetail2);
		checkQuotaDetailText3 = (TextView) findViewById(R.id.checkquotadetail3);
		checkQuotaDetailText4 = (TextView) findViewById(R.id.checkquotadetail4);
		checkQuotaDetailText5 = (TextView) findViewById(R.id.checkquotadetail5);
		checkPointText = (TextView) findViewById(R.id.checkpoint);
		checkPointDetailText = (TextView) findViewById(R.id.checkpointdetail);
		memberListView = (ListView) findViewById(R.id.memberlistview);
		
		memberNameTitleText.setTypeface(InitVal.FontBold);
		memberNameTitleText.setTextSize(InitVal.fontSizeMember);
		memberNameTitleText.setTextColor(InitVal.BLUECOLOR);
		memberNameText.setTypeface(InitVal.FontBold);
		memberNameText.setTextSize(InitVal.fontSizeMember);
		memberNameText.setTextColor(InitVal.BLUECOLOR);
		checkQuotaText.setTypeface(InitVal.FontBold);
		checkQuotaText.setTextSize(InitVal.fontSizeMemberDetail);
		checkQuotaText.setTextColor(InitVal.BLUECOLOR);
		checkQuotaDetailText.setTypeface(InitVal.FontBold);
		checkQuotaDetailText.setTextSize(InitVal.fontSizeMemberDetail-2);
		checkQuotaDetailText.setTextColor(InitVal.REDCOLOR);
		checkQuotaDetailText2.setTypeface(InitVal.FontBold);
		checkQuotaDetailText2.setTextSize(InitVal.fontSizeMemberDetail-2);
		checkQuotaDetailText2.setTextColor(InitVal.BLUECOLOR);
		checkQuotaDetailText3.setTypeface(InitVal.FontBold);
		checkQuotaDetailText3.setTextSize(InitVal.fontSizeMemberDetail-2);
		checkQuotaDetailText3.setTextColor(InitVal.REDCOLOR);
		checkQuotaDetailText4.setTypeface(InitVal.FontBold);
		checkQuotaDetailText4.setTextSize(InitVal.fontSizeMemberDetail-2);
		checkQuotaDetailText4.setTextColor(InitVal.BLUECOLOR);
		checkQuotaDetailText5.setTypeface(InitVal.FontBold);
		checkQuotaDetailText5.setTextSize(InitVal.fontSizeMemberDetail-2);
		checkQuotaDetailText5.setTextColor(InitVal.REDCOLOR);
		checkPointText.setTypeface(InitVal.FontBold);
		checkPointText.setTextSize(InitVal.fontSizeMemberDetail);
		checkPointText.setTextColor(InitVal.BLUECOLOR);
		checkPointDetailText.setTypeface(InitVal.FontBold);
		checkPointDetailText.setTextSize(InitVal.fontSizeMemberDetail-2);
		checkPointDetailText.setTextColor(InitVal.REDCOLOR);
		
		
		
		memberNameText.setText(InitVal.userData.getName()+" "+InitVal.userData.getSurname());
		checkQuotaDetailText.setText(getResources().getText(R.string.checkquotadetail1));

		checkQuotaDetailText2.setText(""+InitVal.userData.getQuota());
		checkQuotaDetailText5.setText(getResources().getText(R.string.checkquotadetail3));
		checkQuotaDetailText3.setText(getResources().getText(R.string.checkquotadetail2));
		checkQuotaDetailText4.setText(InitVal.todayDate);
		checkPointDetailText.setText(InitVal.userData.getPoint()+" "+getResources().getText(R.string.checkpointdetail));
		
		getMemberMenu();

		memberListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		memberListView.setAdapter(new MemberCell(memberMenuList,context));
	}
	
	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.memberdetail, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		memberListView.setOnItemClickListener(listener);
	}
	
	public int getIndexMemberMenu(int index){
		return memberMenuList.get(index).getIndex();
	}
	
	public void recycleBitmap(){
		for(int i=0;i<memberMenuList.size();i++){
			memberMenuList.get(i).recycle();
		}	
	}
	
	private void getMemberMenu(){
		
		memberMenuList = new ArrayList<MemberMenuData>();
		try{
		 Resources res = context.getResources();
		   XmlResourceParser xpp = res.getXml(R.xml.membermenu);
		   xpp.next();
		   int eventType = xpp.getEventType();
		   
		   
		   
		   while (eventType != XmlPullParser.END_DOCUMENT)
		   {
		    if(eventType == XmlPullParser.START_TAG)
		    {

		    	if(xpp.getName().equals("menu")){
		    		MemberMenuData memberMenuData = new MemberMenuData();
		    		memberMenuList.add(memberMenuData);
		    		memberMenuData.setIndex(xpp.getAttributeIntValue(null, "index",0));
		    		memberMenuData.setName(xpp.getAttributeValue(null, "name"));
		    		memberMenuData.setMemberType(xpp.getAttributeValue(null, "type"));
		    		memberMenuData.setThumbURL(xpp.getAttributeValue(null, "thumburl"));
		    	}
		    }
		    eventType = xpp.next();
		   }
		   
		   
		   
		  	} catch (XmlPullParserException e) {
		  		// TODO Auto-generated catch block
		  		//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		   
	}
}
