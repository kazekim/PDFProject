package com.kazekim.brush;

import com.kazekim.Math.PointCal;

import android.graphics.Path;


public class CircleBrush extends Brush{
	
	private float lastX;
	private float lastY;
	
	private boolean isNewCircle=true;

    @Override
    public void mouseMove(Path path, float x, float y) {
    	if(isNewCircle){
    		lastX=x;
    		lastY=y;
    		isNewCircle=false;
    	}
    	path.reset();
        path.addCircle(lastX,lastY,PointCal.distanceFromPoint(lastX, lastY, x, y),Path.Direction.CW);
        
    }


}
