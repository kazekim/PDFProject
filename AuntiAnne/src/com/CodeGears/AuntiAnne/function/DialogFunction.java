package com.CodeGears.AuntiAnne.function;

import android.app.AlertDialog;
import android.content.Context;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;

public class DialogFunction {
	public static void showAlertDialog(Context context,String valueString){
		AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
		 alertbox.setMessage(valueString);
		 alertbox.setNeutralButton(R.string.yes, null);
		 alertbox.show();
	}
	
	public static void showAlertDialog(Context context,int valueID){
		AlertDialog.Builder alertbox = new AlertDialog.Builder(context);
		 alertbox.setMessage(valueID);
		 alertbox.setNeutralButton(R.string.yes, null);
		 alertbox.show();
	}
	
	public static void showConnectionErrorDialog(Context context){
		showAlertDialog(context,R.string.connectionerror);
	}
	
	public static void showDataErrorDialog(Context context){
		showAlertDialog(context,R.string.dataerror);
	}
	
	public static void showLoginFailDialog(Context context){
		showAlertDialog(context,R.string.loginfail);
	}
	
	public static void showNoDataDialog(Context context){
		showAlertDialog(context,R.string.nodata);
	}
	
	public static void showError(Context context,int status){
		switch(status){
			case InitVal.CONNECTIONERROR: 
				showConnectionErrorDialog(context);break;
			case InitVal.DATAERROR: 
				showDataErrorDialog(context);break;
			case InitVal.FAIL: 
				showLoginFailDialog(context);break;
			case InitVal.NODATA:
				showNoDataDialog(context);break;
				
		}
	}
}
