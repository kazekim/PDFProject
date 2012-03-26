package com.kazekim.bookcase;

import java.util.ArrayList;
import java.util.List;


import android.graphics.Bitmap;

public class BookData {
	private int bookID;
	private String bookTitle;
	private String bookFilename;
	private String bookImageName;
	private Bitmap bookBitmap;
	
	public static final String BOOKCASETABLENAME = "flipbook_bookcase";
	public static final String[] column={"bookid","booktitle","bookfilename","bookimagename"};
	public static final String CREATETABLEQUERY = "CREATE TABLE " + BOOKCASETABLENAME + " ("
	+column[0]+" INTEGER PRIMARY KEY AUTOINCREMENT, "+column[1]+" TEXT, "+column[2]+" TEXT UNIQUE, "
	+column[3]+" TEXT UNIQUE);";

	public static final String INSERTQUERY = "insert into "+BOOKCASETABLENAME+" VALUES (null,?,?,?);";
	public static final String DELETEQUERY = "delete from "+BOOKCASETABLENAME+" where "+column[0]+"='?';";
	
	public BookData(){
		
	}
	
	public void setBookID(int bookID){
		this.bookID=bookID;
	}
	
	public void setBookTitle(String bookTitle){
		this.bookTitle=bookTitle;
	}
	
	public void setBookFilename(String bookFilename){
		this.bookFilename=bookFilename;
	}
	
	public void setBookImageName(String bookImageName){
		this.bookImageName=bookImageName;
	}
	
	public void setBookBitmap(Bitmap bitmap){
		this.bookBitmap=bitmap;
	}
	
	public int getBookID(){
		return bookID;
	}
	
	public String getBookTitle(){
		return bookTitle;
	}
	
	public String getBookFilename(){
		return bookFilename;
	}
	
	public String getBookImageName(){
		return bookImageName;
	}
	
	public Bitmap getBookBitmap(){
		return bookBitmap;
	}
	
	public static ArrayList<BookData> createEcouponList(List<DatabaseQueryData> queryDataList){
		ArrayList<BookData> ecouponDataList = new ArrayList<BookData>();
		
		for(int i=0;i<queryDataList.size();i++){
			BookData couponData = new BookData();
			ArrayList<String> queryData = queryDataList.get(i).getData();
			couponData.setBookID(Integer.parseInt(queryData.get(0)));
			couponData.setBookTitle(queryData.get(1));
			couponData.setBookFilename(queryData.get(2));
			couponData.setBookImageName(queryData.get(3));
			
			
			ecouponDataList.add(couponData);
		}
		
		return ecouponDataList;
	}
	
	public void recycle(){
		if(bookBitmap!=null){
			bookBitmap.recycle();
		}
	}
}
