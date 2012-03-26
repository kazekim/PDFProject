package com.kazekim.bookcase;

import java.util.ArrayList;

import com.kazekim.loadimage.PicUtil;

import th.co.tnt.FlipbookSoft.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BookcaseCell  extends BaseAdapter implements OnClickListener{

	private Activity activity;
	private ArrayList<BookData> bookList;
	private LayoutInflater mInflater;

	  private ViewHolder holder;
	  
	  private BookcaseEditListener listener;

	//  private Context context;
	  
	 public BookcaseCell(ArrayList<BookData> bookList,Activity activity){
		 this.bookList = bookList;

		 this.activity=activity;
		  	
		 mInflater = LayoutInflater.from(activity.getApplicationContext());
	 }
	 
	 public void setBookcaseEditListener(BookcaseEditListener listener){
		 this.listener=listener;
	 }
	 
	public int getCount() {
		// TODO Auto-generated method stub
		return bookList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bookList.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public void setBookList(ArrayList<BookData> bookList){
		this.bookList=bookList;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)  {
		if (convertView == null) {
	           convertView = mInflater.inflate(R.layout.bookcasecell , null);
	           
	           // Creates a ViewHolder and store references to the two children views

	           // we want to bind data to.
	           holder = new ViewHolder();
	           holder.titleTextView = (TextView) convertView.findViewById(R.id.booktitle);
	           holder.bookPathTextView = (TextView) convertView.findViewById(R.id.bookpath);
	           holder.deleteBookButton = (Button) convertView.findViewById(R.id.deletebookbutton);
	           holder.deleteBookButton.setOnClickListener(this);

	           convertView.setTag(holder);
	           
	       //    holder.titleTextView.setTypeface(InitVal.Font);			
	       //    holder.titleTextView.setTextSize(InitVal.fontSizeNewsCell);
	           
           
	       }else {

	           // Get the ViewHolder back to get fast access to the TextView

	           // and the ImageView.

	           holder = (ViewHolder) convertView.getTag();
	          
	         
	       }
		
		 
		BookData bookData = bookList.get(position);
		
		if(bookData.getBookImageName()!=null){
        	//holder.bookPathTextView.setText(bookData.getBookFilename());
			holder.bookPathTextView.setText(bookData.getBookTitle());
        	//holder.titleTextView.setText(bookData.getBookTitle());
        	holder.deleteBookButton.setId(position);
        }else{
        	return null;
        }
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView titleTextView;
		TextView bookPathTextView;
		  Button deleteBookButton;
	}
	
	public void onClick(View v) {
		BookData bookData = bookList.get(v.getId());
		
		listener.onDeleteBookClick(v, v.getId(),bookData.getBookID());
	}


}