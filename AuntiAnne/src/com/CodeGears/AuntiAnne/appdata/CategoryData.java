package com.CodeGears.AuntiAnne.appdata;

import android.graphics.Bitmap;

public class CategoryData {
	private String categoryType;
	private String categoryTitle;
	private String categoryThumb;
	private int index;
	private Bitmap thumb;
	private boolean isSetBitmapThumb;
	
	public CategoryData(){
		isSetBitmapThumb=false;
	}
	
	public void setNewsType(String categoryType){
		this.categoryType=categoryType;
	}
	
	public void setNewsTitle(String categoryTitle){
		this.categoryTitle=categoryTitle;
	}
	
	public void setNewsThumb(String categoryThumb){
		this.categoryThumb=categoryThumb;
	}
	
	public void setIndex(int index){
		this.index=index;
	}

	public String getNewsType(){
		return categoryType;
	}
	
	public String getNewsTitle(){
		return categoryTitle;
	}
	
	public String getNewsThumb(){
		return categoryThumb;
	}
	
	public void setBitmapThumb(Bitmap bm){
		this.thumb=bm;
		isSetBitmapThumb=true;
	}
	
	public Bitmap getBitmapThumb(){
		return thumb;
	}
	
	public int getIndex(){
		return index;
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
