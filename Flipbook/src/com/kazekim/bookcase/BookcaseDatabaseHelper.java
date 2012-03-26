package com.kazekim.bookcase;

import java.util.ArrayList;
import java.util.List;

import com.kazekim.function.InitVal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class BookcaseDatabaseHelper {
	 
		private  static final String DATABASE_NAME = "flipbook_p1.db";
	   private static final int DATABASE_VERSION = 1;
	 //  private static final String TABLE_NAME = "table1";
	 
	   private Context context;
	   private SQLiteDatabase db;
	 
	   private SQLiteStatement insertStmt;
	 
	   public BookcaseDatabaseHelper(Context context) {
	      this.context = context;
	      OpenHelper openHelper = new OpenHelper(this.context);
	      this.db = openHelper.getWritableDatabase();
	      
	      
	      
	   }
	   
	   public void closeDB(){
		   this.db.close();
	   }
	   
	   public void setInsertStatement(String insertString){
	   		this.insertStmt = this.db.compileStatement(insertString);
		}
	   
	 
	   public long insert(String string1) {
		   this.insertStmt.bindString(1, string1);

	      return this.insertStmt.executeInsert();
	   }
	   
	   public long insert(String string1,String string2) {
		   this.insertStmt.bindString(1, string1);
		   this.insertStmt.bindString(2, string2);

	      return this.insertStmt.executeInsert();
	   }
	   
	   public long insert(String string1,String string2,String string3) {
		   this.insertStmt.bindString(1, string1);
		   this.insertStmt.bindString(2, string2);
		   this.insertStmt.bindString(3, string3);
		   
	      return this.insertStmt.executeInsert();
	   }
	   
	   public long insert(String string1,String string2,String string3,String string4) {
		   this.insertStmt.bindString(1, string1);
		   this.insertStmt.bindString(2, string2);
		   this.insertStmt.bindString(3, string3);
		   this.insertStmt.bindString(4, string4);
		   
	      return this.insertStmt.executeInsert();
	   }
	   
	   public long insert(String string1,String string2,String string3,String string4,String string5) {
		   this.insertStmt.bindString(1, string1);
		   this.insertStmt.bindString(2, string2);
		   this.insertStmt.bindString(3, string3);
		   this.insertStmt.bindString(4, string4);
		   this.insertStmt.bindString(5, string5);

	      return this.insertStmt.executeInsert();
	   }
	   
	   public long insert(String string1,String string2,String string3,String string4,String string5,String string6) {
		   this.insertStmt.bindString(1, string1);
		   this.insertStmt.bindString(2, string2);
		   this.insertStmt.bindString(3, string3);
		   this.insertStmt.bindString(4, string4);
		   this.insertStmt.bindString(5, string5);
		   this.insertStmt.bindString(6, string6);

	      return this.insertStmt.executeInsert();
	   }
	   
	   public long insert(String string1,String string2,String string3,String string4,String string5,String string6,String string7) {
		   this.insertStmt.bindString(1, string1);
		   this.insertStmt.bindString(2, string2);
		   this.insertStmt.bindString(3, string3);
		   this.insertStmt.bindString(4, string4);
		   this.insertStmt.bindString(5, string5);
		   this.insertStmt.bindString(6, string6);
		   this.insertStmt.bindString(7, string7);

	      return this.insertStmt.executeInsert();
	   }
	   

	   public void update(String tableName, ContentValues values, String whereClause, String[] whereArgs){

		   this.db.update(tableName, values, whereClause, whereArgs);
		  
	   }
	   
	   public void delete(String tablename,String whereClaus){
		   this.db.delete(tablename, whereClaus, null);
	   }
	 
	   public void deleteAll(String tablename) {
	      this.db.delete(tablename, null, null);
	   }
	 
	   public List<DatabaseQueryData> selectAll(String tableName,String[] columns,String selection
			   ,String[] selectionArgs,String groupBy,String having,String orderBy) {
		  
	      List<DatabaseQueryData> list = new ArrayList<DatabaseQueryData>();
	      
	      Cursor cursor = this.db.query(tableName, columns, 
	    		  selection, selectionArgs, groupBy, having, orderBy);
	      if (cursor.moveToFirst()) {
	         do {
	        	 DatabaseQueryData queryData = new DatabaseQueryData();
	        	 for(int i=0;i<cursor.getColumnCount();i++){
	        		 queryData.addData(cursor.getString(i));
	        	 }
	            list.add(queryData); 
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      return list;
	   }
	 
	   private static class OpenHelper extends SQLiteOpenHelper {
	 
	      OpenHelper(Context context) {
	         super(context, DATABASE_NAME, null, DATABASE_VERSION);
	      }
	 
	      @Override
	      public void onCreate(SQLiteDatabase db) {

	    	  //db.execSQL(EcouponData.MEMBERCREATETABLEQUERY);
	    	  db.execSQL(BookData.CREATETABLEQUERY);
	      }
	 
	      @Override
	      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
	         db.execSQL("DROP TABLE IF EXISTS " + BookData.BOOKCASETABLENAME+";");//DROP TABLE IF EXISTS " + EcouponData.MEMBERCOUPONDBTABLENAME);
	         onCreate(db);
	      }
	   }
}