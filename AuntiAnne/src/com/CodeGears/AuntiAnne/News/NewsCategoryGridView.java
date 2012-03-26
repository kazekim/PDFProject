package com.CodeGears.AuntiAnne.News;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.CategoryData;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.function.ImageFunction;
import com.CodeGears.AuntiAnne.loadimage.ImageLoader;
import com.CodeGears.AuntiAnne.restaurantmenu.CategoryMenuData;

public class NewsCategoryGridView extends BaseAdapter{

	private LayoutInflater mInflater;

	  private ViewHolder holder;
	  private ArrayList<CategoryData> categoryDataList;

	 private Context context;
	 private Activity activity;
	  
	 public NewsCategoryGridView(ArrayList<CategoryData> categoryDataList,Context context,Activity _activity){

		 this.context=context;
		 activity=_activity;
		 this.categoryDataList=categoryDataList;
		  	
		 mInflater = LayoutInflater.from(context);

	 }
	 
	public int getCount() {
		// TODO Auto-generated method stub
		return categoryDataList.size()-1;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categoryDataList.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}


	public View getView(int position, View convertView, ViewGroup parent)  {
		if (convertView == null) {
	           convertView = mInflater.inflate(R.layout.categorygridview , null);
	           
	           // Creates a ViewHolder and store references to the two children views

	           // we want to bind data to.
	           holder = new ViewHolder();
	           holder.newsCategoryBackground = (ImageView) convertView.findViewById(R.id.categorybackground);
	           holder.newsCategoryName = (TextView) convertView.findViewById(R.id.categoryname);
	           holder.categoryThumb = (ImageView) convertView.findViewById(R.id.categorythumb);
	          
	           holder.newsCategoryName.setTypeface(InitVal.FontBold);			
	           holder.newsCategoryName.setTextSize(InitVal.fontSizeNewsGridView);
	           

	           convertView.setTag(holder);
	           
	           	 if(Math.floor((position+1)/2)%2!=0){
		  			 holder.newsCategoryBackground.setImageResource(R.drawable.boxyellowhd);
		  			 holder.newsCategoryName.setTextColor(InitVal.BLUECOLOR);
		  		 }else{
		  			 holder.newsCategoryBackground.setImageResource(R.drawable.boxbluehd);
		  			 holder.newsCategoryName.setTextColor(InitVal.YELLOWCOLOR);
		  		 }
	           
	       }else {

	           // Get the ViewHolder back to get fast access to the TextView

	           // and the ImageView.

	           holder = (ViewHolder) convertView.getTag();
	          
	         
	       }

	          // Bind the data with the holder.
		
		CategoryData categoryData = categoryDataList.get(position);
		 holder.newsCategoryName.setText(categoryData.getNewsTitle());
		 /*
		 String uri = "drawable/"+categoryData.getNewsThumb();
		 
		    int imageResource = convertView.getResources().getIdentifier(uri, null, context.getPackageName());

		    Drawable image = context.getResources().getDrawable(imageResource);
		    holder.categoryThumb.setImageDrawable(image);
*/
		 holder.categoryThumb.setBackgroundDrawable(ImageFunction.LoadImageFromWeb(categoryData.getNewsThumb()));
		 
		 
	     //  imageLoader.DisplayImage(InitVal.categoryDataList.get(position).getNewsThumb(), activity, holder.categoryThumb);
     

		return convertView;
	}
	
	static class ViewHolder {
		ImageView newsCategoryBackground;
		ImageView categoryThumb;
		TextView newsCategoryName;
	}

}
