package com.kazekim.ModPageView;

import java.util.LinkedList;

import com.kazekim.bookmark.BookmarkListListener;
import com.kazekim.brush.Brush;
import com.kazekim.brush.PenBrush;
import com.kazekim.drawing.DrawingPath;
import com.kazekim.drawing.DrawingSurface;
import com.kazekim.function.InitVal;

import th.co.tnt.FlipbookSoft.BookmarkEntry;
import th.co.tnt.FlipbookSoft.OpenFileActivity;
import th.co.tnt.FlipbookSoft.Options;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import cx.hell.android.lib.pagesview.N2EpdController;
import cx.hell.android.lib.pagesview.PagesView;
import cx.hell.android.lib.pagesview.Tile;

public class ModPageView extends PagesView{

	private OpenFileActivity activity;
	
	
	
	private static final int HORIZONTAL=0;
	private static final int VERTICAL=1;
	
	private int x=0;
	private int y=0;
	private int diffX ;
	private int diffY;
	private float ratioZoom=1;
	private float diffDistance=0;
	
	private boolean isNewPage=false;
	private boolean isOnDraw=false;
	
	private boolean isEnableDraw=true;
	private boolean isFirstPage=true;
	//private DrawPageView drawPageView;
	
	private boolean isRedraw=false;
	
	private PageViewListener listener;
	
	private int widthZoom;
	private int heightZoom;
	
	private Bitmap mBitmap;
	 
	public ModPageView(OpenFileActivity activity) {
		super(activity);
		
		 this.activity = activity;
		 
		 
	        
	}
	
	public void setPageViewListener(PageViewListener listener){
		 this.listener=listener;
	 }
	 
	
	public void goNextPage(){
			if(currentPage<pageSizes.length-1){
				currentPage++;
				isNewPage=true;
				listener.onChangePage();
			}
	}
	
	public void goPreviousPage(){
			if(currentPage>0){
				currentPage--;
				isNewPage=true;
				listener.onChangePage();
			}
	}
	
	
	public void jumpToPage(int page){
			this.currentPage=page;
			isNewPage=true;
			postInvalidate();
	}
	

	public void setPageInitialPosition(){
		int pageWidth = this.getCurrentPageWidth(currentPage);
		int pageHeight = (int) this.getCurrentPageHeight(currentPage);
		activity.resetMatrix(pageWidth, pageHeight);
	}
	/*
	public void createDrawBitmap(){

		int pageWidth = this.getCurrentPageWidth(currentPage);
		int pageHeight = (int) this.getCurrentPageHeight(currentPage);
		
		createDrawBitmap(pageWidth,pageHeight);
	}
	
	public void createDrawBitmap(int pageWidth,int pageHeight){
		drawPageView = new DrawPageView(pageWidth, pageHeight);
		
	}
	*/
	private void drawPages(Canvas canvas,int page) {
		
		Rect src = new Rect(); /* TODO: move out of drawPages */
		Rect dst = new Rect(); /* TODO: move out of drawPages */
		int pageWidth = 0;
		int pageHeight = 0;
		float pagex0, pagey0, pagex1, pagey1; // in doc, counts zoom
		int viewx0, viewy0; // view over doc
		LinkedList<Tile> visibleTiles = new LinkedList<Tile>();
		float currentMarginX = this.getCurrentMarginX();
		float currentMarginY = this.getCurrentMarginY();
		float renderAhead = this.pagesProvider.getRenderAhead();

		canvas.save();
		canvas.translate(diffX, diffY);
		canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        canvas.scale(ratioZoom, ratioZoom);
        canvas.translate(-canvas.getWidth()/2, -canvas.getHeight()/2);
		
		if (this.pagesProvider != null) {
                        if (this.zoomLevel < 5)
                            this.zoomLevel = 5;

			viewx0 = left - width/2;
			viewy0 = top - height/2;

			int pageCount = this.pageSizes.length;

			/* We now adjust the position to make sure we don't scroll too
			 * far away from the document text.
			 */

			viewx0 = adjustPosition(viewx0, width, (int)currentMarginX,
					getCurrentMaxPageWidth());
			viewy0 = adjustPosition(viewy0, height, (int)currentMarginY,
					(int)getCurrentDocumentHeight());


			pagey0 = 0;
			int[] tileSizes = new int[2];

		//	for(int i = 0; i < pageCount; ++i) {
				// is page i visible?
			
			

				pageWidth = this.getCurrentPageWidth(page);
				pageHeight = (int) this.getCurrentPageHeight(page);
				
				pagex0 = currentMarginX;
				pagex1 = (int)(currentMarginX + pageWidth);
				pagey0 = 0;
				pagey1 = (int)(pageHeight);

					getGoodTileSizes(tileSizes, pageWidth, pageHeight);
					//System.out.println("Size "+pageWidth+" "+pageHeight);
					for(int tileix = 0; tileix < (pageWidth + tileSizes[0]-1) / tileSizes[0]; ++tileix)
						for(int tileiy = 0; tileiy < (pageHeight + tileSizes[1]-1) / tileSizes[1]; ++tileiy) {

							dst.left = (int)(x + tileix*tileSizes[0]);
							dst.top = (int)(y + tileiy*tileSizes[1]);
							dst.right = dst.left + tileSizes[0];
							dst.bottom = dst.top + tileSizes[1];	
						
							if (dst.intersects(0, 0, this.width, (int)(renderAhead*this.height))) {
//if(i==currentPage){
								
								Tile tile = new Tile(page, (int)(this.zoomLevel * scaling0), 
										tileix*tileSizes[0], tileiy*tileSizes[1], this.rotation,
										tileSizes[0], tileSizes[1]);
								if (dst.intersects(0, 0, this.width, this.height)) {
									Bitmap b = this.pagesProvider.getPageBitmap(tile);
									if (b != null) {
										//Log.d(TAG, "  have bitmap: " + b + ", size: " + b.getWidth() + " x " + b.getHeight());
										src.left = 0;
										src.top = 0;
										src.right = b.getWidth();
										src.bottom = b.getHeight();
										
										if (dst.right > x + pageWidth) {
											src.right = (int)(b.getWidth() * (float)((x+pageWidth)-dst.left) / (float)(dst.right - dst.left));
											dst.right = (int)(x + pageWidth);
										}
										
										if (dst.bottom > y + pageHeight) {
											src.bottom = (int)(b.getHeight() * (float)((y+pageHeight)-dst.top) / (float)(dst.bottom - dst.top));
											dst.bottom = (int)(y + pageHeight);
										}
										
											drawBitmap(canvas, b, src, dst);
											//if(isEnableDraw){
											//	drawDrawBitmap(canvas,tileix*tileSizes[0], tileiy*tileSizes[1],tileSizes[0],tileSizes[1],pageWidth,pageHeight,src,dst);
											//}
									}
							}
								visibleTiles.add(tile);
							}
						}
				}
				
			
			this.pagesProvider.setVisibleTiles(visibleTiles);
		//}
			canvas.restore();
	}
	
