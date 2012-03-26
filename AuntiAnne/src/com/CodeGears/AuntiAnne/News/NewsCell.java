package com.CodeGears.AuntiAnne.News;

import java.util.ArrayList;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.loadimage.ImageLoader;

public class NewsCell extends BaseAdapter{

	private Activity activity;
	private ArrayList<NewsData> newsDataList;
	private LayoutInflater mInflater;

	  private ViewHolder holder;
	  
	  private ImageLoader imageLoader;

	//  private Context context;
	  
	 public NewsCell(ArrayList<NewsData> newsDataList,Activity activity){
		 this.newsDataList = newsDataList;

		 this.activity=activity;
		// this.context=context;
		  	
		 mInflater = LayoutInflater.from(activity.getApplicationContext());
		 imageLoader=new ImageLoader(activity.getApplicationContext());
	 }
	 
	public int getCount() {
		// TODO Auto-generated method stub
		return newsDataList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return newsDataList.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent)  {
		if (convertView == null) {
	           convertView = mInflater.inflate(R.layout.newscell , null);
	           
	           // Creates a ViewHolder and store references to the two children views

	           // we want to bind data to.
	           holder = new ViewHolder();
	           holder.titleTextView = (TextView) convertView.findViewById(R.id.newstitle);
	           holder.thumbImageView = (ImageView) convertView.findViewById(R.id.newsthumb);
	           holder.newsBox = (ImageView) convertView.findViewById(R.id.newsbox);

	           convertView.setTag(holder);
	           
	           holder.titleTextView.setTypeface(InitVal.Font);			
	           holder.titleTextView.setTextSize(InitVal.fontSizeNewsCell);
	           
	           	

	           
	          
	 
	          
	           	
	           
	       }else {

	           // Get the ViewHolder back to get fast access to the TextView

	           // and the ImageView.

	           holder = (ViewHolder) convertView.getTag();
	          
	         
	       }
		
		 
		/*
		 ViewTreeObserver vto = holder.newsBox.getViewTreeObserver();
         vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
             public boolean onPreDraw() {
          	   int finalHeight;
                 //finalHeight = holder.newsBox.getMeasuredHeight()*3/4;

                 holder.thumbImageView.setLayoutParams(new LinearLayout.LayoutParams(110,85));
                 return true;
             }
         });
         */
         if(position%2!=0){
  			 holder.newsBox.setImageResource(R.drawable.boxyellowhd02);
  			 holder.titleTextView.setTextColor(InitVal.BLUECOLOR);
  		 }else{
  			 holder.newsBox.setImageResource(R.drawable.boxbluehd02);
  			 holder.titleTextView.setTextColor(InitVal.YELLOWCOLOR);
  		 }

	          // Bind the data with the holder.
	       holder.titleTextView.setText(newsDataList.get(position).getNewsTitle());

	       imageLoader.DisplayImage(newsDataList.get(position).getNewsThumb(), activity, holder.thumbImageView,true);
     
	       
		return convertView;
	}
	
	static class ViewHolder {
		  TextView titleTextView;
		  ImageView thumbImageView;
		  ImageView newsBox;
	}

}
