package com.kazekim.brush;

import android.graphics.Path;

import com.kazekim.Math.PointCal;

public class SquareBrush extends Brush{
	
	private float lastX;
	private float lastY;
	
	private boolean isNewSquare=true;

    @Override
    public void mouseMove(Path path, float x, float y) {
    	if(isNewSquare){
    		lastX=x;
    		lastY=y;
    		isNewSquare=false;
    	}
    	path.reset();

        path.addRect(lastX, lastY, x, y, Path.Direction.CW);
    }


}
