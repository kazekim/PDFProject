package com.kazekim.bookmark;

import java.util.ArrayList;

import th.co.tnt.FlipbookSoft.BookmarkEntry;
import th.co.tnt.FlipbookSoft.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.kazekim.bookcase.BookData;
import com.kazekim.bookcase.BookcaseEditListener;

public class BookmarkCell  extends BaseAdapter implements OnClickListener{

	private Activity activity;
	private ArrayList<BookmarkEntry> bookmarkList;
	private LayoutInflater mInflater;

	  private ViewHolder holder;
	  
	  private BookmarkListListener listener;

	//  private Context context;
	  
	 public BookmarkCell(ArrayList<BookmarkEntry> bookmarkList,Activity activity){
		 this.bookmarkList = bookmarkList;

		 this.activity=activity;
		 System.out.println("Num Bookmark "+bookmarkList.size());
		  	
		 mInflater = LayoutInflater.from(activity.getApplicationContext());
	 }
	 
	 public void setBookmarkListListener(BookmarkListListener listener){
		 this.listener=listener;
	 }
	 
	public int getCount() {
		// TODO Auto-generated method stub
		return bookmarkList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return bookmarkList.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public void setBookList(ArrayList<BookmarkEntry> bookmarkList){
		this.bookmarkList=bookmarkList;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)  {
		if (convertView == null) {
	           convertView = mInflater.inflate(R.layout.bookmarkcell , null);
	           
	           // Creates a ViewHolder and store references to the two children views

	           // we want to bind data to.
	           holder = new ViewHolder();
	           holder.textPage = (TextView) convertView.findViewById(R.id.bookmarktextpage);
	           holder.numPage = (TextView) convertView.findViewById(R.id.bookmarkpagenum);
	           holder.deleteButton = (Button) convertView.findViewById(R.id.deletebookmarkbutton);
	           holder.deleteButton.setOnClickListener(this);

	           convertView.setTag(holder);
	           
	       //    holder.titleTextView.setTypeface(InitVal.Font);			
	       //    holder.titleTextView.setTextSize(InitVal.fontSizeNewsCell);
	           
           
	       }else {

	           // Get the ViewHolder back to get fast access to the TextView

	           // and the ImageView.

	           holder = (ViewHolder) convertView.getTag();
	          
	         
	       }
		
		 
		BookmarkEntry entry = bookmarkList.get(position);
        	holder.numPage.setText(Integer.toString(entry.page+1));
        	holder.deleteButton.setId(position);
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView textPage;
		TextView numPage;
		  Button deleteButton;
	}
	
	public void onClick(View v) {
		System.out.println(v.getId()+ " "+bookmarkList.size());
		
		BookmarkEntry bookmarkData = bookmarkList.get(v.getId());
		
		listener.onDeleteBookmarkClick(v,bookmarkData);
	}


}