	private void drawBitmap(Canvas canvas, Bitmap b, Rect src, Rect dst) {
		if (colorMode != Options.COLOR_MODE_NORMAL) {
			Paint paint = new Paint();
			Bitmap out;
			
			if (b.getConfig() == Bitmap.Config.ALPHA_8) {
				out = b.copy(Bitmap.Config.ARGB_8888, false);
			}
			else {
				out = b;
			}
			
			paint.setColorFilter(new 
					ColorMatrixColorFilter(new ColorMatrix(
							Options.getColorModeMatrix(this.colorMode))));

			canvas.drawBitmap(out, src, dst, paint);
			
			if (b.getConfig() == Bitmap.Config.ALPHA_8) {
				out.recycle();
			}
		}
		else {
			canvas.drawBitmap(b, src, dst, null);

		}
		if(isNewPage){
			//createDrawBitmap();
			
			listener.onCheckBookMarkOnNewPage(this, currentPage);
			
		/*	if(OpenFileActivity.prevOrientation==ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT){
				zoomWidth();

		    }else{*/
		    	zoomFit();
		 //   }
			
			newSurface();
			isNewPage=false;
		}
	}
	
	public void newSurface(){
		int pageWidth = this.getCurrentPageWidth(currentPage);
		int pageHeight = (int)this.getCurrentPageHeight(currentPage);
		
		widthZoom = pageWidth;
		heightZoom =pageHeight;
		
		DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

			x=metrics.widthPixels/2-pageWidth/2;
			y=metrics.heightPixels/2-pageHeight/2;
        
			if(isFirstPage){
				listener.onGenerateFirstPageFinish(this,x,y, pageWidth, pageHeight);
				isFirstPage=false;
			}
	}
	/*
	public void drawPoint(int pointX,int pointY){
		int pageWidth = this.getCurrentPageWidth(currentPage);
		int pageHeight = (int) this.getCurrentPageHeight(currentPage);
		if(pointX<x+pageWidth && pointX>=x && pointY<y+pageHeight && pointY>=y){
			//System.out.println(pointX+ " "+pointY);
			drawPageView.addPoint(pointX, pointY, pageWidth, pageHeight);
			
			this.invalidate();
		}
	}
	
	public void  setCommitDraw(){
		drawPageView.setCommit();
	}
	
	public void drawDrawBitmap(Canvas canvas,int startX,int startY,int tileWidth,int tileHeight,int pageWidth,int pageHeight, Rect src, Rect dst){
		Bitmap b = drawPageView.getPartBitmap(startX, startY,tileWidth,tileHeight, pageWidth, pageHeight);
		canvas.drawBitmap(b, src, dst, null);
	}
	*/
	
	
	public void setXY(int diffx,int diffy){
	
	this.diffX += diffx;
	this.diffY += diffy ;
		//this.x+=diffx;
		//this.y+=diffy;
	listener.doPageMove(diffx, diffy);
		this.invalidate();	
	}
	
