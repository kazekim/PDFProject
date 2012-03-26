package com.CodeGears.AuntiAnne.eCard;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.CodeGears.AuntiAnne.EcardDecorateActivity;
import com.CodeGears.AuntiAnne.MainApp;
import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.function.AnimatedSprite;
import com.CodeGears.AuntiAnne.function.NetworkThread2Listener;
import com.CodeGears.AuntiAnne.function.NetworkThreadUtil2;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

public class EcardDecorateView extends SurfaceView implements SurfaceHolder.Callback {
	
	 /*
     * Difficulty setting constants
     */
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_HARD = 1;
    public static final int DIFFICULTY_MEDIUM = 2;
    
    /*
     * State-tracking constants
     */
    public static final int STATE_LOSE = 1;
    public static final int STATE_PAUSE = 2;
    public static final int STATE_READY = 3;
    public static final int STATE_RUNNING = 4;
    public static final int STATE_WIN = 5;

        /*
         * UI constants 
         */
        private static final String KEY_DIFFICULTY = "mDifficulty";
        private static final String KEY_WINS = "mWinsInARow";
	
	/** Handle to the application context, used to e.g. fetch Drawables. */
    private Context mContext;

    /** Pointer to the text view to display "Paused.." etc. */
    private TextView mStatusText;

    /** The thread that actually draws the animation */
    private AnimationThread thread;

    private Bitmap ecard;
    private Bitmap colorBox;
    
    private float scaleHeight;
    private float scaleWidth;
    
    public static String GENERATECARD="Generate Card";
    
    public static final int COLOR1=0;
    public static final int COLOR2=1;
    
    private boolean isThreadStart=false;
    
   SurfaceHolder holder;
   
   private Context context;
   
   
   private Resources res;
   
   private ArrayList<StickerData> cardStickers;
   private AnimatedSprite cardSprite;

   private Paint textPaint;
   
   /** Number of sprites drawed in a row. */
   private int numSprites;
   
   /**
    * Current difficulty -- amount of fuel, allowed angle, etc. Default is
    * MEDIUM.
    */
   private int mDifficulty;
   
   private String choosedColor1;
   private String choosedColor2;
   
   private Rect cardBorder;
	private Paint borderPaint;
   
   
   private ArrayList<StickerData> stickerDataList;
	private CardData cardData;
    
