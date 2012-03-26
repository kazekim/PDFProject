package com.CodeGears.AuntiAnne.restaurantmenu;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.loadimage.ImageLoader;

public class CategoryMenuGridView  extends BaseAdapter{

	private LayoutInflater mInflater;

	  private ViewHolder holder;
	  
	  private CategoryMenuData categoryData;
	  
	  private ImageLoader imageLoader;

	  private Activity activity;
	  
	 public CategoryMenuGridView(Activity _activity,CategoryMenuData categoryData){

		// this.context=context;
		 this.categoryData = categoryData;
		 
		 activity=_activity;
		  	
		 mInflater = LayoutInflater.from(activity.getApplicationContext());
		 
		
		 
		 imageLoader=new ImageLoader(activity.getApplicationContext());

	 }
	 
	public int getCount() {
		// TODO Auto-generated method stub
		return categoryData.getFoodNum();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categoryData.getFoodDataAtIndex(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}


	public View getView(int position, View convertView, ViewGroup parent)  {
		if (convertView == null) {
	           convertView = mInflater.inflate(R.layout.foodgridview , null);
	           
	           // Creates a ViewHolder and store references to the two children views

	           // we want to bind data to.
	           holder = new ViewHolder();
	           holder.foodBackground = (ImageView) convertView.findViewById(R.id.foodbackground);
	           holder.foodName = (TextView) convertView.findViewById(R.id.foodname);
	           holder.foodImage = (ImageView) convertView.findViewById(R.id.foodimage);
	           holder.foodDescription = (TextView) convertView.findViewById(R.id.fooddescript);

	           holder.foodName.setTypeface(InitVal.FontBold);			
	           holder.foodName.setTextSize(InitVal.fontSizeCoreMenuName);
	           holder.foodDescription.setTypeface(InitVal.Font);			
	           holder.foodDescription.setTextSize(InitVal.fontSizeCoreMenuDescript);
	           

	           convertView.setTag(holder);
	           
	           	 if(Math.floor((position+1)/2)%2!=0){
	           		holder.foodBackground.setImageResource(R.drawable.boxyellowhd);
		  			 holder.foodName.setTextColor(InitVal.BLUECOLOR);
		  			 holder.foodDescription.setTextColor(InitVal.BLUECOLOR);
		  		 }else{
		  			holder.foodBackground.setImageResource(R.drawable.boxbluehd);
		  			 holder.foodName.setTextColor(InitVal.YELLOWCOLOR);
		  			 holder.foodDescription.setTextColor(InitVal.YELLOWCOLOR);
		  		 }
	           
	       }else {

	           // Get the ViewHolder back to get fast access to the TextView

	           // and the ImageView.

	           holder = (ViewHolder) convertView.getTag();
	          
	         
	       }

	          // Bind the data with the holder.
		
		FoodData foodData = categoryData.getFoodDataAtIndex(position);
		 holder.foodName.setText(foodData.getName());
		 holder.foodDescription.setText(foodData.getDescription());
		
		 
	     imageLoader.DisplayImage(foodData.getThumbURL(), activity, holder.foodImage,true);
     
	     DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
	    	 holder.foodImage.setLayoutParams(new RelativeLayout.LayoutParams((int)(80*metrics.density)
	    			 ,(int)(60*metrics.density)));
	    	 
     	
	     RelativeLayout.LayoutParams layoutParams = 
 		    (RelativeLayout.LayoutParams)holder.foodImage.getLayoutParams();
	     layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.foodgrid); 
	     
	     layoutParams.setMargins(0, (int)(43*metrics.density), 0, 0);
		return convertView;
	}
	
	static class ViewHolder {
		ImageView foodBackground;
		TextView foodName;
		TextView foodDescription;
		  ImageView foodImage;
	}

}