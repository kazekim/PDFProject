package com.kazekim.drawing;

import android.content.Context;
import android.graphics.*;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import th.co.tnt.FlipbookSoft.OpenFileActivity;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {
    private Boolean _run;
    protected DrawThread thread;
    private Bitmap mBitmap;
    public boolean isDrawing = true;
    public DrawingPath previewPath;
    
    private int x=0;
    private int y=0;
    private float ratioZoom=1;
    private float bookX=0;
    private float bookY=0;
    private float diffX=0;
    private float diffY=0;
    private float pageWidth=0;
    private float pageHeight=0;
    
    private CommandManager commandManager;
    
    public static  Rect drawAreaRect;
   // private Paint drawAreaPaint;

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);


        commandManager = new CommandManager();
        thread = new DrawThread(getHolder());
    }
    
    public void setBookInitial(int x, int y, int width, int height){

    	this.bookX=x;
    	this.bookY=y;
    	this.pageWidth=width;
    	this.pageHeight=height;
    	
    	drawAreaRect = new Rect(x, y, x+width, y+height);
    //	drawAreaPaint = new Paint();
    //	drawAreaPaint.setColor(Color.RED);
    }

    
    
    public int getOriginY(float _y){
		int posY = (int)((float)(diffY-bookY+_y)*(float)pageWidth/(float)(pageWidth+diffY));

		return posY;
	}
    

    public void setXY(int _x ,int _y){
    	
    	this.diffX+=_x;
    	this.diffY+=_y;
    	
    	invalidate();
    }
    
    public void zoomWithRatio(float ratio){
		this.ratioZoom=ratio;

		invalidate();
	}
    
    private Handler previewDoneHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            isDrawing = false;
        }
    };

    class DrawThread extends  Thread{
        private SurfaceHolder mSurfaceHolder;



        public DrawThread(SurfaceHolder surfaceHolder){
            mSurfaceHolder = surfaceHolder;

        }

        public void setRunning(boolean run) {
            _run = run;
        }
        
       


        @Override
        public void run() {
            Canvas canvas = null;
            while (_run){
                if(isDrawing == true){
                    try{
                        canvas = mSurfaceHolder.lockCanvas(null);
                        if(mBitmap == null){
                            mBitmap =  Bitmap.createBitmap (1, 1, Bitmap.Config.ARGB_8888);
                        }
                      //  final Canvas c = new Canvas (mBitmap);
                        
                     //   c.save();
                     //   c.translate(diffX, diffY);
                        canvas.save();
                        canvas.translate(diffX, diffY);
                        canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2);
                        canvas.scale(ratioZoom, ratioZoom);
                        canvas.translate(-canvas.getWidth()/2, -canvas.getHeight()/2);
                        
                     //   c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                  //      canvas.drawRect(drawAreaRect, drawAreaPaint);
                        commandManager.executeAll(canvas,previewDoneHandler);
                        previewPath.draw(canvas);
                        
                       // canvas.drawBitmap (mBitmap, x,  y,null);
                        
                    //    c.restore();
                        canvas.restore();
                    }catch(NullPointerException e){
  
                    }finally {
                    
                    	try{
                    		mSurfaceHolder.unlockCanvasAndPost(canvas);
                    	}catch(IllegalArgumentException e){
                    		
                    	}
                    }


                }

            }

        }
    }


    public void addDrawingPath (DrawingPath drawingPath){
        commandManager.addCommand(drawingPath);
    }

    public boolean hasMoreRedo(){
        return commandManager.hasMoreRedo();
    }

    public void redo(){
        isDrawing = true;
        commandManager.redo();


    }

    public void undo(){
        isDrawing = true;
        commandManager.undo();
    }

    public boolean hasMoreUndo(){
        return commandManager.hasMoreUndo();
    }

    public Bitmap getBitmap(){
        return mBitmap;
    }


    public void surfaceChanged(SurfaceHolder holder, int format, int width,  int height) {
        // TODO Auto-generated method stub
        mBitmap =  Bitmap.createBitmap (width, height, Bitmap.Config.ARGB_8888);;
    }


    public void surfaceCreated(SurfaceHolder holder) {
    	if (thread.getState() == Thread.State.TERMINATED) {
			thread = new DrawThread(getHolder());
			thread.setRunning(true);
			thread.start();
		}
		else {
			thread.setRunning(true);
			thread.start();
		}  
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }

    public void hide(){
    	isDrawing=false;
    	//surfaceDestroyed(getHolder());
    	invalidate();
    }
    
    public void show(){
    	isDrawing=true;
    	//surfaceCreated(getHolder());
    	invalidate();
    }
    
    public void clearDraw(){
    	commandManager.clearStack();
    	invalidate();
    }
}
