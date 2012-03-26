package com.kazekim.bookcase;

import java.util.ArrayList;

import th.co.tnt.FlipbookSoft.R;

import com.kazekim.loadimage.ImageLoader;
import com.kazekim.loadimage.PicUtil;

import android.app.Activity;
import android.content.Context;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class BookcaseGridView extends BaseAdapter implements OnClickListener{

	public ImageLoader imageLoader; 
	private ArrayList<BookData> bookList;
	private LayoutInflater mInflater;

	  private ViewHolder holder;
	  
	//  private ImageThreadLoader imageLoader = new ImageThreadLoader();

	//  private Context context;
	  private Activity activity;
	  
	  private boolean enableEditMode;
	  
	  private BookcaseEditListener listener;
	  
	 public BookcaseGridView(ArrayList<BookData> bookList, Context context,Activity activity){
		 this.bookList = bookList;
		 
		 

		// this.context=context;
		 this.activity=activity;
		  	
		 mInflater = LayoutInflater.from(context);
		 imageLoader=new ImageLoader(activity.getApplicationContext());
	 }
	 
	 public void setBookcaseEditListener(BookcaseEditListener listener){
		 this.listener=listener;
	 }
	 
	public int getCount() {
		// TODO Auto-generated method stub
		if(bookList.size()<8){
			return 8;
		}else{
			return bookList.size();
		}
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bookList.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	public void setEnableEditMode(){
		enableEditMode=true;
	}
	
	public void setDisableEditMode(){
		enableEditMode=false;
	}
	
	public boolean getEnableEditMode(){
		return enableEditMode;
	}

	public void setBookList(ArrayList<BookData> bookList){
		this.bookList=bookList;
	}

	public View getView(int position, View convertView, ViewGroup parent)  {
		if (convertView == null) {
	           convertView = mInflater.inflate(R.layout.bookcasegridview , null);
	           
	           // Creates a ViewHolder and store references to the two children views

	           // we want to bind data to.
	           holder = new ViewHolder();
	           holder.shelfImage = (ImageView) convertView.findViewById(R.id.bookshelf);
	           holder.bookImage = (ImageView) convertView.findViewById(R.id.bookimage);
	           holder.shelfbar = (RelativeLayout) convertView.findViewById(R.id.shelfbar);
	           holder.deleteBookButton = (Button) convertView.findViewById(R.id.deletebookbutton);

	           convertView.setTag(holder);
	           
		         
	           
	       }else {

	           // Get the ViewHolder back to get fast access to the TextView

	           // and the ImageView.

	           holder = (ViewHolder) convertView.getTag();
	          
	         
	       }
		

	          // Bind the data with the holder.
		BookData bookData;
		if(position<bookList.size()){
			bookData = bookList.get(position);
			//holder.bookImage.setImageBitmap(PicUtil.loadFromFile(bookData.getBookImageName()));
			holder.bookImage.setImageBitmap(PicUtil.loadPrivateFile(activity,bookData.getBookImageName()));
        	if(enableEditMode){
    			holder.deleteBookButton.setVisibility(View.VISIBLE);
    			holder.deleteBookButton.setOnClickListener(this);
    		}else{
    			holder.deleteBookButton.setVisibility(View.GONE);
    		}
        }
		if(position%4==0){
			 holder.shelfImage.setImageResource(R.drawable.shelfbarleft);
		}else if(position%4==3){
			holder.shelfImage.setImageResource(R.drawable.shelfbarright);
		}else{
			holder.shelfImage.setImageResource(R.drawable.shelfbarmiddle);
		}
		
		holder.deleteBookButton.setId(position);
		
		DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		
        
		AbsListView.LayoutParams slp =new AbsListView.LayoutParams((int)(256*metrics.density),(int)(210*metrics.density));
		holder.shelfbar.setLayoutParams(slp);


		holder.shelfbar.removeView(holder.bookImage);
		RelativeLayout.LayoutParams bllp =new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		bllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		bllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		bllp.bottomMargin=20;
		holder.bookImage.setLayoutParams(bllp);
		holder.shelfbar.addView(holder.bookImage);
		
		
		return convertView;
	}
	
	static class ViewHolder {
		  ImageView bookImage;
		  ImageView shelfImage;
		  RelativeLayout shelfbar;
		  Button deleteBookButton;
	}

	public void onClick(View v) {
		BookData bookData = bookList.get(v.getId());
		
		listener.onDeleteBookClick(v, v.getId(),bookData.getBookID());
	}



}
