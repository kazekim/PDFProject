package com.CodeGears.AuntiAnne.restaurantmenu;

import android.graphics.Bitmap;

public class FoodData {

	private String name;
	private String thumbURL;
	private String description;
	private Bitmap thumb;
	private boolean isSetBitmapThumb;
	
	public FoodData(){
		isSetBitmapThumb=false;
	}
	
	public void setName(String name){ 
		this.name=name;
	}
	
	public void setthumbURL(String thumbURL){
		this.thumbURL=thumbURL;
	}
	
	public void setDescription(String description){
		this.description=description;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getThumbURL(){
		return thumbURL;
	}
	
	public void setBitmapThumb(Bitmap bm){
		this.thumb=bm;
		isSetBitmapThumb=true;
	}
	
	public Bitmap getBitmapThumb(){
		return thumb;
	}
	
	public boolean isSetBitmapThumb(){
		return isSetBitmapThumb;
	}
	
	public void recycle(){
		if(thumb!=null){
			thumb.recycle();
		}
	}
}
