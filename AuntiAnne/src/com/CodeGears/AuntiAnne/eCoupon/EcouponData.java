package com.CodeGears.AuntiAnne.eCoupon;

import java.util.ArrayList;
import java.util.List;

import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.function.DatabaseQueryData;

import android.graphics.Bitmap;

public class EcouponData {
	private String couponid;
	private String pictureURL;
	private String couponDescript;
	private String couponNotice;
	private Bitmap picture;
	private boolean isUsed;
	private boolean isSetPicture; 
	
	public static final String COUPONDBTABLENAME = "auntianne_coupon";
//	public static final String MEMBERCOUPONDBTABLENAME = "auntianne_membercoupon";
	public static final String[] column={"columnid","couponid","pictureURL","couponDescript","couponNotice","isUsed","couponType"};
	public static final String CREATETABLEQUERY = "CREATE TABLE " + COUPONDBTABLENAME + " ("
	+column[0]+" INTEGER PRIMARY KEY AUTOINCREMENT, "+column[1]+" TEXT, "+column[2]+" TEXT"+", "
	+column[3]+" TEXT, "+column[4]+" TEXT, "+column[5]+" TEXT, "+column[6]+" TEXT);";
	public static final String INSERTQUERY = "insert into "+COUPONDBTABLENAME+" VALUES (null,?,?,?,?,?,?);";
	/*public static final String MEMBERCREATETABLEQUERY = "CREATE TABLE " + MEMBERCOUPONDBTABLENAME + "("
	+column[0]+" INTEGER PRIMARY KEY AUTOINCREMENT, "+column[1]+" TEXT, "+column[2]+" TEXT"+", "
	+column[3]+" TEXT, "+column[4]+" TEXT, "+column[5]+" TEXT);";
	public static final String MEMBERINSERTQUERY = "insert into "+MEMBERCOUPONDBTABLENAME+" VALUES (null,?,?,?,?,?);";
	*/

	public EcouponData(){
		isSetPicture=false;
	}
	
	public void setCouponID(String couponid){
		this.couponid=couponid;
	}
	
	public void setCouponDescript(String couponDescript){
		this.couponDescript=couponDescript;
	}
	
	public void setPictureURL(String pictureURL){
		this.pictureURL=pictureURL;
	}
	
	public void setCouponNotice(String couponNotice){
		this.couponNotice=couponNotice;
	}
	
	public void setIsUsed(boolean isUsed){
		this.isUsed=isUsed;
	}
	
	public void setIsUsed(String isUsedString){
		if(isUsedString!=null && isUsedString.equals("1")){
			this.isUsed=true;
		}else{
			this.isUsed=false;
		}
	}
	
	public String getCouponID(){
		return couponid;
	}
	
	public String getPictureURL(){
		return pictureURL;
	}
	
	public String getCouponDescript(){
		return couponDescript;
	}
	
	public String getCouponNotice(){
		return couponNotice;
	}
	
	public void setBitmapThumb(Bitmap bm){
		this.picture=bm;
		isSetPicture=true;
	}
	
	public Bitmap getBitmapThumb(){
		return picture;
	}
	
	public boolean isSetPicture(){
		return isSetPicture;
	}
	
	public boolean isUsed(){
		return isUsed;
	}
	
	
	public static ArrayList<EcouponData> createEcouponList(List<DatabaseQueryData> queryDataList){
		ArrayList<EcouponData> ecouponDataList = new ArrayList<EcouponData>();
		
		for(int i=0;i<queryDataList.size();i++){
			EcouponData couponData = new EcouponData();
			ArrayList<String> queryData = queryDataList.get(i).getData();
			couponData.setCouponID(queryData.get(1));
			couponData.setPictureURL(queryData.get(2));
			couponData.setCouponDescript(queryData.get(3));
			couponData.setCouponNotice(queryData.get(4));
			couponData.setIsUsed(queryData.get(5));
			
			ecouponDataList.add(couponData);
		}
		
		return ecouponDataList;
	}
	
	public void recycle(){
		if(picture!=null){
			picture.recycle();
		}
	}
	
}
