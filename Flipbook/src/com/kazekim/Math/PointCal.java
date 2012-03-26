package com.kazekim.Math;

import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.MotionEvent;

public class PointCal {
	
	public static final int NOTESAMEWAY=0;
	public static final int SAMEWAYLEFT=1;
	public static final int SAMEWAYRIGHT=2;
	  public static float spacing(MotionEvent event) {
		   float x = event.getX(0) - event.getX(1);
		   float y = event.getY(0) - event.getY(1);
		   return FloatMath.sqrt(x * x + y * y);
		}
	   
	  public static int sameDirection(PointF start,MotionEvent event,int markLength) {

		  if(start.x-event.getX(0)>markLength && start.x-event.getX(1)>markLength){
			  return SAMEWAYLEFT;
		  }else if(start.x-event.getX(0)<-markLength && start.x-event.getX(1)<-markLength){
			  return SAMEWAYRIGHT;
		  }else{
			  return NOTESAMEWAY;
		  }
		}
	   public static void midPoint(PointF point, MotionEvent event) {
		   float x = event.getX(0) + event.getX(1);
		   float y = event.getY(0) + event.getY(1);
		   point.set(x / 2, y / 2);
		}
	   
	   public static float distanceFromPoint(float x1,float y1,float x2,float y2){
		   float distance;
		   
		   distance = (float) Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
		   
		   return distance;
	   }
}
