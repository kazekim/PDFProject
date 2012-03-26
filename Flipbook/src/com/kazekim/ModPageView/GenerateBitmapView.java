package com.kazekim.ModPageView;

import java.io.IOException;
import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import th.co.tnt.FlipbookSoft.GenerateBitmapActivity;
import th.co.tnt.FlipbookSoft.OpenFileActivity;
import th.co.tnt.FlipbookSoft.Options;
import cx.hell.android.lib.pagesview.N2EpdController;
import cx.hell.android.lib.pagesview.PagesView;
import cx.hell.android.lib.pagesview.Tile;

public class GenerateBitmapView  extends PagesView{

	public static int generateType=-1;
	public static final int NONE=-1;
	public static final int COVERPAGE=0;
	public static final int ALLPAGE=1;
	
	private int currentPage=0;
	
	private GenerateBitmapActivity activity;
	
	private boolean isLoadFinish=true;
	
	private int zoomForCover=250;
	
	public GenerateBitmapView(GenerateBitmapActivity activity) {
		super(activity);
		
		 this.activity = activity;
	}
	
	public void getThumbnail(Canvas canvas,int page){
		isLoadFinish=false;

		int pageWidth = 0;
		int pageHeight = 0;
		int viewx0, viewy0; // view over doc
		LinkedList<Tile> visibleTiles = new LinkedList<Tile>();
		float currentMarginX = this.getCurrentMarginX();
		float currentMarginY = this.getCurrentMarginY();
		
		if (this.pagesProvider != null) {
                        if (this.zoomLevel < 5)
                            this.zoomLevel = 5;

			viewx0 = left - width/2;
			viewy0 = top - height/2;

			//int pageCount = this.pageSizes.length;

			
			Rect src = new Rect(); 
			Rect dst = new Rect(); 
			int oldviewx0 = viewx0;
			int oldviewy0 = viewy0;
			float renderAhead = this.pagesProvider.getRenderAhead();

			viewx0 = adjustPosition(viewx0, width, (int)currentMarginX,
					getCurrentMaxPageWidth());
			viewy0 = adjustPosition(viewy0, height, (int)currentMarginY,
					(int)getCurrentDocumentHeight());

			left += viewx0 - oldviewx0;
			top += viewy0 - oldviewy0;
			
			int[] tileSizes = new int[2];

				pageWidth = this.getCurrentPageWidth(page);
				pageHeight = (int) this.getCurrentPageHeight(page);
				

			tileSizes = new int[]{pageWidth,pageHeight};
			
			Tile tile = new Tile(page, zoomForCover, 
						0*tileSizes[0], 0*tileSizes[1], this.rotation,
						tileSizes[0], tileSizes[1]);
						
			Bitmap b = this.pagesProvider.getPageBitmap(tile);
			
			
			visibleTiles.add(tile);
			
			this.pagesProvider.setVisibleTiles(visibleTiles);
			isLoadFinish=true;
			
			if (b != null) {
			/*	while(pageHeight>250){
					pageHeight/=2;
					pageWidth/=2;
				}
				int dstWidth = pageWidth;
				int dstHeight = pageHeight;


				
				// calc exact destination size
				Matrix m = new Matrix();
				RectF inRect = new RectF(0, 0, b.getWidth(), b.getHeight());
				RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
				m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
				float[] values = new float[9];
				m.getValues(values);

				// resize bitmap
				Bitmap resizedBitmap = Bitmap.createScaledBitmap(b, (int) (b.getWidth() * values[0]), (int) (b.getHeight() * values[4]), true);
				
				activity.saveBitmap(resizedBitmap);
				*/
				int newHeight=250;
				int newWidth= 250*b.getWidth()/b.getHeight();
				if(newWidth>200){
					newWidth=200;
					newHeight=200*b.getHeight()/ b.getWidth();
				}
				Matrix m = new Matrix();
				RectF inRect = new RectF(0, 0, b.getWidth(), b.getHeight());
				RectF outRect = new RectF(0, 0, newWidth, newHeight);
				m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
				float[] values = new float[9];
				m.getValues(values);

				Bitmap croppedBmp = Bitmap.createBitmap(b, 0, 0,(int) (b.getWidth() * values[0]),(int) (b.getHeight() * values[4]));
				src.right=croppedBmp.getWidth();//(int) (b.getWidth() * values[0]);
				src.bottom=croppedBmp.getHeight();//(int) (b.getHeight() * values[4]);
				dst.right=croppedBmp.getWidth();
				dst.bottom=croppedBmp.getHeight();
				//canvas.drawBitmap(croppedBmp, src, dst, null);
				activity.saveBitmap(croppedBmp);
			}

			
				
		
		
		}
	}
	
	/*	
	private void getThumbnail(Canvas canvas,int page) {
			
			Rect src = new Rect(); 
			Rect dst = new Rect(); 
			int pageWidth = 0;
			int pageHeight = 0;
			float pagex0, pagey0, pagex1, pagey1; // in doc, counts zoom
			int viewx0, viewy0; // view over doc
			LinkedList<Tile> visibleTiles = new LinkedList<Tile>();
			float currentMarginX = this.getCurrentMarginX();
			float currentMarginY = this.getCurrentMarginY();
			float renderAhead = this.pagesProvider.getRenderAhead();

			Bitmap  bitmap;
			

			if (this.pagesProvider != null) {
	                        if (this.zoomLevel < 5)
	                            this.zoomLevel = 5;

				viewx0 = left - width/2;
				viewy0 = top - height/2;

				int pageCount = this.pageSizes.length;


				viewx0 = adjustPosition(viewx0, width, (int)currentMarginX,
						getCurrentMaxPageWidth());
				viewy0 = adjustPosition(viewy0, height, (int)currentMarginY,
						(int)getCurrentDocumentHeight());


				pagey0 = 0;
				int[] tileSizes = new int[2];

			//	for(int i = 0; i < pageCount; ++i) {
					// is page i visible?
				
				int x=0;
				int y=0;

					pageWidth = this.getCurrentPageWidth(page);
					pageHeight = (int) this.getCurrentPageHeight(page);
						
					
					
					bitmap = Bitmap.createBitmap( pageWidth, pageHeight, Bitmap.Config.ARGB_8888);
					Canvas c = new Canvas(bitmap);
					c.drawColor(Color.RED);
					c.save();
				//	c.scale((float)(250/pageWidth), (float)(250/pageWidth));
					
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
											System.out.println("dsadasdds");
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
											
												c.drawBitmap(b
											//	canvas.drawBitmap (b, src, dst,null);
												//if(isEnableDraw){
												//	drawDrawBitmap(canvas,tileix*tileSizes[0], tileiy*tileSizes[1],tileSizes[0],tileSizes[1],pageWidth,pageHeight,src,dst);
												//}
										}
								}
									visibleTiles.add(tile);
								}
							}
						c.restore();
						//activity.saveBitmap(bitmap);
						canvas.drawBitmap (bitmap, x,  y,null);
					}
					
				
				this.pagesProvider.setVisibleTiles(visibleTiles);
			//}
				
				
			//	bitmap.compress(Bitmap.CompressFormat.PNG, 100,fos ); 
					
				
		}
		*/
	
	private void drawBitmap(Canvas canvas, Bitmap b, Rect src, Rect dst) {

			canvas.drawBitmap(b, src, dst, null);

		
	}

	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.TRANSPARENT);

		if (this.nook2) {
			N2EpdController.setGL16Mode();
		}
		
		this.getThumbnail(canvas,currentPage);
		if (this.findMode) this.drawFindResults(canvas);
	}

}