	public EcardDecorateView(Context context, AttributeSet attrs) {
        super(context, attrs);
		
        
     // register our interest in hearing about changes to our surface
        holder = getHolder();
        holder.addCallback(this);

        this.context=context;
		
        // create thread only; it's started in surfaceCreated()
        thread = new AnimationThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
            //    mStatusText.setVisibility(m.getData().getInt("viz"));
           //      mStatusText.setText(m.getData().getString("text"));
            }
        });

        setFocusable(true); // make sure we get key events
        
        res = context.getResources();
        
        cardStickers = new ArrayList<StickerData>();
        cardSprite = new AnimatedSprite();
        
        stickerDataList = new  ArrayList<StickerData>();
        
        this.stickerDataList = InitVal.stickerDataList;
                    
        textPaint = new Paint();
        textPaint.setARGB(255,0,0,255);

        //textPaint.setTypeface(Typeface.SERIF)
        
        numSprites = 0;
        mDifficulty = DIFFICULTY_MEDIUM;
		
        colorBox = BitmapFactory.decodeResource(context.getResources(), R.drawable.colorbox);
	}
	
	  public void setCard(){
      	cardData = InitVal.cardDataList.get(InitVal.cardIndex);
      	textPaint.setTextSize(cardData.getFontSize()/2);
      	textPaint.setTypeface(InitVal.Circular);
      	//System.out.println(cardData.getFontColor());
      	textPaint.setColor(Color.parseColor("#"+cardData.getFontColor()));
         // textPaint.setTextAlign(cardData.getFontAlign());
      	
      	DisplayMetrics metrics = getResources().getDisplayMetrics();
      	
      	ecard = cardData.getBitmapPicture();
          int newWidth=(int)(metrics.density*320);
          int newHeight=(int) (metrics.density*320*ecard.getHeight()/ecard.getWidth());
      	scaleWidth = ((float) newWidth) / ecard.getWidth();
          scaleHeight = ((float) newHeight) / ecard.getHeight();

          
          Matrix matrix = new Matrix();

          matrix.postScale(scaleWidth, scaleHeight);
          
      	Bitmap temp =Bitmap.createBitmap(ecard, 0, 0,ecard.getWidth(), ecard.getHeight(), matrix, true);
      	
      	
      	cardSprite = new AnimatedSprite();
      	cardSprite.Initialize(temp, temp.getHeight(), temp.getWidth(), 24, 1, true);
          
      	cardSprite.setXPos(InitVal.screenW/2);
      	cardSprite.setYPos(temp.getHeight()*1/2);  

      	cardBorder =  new Rect(cardSprite.getXPos()-10,cardSprite.getYPos()-10
  				,cardSprite.getXPos()+temp.getWidth()+10,cardSprite.getYPos()+temp.getHeight()+10);
      	
      	choosedColor1 = cardData.getColor1();
      	choosedColor2 = cardData.getColor2();
      	
      	borderPaint = new Paint(); 
      	borderPaint.setARGB(255, 0, 0, 0); 
      	borderPaint.setStrokeWidth(3);
      	borderPaint.setColor(Color.TRANSPARENT);
      }
      
	  
	public void recycleBitmap(){
		ecard.recycle();
		colorBox.recycle();
	}
	
	public void resumeSurface(){
		surfaceDestroyed(holder);
        surfaceCreated(holder);
        
        thread = new AnimationThread(holder, context, new Handler() {
            @Override
            public void handleMessage(Message m) {
            //    mStatusText.setVisibility(m.getData().getInt("viz"));
           //      mStatusText.setText(m.getData().getString("text"));
            }
        });
	}

