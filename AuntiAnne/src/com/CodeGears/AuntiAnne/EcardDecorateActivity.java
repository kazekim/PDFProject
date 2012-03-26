package com.CodeGears.AuntiAnne;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.Util;

import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.eCard.CardData;
import com.CodeGears.AuntiAnne.eCard.ColorPickBox;
import com.CodeGears.AuntiAnne.eCard.EcardDecorateView;
import com.CodeGears.AuntiAnne.eCard.EcardDecorateView.AnimationThread;
import com.CodeGears.AuntiAnne.eCard.OnEcardSubmitListener;
import com.CodeGears.AuntiAnne.eCard.StickerData;
import com.CodeGears.AuntiAnne.layout.HeadView;
import com.CodeGears.AuntiAnne.loadimage.FileCache;
import com.CodeGears.AuntiAnne.loadimage.Utils;
import com.kazekim.customlayout.BaseRequestListener;
import com.kazekim.customlayout.OnChooseColorListener;
import com.kazekim.customlayout.PickColorButton;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.sax.Element;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

 
public class EcardDecorateActivity extends Activity  implements OnClickListener,OnTouchListener,TextWatcher,OnEcardSubmitListener,OnChooseColorListener{
	
	private ArrayList<StickerData> stickerDataList;
	private ArrayList<CardData> cardDataList;
	
	private ArrayList<ImageButton> stickerButtons;
	
	private AnimationThread mAnimationThread;
	private EcardDecorateView mAnimationView;
	
	private FrameLayout headLayout;
	private HeadView headView;
	
	private LinearLayout ecardStickerLayout;	
	
	private EditText cardTextView;
	private Button shareEmailButton;
	private Button shareFacebookButton;
	private FileCache fileCache;
	
	private ImageButton pickColorButton1;
	private ImageButton pickColorButton2;
	
	private ProgressBar progressBar;
	//private PickColorButton pickColorBoxLayout1;
	
	private PickColorButton pickColorBox1;
	
	private int scale=1;
	
	private int shareType;
	private static final int SHAREEMAIL=0;
	private static final int SHAREFACEBOOK=1;
	
	private int changeColor;

