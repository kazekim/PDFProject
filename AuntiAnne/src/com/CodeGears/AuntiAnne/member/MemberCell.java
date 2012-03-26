package com.CodeGears.AuntiAnne.member;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.News.NewsData;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.loadimage.ImageLoader;

public class MemberCell extends BaseAdapter{

	private ArrayList<MemberMenuData> memberMenuList;
	private LayoutInflater mInflater;

	  private ViewHolder holder;
	  
	  private ImageLoader imageLoader;

	  private Context context;
	  
	 public MemberCell(ArrayList<MemberMenuData> memberMenuList,Context context){
		 this.memberMenuList = memberMenuList;

		this.context=context;
		  	
		 mInflater = LayoutInflater.from(context);
	 }
	 
	public int getCount() {
		// TODO Auto-generated method stub
		return memberMenuList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return memberMenuList.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup parent)  {
		if (convertView == null) {
	           convertView = mInflater.inflate(R.layout.membercell , null);
	           
	           // Creates a ViewHolder and store references to the two children views

	           // we want to bind data to.
	           holder = new ViewHolder();
	           holder.memberName = (TextView) convertView.findViewById(R.id.membermenuname);
	           holder.memberThumb = (ImageView) convertView.findViewById(R.id.membermenuthumb);

	           convertView.setTag(holder);
	           
	           holder.memberName.setTypeface(InitVal.FontBold);			
	           holder.memberName.setTextSize(InitVal.fontSizeMember);        	
	           holder.memberName.setTextColor(InitVal.BLUECOLOR);
	       }else {

	           // Get the ViewHolder back to get fast access to the TextView

	           // and the ImageView.

	           holder = (ViewHolder) convertView.getTag();
	          
	         
	       }

	          // Bind the data with the holder.
		
			MemberMenuData memberMenuData = memberMenuList.get(position);
	       holder.memberName.setText(memberMenuData.getName());
	       
	       
	       String uri = "drawable/"+memberMenuData.getThumbURL();
	       int imageResource = convertView.getResources().getIdentifier(uri, null, context.getPackageName());

		    Drawable image = context.getResources().getDrawable(imageResource);
		    holder.memberThumb.setImageDrawable(image);
	       
		return convertView;
	}
	
	static class ViewHolder {
		  TextView memberName;
		  ImageView memberThumb;
	}

}
