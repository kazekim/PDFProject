package th.co.tnt.FlipbookSoft;

import java.io.File;
import java.util.ArrayList;

import com.kazekim.bookcase.BookData;
import com.kazekim.bookcase.BookcaseCell;
import com.kazekim.bookcase.BookcaseDatabaseHelper;
import com.kazekim.bookcase.BookcaseEditListener;
import com.kazekim.bookcase.BookcaseGridView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;

public class BookcaseActivity  extends Activity implements OnItemClickListener,OnClickListener,BookcaseEditListener {

	private final static String TAG = "th.co.tnt.FlipbookSoft";
	
	private static GridView bookcaseGridView;
	private static ListView bookcaseListView;
	private static BookcaseGridView bookcaseAdaptor;
	private static BookcaseCell bookcaseListAdaptor;
	
	private static String dbname;
	
	private RelativeLayout bookcase;
	private Button addBookButton;
	private Button editShelfButton;
	private Button settingButton;
	private Button searchButton;
	private Button viewGridButton;
	private Button viewListButton;
	private EditText searchEditText;
	
	private static ArrayList<BookData> bookList;
	
	private static BookcaseDatabaseHelper dh;
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
				setContentView(R.layout.bookcase);
				
				dbname=BookData.BOOKCASETABLENAME;
				
				bookcaseGridView = (GridView) findViewById(R.id.bookcasegridview);
				
				bookcaseListView = (ListView) findViewById(R.id.bookcaselistview);
				bookcaseListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				
				dh = new BookcaseDatabaseHelper(this);
				dh.setInsertStatement(BookData.INSERTQUERY);
				dh.closeDB();
				
				bookcase = (RelativeLayout) findViewById(R.id.bookcase);
				addBookButton = (Button) findViewById(R.id.addbookbutton);
				editShelfButton = (Button) findViewById(R.id.editshelfbutton);
				settingButton = (Button) findViewById(R.id.settingbutton);
				searchButton = (Button) findViewById(R.id.searchbutton);
				searchEditText = (EditText) findViewById(R.id.searchEditText);
				viewGridButton = (Button) findViewById(R.id.viewgridbutton);
				viewListButton = (Button) findViewById(R.id.viewlistbutton);
				
				searchEditText.clearFocus();
				addBookButton.requestFocus();
		        settingButton.setVisibility(View.GONE);
		        searchButton.setVisibility(View.GONE);
		        searchEditText.setVisibility(View.GONE);
				loadBook();
				
				addBookButton.setOnClickListener(this);
				editShelfButton.setOnClickListener(this);
				settingButton.setOnClickListener(this);
				bookcaseAdaptor.setBookcaseEditListener(this);
				bookcaseListAdaptor.setBookcaseEditListener(this);
				searchButton.setOnClickListener(this);
				viewGridButton.setOnClickListener(this);
				viewListButton.setOnClickListener(this);
				bookcaseGridView.setOnItemClickListener(this);
				bookcaseListView.setOnItemClickListener(this);
	 }
	 
	 public void loadBook(){
		 dh = new BookcaseDatabaseHelper(this);
			
				bookList = BookData.createEcouponList(dh.selectAll(dbname, BookData.column
						, null, null, null,null, null));

				dh.closeDB();
				bookcaseAdaptor = new BookcaseGridView(bookList,this,this);
				bookcaseGridView.setAdapter(bookcaseAdaptor);
				
				bookcaseListAdaptor = new BookcaseCell(bookList,this);
				bookcaseListView.setAdapter(bookcaseListAdaptor);

		}
	 
		
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		if(pos<bookList.size()){
			
			BookData bookData = bookList.get(pos);
			File file = new File(bookData.getBookFilename());
			pdfView(file);
		}
	}

	public void onClick(View v) {
		if(v==addBookButton){
		//	dh.closeDB();
			Intent myIntent;
			myIntent = new Intent(BookcaseActivity.this,
				AddBookActivity.class);

			startActivityForResult(myIntent,1);
			overridePendingTransition (R.anim.fadeout, R.anim.fadein);
		}else if(v==editShelfButton){
			pressEditModeButton();
		}else if(v==viewGridButton){
			bookcaseGridView.setVisibility(View.VISIBLE);
			bookcaseListView.setVisibility(View.GONE);
		}else if(v==viewListButton){
			bookcaseGridView.setVisibility(View.GONE);
			bookcaseListView.setVisibility(View.VISIBLE);
		}
	}

	public void pressEditModeButton(){
		if(bookcaseAdaptor.getEnableEditMode()){
			bookcaseAdaptor.setDisableEditMode();
		}else{
			bookcaseAdaptor.setEnableEditMode();
		}
		bookcaseGridView.invalidateViews();
	}

	public void onDeleteBookClick(View v,int position, int id) {
		 dh = new BookcaseDatabaseHelper(this);
	
		dh.delete(BookData.BOOKCASETABLENAME, BookData.column[0]+" = '"+id+"'");
		dh.closeDB();
		
		bookList.remove(position);
		
		bookcaseAdaptor.setBookList(bookList);
		bookcaseListAdaptor.setBookList(bookList);
		
		bookcaseGridView.setAdapter(bookcaseAdaptor);
		bookcaseListView.setAdapter(bookcaseListAdaptor);
		
		bookcaseGridView.invalidateViews();
		bookcaseListView.invalidateViews();
	}

	public void pdfView(File f) {
			Log.i(TAG, "post intent to open file " + f);
			Intent intent = new Intent();
			intent.setDataAndType(Uri.fromFile(f), "application/pdf");
			intent.setClass(this, OpenFileActivity.class);
			intent.setAction("android.intent.action.VIEW");
			this.startActivity(intent);
	 }

}
