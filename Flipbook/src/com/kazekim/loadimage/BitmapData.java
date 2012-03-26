package com.kazekim.loadimage;

import android.graphics.Bitmap;

public class BitmapData {
	private String pictureURL;
	private Bitmap picture;
	private boolean isSetBitmapPicture;
	
	public BitmapData(){
		isSetBitmapPicture=false;
	}
	
	public void setPictureURL(String pictureURL){
		this.pictureURL = pictureURL;
	}
	
	public void setBitmapPicture(Bitmap bm){
		this.picture=bm;
		isSetBitmapPicture=true;
	}

	public String getPictureURL(){
		return pictureURL;
	}
	
	public Bitmap getBitmapPicture(){
		return picture;
	}
	
	public boolean isSetBitmapPicture(){
		return isSetBitmapPicture;
	}
}
