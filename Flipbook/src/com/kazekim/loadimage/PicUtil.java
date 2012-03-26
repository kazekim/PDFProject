package com.kazekim.loadimage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kazekim.function.InitVal;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class PicUtil {
  public static File getSavePath() {
      File path;
      if (hasSDCard()) { // SD card
          path = new File(getSDCardPath() + "/flipbook/");
          path.mkdir();
      }else { 
          path = Environment.getDataDirectory();
      }
      return path;
  }
  public static String getCacheFilename() {
      File f = getSavePath();
      return f.getAbsolutePath() + "/cache.png";
  }

  public static Bitmap loadFromFile(String filename) {
      try {
          File f = new File(filename);
          if (!f.exists()) { 
        	  return null; }
          Bitmap tmp = BitmapFactory.decodeFile(filename);
          return tmp;
      } catch (Exception e) {
    	  e.printStackTrace();
          return null;
      }
  }
  public static Bitmap loadFromCacheFile() {
      return loadFromFile(getCacheFilename());
  }
  public static void saveToCacheFile(Bitmap bmp) {
      saveToFile(getCacheFilename(),bmp);
  }
  public static String saveToFile(String filename,Bitmap bmp) {
      try {
          FileOutputStream out = new FileOutputStream(filename);
          bmp.compress(CompressFormat.PNG, 100, out);
          out.flush();
          out.close();
          
          return filename;
      } catch(Exception e) {
    	  e.printStackTrace();
    	  return "";
      }
  }

  public static boolean hasSDCard() { // SD????????
      String status = Environment.getExternalStorageState();
      return status.equals(Environment.MEDIA_MOUNTED);
  }
  public static String getSDCardPath() {
      File path = Environment.getExternalStorageDirectory();
      return path.getAbsolutePath();
  }
  
  public static String saveToPrivateFile(Activity activity,String filename,Bitmap bmp){
	  FileOutputStream fos;
		try {
			fos = activity.openFileOutput(filename, Context.MODE_PRIVATE);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
			
			return filename;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
  }
  
  public static Bitmap loadPrivateFile(Activity activity,String filename){
	  
	  Bitmap bitmap = null;
      FileInputStream fis;
      
      try {
			fis = activity.openFileInput(filename);
			bitmap = BitmapFactory.decodeStream(fis);
			fis.close();
			System.out.println("dsdsdsddd");
			return bitmap;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return BitmapFactory.decodeResource(activity.getResources(), InitVal.defaultBookID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return BitmapFactory.decodeResource(activity.getResources(), InitVal.defaultBookID);
		}
  }
  
  public static void deletePrivateFile(Activity activity,String filename){
	  activity.deleteFile(filename);
  }

}