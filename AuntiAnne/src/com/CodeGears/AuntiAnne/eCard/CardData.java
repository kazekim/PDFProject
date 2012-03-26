package com.CodeGears.AuntiAnne.eCard;

import android.graphics.Bitmap;
import android.graphics.Paint.Align;

public class CardData {
	private String name;
	private String pictureURL;
	private String color1;
	private String color2;
	private String font;
	private String fontColor;
	private int fontAlign;
	
	public static final int CENTER=0;
	public static final int RIGHT=1;
	public static final int LEFT=2;
	
	private double fontPositionX;
	private double fontPositionY;
	private int fontSize;
	private Bitmap picture;
	private boolean isSetBitmapPicture;
	
	public CardData(){
		isSetBitmapPicture=false;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPictureURL(String pictureURL){
		this.pictureURL = pictureURL;
	}
	
	public void setColor1(String color1){
		this.color1=color1;
	}
	
	public void setColor2(String color2){
		this.color2=color2;
	}
	
	public void setFont(String font){
		this.font=font;
	}
	
	public void setFontColor(String fontColor){
		this.fontColor=fontColor;
	}
	
	public void setFontAlign(String fontAlignText){
		if(fontAlignText.equals("center")){
			fontAlign=CENTER;
		}else if(fontAlignText.equals("right")){
			fontAlign=RIGHT;
		}else{
			fontAlign=LEFT;
		}
	}
	
	public void setFontPositionX(double fontPositionX){
		System.out.println("dasfasf "+fontPositionX);
		this.fontPositionX = fontPositionX;
	}
	
	public void setFontPositionY(double fontPositionY){
		this.fontPositionY = fontPositionY;
	}
	
	public void setFontSize(int fontSize){
		this.fontSize = fontSize;
	}
	
	public void setBitmapPicture(Bitmap bm){
		this.picture=bm;
		isSetBitmapPicture=true;
	}
	
	public String getName(){
		return name;
	}
	
	public String getPictureURL(){
		return pictureURL;
	}
	
	public String getColor1(){
		return color1;
	}
	
	public String getColor2(){
		return color2;
	}
	
	public String getFont(){
		return font;
	}
	
	public String getFontColor(){
		return fontColor;
	}

	public int getFontAlign(){
		return fontAlign;
	}
	
	public double getFontPositionX(){
		return fontPositionX;
	}
	
	public double getFontPositionY(){
		return fontPositionY;
	}
	
	public int getFontSize(){
		return fontSize;
	}
	
	public Bitmap getBitmapPicture(){
		return picture;
	}
	
	public boolean isSetBitmapPicture(){
		return isSetBitmapPicture;
	}
	
	public void recycle(){
		if(picture!=null){
			picture.recycle();
		}
	}
	
}
