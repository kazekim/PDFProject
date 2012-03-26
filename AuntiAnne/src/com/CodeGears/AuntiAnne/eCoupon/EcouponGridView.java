package com.CodeGears.AuntiAnne.eCoupon;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.loadimage.ImageLoader;

public class EcouponGridView extends BaseAdapter{

	public ImageLoader imageLoader; 
	private ArrayList<EcouponData> couponDataList;
	private LayoutInflater mInflater;

	  private ViewHolder holder;
	  
	//  private ImageThreadLoader imageLoader = new ImageThreadLoader();

	//  private Context context;
	  private Activity activity;
	  
	 public EcouponGridView(ArrayList<EcouponData> couponDataList, Context context,Activity activity){
		 this.couponDataList = couponDataList;

		// this.context=context;
		 this.activity=activity;
		  	
		 mInflater = LayoutInflater.from(context);
		 imageLoader=new ImageLoader(activity.getApplicationContext());
	 }
	 
	public int getCount() {
		// TODO Auto-generated method stub
		return couponDataList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return couponDataList.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}


	public View getView(int position, View convertView, ViewGroup parent)  {
		if (convertView == null) {
	           convertView = mInflater.inflate(R.layout.coupongridview , null);
	           
	           // Creates a ViewHolder and store references to the two children views

	           // we want to bind data to.
	           holder = new ViewHolder();
	           holder.couponBackground = (LinearLayout) convertView.findViewById(R.id.couponbackground);
	           holder.couponDescript = (TextView) convertView.findViewById(R.id.coupondescript);
	           holder.couponNotice = (TextView) convertView.findViewById(R.id.couponnotice);
	           holder.couponImage = (ImageView) convertView.findViewById(R.id.couponimage);

	        //   holder.couponDescript.setTypeface(InitVal.Font);
	         //  holder.couponDescript.setTextSize(InitVal.fontSizeCouponGridViewDescript);
	         //  holder.couponDescript.setTextColor(InitVal.BLUECOLOR);
	   		
	           	holder.couponNotice.setTypeface(InitVal.Font);
		   		holder.couponNotice.setTextSize(InitVal.fontSizeCouponGridViewNotice);
		   		holder.couponNotice.setTextColor(InitVal.BLUECOLOR);
	           convertView.setTag(holder);
	           
		         
	           
	       }else {

	           // Get the ViewHolder back to get fast access to the TextView

	           // and the ImageView.

	           holder = (ViewHolder) convertView.getTag();
	          
	         
	       }

	          // Bind the data with the holder.
		EcouponData couponData = couponDataList.get(position);
		
		// holder.couponDescript.setText(couponData.getCouponDescript());
		 holder.couponNotice.setText(couponData.getCouponNotice());
		 
		 if(couponData.isUsed()){
			 holder.couponImage.setVisibility(View.INVISIBLE);
			 holder.couponDescript.setVisibility(View.INVISIBLE);
			 holder.couponNotice.setVisibility(View.INVISIBLE);
		 }else{
	       imageLoader.DisplayImage(couponData.getPictureURL(), activity, holder.couponImage,true);
		 }
		 if(position%2!=0){
			 holder.couponBackground.setBackgroundResource(R.drawable.coupongridright);
		 }

		 if(InitVal.screenW==InitVal.MDPI){
	    	 holder.couponImage.setLayoutParams(new LinearLayout.LayoutParams(90,65));
	    	 
     	}else if(InitVal.screenType==InitVal.HDPI){
     		holder.couponImage.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,98));
 
     	}else{
     		holder.couponImage.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,130));

     	}
	     
		return convertView;
	}
	
	static class ViewHolder {
		LinearLayout couponBackground;
		TextView couponDescript;
		TextView couponNotice;
		  ImageView couponImage;
	}

}