/*	
	 @Override
	    public boolean onTouchEvent(MotionEvent event){
	    	thread.doTouch(event);
	    	
	    	final int action = event.getAction();
	    	System.out.println("Sad " + action+" "+MotionEvent.ACTION_MOVE);
	    	return super.onTouchEvent(event);
	    }
	*/    
	 public AnimationThread getThread() {
	        return thread;
	 }
	 
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
		
        thread.setSurfaceSize(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(!isThreadStart){
			thread.setRunning(true);
			isThreadStart=true;
        	thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		 boolean retry = true;
	        thread.setRunning(false);
	        while (retry) {
	            try {
	                thread.join();
	                retry = false;
	            } catch (InterruptedException e) {
	            }
	        }
	}
	

	public class AnimationThread extends Thread implements NetworkThread2Listener{
	    	
		
	        
	        /*
	         * Member (state) fields
	         */
	        
	        /** What to draw for the Lander when it has crashed */
	        
	        
	    	private int selectedSticker=-1;
	    	
	    	
	    	
	    	 private OnEcardSubmitListener listener;
	    	
	        

	        /** Message handler used by thread to interact with TextView */
	        private Handler mHandler;

	        /** The state of the game. One of READY, RUNNING, PAUSE, LOSE, or WIN */
	        private int mMode;

	        /** Used to figure out elapsed time between frames */
	        private long mLastTime;
	        
	        /** Indicate whether the surface has been created & is ready to draw */
	        private boolean mRun = false;
	       
	        private int frameSamplesCollected = 0;
	        private int frameSampleTime = 0;
	        

	        /** Handle to the surface manager object we interact with */
	        private SurfaceHolder mSurfaceHolder;

	        
	        
	        private int fps;
	        
	        private String title="";
	        
	        
	        
	        
	        
	        public AnimationThread(SurfaceHolder surfaceHolder, Context context, Handler handler) {
	            // get handles to some important objects
	            mSurfaceHolder = surfaceHolder;
	            mHandler = handler;
	            mContext = context;

	            
	            
	        }
	        
	        public void setTitle(String title){
	        	this.title=title;
	        }
	        
	        public void setOnEcardSubmitListener(OnEcardSubmitListener listener ) {
	        	this.listener = listener;
	        }
	        
	      
	        public void setColorBitmap(String colorString,int replacedColor){
	        	ecard = setColorBitmap(ecard,cardData, colorString, replacedColor);
	        	
	        	DisplayMetrics metrics = getResources().getDisplayMetrics();
	        	int newWidth=(int)(metrics.density*320);
	            int newHeight=(int) (metrics.density*320*ecard.getHeight()/ecard.getWidth());
	        	scaleWidth = ((float) newWidth) / ecard.getWidth();
	            scaleHeight = ((float) newHeight) / ecard.getHeight();

	            
	            Matrix matrix = new Matrix();

	            matrix.postScale(scaleWidth, scaleHeight);
	            
	        	Bitmap temp =Bitmap.createBitmap(ecard, 0, 0,ecard.getWidth(), ecard.getHeight(), matrix, true);
	        	
	        	
    			cardSprite.Initialize(temp, temp.getHeight(), temp.getWidth(), 24, 1, true);
	        }
	        
	        private Bitmap setColorBitmap(Bitmap bitmap,CardData _cardData,String colorString,int replacedColor){

	        	int [] allGrid = new int [ bitmap.getHeight()*bitmap.getWidth()];
	        	int [] colorGrid = new int [ bitmap.getHeight()*bitmap.getWidth()];
        
	        	
	        	Bitmap tempBitmap = bitmap.copy(bitmap.getConfig(), true);
	        	
	        	_cardData.getBitmapPicture().getPixels(allGrid, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),bitmap.getHeight());
	        	
	        	int choosedColor;
	        	
	        	if(replacedColor == COLOR1){
	        		choosedColor = Color.parseColor("#"+_cardData.getColor1());
	        		choosedColor1 = colorString;
	        	}else{
	        		choosedColor = Color.parseColor("#"+_cardData.getColor2());
	        		choosedColor2 = colorString;
	        	}
	        	
	        	for(int i =0; i<bitmap.getHeight()*bitmap.getWidth();i++){

	        		if( allGrid[i] == choosedColor){
	        			colorGrid[i] = 1;
	        	 	}else{
	        	 		colorGrid[i] = 0;
	        	 	}
	        	 }
	        	
	        	bitmap.getPixels(allGrid, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),bitmap.getHeight());
	        	
	        	for(int i =0; i<bitmap.getHeight()*bitmap.getWidth();i++){

	        		if( colorGrid[i] == 1){
	        			allGrid[i] = Color.parseColor("#"+colorString);
	        	 	}
	        	 }
	        	
	        	
	        	
	        	
	        	  tempBitmap.setPixels(allGrid, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
	        	
	        	  return tempBitmap;
	        }
	        
	        public void setSticker(StickerData stickerData,int scale){
	        	stickerDataList.add(stickerData);
	        	
	        	 AnimatedSprite stickerSprite = new AnimatedSprite();
	        	 
	        	 Matrix matrix = new Matrix();

		            matrix.postScale(scaleWidth, scaleHeight);
		            
		            Bitmap sticker = Bitmap.createBitmap(stickerData.getBitmapPicture(), 0, 0,stickerData.getBitmapPicture().getWidth(), stickerData.getBitmapPicture().getHeight(), matrix, true);
 
	        	 stickerSprite.Initialize(sticker, sticker.getHeight(), sticker.getWidth(), 24, 1, true);
	        	 
	        	 stickerSprite.setXPos(InitVal.screenW/2);
	        	 stickerSprite.setYPos((int)(scaleHeight*ecard.getHeight()*2/3));
	        	 
	        	 StickerData stickerDataTemp = stickerData.clone();
	        	 stickerDataTemp.setSprite(stickerSprite);
	        	 cardStickers.add(stickerDataTemp);
	        }
	        
			
	        @Override
	        public void run() {
	            while (mRun) {
	                Canvas c = null;
	                try {
	                    c = mSurfaceHolder.lockCanvas(null);
	                    synchronized (mSurfaceHolder) {
	                    	
	                        if (mMode == STATE_RUNNING) {
	                        	updatePhysics();
	                        }
	                        
	                        doDraw(c);
	                    }
	                } catch(Exception e){

	                //	e.printStackTrace();
	                }finally {
	                    // do this in a finally so that if an exception is thrown
	                    // during the above, we don't leave the Surface in an
	                    // inconsistent state
	                    if (c != null) {
	                        mSurfaceHolder.unlockCanvasAndPost(c);
	                    }
	                }
	            }
	        }
	        
	        
	        /**
	         * Draws to the provided Canvas.
	         */
	        private void doDraw(Canvas canvas) {
	            // Draw the background image. Operations on the Canvas accumulate
	            // so this is like clearing the screen.
	            //canvas.drawBitmap(mBackgroundImage, 0, 0, null);
	       
	            
	        	 final RectF rectF = new RectF();
	        	  final Paint paint = new Paint();
	        	  paint.setARGB(128, 255, 255, 255);

	        	  rectF.set(0,0, getMeasuredWidth(), getMeasuredHeight());

	        	  canvas.drawRoundRect(rectF, 0, 0, paint);
	        	        	  
	        	canvas.drawRect(cardBorder,borderPaint);
	        	cardSprite.draw(canvas);
	        	
	        	for( StickerData a : cardStickers){
	        		a.getSprite().draw(canvas);
	        	} 
	        	
	        	if(title!=""){
	        		int alignSpace=title.length()*cardData.getFontSize();
	        		if(cardData.getFontAlign() ==CardData.CENTER){
	        			alignSpace /=8;
	        		}else if(cardData.getFontAlign() == CardData.RIGHT){
	        			alignSpace /=4;
	        		}else{
	        			alignSpace=0;
	        		}
	        		canvas.drawText(title, (int)(cardSprite.getXPos()+scaleWidth*ecard.getWidth()*cardData.getFontPositionX()-alignSpace)
		        			, (int)(cardSprite.getYPos()+scaleHeight*ecard.getHeight()*cardData.getFontPositionY()), textPaint);

	        	}
        	
	        	canvas.restore();       
	        }

	        
	        
	        /**
	         * Figures the lander state (x, y, fuel, ...) based on the passage of
	         * realtime. Does not invalidate(). Called at the start of draw().
	         * Detects the end-of-game and sets the UI to the next state.
	         */
	        private void updatePhysics() {
	            long now = System.currentTimeMillis();
	            
	            // Do nothing if mLastTime is in the future.
	            // This allows the game-start to delay the start of the physics
	            // by 100ms or whatever.
	            if (mLastTime > now) return;
	            
	            if (mLastTime != 0) {
	        		int time = (int) (now - mLastTime);
	        		frameSampleTime += time;
	        		frameSamplesCollected++;
	        		if (frameSamplesCollected == 10) {
		        		fps = (int) (10000 / frameSampleTime);
		        		frameSampleTime = 0;
		        		frameSamplesCollected = 0;
	        		}        		
	        	}
	            
	            
	            numSprites = cardStickers.size();
	            mLastTime = now;

	        }
	        
	        /**
	         * Detected touch event
	         * @param e
	         */
	        public void doTouch(MotionEvent event){
	        	
	        	int action = event.getAction();
	            float x = event.getX();  // or getRawX();
	            float y = event.getY();
	            
	           
	           // System.out.println(InitVal.screenW+" "+background.getWidth()/2+" "+InitVal.screenH+" "+background.getHeight()/2+" "+x+" "+y);
	            switch(action){
	            case MotionEvent.ACTION_MOVE:
	            	 if(selectedSticker>-1){

	            		 cardStickers.get(selectedSticker).getSprite().setXPos((int)x);
	            		 cardStickers.get(selectedSticker).getSprite().setYPos((int)y);
	            	 }
	            	break;
	            case MotionEvent.ACTION_DOWN:

		            	for(int i=cardStickers.size()-1;i>=0;i--){

		            		if(x>cardStickers.get(i).getSprite().getXPos() && x<cardStickers.get(i).getSprite().getXPos()+stickerDataList.get(i).getBitmapPicture().getWidth() 
		            				&& y>cardStickers.get(i).getSprite().getYPos() 
		            				&& y<cardStickers.get(i).getSprite().getYPos()+stickerDataList.get(i).getBitmapPicture().getHeight() ){
		            			
		            			selectedSticker=i;
		            		
		            			break;
		            		}
		            	}
		            	
	            	break;
	            	
	            	case MotionEvent.ACTION_UP:
	            		if(selectedSticker!=-1 && (x<cardBorder.left || x> cardBorder.right || y < cardBorder.top || y> cardBorder.bottom)){
	            			cardStickers.remove(selectedSticker);
	            		}
	            		selectedSticker=-1;
	            		
	            		break;
	            }
	            
	        }
	        
	     
	        
	        /**
	         * Sets the game mode. That is, whether we are running, paused, in the
	         * failure state, in the victory state, etc.
	         * 
	         * @param mode one of the STATE_* constants
	         * @param message string to add to screen or null
	         */
	        public void setState(int mode, CharSequence message) {
	            /*
	             * This method optionally can cause a text message to be displayed
	             * to the user when the mode changes. Since the View that actually
	             * renders that text is part of the main View hierarchy and not
	             * owned by this thread, we can't touch the state of that View.
	             * Instead we use a Message + Handler to relay commands to the main
	             * thread, which updates the user-text View.
	             */
	        	synchronized (mSurfaceHolder) {
	                mMode = mode;

	                if (mMode == STATE_RUNNING) {
	                    Message msg = mHandler.obtainMessage();
	                    Bundle b = new Bundle();
	                    b.putString("text", "");
	                    b.putInt("viz", View.INVISIBLE);
	                    msg.setData(b);
	                    mHandler.sendMessage(msg);
	                } else {
	                   
	                    Resources res = mContext.getResources();
	                    CharSequence str = "";
	                    if (mMode == STATE_READY)
	                        str = res.getText(R.string.mode_ready);
	                    else if (mMode == STATE_PAUSE)
	                        str = res.getText(R.string.mode_pause);
	                    else if (mMode == STATE_LOSE)
	                        str = res.getText(R.string.mode_lose);
	                    else if (mMode == STATE_WIN)
	                        str = res.getString(R.string.mode_win_prefix)
	                                + numSprites + " "
	                                + res.getString(R.string.mode_win_suffix);

	                    if (message != null) {
	                        str = message + "\n" + str;
	                    }

	                    if (mMode == STATE_LOSE) numSprites = 0;

	                    Message msg = mHandler.obtainMessage();
	                    Bundle b = new Bundle();
	                    b.putString("text", str.toString());
	                    b.putInt("viz", View.VISIBLE);
	                    msg.setData(b);
	                    mHandler.sendMessage(msg);
	                }
	            }
	        }
	        
	        /**
	         * Dump game state to the provided Bundle. Typically called when the
	         * Activity is being suspended.
	         * 
	         * @return Bundle with this view's state
	         */
	        public Bundle saveState(Bundle map) {
	            synchronized (mSurfaceHolder) {
	                if (map != null) {
	                    map.putInt(KEY_DIFFICULTY, Integer.valueOf(mDifficulty));                    
	                    map.putInt(KEY_WINS, Integer.valueOf(numSprites));
	                }
	            }
	            return map;
	        }

	        /**
	         * Sets the current difficulty.
	         * 
	         * @param difficulty
	         */
	        public void setDifficulty(int difficulty) {
	            synchronized (mSurfaceHolder) {
	                mDifficulty = difficulty;
	            }
	        }

	        /**
	         * Used to signal the thread whether it should be running or not.
	         * Passing true allows the thread to run; passing false will shut it
	         * down if it's already running. Calling start() after this was most
	         * recently called with false will result in an immediate shutdown.
	         * 
	         * @param b true to run, false to shut down
	         */
	        public void setRunning(boolean b) {
	            mRun = b;
	        }

	        /**
	         * Sets the game mode. That is, whether we are running, paused, in the
	         * failure state, in the victory state, etc.
	         * 
	         * @see #setState(int, CharSequence)
	         * @param mode one of the STATE_* constants
	         */
	        public void setState(int mode) {
	            synchronized (mSurfaceHolder) {
	                setState(mode, null);
	            }
	        }

	        /* Callback invoked when the surface dimensions change. */
	        public void setSurfaceSize(int width, int height) {
	            // synchronized to make sure these all change atomically
	            synchronized (mSurfaceHolder) {


	            }
	        }

	        
	        /**
	         * Starts the game, setting parameters for the current difficulty.
	         */
	        public void doStart() {
	            synchronized (mSurfaceHolder) {               
	                mLastTime = System.currentTimeMillis() + 100;
	                setState(STATE_RUNNING);
	            }
	        }

	        /**
	         * Restores game state from the indicated Bundle. Typically called when
	         * the Activity is being restored after having been previously
	         * destroyed.
	         * 
	         * @param savedState Bundle containing the game state
	         */
	        public synchronized void restoreState(Bundle savedState) {
	            synchronized (mSurfaceHolder) {
	                setState(STATE_PAUSE);         

	                mDifficulty = savedState.getInt(KEY_DIFFICULTY);               
	                numSprites = savedState.getInt(KEY_WINS);
	            }
	        }

			public void clearSprites() {
				synchronized(cardStickers){
					cardStickers.clear();			
				}
			}

			public String getTitle(){
				
				return title;
			}
			
			public void recycleBitmap(){
				for(int i=0;i<stickerDataList.size();i++){
					stickerDataList.get(i).recycle();
		    	}
				
				cardData.recycle();
			}
			
			public void submitCardToServer(){
				
				try {
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
					
					Document doc = docBuilder.newDocument();
					
				  //getTileData and change millisec to sec.
					Element generateElement = (Element) doc.createElement("generate");
					generateElement.setAttribute("name",cardData.getName());
					generateElement.setAttribute("color1",choosedColor1);
					generateElement.setAttribute("color2",choosedColor2);
					generateElement.setAttribute("text",title);
					for(StickerData stickerData:cardStickers){
						
						Element tileElement = (Element) doc.createElement("sticker");
						tileElement.setAttribute("name", stickerData.getName());
						tileElement.setAttribute("positionx", String.valueOf((double)(stickerData.getSprite().getXPos()-cardSprite.getXPos())/ecard.getWidth()));
						tileElement.setAttribute("positiony", String.valueOf((double)(stickerData.getSprite().getYPos()-cardSprite.getYPos())/ecard.getWidth()));

						generateElement.appendChild(tileElement);
					}
					
					doc.appendChild(generateElement);
					
					//--- Transform XML Document to string ----//
					TransformerFactory tf = TransformerFactory.newInstance();
					Transformer transformer = tf.newTransformer();
					transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					StringWriter writer = new StringWriter();
					transformer.transform(new DOMSource(doc), new StreamResult(writer));
					String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
					//System.out.println(output);
					//---- Send Data To Server ----//
			    NetworkThreadUtil2 nThread = new NetworkThreadUtil2(GENERATECARD);
			    nThread.setListener(this);
			    nThread.sendRawBody(MainApp.GENERATECARD_URL, output );
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
			} catch ( TransformerException e ) {
				e.printStackTrace();
			}
		}

			@Override
			public void onNetworkThreadComplete(String referenceKey,
					Document doc) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNetworkThreadComplete(String referenceKey, String raw) {
				// TODO Auto-generated method stub
				//System.out.println(raw);
				InitVal.cardURL=raw;
				
				listener.onEcardGenerateFinish();
				
			}

			@Override
			public void onNetworkThreadFail(String referenceKey) {
				// TODO Auto-generated method stub
				
			}

	}


}