	public void zoomWithPinchDistance(int distance){

		diffDistance+=distance;

		this.ratioZoom=(float)(widthZoom+distance)/(float)widthZoom;
		listener.doPageZoom(ratioZoom);
		invalidate();
	}

	
	
	public void fitPage(){
		int pageWidth = this.getCurrentPageWidth(currentPage);
		int pageHeight = (int)this.getCurrentPageHeight(currentPage);
		
		DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

	/*		x=metrics.widthPixels/2-pageWidth/2;
			y=metrics.heightPixels/2-pageHeight/2;
*/

	}
	
	 public Boolean checkIsOnPDF(float _x,float _y){
	    	
	    	
			Boolean isOnPdf=true;
			
			int posX =  getOriginX(_x);
			int posY = getOriginY(_y);

			if(posX>=DrawingSurface.drawAreaRect.left && posX<= DrawingSurface.drawAreaRect.right 
					&& posY>= DrawingSurface.drawAreaRect.top && posY<= DrawingSurface.drawAreaRect.bottom){
				return true;
			}else{
				return false;
			}

	    	
	    }
	    
	
	public int getOriginX(float _x){
		DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
    	int posX = (int)((float)metrics.widthPixels/2-((float)metrics.widthPixels/2-_x)/ratioZoom-diffX);
    	
		return posX;
	}
	
	public int getOriginY(float _y){
		DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
    	int posY = (int)((float)metrics.heightPixels/2-((float)metrics.heightPixels/2-_y)/ratioZoom-diffY);

		return posY;
	}
	
	@Override
	public void zoomWidth(){
		super.zoomWidth();

	}
	
	@Override
	public void zoomFit(){
		super.zoomFit();

	}
	
	public int getFullPageWidth(){
		return this.getCurrentPageWidth(currentPage);
	}
	
	public int getFullPageHeight(){
		return (int)this.getCurrentPageHeight(currentPage);
	}
	
	public int getStartX(){
		return x;
	}
	
	public int getStartY(){
		return y;
	}
	
	public void setLoadNewPage(){
		isNewPage=true;
	}
	
	public boolean isNewPage(){
		return isNewPage;
	}

	
	protected int getCurrentDocumentHeight() {
		
		float realheight = this.realDocumentSize[this.rotation % 2 == 0 ? 1 : 0];
		/* we add pageSizes.length to account for round-off issues */
		return (int)(scale(realheight) +  
			(pageSizes.length - 1) * this.getCurrentMarginY());///pageSizes.length;
	}
	
	public void onDraw(Canvas canvas) {

		 
		
		canvas.drawColor(Color.parseColor("#bdcba7"));
		
		if (this.nook2) {
			N2EpdController.setGL16Mode();
		}
		

		this.drawPages(canvas,currentPage);
		
		if(isRedraw){
			System.out.println("dsadsad");
		}
		
		if (this.findMode) this.drawFindResults(canvas);
	}
	
	public void setRedraw(){
		isRedraw=true;
	}
	
/*	public boolean checkInArea(int x , int y){
		
		
		return 
	}*/
	
	public Bitmap combineImages(Bitmap c, Bitmap s,int combineType) { 
		// can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom 
	    Bitmap cs = null; 

	    int width, height = 0; 

	    if(c.getWidth() > s.getWidth()) { 
	      width = c.getWidth() + s.getWidth();
	      height = c.getHeight(); 
	    } else { 
	      width = s.getWidth() + s.getWidth(); 
	      height = c.getHeight(); 
	    } 

	    cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); 

	    Canvas comboImage = new Canvas(cs); 

	    comboImage.drawBitmap(c, 0f, 0f, null); 
	    comboImage.drawBitmap(s, c.getWidth(), 0f, null); 

	    // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location 
	    /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png"; 

	    OutputStream os = null; 
	    try { 
	      os = new FileOutputStream(loc + tmpImg); 
	      cs.compress(CompressFormat.PNG, 100, os); 
	    } catch(IOException e) { 
	      Log.e("combineImages", "problem combining images", e); 
	    }*/ 

	    return cs; 
	  } 
	
}
