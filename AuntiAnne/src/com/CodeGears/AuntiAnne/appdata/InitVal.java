package com.CodeGears.AuntiAnne.appdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.eCard.CardData;
import com.CodeGears.AuntiAnne.eCard.StickerData;
import com.CodeGears.AuntiAnne.eCoupon.EcouponData;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class InitVal {
	
	public static int screenW;
	public static int screenH;
	public static int screenType;
	public static final int MDPI=0;
	public static final int HDPI=1;
	public static final int XLARGEMDPI=2;
	
	public static Typeface Circular;
	public static Typeface CircularBold;
	public static Typeface Font;
	public static Typeface FontBold;
	
	private static Context context;
	
	public static int curPage=0;
	public static int newsListPage=0; 
	public static String detailPageID="";
	
	public static final int NEWS=0;
	public static final int MEMBER=1;
	public static final int ECOUPON=2;
	public static final int ECARD=3;
	public static final int LOCATION=4;
	public static final int RESTAURANTMENU=5;
	public static final int CONTACT=6;
	public static final int NEWSDETAIL=7;
	public static final int COREMENU=8;
	public static final int CATEGORYMENU=9;
	public static final int MEMBERBIRTHDAY=10;
	public static final int MEMBERACTIVITY=11;
	public static final int MEMBERECOUPON=12;
	
	public static final int NEWSMEMBERACTIVITY=4;
	
	public static final int FAIL=0;
	public static final int SUCCESS=1;
	public static final int CONNECTIONERROR=2;
	public static final int DATAERROR=3;
	public static final int LOADFROMPREFERENCE=4;
	public static final int NODATA=5;
	
	public static final int NORMALCOUPON=1;
	public static final int MEMBERCOUPON=2;
	
	public static final int BLUECOLOR=0xFF0060A6;
	public static final int YELLOWCOLOR=0xFFF5B721;
	public static final int WHITECOLOR=0xFFFFFFFF;
	public static final int REDCOLOR=0xFFFF0000;
	
	public static int fontSizeMainMenu;
	public static int fontSizeNewsGridView;
	public static int fontSizeNewsCell;
	public static int fontSizeHead;
	public static int fontSizeContact;
	public static int fontSizeContact2;
	public static int fontSizeCoreMenuTitle;
	public static int fontSizeCoreMenuName;
	public static int fontSizeCoreMenuDescript;
	public static int fontSizeConfirmCouponDialog;
	public static int fontSizeCouponDialog;
	public static int fontSizeCouponDialogCode;
	public static int fontSizeCouponGridViewDescript;
	public static int fontSizeCouponGridViewNotice;
	public static int fontSizeBalloon;
	public static int fontSizeMember;
	public static int fontSizeMemberDetail;
	
	public static final String FACEBOOK_APPID="276575842416175";
	
	public static final String COUPONDATA="COUPONDATA";
	public static final String USERPREFERENCE="USERPREFERENCE";
	
	public static String CREATETABLESTRING;

	public static final String[] CATEGORYKEY={};
	
	public static AppSharedPreference appSettings;
	public static UserData userData;

	public static String todayDate;
	
	public static ArrayList<StickerData> stickerDataList;
	public static ArrayList<CardData> cardDataList;
	
	public static int cardIndex=0;
	public static String cardURL;
	
	public static boolean canOpenMember=true;
	
	public static DisplayMetrics metrics;
	
	public InitVal(Context context) {
		init(context);
	}
	
	public static void init(Context _context) {
		
		context=_context;
		
		//Set Display
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
						.getDefaultDisplay();
		
		 metrics = new DisplayMetrics();
	     display.getMetrics(metrics);
		
		//Set Screen Size
		screenW = display.getWidth();
		screenH = display.getHeight();
		
		if(screenW<480){
			screenType=MDPI;
		}else if(screenW<720){
			screenType=HDPI;
		}else{
			screenType=XLARGEMDPI;
		}
		
		Calendar c = Calendar.getInstance();
		todayDate = c.get(Calendar.YEAR) + "-" 
		+ (c.get(Calendar.MONTH)+1)
		+ "-" + c.get(Calendar.DAY_OF_MONTH);
		
		appSettings = new AppSharedPreference(context);

		
		
		userData = new UserData();
		
		setFont();

	}
	
	
	private static void setFont(){
		
		Circular= Typeface.createFromAsset(context.getAssets(), "font/Circular.ttf");
		CircularBold= Typeface.createFromAsset(context.getAssets(), "font/CircularBold.ttf");
		
		Font=Circular;
		FontBold=CircularBold;
		
		double multiplyValue=1;
		
		if(screenType==MDPI){
			multiplyValue=0.9;
		}else if(screenType==XLARGEMDPI){
			multiplyValue=2;
		}
		
		fontSizeHead = (int) (20*multiplyValue);
		fontSizeMainMenu = (int) (25*multiplyValue);
		fontSizeNewsCell = (int) (20*multiplyValue);
		fontSizeNewsGridView = (int) (20*multiplyValue);
		fontSizeContact = (int) (18*multiplyValue);
		fontSizeContact2 = (int) (14*multiplyValue);
		fontSizeCoreMenuTitle = (int) (20*multiplyValue);
		fontSizeCoreMenuName = (int) (20*multiplyValue);
		fontSizeCoreMenuDescript = (int) (16*multiplyValue);
		fontSizeConfirmCouponDialog = (int) (25*multiplyValue);
		fontSizeCouponDialog = (int) (25*multiplyValue);
		fontSizeCouponDialogCode = (int) (20*multiplyValue);
		fontSizeCouponGridViewDescript =(int) (18*multiplyValue);
		fontSizeCouponGridViewNotice = (int) (15*multiplyValue);
		fontSizeBalloon = (int) (25*multiplyValue);
		fontSizeMember = (int) (20*multiplyValue);
		fontSizeMemberDetail = (int) (15*multiplyValue);
	}
	
}
