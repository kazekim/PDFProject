package com.kazekim.ModPageView;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/*public class DrawPageView {
	
	int[] bitmapGrid;
	int[] layerGrid;
	int originalWidth;
	int originalHeight;
	
	
	public static final String CLEAR="00";
	public static final String ALPHA1="44";
	public static final String ALPHA2="99";
	public static final String OPAQUE="FF";
	public static final String BLACK="000000";
	public static final String RED="C2272D";
	public static final String BLUE="071BD";
	public static final String GREEN="8CC63E";
	public static final String PINK="FF7BAC";
	public static final String YELLOW="EBBF3A";
	public static final String WHITE="FFFFFF";
	
	public static final int PENPLUSSIZE=2;
	public static final int HIGHLIGHTPLUSSIZE=2;
	
	private String color=BLACK;
	private int penSize=3;
	private int highlightSize=3;
	
	private boolean isCommit=false;
	
	public DrawPageView(int width,int height){
		
		originalHeight=height;
		originalWidth=width;
		
		bitmapGrid = new int[width*height];
		layerGrid = new int[width*height];
		for(int i=0;i<bitmapGrid.length;i++){
		//	bitmapGrid[i]=Color.parseColor("#00000000");
		}
	}
	
	public Bitmap getFullBitmap(){
		Bitmap	tempBitmap = Bitmap.createBitmap(bitmapGrid, originalWidth, originalHeight, Bitmap.Config.ARGB_8888);	
  	  return tempBitmap;
	}
	
	public Bitmap getPartBitmap(int x,int y,int tileWidth,int tileHeight,int pageWidth,int pageHeight){
		int startX = (int)(((float)x/pageWidth)*originalWidth);
		int startY = (int)(((float)y/pageHeight)*originalHeight);
		int endX = (int)(((float)(x+tileWidth)/pageWidth)*originalWidth);
		int endY = (int)(((float)(y+tileHeight)/pageHeight)*originalHeight);
			
		int[] tempGrid = new int[(endX+1-startX)*(endY+1-startY)];
		int k=0;
		for(int i=startY;i<=endY;i++){
			for(int j=startX;j<=endX;j++){
				if(i*originalWidth+j<bitmapGrid.length)
					tempGrid[k]=bitmapGrid[i*originalWidth+j];
			}
		}
		Bitmap	tempBitmap = Bitmap.createBitmap(bitmapGrid, tileWidth, tileHeight, Bitmap.Config.ARGB_8888);	
	  	  return tempBitmap;
	}
	
	public void combineLayer(){
		for(int i=0;i<layerGrid.length;i++){
			//System.out.println("sdsdd "+bitmapGrid[i]+" "+layerGrid[i]);
			if(layerGrid[i]!=0){
				System.out.println(i+" sdsdd "+bitmapGrid[i]+" "+layerGrid[i]);
				bitmapGrid[i]=layerGrid[i];
			}
		}
		layerGrid = new int[originalHeight*originalWidth];
	}
	
	public void setCommit(){
		isCommit=true;
	}
	
	public void addPoint(int x,int y,int pageWidth,int pageHeight){
		if(isCommit){
			combineLayer();
		}
		int pointX = (int)((float)((float)x/(float)pageWidth)*originalWidth);
		int pointY = (int)((float)((float)y/(float)pageHeight)*originalHeight);
		int pos = pointY*originalWidth +pointX;
		
		System.out.println("asdas "+pointX+" "+pointY+" "+pos);
		for(int i=0;i<penSize+2;i++){
			for(int j=0;j<=penSize+2;j++){
				if(i+j<=penSize+2){
					int colorHex=0;
				if(i+j<=penSize){
						colorHex=Color.parseColor("#"+OPAQUE+color);
					}else if(i+j<=penSize+1){
						colorHex=Color.parseColor("#"+ALPHA2+color);
					}else if(i+j<=penSize+2){
						colorHex=Color.parseColor("#"+ALPHA1+color);
					}else{
						colorHex=Color.parseColor("#"+CLEAR+BLACK);
					}
					if(pos-j>=0){
					//	System.out.println("Dsafsadg "+(pos-j));
						layerGrid[pos-j]=colorHex;
					}
					if(pos+j<layerGrid.length){
						layerGrid[pos+j]=colorHex;
					}
					if(pos-i*originalWidth>=0){
						layerGrid[pos-i*originalWidth]=colorHex;
					}
					if(pos+i*originalWidth<layerGrid.length){
					//	System.out.println("Value "+layerGrid[pos+i*originalWidth]);
						layerGrid[pos+i*originalWidth]=colorHex;

					}
				}
			}
		}

	}
	
	public void setColor(String color){
		this.color=color;
	}
	
	public String getColor(){
		return color;
	}
	
	public void setPenSize(int size){
		this.penSize=size+PENPLUSSIZE;
	}
	
	public int getPenSize(){
		return penSize-PENPLUSSIZE;
	}
	
	public void setHighLightSize(int size){
		this.highlightSize=size+HIGHLIGHTPLUSSIZE;
	}
	
	public int getHighlightSize(){
		return highlightSize-HIGHLIGHTPLUSSIZE;
	}
	
}*/
