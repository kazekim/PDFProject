package com.CodeGears.AuntiAnne;

import com.CodeGears.AuntiAnne.appdata.InitVal;

import android.app.Application;

public class MainApp  extends Application {
	//public static ArrayList<NewsCategoryData> newsCategoryList;
	
	public static final String STATS_URL="http://www.pretzelclub.com/codegears/stat.php";
	public static final String NEWSLIST_URL ="http://www.pretzelclub.com/codegears/news_list.php";
	public static final String NEWSDETAIL_URL= "http://www.pretzelclub.com/codegears/news.php?news_id=";
	public static final String COUPON_URL= "http://www.pretzelclub.com/codegears/coupon.php";
	//public static final String COUPON_URL= "http://www.codegears.co.th/auntieanne/coupon.php";
	public static final String ECARD_URL= "http://www.pretzelclub.com/codegears/card.xml";
	public static final String GENERATECARD_URL = "http://www.pretzelclub.com/codegears/generate.php";
	public static final String LOGIN_URL= "http://www.pretzelclub.com/codegears/login.php";
	public static final String LOCATION_URL= "http://www.pretzelclub.com/codegears/location.php";
//	public static final String LOCATION_URL= "http://www.pretzelclub.com/codegears/location.php";
	//public static final String RESTAURANTMENU_URL="http://www.codegears.co.th/auntieanne/menu.php";
	public static final String RESTAURANTMENU_URL="http://www.pretzelclub.com/codegears/menu.php";
	public static final String REGISTER_URL="http://www.crg.co.th/mobile/";
	public static final String BIRTHDAYUSE_URL="http://www.pretzelclub.com/codegears/birthday_use.php";

	//public static int selectedGarageID=0;
	public static boolean isPlayIntro=true;
	
	@Override
	public void onCreate(){
		super.onCreate();
		
		InitVal.init(this);
	}
}
