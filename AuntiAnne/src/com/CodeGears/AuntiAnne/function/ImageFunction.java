package com.CodeGears.AuntiAnne.function;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.CodeGears.AuntiAnne.appdata.InitVal;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;

public class ImageFunction {
	public static Drawable LoadImageFromWeb(String url)
	{
		try
		{
		InputStream is = (InputStream) new URL(url).getContent();
		Drawable d = Drawable.createFromStream(is, "src name");
		return d;
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}
	
	public static Bitmap scaleFitImageViewHeight(int imageViewHeight,Bitmap bitmap){

    	int imageViewWidth = (int)((double)(imageViewHeight*InitVal.metrics.density)/(double)bitmap.getHeight()*(double)bitmap.getWidth());
    	
    	Matrix matrix = new Matrix();
    	matrix.postScale( (float)imageViewWidth/bitmap.getWidth(),(float)imageViewHeight/bitmap.getHeight());
    	Bitmap temp =Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    	//System.out.println("sdsd "+imageViewHeight+" "+imageViewWidth+" "+temp.getHeight()+ " "+temp.getWidth());
		return temp;
		
	}
}
