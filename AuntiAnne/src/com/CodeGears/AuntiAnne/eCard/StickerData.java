package com.CodeGears.AuntiAnne.eCard;

import com.CodeGears.AuntiAnne.function.AnimatedSprite;

import android.graphics.Bitmap;

public class StickerData {
	private String name;
	private String pictureURL;
	private Bitmap picture;
	private double posX;
	private double posY;
	private boolean isSetBitmapPicture;
	private AnimatedSprite sprite;
	
	public StickerData(){
		isSetBitmapPicture=false;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPictureURL(String pictureURL){
		this.pictureURL = pictureURL;
	}
	
	public void setBitmapPicture(Bitmap bm){
		this.picture=bm;
		isSetBitmapPicture=true;
	}
	
	public void setPosX(double posX){
		this.posX = posX;
	}
	
	public void setPosY(double posY){
		this.posY = posY;
	}
	
	public void setSprite(AnimatedSprite sprite){
		this.sprite = sprite;
	}
	
	public String getName(){
		return name;
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
	
	public double getPosX(){
		return posX;
	}
	
	public double getPosY(){
		return posY;
	}
	
	public AnimatedSprite getSprite(){
		return sprite;
	}
	
	public StickerData clone(){
		StickerData stickerData = new StickerData();
		stickerData.setName(name);
		stickerData.setPictureURL(pictureURL);
		stickerData.setBitmapPicture(picture);
		
		
		return stickerData; 
	}
	
	public void recycle(){
		if(picture!=null){
			picture.recycle();
		}
	}
}
