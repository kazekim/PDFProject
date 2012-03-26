package th.co.tnt.FlipbookSoft;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;

import com.kazekim.ModPageView.GenerateBitmapView;
import com.kazekim.ModPageView.ModPageView;
import com.kazekim.bookcase.BookData;
import com.kazekim.bookcase.BookcaseDatabaseHelper;
import com.kazekim.loadimage.PicUtil;

import cx.hell.android.lib.pagesview.PagesView;
import cx.hell.android.lib.pdf.PDF;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GenerateBitmapActivity  extends Activity{

	private GenerateBitmapActivity activity;

	private final static String TAG = "com.kazekim";
	
	private static BookcaseDatabaseHelper dh;
	private static String dbname;
	private static String insertQuery;
	
	private int box = 2;
	
	private GenerateBitmapView pagesView;
	
	private PDF pdf = null;
	private PDFPagesProvider pdfPagesProvider = null;
	
	private String filePath = "/";
	
	private RelativeLayout activityLayout = null;
	
	private int colorMode = Options.COLOR_MODE_NORMAL;
	
	private String filename;
	private String title;
	
	private Runnable pageRunnable = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Options.setOrientation(this);
		SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(this);
        
        activity = this;
        
        
        Bundle b = getIntent().getExtras();
        
        filename = b.getString("filename");
        title = b.getString("title");
        
        if(title==null){
			title=filename;
		}
        
        this.box = Integer.parseInt(options.getString(Options.PREF_BOX, "2"));
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// Get display metrics
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
        activityLayout = new RelativeLayout(this);

        dbname=BookData.BOOKCASETABLENAME;
		insertQuery = BookData.INSERTQUERY;
		
		dh = new BookcaseDatabaseHelper(this);
		dh.setInsertStatement(insertQuery);

		
		
        pagesView = new GenerateBitmapView(activity);
        activityLayout.addView(pagesView);
        
	  	
	  	
	  	
	  	
	  	 //dh.insert(file.get,couponData.getPictureURL(),couponData.getCouponDescript());
	  	startPDF(options);
        if (!this.pdf.isValid()) {
        	finish();
        }
        
       // String pathImageName = generateBookCoverThumb(name[0], file);
       // System.out.println("Path: "+pathImageName);
        
        this.setContentView(activityLayout);
        
     // send keyboard events to this view
        pagesView.setFocusable(true);
        pagesView.setFocusableInTouchMode(true);
        
        
	}
	
	public void saveBitmap(Bitmap bitmap){
		
		File file = new File(filename);
		String[] name= file.getName().split("\\.");
		
		//String savePath=PicUtil.getSavePath().getAbsolutePath()+"/"+name[0]+".png";
		String savePath=name[0]+".png";

		//String pathName = PicUtil.saveToFile(savePath, bitmap);
		String pathName = PicUtil.saveToPrivateFile(this, savePath, bitmap);
		

		long status = dh.insert(title, filename, pathName);
		dh.closeDB();
	//	System.out.println("Path Name : "+pathName+" status "+status);
	/*	
		if(status == 3){
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.duplicatedata)
			       .setCancelable(false)
			       .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   Intent intent = new Intent( GenerateBitmapActivity.this, BookcaseActivity.class );
					        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
					        startActivity( intent );
			           }
			       });
			AlertDialog alert = builder.create();
        	alert.show();
        	return;
		}else if(status == 1){
			Intent intent = new Intent( GenerateBitmapActivity.this, BookcaseActivity.class );
	        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
	        startActivity( intent );
			
		}else{
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.addbookerror)
			       .setCancelable(false)
			       .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   Intent intent = new Intent( GenerateBitmapActivity.this, BookcaseActivity.class );
					        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
					        startActivity( intent );
			           }
			       });
			AlertDialog alert = builder.create();
        	alert.show();
        	return;
		}
	*/	
		Intent intent = new Intent( GenerateBitmapActivity.this, BookcaseActivity.class );
        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity( intent );
	}
	
	  private PDF getPDF() {
	        final Intent intent = getIntent();
			Uri uri = intent.getData();    	
			filePath = uri.getPath();
			if (uri.getScheme().equals("file")) {
				return new PDF(new File(filePath), this.box);
	    	} else if (uri.getScheme().equals("content")) {
	    		ContentResolver cr = this.getContentResolver();
	    		FileDescriptor fileDescriptor;
				try {
					fileDescriptor = cr.openFileDescriptor(uri, "r").getFileDescriptor();
				} catch (FileNotFoundException e) {
					throw new RuntimeException(e); // TODO: handle errors
				}
	    		return new PDF(fileDescriptor, this.box);
	    	} else {
	    		throw new RuntimeException("don't know how to get filename from " + uri);
	    	}
	    }
	
	  private void startPDF(SharedPreferences options) {
		    this.pdf = this.getPDF();
		    if (!this.pdf.isValid()) {
		    	Log.v(TAG, "Invalid PDF");
		    	if (this.pdf.isInvalidPassword()) {
		    		Toast.makeText(this, "This file needs a password", 4000).show();
		    	}
		    	else {
		    		Toast.makeText(this, "Invalid PDF file", 4000).show();
		    	}
		    	return;
		    }
		    this.colorMode = Options.getColorMode(options);
		    this.pdfPagesProvider = new PDFPagesProvider(this, pdf,        		
		    		Options.isGray(this.colorMode), 
		    		options.getBoolean(Options.PREF_OMIT_IMAGES, false),
		    		options.getBoolean(Options.PREF_RENDER_AHEAD, true));
		    pagesView.setPagesProvider(pdfPagesProvider);

	    }
}
