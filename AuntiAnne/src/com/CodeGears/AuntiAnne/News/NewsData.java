package com.CodeGears.AuntiAnne.News;

import android.graphics.Bitmap;

public class NewsData {
	private String newsID;
	private String newsTitle;
	private String newsThumb;
	private Bitmap thumb;
	private boolean isSetBitmapThumb;
	
	public NewsData(){
		isSetBitmapThumb=false;
	}
	
	public void setNewsID(String newsID){
		this.newsID=newsID;
	}
	
	public void setNewsTitle(String newsTitle){
		this.newsTitle=newsTitle;
	}
	
	public void setNewsThumb(String newsThumb){
		this.newsThumb=newsThumb;
	}
	
	public String getNewsID(){
		return newsID;
	}
	
	public String getNewsTitle(){
		return newsTitle;
	}
	
	public String getNewsThumb(){
		return newsThumb;
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