	private Facebook facebook;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecarddecorateactivity);

        headView = new HeadView(this);
        headLayout = (FrameLayout) findViewById(R.id.headview);
        headLayout.addView(headView);
      
        ecardStickerLayout = (LinearLayout) findViewById(R.id.ecardstickerlayout);
        cardTextView = (EditText) findViewById(R.id.ecardtext);
        shareEmailButton = (Button) findViewById(R.id.shareemailbutton);
        shareFacebookButton = (Button) findViewById(R.id.sharefacebookbutton);
        
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        pickColorBox1 = (PickColorButton) findViewById(R.id.pickcolorbox);
        
        this.stickerDataList = InitVal.stickerDataList;
        this.cardDataList = InitVal.cardDataList;
        
        fileCache=new FileCache(this); 
        
        pickColorBox1.setBackgroundDrawable(getResources().getDrawable(R.drawable.box_colourssss_1b));
        
        pickColorBox1.addColor("A066AA");
        pickColorBox1.addColor("A788BE");
        pickColorBox1.addColor("F06793");
        pickColorBox1.addColor("F599B1");
        pickColorBox1.addColor("F1B94A");
        pickColorBox1.addColor("FFCE71");
        pickColorBox1.addColor("F2ED72");
    	pickColorBox1.addColor("C9DC5D");
    	pickColorBox1.addColor("B1D355");
    	pickColorBox1.addColor("63C29D");
    	pickColorBox1.addColor("5EC4B6");
    	pickColorBox1.addColor("5E9DB6");
    	pickColorBox1.addColor("7A98C9");
    	pickColorBox1.addColor("95C5E0");
    	pickColorBox1.addColor("BED7E3");

    	
    	pickColorButton1 = (ImageButton) findViewById(R.id.colorpickbutton1);
    	pickColorButton2 = (ImageButton) findViewById(R.id.colorpickbutton2);
    	
    	
       
        mAnimationView = (EcardDecorateView) findViewById(R.id.aview);
        mAnimationThread = mAnimationView.getThread();
        
        getCardImage();
        getStickerButton();

        
        headView.setHeadText("E CARD");
        
        headView.setListener(this);
        
        
        mAnimationView.setOnTouchListener(this);
      //  mAnimationThread.setState(AnimationThread.STATE_READY);

        
       cardTextView.addTextChangedListener(this);
       shareEmailButton.setOnClickListener(this);
       shareFacebookButton.setOnClickListener(this);
       mAnimationThread.setOnEcardSubmitListener(this);
       pickColorButton1.setOnClickListener(this);
       pickColorButton2.setOnClickListener(this);
       pickColorBox1.setOnChooseColorListener(this);
       
    }
 
    @Override
	public void onClick(View v) {

			if(headView.backButtonisClick()){
				recycleBitmap();
				this.finish();
				
				if(InitVal.curPage == InitVal.MEMBERACTIVITY){
					InitVal.curPage=InitVal.ECARD;
				}
				
				InitVal.newsListPage=-1;
			}else if(headView.homeButtonisClick()){
				Intent intent = new Intent( EcardDecorateActivity.this, MainActivity.class );
		        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		        this.startActivity( intent );
			
			}else if(shareEmailButton.isPressed()){
				mAnimationView.setVisibility(View.INVISIBLE);
				progressBar.setVisibility(View.VISIBLE);
				shareType = SHAREEMAIL;
				mAnimationThread.submitCardToServer();
				//mAnimationView.setVisibility(View.VISIBLE);
			}else if(shareFacebookButton.isPressed()){
				progressBar.setVisibility(View.VISIBLE);
				
				shareType=SHAREFACEBOOK;
				mAnimationThread.submitCardToServer();
			}
			else if(pickColorButton1.isPressed()){
				
				if(pickColorBox1.getVisibility()==View.INVISIBLE){
					changeColor=EcardDecorateView.COLOR1;
					forceShowPickColorBox();
					RelativeLayout.LayoutParams layoutParams = 
					    (RelativeLayout.LayoutParams)pickColorBox1.getLayoutParams();
					layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
					pickColorBox1.setLayoutParams(layoutParams);

				}else{
					pickColorBox1.setVisibility(View.INVISIBLE);
					pickColorBox1.setClickable(false);
				}
			}
			else if(pickColorButton2.isPressed()){
				
				if(pickColorBox1.getVisibility()==View.INVISIBLE){
					changeColor=EcardDecorateView.COLOR2;
					forceShowPickColorBox();
					RelativeLayout.LayoutParams layoutParams = 
					    (RelativeLayout.LayoutParams)pickColorBox1.getLayoutParams();
					layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
					pickColorBox1.setLayoutParams(layoutParams);

				}else{
					pickColorBox1.setVisibility(View.INVISIBLE);
					pickColorBox1.setClickable(false);
				}
			}else{
			
				
				if(stickerButtons.size()>0){
					for(int i=0;i<stickerButtons.size();i++){
						if(v==stickerButtons.get(i)){
							mAnimationThread.setSticker(stickerDataList.get(i),scale);
							return;
						}
					}
				}
				
			}
			
	}
    
    @Override 
    public void onResume() { 
        super.onResume(); 
       
        mAnimationView.resumeSurface();
        forceShowPickColorBox();
        pickColorBox1.setVisibility(View.INVISIBLE);
		pickColorBox1.setClickable(false);
    } 
    
    public void forceShowPickColorBox(){
    	InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // only will trigger it if no physical keyboard is open
        //mgr.showSoftInput(cardTextView, InputMethodManager.SHOW_IMPLICIT);
        pickColorBox1.setVisibility(View.VISIBLE);
        pickColorBox1.setClickable(true);
        mgr.hideSoftInputFromWindow(cardTextView.getWindowToken(), 0);

    }
   
    private void getCardImage(){
    	CardData cardData = cardDataList.get(InitVal.cardIndex);
    	String url = cardData.getPictureURL();
    	File f=fileCache.getFile(url);
        
        //from SD cache
        Bitmap bitmap = decodeFile(f,true);
        if(bitmap!=null){
        	cardDataList.get(InitVal.cardIndex).setBitmapPicture(bitmap);
        	 mAnimationView.setCard();
        	 
        }else{
        //from web
            try {
                URL imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                InputStream is=conn.getInputStream();
                OutputStream os = new FileOutputStream(f);
                Utils.CopyStream(is, os);
                os.close();
                bitmap = decodeFile(f,true);
                cardDataList.get(InitVal.cardIndex).setBitmapPicture(bitmap);

                mAnimationView.setCard();
            } catch (Exception ex){
               ex.printStackTrace();
            }
        }
        
        pickColorButton1.setBackgroundColor(Color.parseColor("#"+cardData.getColor1()));
        pickColorButton2.setBackgroundColor(Color.parseColor("#"+cardData.getColor2()));
    }
    
    private void getStickerButton(){
    	
    	stickerButtons = new ArrayList<ImageButton>();
    	DisplayMetrics metrics = this.getResources().getDisplayMetrics();
    	
    	for(int i=0;i<stickerDataList.size();i++){
    		StickerData stickerData = stickerDataList.get(i);
    		
    		//Load Sticker Bitmap
    		String url = stickerData.getPictureURL();
        	File f=fileCache.getFile(url);
            
            //from SD cache
            Bitmap b = decodeFile(f,false);
            if(b!=null){
            	stickerData.setBitmapPicture(b);
            	mAnimationView.setCard();

            }else{
            //from web
                try {
                    Bitmap bitmap=null;
                    URL imageUrl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
                    conn.setConnectTimeout(30000);
                    conn.setReadTimeout(30000);
                    InputStream is=conn.getInputStream();
                    OutputStream os = new FileOutputStream(f);
                    Utils.CopyStream(is, os);
                    os.close();
                    bitmap = decodeFile(f,false);
                    stickerData.setBitmapPicture(bitmap);
                    
                    mAnimationView.setCard();
                } catch (Exception ex){
                	stickerData.setBitmapPicture(BitmapFactory.decodeResource(getResources(),
                            R.drawable.icon));
                   ex.printStackTrace();
                }
            }
            	
            	FrameLayout frame = new FrameLayout(this);
            	frame.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    			
                ImageButton button = new ImageButton(this);
                
                button.setLayoutParams(new ViewGroup.LayoutParams((int)(75*metrics.density), (int)(63*metrics.density)));
    			
    			button.setBackgroundResource(R.drawable.boxsticker);
    			
    			Matrix matrix = new Matrix();

	            matrix.postScale(50*metrics.density/stickerData.getBitmapPicture().getHeight()
	            		, 50*metrics.density/stickerData.getBitmapPicture().getHeight());
	            
	            Bitmap sticker = Bitmap.createBitmap(stickerData.getBitmapPicture(), 0, 0
	            		,stickerData.getBitmapPicture().getWidth()
	            		, stickerData.getBitmapPicture().getHeight(), matrix, true);

	            
    			button.setImageBitmap(sticker);
    			
    			stickerButtons.add(button);
    			
    			frame.addView(button);
    			
    		/*	ImageView image = new ImageView(this);
    			
    			image.setBackgroundDrawable(new BitmapDrawable(stickerData.getBitmapPicture()));
    			
    			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
    					(int)(50*metrics.density/stickerData.getBitmapPicture().getHeight()*stickerData.getBitmapPicture().getWidth())
    					, (int)(50*metrics.density));
    				params.gravity=Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL;
    				image.setLayoutParams(params);
    				
    				frame.addView(image);*/
    				
    				FrameLayout.LayoutParams paramFrame = new FrameLayout.LayoutParams(
        				    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,Gravity.CENTER_VERTICAL);
    				frame.setLayoutParams(paramFrame);
    				
    			ecardStickerLayout.addView(frame);
    			
    			button.setOnClickListener(this);
    	}
    }
    
    /*
    public void viewCard(){
    	
    	
    	Intent myIntent;
		myIntent = new Intent(EcardDecorateActivity.this,
				DetailsActivity.class);

			
		startActivityForResult(myIntent,1);
		overridePendingTransition (R.anim.fadeout, R.anim.fadein);
    }
    */
    
    private void shareCard(){
    	//progressBar.setVisibility(View.GONE);
    	if(shareType == SHAREEMAIL){
    		shareOnEmail();
    	}else if(shareType == SHAREFACEBOOK){
    		shareOnFacebook();
    	}
    	
    	
    	recycleBitmap();
    	finish();
    }
    
    private void recycleBitmap(){
    	for(int i=0;i<cardDataList.size();i++){
    		cardDataList.get(i).recycle();
    	}
    	
    	for(int i=0;i<stickerDataList.size();i++){
    		stickerDataList.get(i).recycle();
    	}
    	
    	mAnimationThread.recycleBitmap();
    	mAnimationView.recycleBitmap();
    }
    
    private File downloadPicture(String urlString){
    	try {
    	    File rootSdDirectory = Environment.getExternalStorageDirectory();

    	    File pictureFile = new File(rootSdDirectory, "ecard.jpg");
    	    if (pictureFile.exists()) {
    	        pictureFile.delete();
    	    }                   
    	    pictureFile.createNewFile();

    	    FileOutputStream fos = new FileOutputStream(pictureFile);

    	    URL url = new URL(urlString);
    	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    	    connection.setRequestMethod("GET");
    	    connection.setDoOutput(true);
    	    connection.connect();
    	    InputStream in = connection.getInputStream();

    	    byte[] buffer = new byte[1024];
    	    int size = 0;
    	    while ((size = in.read(buffer)) > 0) {
    	        fos.write(buffer, 0, size);
    	    }
    	    fos.close();
    	    return pictureFile;
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    return null;
    	}
    }
    
    private void shareOnEmail(){
    	
    	File pictureFile = downloadPicture(InitVal.cardURL);
    	Uri pictureUri = Uri.fromFile(pictureFile);
    	
    	Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/jpg");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, "me@gmail.com");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Aunite Anne's E-Card"
                    );
        sendIntent.putExtra(Intent.EXTRA_STREAM, pictureUri);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mAnimationThread.getTitle());

        startActivity(Intent.createChooser(sendIntent, "Email:"));
    }
    
    private void shareOnFacebook() {
    	
    	File pictureFile = downloadPicture(InitVal.cardURL);
    	
    //	Uri pictureUri = Uri.fromFile(pictureFile);
    	facebook = new Facebook(InitVal.FACEBOOK_APPID);
    	
    	facebook.authorize(EcardDecorateActivity.this, new String[]{ "user_photos,publish_checkins,publish_actions,publish_stream"},new DialogListener() {
            @Override
            public void onComplete(Bundle values) {
            	//postImageonWall();


            }

            @Override
            public void onFacebookError(FacebookError error) {
            }

            @Override
            public void onError(DialogError e) {
            }

            @Override
            public void onCancel() {
            }
        });
    	facebook.authorize(EcardDecorateActivity.this, new String[]{ "user_photos,publish_checkins,publish_actions,publish_stream"},new DialogListener() {
            @Override
            public void onComplete(Bundle values) {

            }

            @Override
            public void onFacebookError(FacebookError error) {
            }

            @Override
            public void onError(DialogError e) {
            }

            @Override
            public void onCancel() {
            }
        });
 
    	byte[] data = null;

        Bitmap bi = BitmapFactory.decodeFile("/sdcard/ecard.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bi.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        data = baos.toByteArray();
        
        Bundle params = new Bundle();
       // System.out.println("dsadasd "+facebook.getAccessToken());
        //params.putString(Facebook.TOKEN, facebook.getAccessToken());
        params.putString("method", "photos.upload");
        params.putByteArray("picture", data);

        AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
        mAsyncRunner.request(null, params, "POST", new SampleUploadListener(), null);
/*
    	byte[] data = null;
    	 try {
    	     FileInputStream fis = new FileInputStream("/sdcard/ecard.jpg");
    	     Bitmap bi = BitmapFactory.decodeStream(fis);
    	     ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	     bi.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    	     data = baos.toByteArray();  
    	  } catch (FileNotFoundException e) { 
    	     e.printStackTrace();
    	     Log.d("onCreate", "debug error  e = " + e.toString());
    	  }     
    	  Bundle param = new Bundle();
    	  param.putString("message", "picture caption");
    	  param.putByteArray("picture", data);
    	  

    	     Facebook facebook = new Facebook(InitVal.FACEBOOK_APPID);
    	     AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
    	     mAsyncRunner.request("me/photos", param, "POST", new SampleUploadListener());
    	  /*   mAsyncRunner.request(null, params, "POST", new RequestListener() {

    	        public void onMalformedURLException(MalformedURLException e, Object state) {
    	            Log.d("request RequestListener", "debug onMalformedURLException");
    	        }

    	        public void onIOException(IOException e, Object state) {
    	            Log.d("request RequestListener", "debug onIOException");
    	        }

    	        public void onFileNotFoundException(FileNotFoundException e, Object state) {
    	            Log.d("request RequestListener", "debug onFileNotFoundException");
    	        }

    	        public void onFacebookError(FacebookError e, Object state) {
    	            Log.d("request RequestListener", "debug onFacebookError");
    	        }

    	        public void onComplete(String response, Object state) {
    	             Log.d("request RequestListener", "debug onComplete");
    	        }
    	     }, null);

	  /*  Uri uri = Uri.parse("http://m.facebook.com/sharer.php?u=" +
	            InitVal.cardURL + "&t=Auntie+Annes+ECard+"+mAnimationThread.getTitle().replaceAll(" ","+"));
	    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	    startActivity(intent);
	    */
	}
    
    public void postImageonWall() {

        byte[] data = null;

          Bitmap bi = BitmapFactory.decodeFile("/sdcard/ecard.jpg");
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          bi.compress(Bitmap.CompressFormat.JPEG, 100, baos);
          data = baos.toByteArray();


          Bundle params = new Bundle();
          params.putString(Facebook.TOKEN, facebook.getAccessToken());
          params.putString("method", "photos.upload");
          params.putByteArray("picture", data);

          AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
          mAsyncRunner.request(null, params, "POST", new SampleUploadListener(), null);


 }
    
    
  //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f,boolean getSize){
        try {

            BitmapFactory.Options o2 = new BitmapFactory.Options();
       
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        catch (ArithmeticException e) {
        	e.printStackTrace();
		}
        return null;
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		mAnimationThread.doTouch(event);

		return true;
	}
	
	
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		mAnimationThread.setTitle(cardTextView.getText().toString());
		/*	int count = 140 - cardTextView.length();
		textCount.setText(Integer.toString(count));
		textCount.setTextColor(Color.GREEN);
		if (count <10)
			textCount.setTextColor(Color.YELLOW);
		if (count < 0)
			textCount.setTextColor(Color.RED);
			*/
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    // Checks whether a hardware keyboard is available
    if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
    	mAnimationView.setVisibility(View.VISIBLE);
    }else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO){
    	mAnimationView.setVisibility(View.INVISIBLE);
    }
}

	@Override
	public void onEcardGenerateFinish() {
		shareCard();
	}

	@Override
	public void onChooseColor(PickColorButton v,String colorString) {
		
		mAnimationThread.setColorBitmap(colorString, changeColor);
		pickColorBox1.setVisibility(View.INVISIBLE);
		pickColorBox1.setClickable(false);
		if(changeColor== EcardDecorateView.COLOR1){
			pickColorButton1.setBackgroundColor(Color.parseColor("#"+colorString));
		}else{
	        pickColorButton2.setBackgroundColor(Color.parseColor("#"+colorString));
		}
	}
    
	public class SampleUploadListener extends BaseRequestListener {

	    public void onComplete(final String response, final Object state) {
	        try {
	            // process the response here: (executed in background thread)
	            Log.d("Facebook-Example", "Response: " + response.toString());
	            JSONObject json = Util.parseJson(response);
	            final String src = json.getString("src");

	            // then post the processed result back to the UI thread
	            // if we do not do this, an runtime exception will be generated
	            // e.g. "CalledFromWrongThreadException: Only the original
	            // thread that created a view hierarchy can touch its views."

	        } catch (JSONException e) {
	            Log.w("Facebook-Example", "JSON Error in response");
	        } catch (FacebookError e) {
	            Log.w("Facebook-Example", "Facebook Error: " + e.getMessage());
	        }
	    }

	    @Override
	    public void onFacebookError(FacebookError e, Object state) {
	        // TODO Auto-generated method stub

	    }
	}
}