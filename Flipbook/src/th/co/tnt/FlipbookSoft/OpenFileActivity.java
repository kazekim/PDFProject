 package th.co.tnt.FlipbookSoft;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.PorterDuffXfermode;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cx.hell.android.lib.pagesview.FindResult;
import cx.hell.android.lib.pagesview.PagesView;
import cx.hell.android.lib.pdf.PDF;

// #ifdef pro
import java.util.Stack;
import java.util.Map;

import com.kazekim.Math.PointCal;
import com.kazekim.ModPageView.ModPageView;
import com.kazekim.ModPageView.PageViewListener;
import com.kazekim.bookcase.BookcaseCell;
import com.kazekim.bookmark.BookmarkCell;
import com.kazekim.bookmark.BookmarkListListener;
import com.kazekim.brush.Brush;
import com.kazekim.brush.CircleBrush;
import com.kazekim.brush.PenBrush;
import com.kazekim.brush.SquareBrush;
import com.kazekim.drawing.DrawingPath;
import com.kazekim.drawing.DrawingSurface;

import th.co.tnt.FlipbookSoft.R;

import android.content.DialogInterface.OnDismissListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;
import android.text.method.ScrollingMovementMethod;
import cx.hell.android.lib.pdf.PDF.Outline;
import cx.hell.android.lib.view.TreeView;
// #endif




/**
 * Document display activity.
 */
@SuppressWarnings("unused")
public class OpenFileActivity extends Activity implements SensorEventListener,OnTouchListener,OnItemClickListener,BookmarkListListener,PageViewListener {
	
	private final static String TAG = "cx.hell.android.pdfviewpro";
	
	private final static int[] menuAnimations = {
		R.anim.menu_disappear, R.anim.menu_almost_disappear, R.anim.menu
	};
	
	private final static int[] drawAnimations = {
		R.anim.menu_disappear, R.anim.menu_almost_disappear, R.anim.menu
	};
	
	private final static int[] pageNumberAnimations = {
		R.anim.page_disappear, R.anim.page_almost_disappear, R.anim.page, 
		R.anim.page_show_always
	};
	
	private PDF pdf = null;
	private ModPageView pagesView = null;
	private ImageView pdfPageView;
	
	private static final int DRAWTOPLEFTLAYOUTID=2001;
	private static final int DRAWTOOLLAYOUTID=2002;
	private static final int PENLAYOUTID=2003;
	private static final int HIGHLIGHTLAYOUTID=2004;
	private static final int SHAPELAYOUTID=2005;
	private static final int COLORLAYOUTID=2006;
	private static final int ERASORLAYOUTID=2007;
	private static final int PENBALLOONID=2008;
	private static final int HIGHLIGHTBALLOONID=2009;
	private static final int SHAPEBALLOONID=2010;
	private static final int COLORBALLOONID=2011;
	private static final int ERASORBALLOONID=2012;
	private static final int SELECTSQUAREBUTTONID=2013;
	private static final int SELECTCIRCLEBUTTONID=2014;
	private static final int SELECTTRIANGLEBUTTONID=2015;
	private static final int SELECTCOLORBLACKBUTTONID=2016;
	private static final int SELECTCOLORREDBUTTONID=2017;
	private static final int SELECTCOLORBLUEBUTTONID=2018;
	private static final int SELECTCOLORGREENBUTTONID=2019;
	private static final int SELECTCOLORPINKBUTTONID=2020;
	private static final int SELECTCOLORYELLOWBUTTONID=2021;
	private static final int SELECTCOLORWHITEBUTTONID=2022;
	private static final int TOPLEFTLAYOUTID=2023;
	private static final int DRAWPENBUTTONID=2024;
	private static final int DRAWHIGHLIGHTBUTTONID=2025;
	private static final int DRAWSHAPEBUTTONID=2025;
	private static final int COLORBUTTONID=2025;
	private static final int ERASORBUTTONID=2025;
	
	
	public static final String CLEAR="00";
	public static final String ALPHA1="DD";
	public static final String ALPHA2="88";
	public static final String OPAQUE="FF";
	public static final String BLACK="000000";
	public static final String RED="C2272D";
	public static final String BLUE="0070BB";
	public static final String GREEN="8CC63E";
	public static final String PINK="FF7BAC";
	public static final String YELLOW="EBBF3A";
	public static final String WHITE="FFFFFF";

	
	private int drawMode;
	private int colorMode;
	private int pdfMode;
	private int shapeSelected=CIRCLEMODE;
	
	public static final int NORMALMODE=0;
	public static final int PENMODE=1;
	public static final int HIGHLIGHTMODE=2;
	public static final int CIRCLEMODE=3;
	public static final int SQUAREMODE=4;
	public static final int TRIANGLEMODE=5;
	public static final int ERASORMODE=6;
	
	public static final int BLACKCOLOR=0;
	public static final int REDCOLOR=1;
	public static final int BLUECOLOR=2;
	public static final int GREENCOLOR=3;
	public static final int PINKCOLOR=4;
	public static final int YELLOWCOLOR=5;
	public static final int WHITECOLOR=6;

	
	public static final int READMODE=0;
	public static final int DRAWMODE=1;
	public static final int BOOKMARKMODE=2;
	
	
	private String curColor=BLACK;
	private int penSize=3;
	private int highLightSize=5;
	private int erasorSize=10;
	
	private int distanceZoom;
	
	//PDF Page Matrix
	 Matrix matrix = new Matrix();
	   Matrix savedMatrix = new Matrix();
	   
	// We can be in one of these 3 states
	   static final int NONE = 0;
	   static final int DRAG = 1;
	   static final int ZOOM = 2;
	   static final int SWIPE =3;
	   int mode = NONE;
	   
	   private PointF mid;
	   private PointF start;
	   private float oldDist=0.0f;
	   
	   private int swipeDirection=0;
	   private static final int NOSWIPE=0;
	   private static final int SWIPELEFT=1;
	   private static final int SWIPERIGHT=2;
	
	   private boolean isZoomIn=false;
	   private boolean isZoomOut=false;
	   
	   private boolean isBookmarked=false;
	   
	 //  private OrientationEventListener myOrientationEventListener;

// #ifdef pro
	
	private View textReflowView = null;

	private ScrollView textReflowScrollView = null;
	
	private TextView textReflowTextView = null;

// #endif

	private PDFPagesProvider pdfPagesProvider = null;
	private Actions actions = null;
	
	private Handler menuHandler = null;
	private Handler drawHandler = null;
	private Handler pageHandler = null;
	private Runnable zoomRunnable = null;
	private Runnable pageRunnable = null;
	
	private MenuItem aboutMenuItem = null;
	private MenuItem gotoPageMenuItem = null;
	private MenuItem findTextMenuItem = null;
	private MenuItem clearFindTextMenuItem = null;
	private MenuItem chooseFileMenuItem = null;
	private MenuItem optionsMenuItem = null;
	// #ifdef pro
	private MenuItem tableOfContentsMenuItem = null;
	private MenuItem textReflowMenuItem = null;
	// #endif
	
	private EditText pageNumberInputField = null;
	private EditText findTextInputField = null;
	
	private LinearLayout findButtonsLayout = null;
	private Button findPrevButton = null;
	private Button findNextButton = null;
	private Button findHideButton = null;
	
	private RelativeLayout activityLayout = null;
	private boolean eink = false;	

	// currently opened file path
	private String filePath = "/";
	
	private String findText = null;
	private Integer currentFindResultPage = null;
	private Integer currentFindResultNumber = null;

	// zoom buttons, layout and fade animation
	//private ImageButton zoomDownButton;
	//private ImageButton zoomWidthButton;
	private Button contrastButton;
	private Button bookmarkButton;
	private Button drawModeButton;
	private Button readModeButton;
	private Button bookCaseModeButton;
	private Button showDrawButton;
	private Button bookmarkListButton;
	private Button rotateLeftButton;
	private Button rotateRightButton;
	//private ImageButton zoomUpButton;
	private Button browseButton;
	private Button drawPenButton;
	private Button drawHighlightButton;
	private Button drawShapeBUtton;
	private Button drawColorButton;
	private Button drawErasorButton;
	private RelativeLayout penBalloonLayout;
	private RelativeLayout highlightBalloonLayout;
	private RelativeLayout shapeBalloonLayout;
	private RelativeLayout colorBalloonLayout;
	private RelativeLayout erasorBalloonLayout;
	private RelativeLayout bookmarkCenterLayout;
	private Animation menuAnim;
	private Animation drawAnim;
	private RelativeLayout menuLayout;
	private RelativeLayout drawLayout;
	private RelativeLayout bookmarkLayout;
	private LinearLayout penLayout;
	private LinearLayout highlightLayout;
	private LinearLayout shapeLayout;
	private LinearLayout colorLayout;
	private LinearLayout erasorLayout;
	private Button drawPenScrollButton;
	private Button drawHighlightScrollButton;
	private Button drawErasorScrollButton;
	
	private Button selectCircleButton;
	private Button selectSquareButton;
	private Button selectTriangleButton;
	private Button selectBlackColorButton;
	private Button selectRedColorButton;
	private Button selectBlueColorButton;
	private Button selectGreenColorButton;
	private Button selectPinkColorButton;
	private Button selectYellowColorButton;
	private Button selectWhiteColorButton;
	private Button clearDrawButton;
	
	private TextView penSizeText;
	private TextView highlightSizeText;
	private ImageView shapeImage;
	private ImageView colorImage;
	private TextView erasorSizeText;
	private ImageView shapeSelectedView;
	private ImageView colorSelectedView;
	private TextView penSizeView;
	private TextView highlightSizeView;
	private TextView erazorSizeView;
	
	private ListView bookMarkListView;
	private BookmarkCell bookmarkListAdaptor;

	// page number display
	private TextView pageNumberTextView;
	private Animation pageNumberAnim;
	
	private int box = 2;

	public boolean showMenuOnScroll = false;
	
	private int fadeStartOffset = 7000; 
	
	private SensorManager sensorManager;
	private static final int ZOOM_COLOR_NORMAL = 0;
	private static final int ZOOM_COLOR_RED = 1;
	private static final int ZOOM_COLOR_GREEN = 2;
	private static final int[] menuButtonId = {R.drawable.menudrawingbutton , R.drawable.menudebookmarkbutton
		, R.drawable.menubookmarkbutton , R.drawable.menushowdrawbutton,R.drawable.menuhidedrawbutton,R.drawable.menubookcasebutton,R.drawable.menubrightnessbutton,R.drawable.menubookmarklistbutton};
	private static final int[] drawButtonId = {R.drawable.menubookbutton , R.drawable.menubrowsebutton
		, R.drawable.menurotateleft , R.drawable.menurotateright,R.drawable.drawbar,R.drawable.drawpenbutton
		, R.drawable.drawhighlightbutton,R.drawable.drawshapebutton,R.drawable.drawcolorbutton
		,R.drawable.drawerasorbutton,R.drawable.drawscalebar,R.drawable.drawshapeballoon,R.drawable.drawcolorballoon
		,R.drawable.drawerasorscalebar,R.drawable.drawscrollbutton,R.drawable.drawbinbutton};
	private static final int[] drawShapeId ={R.drawable.drawshapesquare,R.drawable.drawshapecircle,R.drawable.drawshapetriangle};
	private static final int[] drawColorId ={R.drawable.pickblackcolor,R.drawable.pickredcolor,R.drawable.pickbluecolor
		,R.drawable.pickgreencolor,R.drawable.pickpinkcolor,R.drawable.pickyellowcolor,R.drawable.pickwhitecolor};
	
	private static final int[] zoomUpId = {
		R.drawable.btn_zoom_up, R.drawable.red_btn_zoom_up, R.drawable.green_btn_zoom_up
	};
	private static final int[] zoomDownId = {
		R.drawable.btn_zoom_down, R.drawable.red_btn_zoom_down, R.drawable.green_btn_zoom_down		
	};
	private static final int[] zoomWidthId = {
		R.drawable.btn_zoom_width, R.drawable.red_btn_zoom_width, R.drawable.green_btn_zoom_width		
	};
	
	private static final int MENUBUTTONWIDTH=103;
	private static final int MENUBUTTONHEIGHT=104;
	private static final int DRAWBUTTONWIDTH=83;
	private static final int DRAWBUTTONHEIGHT=73;
	private static final int SHAPEBUTTONWIDTH=31;
	private static final int SHAPEBUTTONHEIGHT=31;
	private static final int COLORBUTTONWIDTH=37;
	private static final int COLORBUTTONHEIGHT=36;
	private static final int SCROLLBUTTONWIDTH=19;
	private static final int SCROLLBUTTONHEIGHT=19;
	private static final int BOOKMARKWITH=412;
	private static final int BOOKMARKHEIGHT=536;
	
	private float[] gravity = { 0f, -9.81f, 0f};

	//public static int prevOrientation;

	private boolean history = true;
	
	private DrawingSurface drawingSurface;
	private Matrix surfaceMatrix;
	//private Paint currentPaint;
	private Paint highlightPaint;
	 private DrawingPath currentDrawingPath;
	 
	   private Brush currentBrush;
	
	
// #ifdef pro
	/**
	 * If true, then current activity is in text reflow mode.
	 */
	private boolean textReflowMode = false;
// #endif

	/**
     * Called when the activity is first created.
     * TODO: initialize dialog fast, then move file loading to other thread
     * TODO: add progress bar for file load
     * TODO: add progress icon for file rendering
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		Options.setOrientation(this);
		SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(this);
		
		 mid = new PointF();
	      start = new PointF();

		//this.box = Integer.parseInt(options.getString(Options.PREF_BOX, "2"));
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Get display metrics
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
        // use a relative layout to stack the views
        activityLayout = new RelativeLayout(this);
        
        // the PDF view
        this.pagesView = new ModPageView(this);
        pagesView.setLoadNewPage();
        activityLayout.addView(pagesView);
        
        
        
        drawingSurface = new DrawingSurface(this, null);
		 LinearLayout.LayoutParams dlp = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
       drawingSurface.setLayoutParams(dlp);
       activityLayout.addView(drawingSurface);
       
       surfaceMatrix = new Matrix();

		drawingSurface.setOnTouchListener(this);
       drawingSurface.previewPath = new DrawingPath();
       drawingSurface.previewPath.path = new Path();
       drawingSurface.previewPath.paint = getPreviewPaint();
       drawingSurface.setZOrderOnTop(true); 
       drawingSurface.getHolder().setFormat(PixelFormat.TRANSPARENT);

       currentBrush = new PenBrush();
      
       
        
        pdfPageView = new ImageView(this);
        RelativeLayout.LayoutParams pdfParam = new RelativeLayout.LayoutParams(
        		LayoutParams.FILL_PARENT, 
        		LayoutParams.FILL_PARENT);
        pdfPageView.setLayoutParams(pdfParam);
        pdfPageView.setScaleType(ScaleType.MATRIX);
        pdfPageView.setOnTouchListener(this);
        
    /*    Matrix matrix = new Matrix();
        matrix.postTranslate(metrics.widthPixels/2, metrics.heightPixels/2);
        pdfPageView.setImageMatrix(matrix);*/
        activityLayout.addView(pdfPageView);
        
        
        
        startPDF(options);
        if (!this.pdf.isValid()) {
        	finish();
        }
        
// #ifdef pro

     /*   LinearLayout textReflowLayout = new LinearLayout(this);
        this.textReflowView = textReflowLayout;
        textReflowLayout.setOrientation(LinearLayout.VERTICAL);
        
        this.textReflowScrollView = new ScrollView(this);
        this.textReflowScrollView.setFillViewport(true);
        
        this.textReflowTextView = new TextView(this);
        
        LinearLayout textReflowButtonsLayout = new LinearLayout(this);
        textReflowButtonsLayout.setGravity(Gravity.CENTER);
        textReflowButtonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        Button textReflowPrevPageButton = new Button(this);
        textReflowPrevPageButton.setText("Prev");
        textReflowPrevPageButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OpenFileActivity.this.nextPage(-1);
			}
        });
        Button textReflowNextPageButton = new Button(this);
        textReflowNextPageButton.setText("Next");
        textReflowNextPageButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		OpenFileActivity.this.nextPage(1);
        	}
        });
        textReflowButtonsLayout.addView(textReflowPrevPageButton);
        textReflowButtonsLayout.addView(textReflowNextPageButton);

        this.textReflowScrollView.addView(this.textReflowTextView);
        LinearLayout.LayoutParams textReflowScrollViewLayoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 1);
        textReflowLayout.addView(this.textReflowScrollView, textReflowScrollViewLayoutParams);
        textReflowLayout.addView(textReflowButtonsLayout, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, 0));

        activityLayout.addView(this.textReflowView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
        this.textReflowView.setVisibility(View.GONE);
        AndroidReflections.setScrollbarFadingEnabled(this.textReflowView, true);
// #endif
        
        // the find buttons
        this.findButtonsLayout = new LinearLayout(this);
        this.findButtonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.findButtonsLayout.setVisibility(View.GONE);
        this.findButtonsLayout.setGravity(Gravity.CENTER);
        this.findPrevButton = new Button(this);
        this.findPrevButton.setText("Prev");
        this.findButtonsLayout.addView(this.findPrevButton);
        this.findNextButton = new Button(this);
        this.findNextButton.setText("Next");
        this.findButtonsLayout.addView(this.findNextButton);
        this.findHideButton = new Button(this);
        this.findHideButton.setText("Hide");
        this.findButtonsLayout.addView(this.findHideButton);
        this.setFindButtonHandlers();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        activityLayout.addView(this.findButtonsLayout, lp);
*/
        RelativeLayout.LayoutParams lp;
        this.pageNumberTextView = new TextView(this);
        this.pageNumberTextView.setTextSize(8f*metrics.density);
        lp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        activityLayout.addView(this.pageNumberTextView, lp);
        
		// display this
        this.setContentView(activityLayout);
        
        // go to last viewed page
//        gotoLastPage();
        
        // send keyboard events to this view
        pagesView.setFocusable(true);
        pagesView.setFocusableInTouchMode(true);

        this.menuHandler = new Handler();
        this.drawHandler = new Handler();
        this.pageHandler = new Handler();
        this.zoomRunnable = new Runnable() {
        	public void run() {
        		//fadeZoom();
        	}
        };
        this.pageRunnable = new Runnable() {
        	public void run() {
        		fadePage();
        		
        	}
        };
        
     /*   myOrientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL){

         @Override
         public void onOrientationChanged(int arg0) {
          // TODO Auto-generated method stub
        	 if(arg0<100){
        		 setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        		 pagesView.fitPage();
        	 }else if(arg0>200){
        		 setOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        		 pagesView.fitPage();
        	 }
         }};
         
           if (myOrientationEventListener.canDetectOrientation()){
         //   Toast.makeText(this, "Can DetectOrientation", Toast.LENGTH_LONG).show();
            myOrientationEventListener.enable();
           }
           else{
         //   Toast.makeText(this, "Can't DetectOrientation", Toast.LENGTH_LONG).show();
            finish();
           }
         */
         pagesView.setPageViewListener(this);
       
    }

    private Paint generateErasorPaint(){
    	Paint paint = new Paint();         
    	paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR)); 
    	paint.setColor(Color.TRANSPARENT);
    	//paint.setMaskFilter(new BlurMaskFilter(erasorSize, Blur.NORMAL));
    	paint.setStrokeWidth(erasorSize);
    	return paint;
    }
    
    private Paint generatePenPaint(){
        Paint paint = new Paint();
        paint.setDither(true);
        paint.setColor(Color.parseColor("#"+OPAQUE+curColor));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(penSize);
        paint.setMaskFilter(new BlurMaskFilter(penSize, Blur.NORMAL));

        return paint;
    }
    
    private Paint generateHighlightPaint(){
    	Paint paint = new Paint();
    	paint.setDither(true);
    	paint.setColor(Color.parseColor("#"+ALPHA1+curColor));
    	paint.setStyle(Paint.Style.STROKE);
    	paint.setStrokeJoin(Paint.Join.ROUND);
    	paint.setStrokeCap(Paint.Cap.ROUND);
    	paint.setStrokeWidth(highLightSize);
    	paint.setMaskFilter(new BlurMaskFilter(highLightSize, Blur.NORMAL));
    	return paint;
    }
    
    private void changeColor(String color){
    	/*
    	currentPaint.setColor(Color.parseColor("#"+OPAQUE+color));
    	*/
    	this.curColor=color;
    }

    private void changePenSize(int penSize){
    	//currentPaint.setStrokeWidth(lineStroke);
    	this.penSize= penSize;
    }
    
    private Paint getPreviewPaint(){
       Paint previewPaint;
        
        if(drawMode==HIGHLIGHTMODE){
        	previewPaint=generateHighlightPaint();
        }else if(drawMode==ERASORMODE){
        	previewPaint=generateErasorPaint();
        }else{
        	previewPaint= generatePenPaint();
        }
        return previewPaint;
    }
    

    public void resetMatrix(int width, int height) {

    	DisplayMetrics metrics = new DisplayMetrics();
    	getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	matrix.reset();
    	savedMatrix.reset();
    		if(metrics.widthPixels<metrics.heightPixels){
            	matrix.postScale(metrics.widthPixels,height/metrics.widthPixels);
            }else{
            	matrix.postTranslate(metrics.widthPixels/2-((float)(metrics.heightPixels*width/height))/2
            			, metrics.heightPixels/2-metrics.heightPixels/2);
            	matrix.postScale(((float)metrics.heightPixels/(float)height),((float)metrics.heightPixels/(float)height));
            }
    		savedMatrix.set(matrix);
    		pdfPageView.setImageMatrix(matrix);
	}
	/** 
	 * Save the current page before exiting
	 */
	@Override
	protected void onPause() {
		super.onPause();

		saveLastPage();
		
		if (sensorManager != null) {
			sensorManager.unregisterListener(this);
			sensorManager = null;
	//		SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(this).edit();
	//		edit.putInt(Options.PREF_PREV_ORIENTATION, prevOrientation);
	//		edit.commit();
		}		
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		sensorManager = null;
		
		SharedPreferences options = PreferenceManager.getDefaultSharedPreferences(this);

		if (Options.setOrientation(this)) {
			sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
			if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0) {
				gravity[0] = 0f;
				gravity[1] = -9.81f;
				gravity[2] = 0f;
				sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
						SensorManager.SENSOR_DELAY_NORMAL);
	//			this.prevOrientation = options.getInt(Options.PREF_PREV_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			else {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		}
		
		history  = options.getBoolean(Options.PREF_HISTORY, true);
		boolean eink = options.getBoolean(Options.PREF_EINK, false);
		this.pagesView.setEink(eink);
		if (eink)
    		this.setTheme(android.R.style.Theme_Light);
		this.pagesView.setNook2(options.getBoolean(Options.PREF_NOOK2, false));
		
		if (options.getBoolean(Options.PREF_KEEP_ON, false))
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		else
			this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
		actions = new Actions(options);
		this.pagesView.setActions(actions);

		setMenuLayout(options);
		setDrawLayout(options);
		setBookmarkLayout(options);
		
		this.showMenuOnScroll = options.getBoolean(Options.PREF_SHOW_MENU_ON_SCROLL, false);
		this.pagesView.setSideMargins(
				Integer.parseInt(options.getString(Options.PREF_SIDE_MARGINS, "0")));
		this.pagesView.setTopMargin(
				Integer.parseInt(options.getString(Options.PREF_TOP_MARGIN, "0")));

		this.pagesView.setDoubleTap(Integer.parseInt(options.getString(Options.PREF_DOUBLE_TAP, 
				""+Options.DOUBLE_TAP_ZOOM_IN_OUT)));
		
		int newBox = Integer.parseInt(options.getString(Options.PREF_BOX, "2"));
		if (this.box != newBox) {
			saveLastPage();
			this.box = newBox;
	        startPDF(options);
	        this.pagesView.goToBookmark();
		}

        this.colorMode = Options.getColorMode(options);
        this.eink = options.getBoolean(Options.PREF_EINK, false);
        this.pageNumberTextView.setBackgroundColor(Options.getBackColor(colorMode));
        this.pageNumberTextView.setTextColor(Options.getForeColor(colorMode));
        this.pdfPagesProvider.setGray(Options.isGray(this.colorMode));
        this.pdfPagesProvider.setExtraCache(1024*1024*Options.getIntFromString(options, Options.PREF_EXTRA_CACHE, 0));
        this.pdfPagesProvider.setOmitImages(options.getBoolean(Options.PREF_OMIT_IMAGES, false));
		this.pagesView.setColorMode(this.colorMode);		
		
		this.pdfPagesProvider.setRenderAhead(options.getBoolean(Options.PREF_RENDER_AHEAD, true));
		this.pagesView.setVerticalScrollLock(options.getBoolean(Options.PREF_VERTICAL_SCROLL_LOCK, false));
		this.pagesView.invalidate();
int menuAnimNumber = Integer.parseInt(options.getString(Options.PREF_MENU_ANIMATION, "2"));
		
		if (menuAnimNumber == Options.MENU_BUTTONS_DISABLED)
			menuAnim = null;
		else 
			menuAnim = AnimationUtils.loadAnimation(this,
				menuAnimations[menuAnimNumber]);	
		int drawAnimNumber = Integer.parseInt(options.getString(Options.PREF_DRAW_ANIMATION, "0"));
		
		if (menuAnimNumber == Options.DRAW_BUTTONS_DISABLED)
			drawAnim = null;
		else 
			drawAnim = AnimationUtils.loadAnimation(this,
				drawAnimations[drawAnimNumber]);	
		int pageNumberAnimNumber = Integer.parseInt(options.getString(Options.PREF_PAGE_ANIMATION, "3"));
		
		if (pageNumberAnimNumber == Options.PAGE_NUMBER_DISABLED)
			pageNumberAnim = null;
		else 
			pageNumberAnim = AnimationUtils.loadAnimation(this,
				pageNumberAnimations[pageNumberAnimNumber]);		

		fadeStartOffset = 1000 * Integer.parseInt(options.getString(Options.PREF_FADE_SPEED, "7"));
		
		if (options.getBoolean(Options.PREF_FULLSCREEN, false))
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		else
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.pageNumberTextView.setVisibility(pageNumberAnim == null ? View.GONE : View.VISIBLE);
// #ifdef pro
		//this.zoomLayout.setVisibility((zoomAnim == null || this.textReflowMode) ? View.GONE : View.VISIBLE);
// #endif
		
// #ifdef lite
// 		this.zoomLayout.setVisibility(zoomAnim == null ? View.GONE : View.VISIBLE);
// #endif
        
        showAnimated(true);
	}

    /**
     * Set handlers on findNextButton and findHideButton.
     */
    private void setFindButtonHandlers() {
    	this.findPrevButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//cleanPagePDF();
				OpenFileActivity.this.findPrev();
			}
    	});
    	this.findNextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//cleanPagePDF();
				OpenFileActivity.this.findNext();
			}
    	});
    	this.findHideButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				OpenFileActivity.this.findHide();
			}
    	});
    }
    
    /**
     * Set handlers on draw level buttons
     */
    private void setDrawButtonHandlers() {
    	final ModPageView tempPagesView = pagesView;
    	
    	this.readModeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawLayout.setVisibility(View.GONE);
				menuLayout.setVisibility(View.VISIBLE);
				pdfMode=READMODE;
				drawMode=NORMALMODE;
			}
    	});
    	
    	this.rotateLeftButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//tempPagesView.rotate(-1);
				drawingSurface.undo();
                if( drawingSurface.hasMoreUndo() == false ){
                    rotateLeftButton.setEnabled( false );
                }
                rotateRightButton.setEnabled( true );
				drawMode=NORMALMODE;
			}
    	});

    	this.rotateRightButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//tempPagesView.rotate(1);
				drawingSurface.redo();
                if( drawingSurface.hasMoreRedo() == false ){
                    rotateRightButton.setEnabled( false );
                }

                rotateLeftButton.setEnabled( true );
				drawMode=NORMALMODE;
			}
    	});

    	this.browseButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(OpenFileActivity.this, AddBookActivity.class));
			}
    	});
    	
    	this.drawPenButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawMode=PENMODE;
				pdfMode=DRAWMODE;
				penBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.drawPenButton.setOnLongClickListener(new View.OnLongClickListener() {

			public boolean onLongClick(View v) {
			//	penBalloonLayout.setVisibility(View.VISIBLE);
				return true;
			}
    	});
    	
    	this.drawHighlightButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawMode=HIGHLIGHTMODE;
				pdfMode=DRAWMODE;
				highlightBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.drawHighlightButton.setOnLongClickListener(new View.OnLongClickListener() {

			public boolean onLongClick(View v) {
			//	highlightBalloonLayout.setVisibility(View.VISIBLE);
				return true;
			}
    	});
    	
    	this.drawShapeBUtton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawMode=shapeSelected;
				pdfMode=DRAWMODE;
				shapeBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.selectCircleButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawMode=CIRCLEMODE;
				pdfMode=DRAWMODE;
				shapeBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.selectSquareButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawMode=SQUAREMODE;
				pdfMode=DRAWMODE;
				shapeBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.selectTriangleButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawMode=TRIANGLEMODE;
				pdfMode=DRAWMODE;
				shapeBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.drawShapeBUtton.setOnLongClickListener(new View.OnLongClickListener() {

			public boolean onLongClick(View v) {
				System.out.println("sssssss");
				shapeBalloonLayout.setVisibility(View.VISIBLE);
				return true;
			}
    	});
    	
    	this.drawColorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(colorBalloonLayout.getVisibility()==View.GONE){
					colorBalloonLayout.setVisibility(View.VISIBLE);
				}else{
					colorBalloonLayout.setVisibility(View.GONE);
				}
			}
    	});
    	
    	this.drawColorButton.setOnLongClickListener(new View.OnLongClickListener() {

			public boolean onLongClick(View v) {
				colorBalloonLayout.setVisibility(View.VISIBLE);
				return true;
			}
    	});
    	
    	this.selectBlackColorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeColor(BLACK);
				colorImage.setBackgroundDrawable(getResources().getDrawable(drawColorId[0]));
				colorBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.selectRedColorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeColor(RED);
				colorImage.setBackgroundDrawable(getResources().getDrawable(drawColorId[1]));
				colorBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.selectBlueColorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeColor(BLUE);
				colorImage.setBackgroundDrawable(getResources().getDrawable(drawColorId[2]));
				colorBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	
    	
    	this.selectGreenColorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeColor(GREEN);
				colorImage.setBackgroundDrawable(getResources().getDrawable(drawColorId[3]));
				colorBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.selectPinkColorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeColor(PINK);
				colorImage.setBackgroundDrawable(getResources().getDrawable(drawColorId[4]));
				colorBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.selectYellowColorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeColor(YELLOW);
				colorImage.setBackgroundDrawable(getResources().getDrawable(drawColorId[5]));
				colorBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.selectWhiteColorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				changeColor(WHITE);
				colorImage.setBackgroundDrawable(getResources().getDrawable(drawColorId[6]));
				colorBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.drawErasorButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawMode=ERASORMODE;
				pdfMode=DRAWMODE;
				erasorBalloonLayout.setVisibility(View.GONE);
			}
    	});
    	
    	this.drawErasorButton.setOnLongClickListener(new View.OnLongClickListener() {

			public boolean onLongClick(View v) {
				erasorBalloonLayout.setVisibility(View.VISIBLE);
				return true;
			}
    	});
    	
    	this.clearDrawButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawingSurface.clearDraw();
				
				erasorBalloonLayout.setVisibility(View.GONE);
			}
    	});
    }
    
    /**
     * Set handlers on draw level buttons
     */
    private void setMenuButtonHandlers() {
    	final ModPageView tempPagesView = pagesView;
  
    	this.drawModeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				menuLayout.setVisibility(View.GONE);
				drawLayout.setVisibility(View.VISIBLE);
				pdfMode=DRAWMODE;
				drawMode=NORMALMODE;
			}
    	});

    	this.bookCaseModeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawingSurface.setVisibility(View.GONE);
				drawingSurface.hide();
				startActivity(new Intent(OpenFileActivity.this, BookcaseActivity.class));
			}
    	});
    	this.bookmarkButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Bookmark b = new Bookmark(getApplicationContext()).open();
				BookmarkEntry entry = pagesView.toBookmarkEntry();
				if(!isBookmarked){
					b.addBookmark(filePath, entry);
					bookmarkButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[2]));
					isBookmarked=true;
				}else{
					b.deleteBookmark(filePath, entry);
					bookmarkButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[1]));
					isBookmarked=false;
				}
				b.close();
			}
    	});
    	this.bookmarkListButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				loadBookmarkToListView();
				
				bookmarkLayout.setVisibility(View.VISIBLE);
				menuLayout.setClickable(false);
				pdfMode = BOOKMARKMODE;
			}
    	});
    	
    	this.showDrawButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(!drawingSurface.isDrawing){
					drawingSurface.setVisibility(View.VISIBLE);
					drawingSurface.show();
					showDrawButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[3]));
				}else{
					drawingSurface.setVisibility(View.GONE);
					drawingSurface.hide();
					showDrawButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[4]));
				}
			}
    	});
    	this.contrastButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
			}
    	});
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
	    Bookmark b = new Bookmark(this.getApplicationContext()).open();
	    pagesView.setStartBookmark(b, filePath);
	    b.close();
    }

    /**
     * Return PDF instance wrapping file referenced by Intent.
     * Currently reads all bytes to memory, in future local files
     * should be passed to native code and remote ones should
     * be downloaded to local tmp dir.
     * @return PDF instance
     */
    private PDF getPDF() {
        final Intent intent = getIntent();
		Uri uri = intent.getData();    	
		filePath = uri.getPath();
		if (uri.getScheme().equals("file")) {
			if (history) {
				Recent recent = new Recent(this);
				recent.add(0, filePath);
				recent.commit();
			}
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
    

// #ifdef pro
    /**
     * Handle keys.
     * Handles back key by switching off text reflow mode if enabled.
     * @param keyCode key pressed
     * @param event key press event
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    	    if (this.textReflowMode) {
    	    	this.setTextReflowMode(false);
    	    	return true; /* meaning we've handled event */
    	    }
    	}
    	return super.onKeyDown(keyCode, event);
    }
// #endif
    
    
    /**
     * Handle menu.
     * @param menuItem selected menu item
     * @return true if menu item was handled
     */
  /*  @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
    	if (menuItem == this.aboutMenuItem) {
			Intent intent = new Intent();
			intent.setClass(this, AboutPDFViewActivity.class);
			this.startActivity(intent);
    		return true;
    	} else if (menuItem == this.gotoPageMenuItem) {
    		this.showGotoPageDialog();
    	} else if (menuItem == this.findTextMenuItem) {
    		this.showFindDialog();
    	} else if (menuItem == this.clearFindTextMenuItem) {
    		this.clearFind();
    	} else if (menuItem == this.chooseFileMenuItem) {
    		startActivity(new Intent(this, ChooseFileActivity.class));
    	} else if (menuItem == this.optionsMenuItem) {
    		startActivity(new Intent(this, Options.class));
    	// #ifdef pro
		} else if (menuItem == this.tableOfContentsMenuItem) {
			Outline outline = this.pdf.getOutline();
			if (outline != null) {
				this.showTableOfContentsDialog(outline);
			} else {
				Toast.makeText(this, "Table of Contents not found", Toast.LENGTH_SHORT).show();
			}
		} else if (menuItem == this.textReflowMenuItem) {
			this.setTextReflowMode(! this.textReflowMode);

		// #endif
		}
    	return false;
    }*/
    
 /*   private void setOrientation(int orientation) {
    	if (orientation != this.prevOrientation) {
    		setRequestedOrientation(orientation);
    		this.prevOrientation = orientation;

    	}
    }
    
 */

	/**
     * Intercept touch events to handle the zoom buttons animation
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
    	int action = event.getAction();
    	if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
	    	showPageNumber(true);
    		if (showMenuOnScroll) {
		    	showMenu();
	    	}
    	}
		return super.dispatchTouchEvent(event);    	
    };
    
    public boolean dispatchKeyEvent(KeyEvent event) {
    	int action = event.getAction();
    	if (action == KeyEvent.ACTION_UP || action == KeyEvent.ACTION_DOWN) {
    		if (!eink)
    			showAnimated(false);
    	}
		return super.dispatchKeyEvent(event);    	
    };
   
    
    public void showMenu() {
    	// #ifdef pro
    	    	if (this.textReflowMode) {
    	    		//zoomLayout.setVisibility(View.GONE);
    	    		return;
    	    	}
    	// #endif
    	    	if (menuAnim == null) {
    	    	//	zoomLayout.setVisibility(View.GONE);
    	    		return;
    	    	}
    	    	
    	    	//zoomLayout.clearAnimation();
    	    	menuLayout.setVisibility(View.VISIBLE);
    	    	menuHandler.removeCallbacks(zoomRunnable);
    	    	menuHandler.postDelayed(zoomRunnable, fadeStartOffset);
    	    }
    
    private void fadeMenu() {
    	// #ifdef pro
    	    	if (this.textReflowMode) {
    	    		this.menuLayout.setVisibility(View.GONE);
    	    		return;
    	    	}
    	// #endif
    	    	if (eink || menuAnim == null) {
    	    		menuLayout.setVisibility(View.GONE);
    	    	}
    	    	else {
    	    		menuAnim.setStartOffset(0);
    	    		menuAnim.setFillAfter(true);
    	    		menuLayout.startAnimation(menuAnim);
    	    	}
    	    }
    
    public void showDraw() {
    	// #ifdef pro
    	    	if (this.textReflowMode) {
    	    		//zoomLayout.setVisibility(View.GONE);
    	    		return;
    	    	}
    	// #endif
    	    	if (drawAnim == null) {
    	    	//	zoomLayout.setVisibility(View.GONE);
    	    		return;
    	    	}
    	    	
    	    	//zoomLayout.clearAnimation();
    	    	drawLayout.setVisibility(View.VISIBLE);
    	    	drawHandler.removeCallbacks(zoomRunnable);
    	    	drawHandler.postDelayed(zoomRunnable, fadeStartOffset);
    	    }
    
    private void fadeDraw() {
    	// #ifdef pro
    	    	if (this.textReflowMode) {
    	    		this.menuLayout.setVisibility(View.GONE);
    	    		return;
    	    	}
    	// #endif
    	    	if (eink || drawAnim == null) {
    	    		menuLayout.setVisibility(View.GONE);
    	    	}
    	    	else {
    	    		drawAnim.setStartOffset(0);
    	    		drawAnim.setFillAfter(true);
    	    		drawLayout.startAnimation(drawAnim);
    	    	}
    	    }
    
    public void showPageNumber(boolean force) {
    	if (pageNumberAnim == null) {
    		pageNumberTextView.setVisibility(View.GONE);
    		return;
    	}
    	
    	pageNumberTextView.setVisibility(View.VISIBLE);
    	String newText = ""+(this.pagesView.getCurrentPage()+1)+"/"+
				this.pdfPagesProvider.getPageCount();
    	
    	if (!force && newText.equals(pageNumberTextView.getText()))
    		return;
    	
		pageNumberTextView.setText(newText);
    	pageNumberTextView.clearAnimation();

    	pageHandler.removeCallbacks(pageRunnable);
    	pageHandler.postDelayed(pageRunnable, fadeStartOffset);
    }
    
    private void fadePage() {
    	if (eink || pageNumberAnim == null) {
    		pageNumberTextView.setVisibility(View.GONE);
    	}
    	else {
    		pageNumberAnim.setStartOffset(0);
    		pageNumberAnim.setFillAfter(true);
    		pageNumberTextView.startAnimation(pageNumberAnim);
    	}
    }    
    
    /**
     * Show zoom buttons and page number
     */
    public void showAnimated(boolean alsoZoom) {
    	if (alsoZoom)
    		showMenu();
    	showPageNumber(true);
    }
    
    /**
     * Hide the find buttons
     */
    private void clearFind() {
		this.currentFindResultPage = null;
		this.currentFindResultNumber = null;
    	this.pagesView.setFindMode(false);
		this.findButtonsLayout.setVisibility(View.GONE);
    }
    
    /**
     * Show error message to user.
     * @param message message to show
     */
    private void errorMessage(String message) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	AlertDialog dialog = builder.setMessage(message).setTitle("Error").create();
    	dialog.show();
    }
    
    /**
     * Called from menu when user want to go to specific page.
     */
   /* private void showGotoPageDialog() {
    	final Dialog d = new Dialog(this);
    	d.setTitle(R.string.goto_page_dialog_title);
    	LinearLayout contents = new LinearLayout(this);
    	contents.setOrientation(LinearLayout.VERTICAL);
    	TextView label = new TextView(this);
    	final int pagecount = this.pdfPagesProvider.getPageCount();
    	label.setText("Page number from " + 1 + " to " + pagecount);
    	this.pageNumberInputField = new EditText(this);
    	this.pageNumberInputField.setInputType(InputType.TYPE_CLASS_NUMBER);
    	this.pageNumberInputField.setText("" + (this.pagesView.getCurrentPage() + 1));
    	Button goButton = new Button(this);
    	goButton.setText(R.string.goto_page_go_button);
    	goButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int pageNumber = -1;
				try {
					pageNumber = Integer.parseInt(OpenFileActivity.this.pageNumberInputField.getText().toString())-1;
				} catch (NumberFormatException e) {
					// ignore //
				}
				d.dismiss();
				if (pageNumber >= 0 && pageNumber < pagecount) {
					OpenFileActivity.this.gotoPage(pageNumber);

				} else {
					OpenFileActivity.this.errorMessage("Invalid page number");
				}
			}
    	});
    	Button page1Button = new Button(this);
    	page1Button.setText(getResources().getString(R.string.page) +" 1");
    	page1Button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				d.dismiss();
				OpenFileActivity.this.gotoPage(0);
			}
    	});
    	Button lastPageButton = new Button(this);
    	lastPageButton.setText(getResources().getString(R.string.page) +" "+pagecount);
    	lastPageButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				d.dismiss();
				OpenFileActivity.this.gotoPage(pagecount-1);
			}
    	});
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    	params.leftMargin = 5;
    	params.rightMargin = 5;
    	params.bottomMargin = 2;
    	params.topMargin = 2;
    	contents.addView(label, params);
    	contents.addView(pageNumberInputField, params);
    	contents.addView(goButton, params);
    	contents.addView(page1Button, params);
    	contents.addView(lastPageButton, params);
    	d.setContentView(contents);
    	d.show();
    }
    
    private void gotoPage(int page) {
    	Log.i(TAG, "rewind to page " + page);
    	if (this.pagesView != null) {
    		this.pagesView.scrollToPage(page);
            showAnimated(true);
    	}
    }*/
    
   /**
     * Save the last page in the bookmarks
     */
    private void saveLastPage() {
    	BookmarkEntry entry = this.pagesView.toBookmarkEntry();
        Bookmark b = new Bookmark(this.getApplicationContext()).open();
        b.setLast(filePath, entry);
        b.close();
        Log.i(TAG, "last page saved for "+filePath);    
    }
    
    /**
     * 
     * Create options menu, called by Android system.
     * @param menu menu to populate
     * @return true meaning that menu was populated
     */
 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	
    	this.gotoPageMenuItem = menu.add(R.string.goto_page);
    	this.clearFindTextMenuItem = menu.add(R.string.clear_find_text);
    	this.chooseFileMenuItem = menu.add(R.string.choose_file);
    	this.optionsMenuItem = menu.add(R.string.options);
    	// The following appear on the second page.  The find item can safely be kept
    	// there since it can also be accessed from the search key on most devices.
    	 
    	
    	// #ifdef pro
    	this.tableOfContentsMenuItem = menu.add(R.string.table_of_contents);
    	this.textReflowMenuItem = menu.add(R.string.text_reflow);
    	// #endif
		this.findTextMenuItem = menu.add(R.string.find_text);
    	this.aboutMenuItem = menu.add(R.string.about);
    	return true;
    }*/
        
    /**
     * Prepare menu contents.
     * Hide or show "Clear find results" menu item depending on whether
     * we're in find mode.
     * @param menu menu that should be prepared
     */
  /*  @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
    	super.onPrepareOptionsMenu(menu);
    	this.clearFindTextMenuItem.setVisible(this.pagesView.getFindMode());
    	return true;
    }*/
    
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      Log.i(TAG, "onConfigurationChanged(" + newConfig + ")");
    }
    
    /**
     * Show find dialog.
     * Very pretty UI code ;)
     */
    public void showFindDialog() {
    	Log.d(TAG, "find dialog...");
    	final Dialog dialog = new Dialog(this);
    	dialog.setTitle(R.string.find_dialog_title);
    	LinearLayout contents = new LinearLayout(this);
    	contents.setOrientation(LinearLayout.VERTICAL);
    	this.findTextInputField = new EditText(this);
    	this.findTextInputField.setWidth(this.pagesView.getWidth() * 80 / 100);
    	Button goButton = new Button(this);
    	goButton.setText(R.string.find_go_button);
    	goButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String text = OpenFileActivity.this.findTextInputField.getText().toString();
				OpenFileActivity.this.findText(text);
				dialog.dismiss();
			}
    	});
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    	params.leftMargin = 5;
    	params.rightMargin = 5;
    	params.bottomMargin = 2;
    	params.topMargin = 2;
    	contents.addView(findTextInputField, params);
    	contents.addView(goButton, params);
    	dialog.setContentView(contents);
    	dialog.show();
    }
    
    private void setMenuLayout(SharedPreferences options) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
        int colorMode = Options.getColorMode(options);
        int mode = ZOOM_COLOR_NORMAL;
        
        if (colorMode == Options.COLOR_MODE_GREEN_ON_BLACK) {
        	mode = ZOOM_COLOR_GREEN;
        }
        else if (colorMode == Options.COLOR_MODE_RED_ON_BLACK) {
        	mode = ZOOM_COLOR_RED;
        }

        // the zoom buttons
    	if (menuLayout != null) {
    		activityLayout.removeView(menuLayout);
    	}
    	
    	menuLayout = new RelativeLayout(this);
    	
    	LinearLayout topLeftLayout = new LinearLayout(this);
    	topLeftLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams tllp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        tllp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tllp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        menuLayout.addView(topLeftLayout,tllp);
     
		drawModeButton = new Button(this);		
		drawModeButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[0]));
		//zoomLayout.addView(drawButton, (int)(48 * metrics.density), (int)(48 * metrics.density));
		
        RelativeLayout.LayoutParams dlp = new RelativeLayout.LayoutParams(
        		(int)(MENUBUTTONWIDTH * metrics.density), 
        		(int)(MENUBUTTONHEIGHT * metrics.density));
        topLeftLayout.addView(drawModeButton,dlp);
        
        LinearLayout topRightLayout = new LinearLayout(this);
        topRightLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams trlp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        trlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        trlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        menuLayout.addView(topRightLayout,trlp);

        bookmarkButton = new Button(this);		
        bookmarkButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[1]));
		
        RelativeLayout.LayoutParams blp = new RelativeLayout.LayoutParams(
        		(int)(MENUBUTTONWIDTH * metrics.density), 
        		(int)(MENUBUTTONHEIGHT * metrics.density));

        //topRightLayout.addView(bookmarkButton,blp);
        
        showDrawButton = new Button(this);		
        showDrawButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[3]));
		
        RelativeLayout.LayoutParams sdlp = new RelativeLayout.LayoutParams(
        		(int)(MENUBUTTONWIDTH * metrics.density), 
        		(int)(MENUBUTTONHEIGHT * metrics.density));
        topRightLayout.addView(showDrawButton,blp);
        
        LinearLayout bottomLeftLayout = new LinearLayout(this);
        bottomLeftLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams bllp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        bllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bllp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        menuLayout.addView(bottomLeftLayout,bllp);
        
        bookCaseModeButton = new Button(this);
        bookCaseModeButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[5]));
        bookCaseModeButton.setId(1);
		//zoomLayout.addView(previousButton, (int)(48 * metrics.density), (int)(48 * metrics.density));
		RelativeLayout.LayoutParams bcmp = new RelativeLayout.LayoutParams(
        		(int)(MENUBUTTONWIDTH*metrics.density), 
        		(int)(MENUBUTTONHEIGHT*metrics.density));
		bottomLeftLayout.addView(bookCaseModeButton, bcmp);
        
        LinearLayout bottomRightLayout = new LinearLayout(this);
        bottomRightLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams brlp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        brlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        brlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        menuLayout.addView(bottomRightLayout,brlp);
        
		contrastButton = new Button(this);
		contrastButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[6]));
		contrastButton.setId(1);
		//zoomLayout.addView(previousButton, (int)(48 * metrics.density), (int)(48 * metrics.density));
		RelativeLayout.LayoutParams cp = new RelativeLayout.LayoutParams(
        		(int)(MENUBUTTONWIDTH*metrics.density), 
        		(int)(MENUBUTTONHEIGHT*metrics.density));
		bottomRightLayout.addView(contrastButton, cp);
		contrastButton.setVisibility(View.GONE);
		bookmarkListButton = new Button(this);
		bookmarkListButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[7]));
		bookmarkListButton.setId(2);
		//floatLayout.addView(nextButton, (int)(48 * metrics.density), (int)(48 * metrics.density));
		RelativeLayout.LayoutParams blb = new RelativeLayout.LayoutParams(
				(int)(MENUBUTTONWIDTH*metrics.density), 
				(int)(MENUBUTTONHEIGHT*metrics.density));
		//bottomRightLayout.addView(bookmarkListButton, blb);
        
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.FILL_PARENT, 
        		RelativeLayout.LayoutParams.FILL_PARENT);
        setMenuButtonHandlers();
		activityLayout.addView(menuLayout,lp);
    }
    
    private void setBookmarkLayout(SharedPreferences options){
    	DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
        if (bookmarkLayout != null) {
    		activityLayout.removeView(bookmarkLayout);
    	}
    	
        bookmarkLayout = new RelativeLayout(this);
        
        bookmarkCenterLayout = new RelativeLayout(this);
        bookmarkCenterLayout.setId(DRAWTOPLEFTLAYOUTID);
        RelativeLayout.LayoutParams tllp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        tllp.addRule(RelativeLayout.CENTER_IN_PARENT);
        bookmarkCenterLayout.setId(TOPLEFTLAYOUTID);
        bookmarkLayout.addView(bookmarkCenterLayout,tllp);
        
        ImageView bookmarkBG= new ImageView(this);
        bookmarkBG.setBackgroundDrawable(getResources().getDrawable(R.drawable.bookmarkbg));
        RelativeLayout.LayoutParams dblp = new RelativeLayout.LayoutParams(
        		412, 536);

        bookmarkCenterLayout.addView(bookmarkBG,dblp);
        
        bookMarkListView = new ListView(this);
        RelativeLayout.LayoutParams bmlp = new RelativeLayout.LayoutParams(
        		412,536);
        bmlp.setMargins(10, 120, 10, 30);
        bookMarkListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        bookMarkListView.setBackgroundColor(Color.TRANSPARENT);
        bookmarkCenterLayout.addView(bookMarkListView,bmlp);
        
        ImageView bookmarkBorder= new ImageView(this);
        bookmarkBorder.setBackgroundDrawable(getResources().getDrawable(R.drawable.bookmarkborder));
        RelativeLayout.LayoutParams bblp = new RelativeLayout.LayoutParams(
        		412, 536);

        bookmarkCenterLayout.addView(bookmarkBorder,bblp);
        
        
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.FILL_PARENT, 
        		RelativeLayout.LayoutParams.FILL_PARENT);
        
        activityLayout.addView(bookmarkLayout,lp);
        bookmarkLayout.setVisibility(View.GONE);
    }
    
    private void setDrawLayout(SharedPreferences options) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        

    	if (drawLayout != null) {
    		activityLayout.removeView(drawLayout);
    	}
    	
    	drawLayout = new RelativeLayout(this);
    	
    	LinearLayout topLeftLayout = new LinearLayout(this);
    	topLeftLayout.setOrientation(LinearLayout.HORIZONTAL);
    	topLeftLayout.setId(DRAWTOPLEFTLAYOUTID);
        RelativeLayout.LayoutParams tllp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        tllp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        tllp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        topLeftLayout.setId(TOPLEFTLAYOUTID);
        drawLayout.addView(topLeftLayout,tllp);
        
		readModeButton = new Button(this);		
		readModeButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[0]));
		//zoomLayout.addView(drawButton, (int)(48 * metrics.density), (int)(48 * metrics.density));
		
        RelativeLayout.LayoutParams dlp = new RelativeLayout.LayoutParams(
        		(int)(MENUBUTTONWIDTH * metrics.density), 
        		(int)(MENUBUTTONHEIGHT * metrics.density));
        topLeftLayout.addView(readModeButton,dlp);
        
        
        ImageView drawBar= new ImageView(this);
        drawBar.setBackgroundDrawable(getResources().getDrawable(drawButtonId[4]));
        RelativeLayout.LayoutParams dblp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        dblp.addRule(RelativeLayout.BELOW, TOPLEFTLAYOUTID);
        dblp.leftMargin=(int)(40*metrics.density);
        drawLayout.addView(drawBar,dblp);
        
        LinearLayout drawToolLayout = new LinearLayout(this);
        drawToolLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams dtlp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        dtlp.addRule(RelativeLayout.BELOW, TOPLEFTLAYOUTID);
        dtlp.leftMargin=(int)(10*metrics.density);
        dtlp.topMargin=(int)(30*metrics.density);
        drawToolLayout.setId(DRAWTOOLLAYOUTID);
        drawLayout.addView(drawToolLayout,dtlp);
        
        penLayout = new LinearLayout(this);
        penLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams plp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        drawToolLayout.addView(penLayout, plp);
        
        RelativeLayout penButtonLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams pblp = new RelativeLayout.LayoutParams(
        		(int)(DRAWBUTTONWIDTH*metrics.density), 
           		(int)(DRAWBUTTONHEIGHT*metrics.density));

        penLayout.addView(penButtonLayout, pblp);
        
       drawPenButton = new Button(this);		
       drawPenButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[5]));
       RelativeLayout.LayoutParams dplp = new RelativeLayout.LayoutParams(
       		(int)(DRAWBUTTONWIDTH*metrics.density), 
       		(int)(DRAWBUTTONHEIGHT*metrics.density));
       drawPenButton.setId(DRAWPENBUTTONID);
       penButtonLayout.addView(drawPenButton,dplp);
       
       penSizeText = new TextView(this);
       penSizeText.setText(Integer.toString(penSize-2));
       penSizeText.setTextSize(20);
       penSizeText.setTextColor(Color.BLACK);
       RelativeLayout.LayoutParams pstlp = new RelativeLayout.LayoutParams(
    		   RelativeLayout.LayoutParams.WRAP_CONTENT,
    		   RelativeLayout.LayoutParams.WRAP_CONTENT);
       pstlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
       pstlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
       pstlp.setMargins(0,0, 13, 8);
       penButtonLayout.addView(penSizeText,	 pstlp);
       
       penBalloonLayout = new RelativeLayout(this);
       RelativeLayout.LayoutParams penlp = new RelativeLayout.LayoutParams(
       		RelativeLayout.LayoutParams.WRAP_CONTENT, 
       		RelativeLayout.LayoutParams.WRAP_CONTENT);
       penLayout.addView(penBalloonLayout,dtlp);
       
       ImageView penBalloon = new ImageView(this);
       penBalloon.setImageDrawable(getResources().getDrawable(drawButtonId[10]));
       RelativeLayout.LayoutParams penblp = new RelativeLayout.LayoutParams(
          		RelativeLayout.LayoutParams.WRAP_CONTENT, 
          		RelativeLayout.LayoutParams.WRAP_CONTENT);
       penBalloon.setId(PENBALLOONID);
       penBalloonLayout.addView(penBalloon,penblp);
       
       drawPenScrollButton = new Button(this);
       drawPenScrollButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[14]));
       RelativeLayout.LayoutParams dpslp = new RelativeLayout.LayoutParams(
        		(int)(SCROLLBUTTONWIDTH*metrics.density), 
         		(int)(SCROLLBUTTONHEIGHT*metrics.density));
       dpslp.leftMargin = (int)(33*metrics.density);
       dpslp.topMargin = (int)(16*metrics.density);
       penBalloonLayout.addView(drawPenScrollButton, dpslp);
       
       
       
       highlightLayout = new LinearLayout(this);
       highlightLayout.setOrientation(LinearLayout.HORIZONTAL);
       RelativeLayout.LayoutParams hlp = new RelativeLayout.LayoutParams(
       		RelativeLayout.LayoutParams.WRAP_CONTENT, 
       		RelativeLayout.LayoutParams.WRAP_CONTENT);
       drawToolLayout.addView(highlightLayout, hlp);
       
       RelativeLayout highlightButtonLayout = new RelativeLayout(this);
       RelativeLayout.LayoutParams hblp = new RelativeLayout.LayoutParams(
    		   (int)(DRAWBUTTONWIDTH*metrics.density), 
          		(int)(DRAWBUTTONHEIGHT*metrics.density));
       
       highlightLayout.addView(highlightButtonLayout, hblp);
       
      drawHighlightButton = new Button(this);		
      drawHighlightButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[6]));
      RelativeLayout.LayoutParams dhlp = new RelativeLayout.LayoutParams(
      		(int)(DRAWBUTTONWIDTH*metrics.density), 
      		(int)(DRAWBUTTONHEIGHT*metrics.density));
      highlightButtonLayout.addView(drawHighlightButton,	 dhlp);
      
      highlightSizeText = new TextView(this);
      highlightSizeText.setText(Integer.toString(highLightSize-4));
      highlightSizeText.setTextSize(20);
      highlightSizeText.setTextColor(Color.BLACK);
      RelativeLayout.LayoutParams hstlp = new RelativeLayout.LayoutParams(
      		RelativeLayout.LayoutParams.WRAP_CONTENT, 
      		RelativeLayout.LayoutParams.WRAP_CONTENT);
      hstlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
      hstlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
      hstlp.setMargins(0,0, 11, 8);
      highlightButtonLayout.addView(highlightSizeText,	 hstlp);

      
      highlightBalloonLayout = new RelativeLayout(this);
      RelativeLayout.LayoutParams highlightlp = new RelativeLayout.LayoutParams(
      		RelativeLayout.LayoutParams.WRAP_CONTENT, 
      		RelativeLayout.LayoutParams.WRAP_CONTENT);
      highlightLayout.addView(highlightBalloonLayout,highlightlp);
      
    
      
      ImageView highlightBalloon = new ImageView(this);
      highlightBalloon.setImageDrawable(getResources().getDrawable(drawButtonId[10]));
      RelativeLayout.LayoutParams highlightblp = new RelativeLayout.LayoutParams(
         		RelativeLayout.LayoutParams.WRAP_CONTENT, 
         		RelativeLayout.LayoutParams.WRAP_CONTENT);
      highlightBalloon.setId(HIGHLIGHTBALLOONID);
      highlightBalloonLayout.addView(highlightBalloon,highlightblp);
       
      drawHighlightScrollButton = new Button(this);
      drawHighlightScrollButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[14]));
      RelativeLayout.LayoutParams dhslp = new RelativeLayout.LayoutParams(
     		(int)(SCROLLBUTTONWIDTH*metrics.density), 
     		(int)(SCROLLBUTTONHEIGHT*metrics.density));
      dhslp.leftMargin = (int)(33*metrics.density);
      dhslp.topMargin = (int)(16*metrics.density);
      highlightBalloonLayout.addView(drawHighlightScrollButton, dhslp);
       
       
       
       shapeLayout = new LinearLayout(this);
       shapeLayout.setOrientation(LinearLayout.HORIZONTAL);
       RelativeLayout.LayoutParams slp = new RelativeLayout.LayoutParams(
    		   (int)(DRAWBUTTONWIDTH*metrics.density), 
          		(int)(DRAWBUTTONHEIGHT*metrics.density));
       drawToolLayout.addView(shapeLayout, slp);
       
       RelativeLayout shapeButtonLayout = new RelativeLayout(this);
       RelativeLayout.LayoutParams sblp = new RelativeLayout.LayoutParams(
    		   (int)(DRAWBUTTONWIDTH*metrics.density), 
          		(int)(DRAWBUTTONHEIGHT*metrics.density));
       
       shapeLayout.addView(shapeButtonLayout, sblp);
       
       drawShapeBUtton = new Button(this);		
       drawShapeBUtton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[7]));
       RelativeLayout.LayoutParams dslp = new RelativeLayout.LayoutParams(
       		(int)(DRAWBUTTONWIDTH*metrics.density), 
       		(int)(DRAWBUTTONHEIGHT*metrics.density));
       shapeButtonLayout.addView(drawShapeBUtton, dslp);
       
       shapeImage = new ImageView(this);
       shapeImage.setBackgroundDrawable(getResources().getDrawable(drawShapeId[1]));
       RelativeLayout.LayoutParams sstlp = new RelativeLayout.LayoutParams(
       		(int)(DRAWBUTTONWIDTH*metrics.density/4), 
       		(int)(DRAWBUTTONHEIGHT*metrics.density/4));
       sstlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
       sstlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
       sstlp.setMargins(0,0, 9,13);
       shapeButtonLayout.addView(shapeImage,	 sstlp);
      
      shapeBalloonLayout = new RelativeLayout(this);
      RelativeLayout.LayoutParams shapelp = new RelativeLayout.LayoutParams(
    		  RelativeLayout.LayoutParams.WRAP_CONTENT, 
      		RelativeLayout.LayoutParams.WRAP_CONTENT);
      shapeLayout.addView(shapeBalloonLayout,shapelp);
      
    
      
      ImageView shapeBalloon = new ImageView(this);
      shapeBalloon.setImageDrawable(getResources().getDrawable(drawButtonId[12]));
      RelativeLayout.LayoutParams shapeblp = new RelativeLayout.LayoutParams(
         		RelativeLayout.LayoutParams.WRAP_CONTENT, 
         		RelativeLayout.LayoutParams.WRAP_CONTENT);
      shapeBalloon.setId(SHAPEBALLOONID);
      shapeBalloonLayout.addView(shapeBalloon,shapeblp);
      
      selectSquareButton = new Button(this);
      selectSquareButton.setBackgroundDrawable(getResources().getDrawable(drawShapeId[0]));
      RelativeLayout.LayoutParams sdlp = new RelativeLayout.LayoutParams(
      		(int)(SHAPEBUTTONWIDTH*metrics.density), 
      		(int)(SHAPEBUTTONHEIGHT*metrics.density));
      sdlp.leftMargin = (int)(50*metrics.density);
      sdlp.addRule(RelativeLayout.CENTER_VERTICAL);
      selectSquareButton.setId(SELECTSQUAREBUTTONID);
      shapeBalloonLayout.addView(selectSquareButton, sdlp);
      
      selectCircleButton = new Button(this);
      selectCircleButton.setBackgroundDrawable(getResources().getDrawable(drawShapeId[1]));
      RelativeLayout.LayoutParams sclp = new RelativeLayout.LayoutParams(
      		(int)(SHAPEBUTTONWIDTH*metrics.density), 
      		(int)(SHAPEBUTTONHEIGHT*metrics.density));
      sclp.leftMargin = (int)(5*metrics.density);
      sclp.addRule(RelativeLayout.RIGHT_OF,SELECTSQUAREBUTTONID);
      sclp.addRule(RelativeLayout.CENTER_VERTICAL);
      selectCircleButton.setId(SELECTCIRCLEBUTTONID);
      shapeBalloonLayout.addView(selectCircleButton, sclp);
    
      selectTriangleButton = new Button(this);
      selectTriangleButton.setBackgroundDrawable(getResources().getDrawable(drawShapeId[2]));
      RelativeLayout.LayoutParams stlp = new RelativeLayout.LayoutParams(
      		(int)(SHAPEBUTTONWIDTH*metrics.density), 
      		(int)(SHAPEBUTTONHEIGHT*metrics.density));
      stlp.leftMargin = (int)(5*metrics.density);
      stlp.addRule(RelativeLayout.RIGHT_OF,SELECTCIRCLEBUTTONID);
      stlp.addRule(RelativeLayout.CENTER_VERTICAL);
      shapeBalloonLayout.addView(selectTriangleButton, stlp);
      
      colorLayout = new LinearLayout(this);
      colorLayout.setOrientation(LinearLayout.HORIZONTAL);
      RelativeLayout.LayoutParams clp = new RelativeLayout.LayoutParams(
      		RelativeLayout.LayoutParams.WRAP_CONTENT, 
      		RelativeLayout.LayoutParams.WRAP_CONTENT);
      drawToolLayout.addView(colorLayout, clp);
      
      RelativeLayout colorButtonLayout = new RelativeLayout(this);
      RelativeLayout.LayoutParams cblp = new RelativeLayout.LayoutParams(
    		  (int)(DRAWBUTTONWIDTH*metrics.density), 
         		(int)(DRAWBUTTONHEIGHT*metrics.density));
      
      colorLayout.addView(colorButtonLayout, hblp);
      
      drawColorButton = new Button(this);		
      drawColorButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[8]));
      RelativeLayout.LayoutParams drplp = new RelativeLayout.LayoutParams(
      		(int)(DRAWBUTTONWIDTH*metrics.density), 
      		(int)(DRAWBUTTONHEIGHT*metrics.density));
      colorButtonLayout.addView(drawColorButton, drplp);
      
      colorImage = new ImageView(this);
      colorImage.setBackgroundDrawable(getResources().getDrawable(drawColorId[0]));
      RelativeLayout.LayoutParams cstlp = new RelativeLayout.LayoutParams(
      		(int)(DRAWBUTTONWIDTH*metrics.density/4), 
      		(int)(DRAWBUTTONHEIGHT*metrics.density/4));
      cstlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
      cstlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
      cstlp.setMargins(0,0, 9,20);
      colorButtonLayout.addView(colorImage,	 cstlp);
     
     colorBalloonLayout = new RelativeLayout(this);
     RelativeLayout.LayoutParams colorlp = new RelativeLayout.LayoutParams(
     		RelativeLayout.LayoutParams.WRAP_CONTENT, 
     		RelativeLayout.LayoutParams.WRAP_CONTENT);
     colorLayout.addView(colorBalloonLayout,colorlp);
     
     ImageView colorBalloon = new ImageView(this);
     colorBalloon.setImageDrawable(getResources().getDrawable(drawButtonId[12]));
     RelativeLayout.LayoutParams colorblp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
     colorBalloon.setId(COLORBALLOONID);
     colorBalloonLayout.addView(colorBalloon,colorblp);
     
     
     selectBlackColorButton = new Button(this);
     selectBlackColorButton.setBackgroundDrawable(getResources().getDrawable(drawColorId[0]));
     RelativeLayout.LayoutParams sbclp = new RelativeLayout.LayoutParams(
     		(int)(COLORBUTTONWIDTH*metrics.density), 
     		(int)(COLORBUTTONHEIGHT*metrics.density));
     sbclp.leftMargin = (int)(60*metrics.density);
     sbclp.addRule(RelativeLayout.CENTER_VERTICAL);
     selectBlackColorButton.setId(SELECTCOLORBLACKBUTTONID);
     colorBalloonLayout.addView(selectBlackColorButton, sbclp);
     
     selectRedColorButton = new Button(this);
     selectRedColorButton.setBackgroundDrawable(getResources().getDrawable(drawColorId[1]));
     RelativeLayout.LayoutParams srclp = new RelativeLayout.LayoutParams(
     		(int)(COLORBUTTONWIDTH*metrics.density), 
     		(int)(COLORBUTTONHEIGHT*metrics.density));
     srclp.leftMargin = (int)(5*metrics.density);
     srclp.addRule(RelativeLayout.RIGHT_OF,SELECTCOLORBLACKBUTTONID);
     srclp.addRule(RelativeLayout.CENTER_VERTICAL);
     selectRedColorButton.setId(SELECTCOLORREDBUTTONID);
     colorBalloonLayout.addView(selectRedColorButton, srclp);
     
     selectBlueColorButton = new Button(this);
     selectBlueColorButton.setBackgroundDrawable(getResources().getDrawable(drawColorId[2]));
     RelativeLayout.LayoutParams sbluclp = new RelativeLayout.LayoutParams(
     		(int)(COLORBUTTONWIDTH*metrics.density), 
     		(int)(COLORBUTTONHEIGHT*metrics.density));
     sbluclp.leftMargin = (int)(5*metrics.density);
     sbluclp.addRule(RelativeLayout.RIGHT_OF,SELECTCOLORREDBUTTONID);
     sbluclp.addRule(RelativeLayout.CENTER_VERTICAL);
     selectBlueColorButton.setId(SELECTCOLORBLUEBUTTONID);
     colorBalloonLayout.addView(selectBlueColorButton, sbluclp);
     
     selectGreenColorButton = new Button(this);
     selectGreenColorButton.setBackgroundDrawable(getResources().getDrawable(drawColorId[3]));
     RelativeLayout.LayoutParams sgclp = new RelativeLayout.LayoutParams(
     		(int)(COLORBUTTONWIDTH*metrics.density), 
     		(int)(COLORBUTTONHEIGHT*metrics.density));
     sgclp.leftMargin = (int)(5*metrics.density);
     sgclp.addRule(RelativeLayout.RIGHT_OF,SELECTCOLORBLUEBUTTONID);
     sgclp.addRule(RelativeLayout.CENTER_VERTICAL);
     selectGreenColorButton.setId(SELECTCOLORGREENBUTTONID);
     colorBalloonLayout.addView(selectGreenColorButton, sgclp);
     
     selectPinkColorButton = new Button(this);
     selectPinkColorButton.setBackgroundDrawable(getResources().getDrawable(drawColorId[4]));
     RelativeLayout.LayoutParams spclp = new RelativeLayout.LayoutParams(
     		(int)(COLORBUTTONWIDTH*metrics.density), 
     		(int)(COLORBUTTONHEIGHT*metrics.density));
     spclp.leftMargin = (int)(5*metrics.density);
     spclp.addRule(RelativeLayout.RIGHT_OF,SELECTCOLORGREENBUTTONID);
     spclp.addRule(RelativeLayout.CENTER_VERTICAL);
     selectPinkColorButton.setId(SELECTCOLORPINKBUTTONID);
     colorBalloonLayout.addView(selectPinkColorButton, spclp);
     
     selectYellowColorButton = new Button(this);
     selectYellowColorButton.setBackgroundDrawable(getResources().getDrawable(drawColorId[5]));
     RelativeLayout.LayoutParams syclp = new RelativeLayout.LayoutParams(
     		(int)(COLORBUTTONWIDTH*metrics.density), 
     		(int)(COLORBUTTONHEIGHT*metrics.density));
     syclp.leftMargin = (int)(5*metrics.density);
     syclp.addRule(RelativeLayout.RIGHT_OF,SELECTCOLORPINKBUTTONID);
     syclp.addRule(RelativeLayout.CENTER_VERTICAL);
     selectYellowColorButton.setId(SELECTCOLORYELLOWBUTTONID);
     colorBalloonLayout.addView(selectYellowColorButton, syclp);
     
     selectWhiteColorButton = new Button(this);
     selectWhiteColorButton.setBackgroundDrawable(getResources().getDrawable(drawColorId[6]));
     RelativeLayout.LayoutParams swclp = new RelativeLayout.LayoutParams(
     		(int)(COLORBUTTONWIDTH*metrics.density), 
     		(int)(COLORBUTTONHEIGHT*metrics.density));
     swclp.leftMargin = (int)(5*metrics.density);
     swclp.addRule(RelativeLayout.RIGHT_OF,SELECTCOLORYELLOWBUTTONID);
     swclp.addRule(RelativeLayout.CENTER_VERTICAL);
     selectWhiteColorButton.setId(SELECTCOLORWHITEBUTTONID);
     colorBalloonLayout.addView(selectWhiteColorButton, swclp);
       
     erasorLayout = new LinearLayout(this);
     erasorLayout.setOrientation(LinearLayout.HORIZONTAL);
     RelativeLayout.LayoutParams elp = new RelativeLayout.LayoutParams(
     		RelativeLayout.LayoutParams.WRAP_CONTENT, 
     		RelativeLayout.LayoutParams.WRAP_CONTENT);
     drawToolLayout.addView(erasorLayout, elp);
     
     RelativeLayout erasorButtonLayout = new RelativeLayout(this);
     RelativeLayout.LayoutParams eblp = new RelativeLayout.LayoutParams(
  		   (int)(DRAWBUTTONWIDTH*metrics.density), 
        		(int)(DRAWBUTTONHEIGHT*metrics.density));
     
     erasorLayout.addView(erasorButtonLayout, eblp);
     
     drawErasorButton = new Button(this);		
     drawErasorButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[9]));
     RelativeLayout.LayoutParams delp = new RelativeLayout.LayoutParams(
     		(int)(DRAWBUTTONWIDTH*metrics.density), 
     		(int)(DRAWBUTTONHEIGHT*metrics.density));
     erasorButtonLayout.addView(drawErasorButton, drplp);
     
     erasorSizeText = new TextView(this);
     erasorSizeText.setText(Integer.toString(erasorSize-9));
     erasorSizeText.setTextSize(20);
     erasorSizeText.setTextColor(Color.BLACK);
     RelativeLayout.LayoutParams erstlp = new RelativeLayout.LayoutParams(
     		RelativeLayout.LayoutParams.WRAP_CONTENT, 
     		RelativeLayout.LayoutParams.WRAP_CONTENT);
     erstlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
     erstlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
     erstlp.setMargins(0,0, 11, 10);
     erasorButtonLayout.addView(erasorSizeText,	 hstlp);
    
    erasorBalloonLayout = new RelativeLayout(this);
    RelativeLayout.LayoutParams erasorlp = new RelativeLayout.LayoutParams(
    		RelativeLayout.LayoutParams.WRAP_CONTENT, 
    		RelativeLayout.LayoutParams.WRAP_CONTENT);
    erasorLayout.addView(erasorBalloonLayout,erasorlp); 
    
    ImageView erasorBalloon = new ImageView(this);
    erasorBalloon.setImageDrawable(getResources().getDrawable(drawButtonId[11]));
  //  erasorBalloon.setImageDrawable(getResources().getDrawable(drawButtonId[11]));
    RelativeLayout.LayoutParams erasorblp = new RelativeLayout.LayoutParams(
       		RelativeLayout.LayoutParams.WRAP_CONTENT, 
       		RelativeLayout.LayoutParams.WRAP_CONTENT);
    erasorBalloon.setId(ERASORBALLOONID);
    erasorBalloonLayout.addView(erasorBalloon,erasorblp);
  /*   
    drawErasorScrollButton = new Button(this);
    drawErasorScrollButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[14]));
    RelativeLayout.LayoutParams deslp = new RelativeLayout.LayoutParams(
     		(int)(SCROLLBUTTONWIDTH*metrics.density), 
     		(int)(SCROLLBUTTONHEIGHT*metrics.density));
    deslp.leftMargin = (int)(32*metrics.density);
    deslp.topMargin = (int)(16*metrics.density);
    erasorBalloonLayout.addView(drawErasorScrollButton, deslp);
     */
    clearDrawButton = new Button(this);
    clearDrawButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[15]));
    RelativeLayout.LayoutParams cdlp = new RelativeLayout.LayoutParams(
    		(int)(COLORBUTTONWIDTH*metrics.density), 
    		(int)(COLORBUTTONHEIGHT*metrics.density));
    cdlp.rightMargin = (int)(25*metrics.density);
   // cdlp.addRule(RelativeLayout.ALIGN_RIGHT,ERASORBALLOONID);
    cdlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
    cdlp.addRule(RelativeLayout.CENTER_VERTICAL);
    erasorBalloonLayout.addView(clearDrawButton, cdlp);
       
    penBalloonLayout.setVisibility(View.GONE);
    highlightBalloonLayout.setVisibility(View.GONE);
    shapeBalloonLayout.setVisibility(View.GONE);
    colorBalloonLayout.setVisibility(View.GONE);
    erasorBalloonLayout.setVisibility(View.GONE);
        
        LinearLayout topRightLayout = new LinearLayout(this);
        topRightLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams trlp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        trlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        trlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        drawLayout.addView(topRightLayout,trlp);
        
        browseButton = new Button(this);		
        browseButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[1]));
		//zoomLayout.addView(browseButton, (int)(48 * metrics.density), (int)(48 * metrics.density));
		
		LinearLayout browseLayout = new LinearLayout(this);
		browseLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams blp = new RelativeLayout.LayoutParams(
        		(int)(MENUBUTTONWIDTH * metrics.density), 
        		(int)(MENUBUTTONHEIGHT * metrics.density));
        blp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        topRightLayout.addView(browseButton,blp);
        
        LinearLayout bottomRightLayout = new LinearLayout(this);
        bottomRightLayout.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams brlp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.WRAP_CONTENT, 
        		RelativeLayout.LayoutParams.WRAP_CONTENT);
        brlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        brlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        drawLayout.addView(bottomRightLayout,brlp);
        
		rotateLeftButton = new Button(this);
		rotateLeftButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[2]));
		//zoomLayout.addView(previousButton, (int)(48 * metrics.density), (int)(48 * metrics.density));
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(
        		(int)(MENUBUTTONWIDTH*metrics.density), 
        		(int)(MENUBUTTONHEIGHT*metrics.density));
		bottomRightLayout.addView(rotateLeftButton, rllp);
		
		rotateRightButton = new Button(this);
		rotateRightButton.setBackgroundDrawable(getResources().getDrawable(drawButtonId[3]));
		//floatLayout.addView(nextButton, (int)(48 * metrics.density), (int)(48 * metrics.density));
		RelativeLayout.LayoutParams rrlp = new RelativeLayout.LayoutParams(
				(int)(MENUBUTTONWIDTH*metrics.density), 
				(int)(MENUBUTTONHEIGHT*metrics.density));
		bottomRightLayout.addView(rotateRightButton, rrlp);
        
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        		RelativeLayout.LayoutParams.FILL_PARENT, 
        		RelativeLayout.LayoutParams.FILL_PARENT);

        setDrawButtonHandlers();
		activityLayout.addView(drawLayout,lp);
		drawLayout.setVisibility(View.GONE);

    }
    
    private void findText(String text) {
    	Log.d(TAG, "findText(" + text + ")");
    	this.findText = text;
    	this.find(true);
    }
    
    /**
     * Called when user presses "next" button in find panel.
     */
    private void findNext() {
    	this.find(true);
    }

    /**
     * Called when user presses "prev" button in find panel.
     */
    private void findPrev() {
    	this.find(false);
    }
    
    /**
     * Called when user presses hide button in find panel.
     */
    private void findHide() {
    	if (this.pagesView != null) this.pagesView.setFindMode(false);
    	this.currentFindResultNumber = null;
    	this.currentFindResultPage = null;
    	this.findButtonsLayout.setVisibility(View.GONE);
    }

    /**
     * Helper class that handles search progress, search cancelling etc.
     */
	static class Finder implements Runnable, DialogInterface.OnCancelListener, DialogInterface.OnClickListener {
		private OpenFileActivity parent = null;
		private boolean forward;
		private AlertDialog dialog = null;
		private String text;
		private int startingPage;
		private int pageCount;
		private boolean cancelled = false;
		/**
		 * Constructor for finder.
		 * @param parent parent activity
		 */
		public Finder(OpenFileActivity parent, boolean forward) {
			this.parent = parent;
			this.forward = forward;
			this.text = parent.findText;
			this.pageCount = parent.pagesView.getPageCount();
			if (parent.currentFindResultPage != null) {
				if (forward) {
					this.startingPage = (parent.currentFindResultPage + 1) % pageCount;
				} else {
					this.startingPage = (parent.currentFindResultPage - 1 + pageCount) % pageCount;
				}
			} else {
				this.startingPage = parent.pagesView.getCurrentPage();
			}
		}
		public void setDialog(AlertDialog dialog) {
			this.dialog = dialog;
		}
		public void run() {
			int page = -1;
			this.createDialog();
			this.showDialog();
			for(int i = 0; i < this.pageCount; ++i) {
				if (this.cancelled) {
					this.dismissDialog();
					return;
				}
				page = (startingPage + pageCount + (this.forward ? i : -i)) % this.pageCount;
				Log.d(TAG, "searching on " + page);
				this.updateDialog(page);
				List<FindResult> findResults = this.findOnPage(page);
				if (findResults != null && !findResults.isEmpty()) {
					Log.d(TAG, "found something at page " + page + ": " + findResults.size() + " results");
					this.dismissDialog();
					this.showFindResults(findResults, page);
					return;
				}
			}
			/* TODO: show "nothing found" message */
			this.dismissDialog();
		}
		/**
		 * Called by finder thread to get find results for given page.
		 * Routed to PDF instance.
		 * If result is not empty, then finder loop breaks, current find position
		 * is saved and find results are displayed.
		 * @param page page to search on
		 * @return results 
		 */
		private List<FindResult> findOnPage(int page) {
			if (this.text == null) throw new IllegalStateException("text cannot be null");
			return this.parent.pdf.find(this.text, page);
		}
		private void createDialog() {
			this.parent.runOnUiThread(new Runnable() {
				public void run() {
					String title = Finder.this.parent.getString(R.string.searching_for).replace("%1$s", Finder.this.text);
					String message = Finder.this.parent.getString(R.string.page_of).replace("%1$d", String.valueOf(Finder.this.startingPage)).replace("%2$d", String.valueOf(pageCount));
			    	AlertDialog.Builder builder = new AlertDialog.Builder(Finder.this.parent);
			    	AlertDialog dialog = builder
			    		.setTitle(title)
			    		.setMessage(message)
			    		.setCancelable(true)
			    		.setNegativeButton(R.string.cancel, Finder.this)
			    		.create();
			    	dialog.setOnCancelListener(Finder.this);
			    	Finder.this.dialog = dialog;
				}
			});
		}
		public void updateDialog(final int page) {
			this.parent.runOnUiThread(new Runnable() {
				public void run() {
					String message = Finder.this.parent.getString(R.string.page_of).replace("%1$d", String.valueOf(page)).replace("%2$d", String.valueOf(pageCount));
					Finder.this.dialog.setMessage(message);
				}
			});
		}
		public void showDialog() {
			this.parent.runOnUiThread(new Runnable() {
				public void run() {
					Finder.this.dialog.show();
				}
			});
		}
		public void dismissDialog() {
			final AlertDialog dialog = this.dialog;
			this.parent.runOnUiThread(new Runnable() {
				public void run() {
					dialog.dismiss();
				}
			});
		}
		public void onCancel(DialogInterface dialog) {
			Log.d(TAG, "onCancel(" + dialog + ")");
			this.cancelled = true;
		}
		public void onClick(DialogInterface dialog, int which) {
			Log.d(TAG, "onClick(" + dialog + ")");
			this.cancelled = true;
		}
		private void showFindResults(final List<FindResult> findResults, final int page) {
			this.parent.runOnUiThread(new Runnable() {
				public void run() {
					int fn = Finder.this.forward ? 0 : findResults.size()-1;
					Finder.this.parent.currentFindResultPage = page;
					Finder.this.parent.currentFindResultNumber = fn;
					Finder.this.parent.pagesView.setFindResults(findResults);
					Finder.this.parent.pagesView.setFindMode(true);
					Finder.this.parent.pagesView.scrollToFindResult(fn);
					Finder.this.parent.findButtonsLayout.setVisibility(View.VISIBLE);					
					Finder.this.parent.pagesView.invalidate();
				}
			});
		}
	};
    
    /**
     * GUI for finding text.
     * Used both on initial search and for "next" and "prev" searches.
     * Displays dialog, handles cancel button, hides dialog as soon as
     * something is found.
     * @param 
     */
    private void find(boolean forward) {
    	if (this.currentFindResultPage != null) {
    		/* searching again */
    		int nextResultNum = forward ? this.currentFindResultNumber + 1 : this.currentFindResultNumber - 1;
    		if (nextResultNum >= 0 && nextResultNum < this.pagesView.getFindResults().size()) {
    			/* no need to really find - just focus on given result and exit */
    			this.currentFindResultNumber = nextResultNum;
    			this.pagesView.scrollToFindResult(nextResultNum);
    			this.pagesView.invalidate();
    			return;
    		}
    	}

    	/* finder handles next/prev and initial search by itself */
    	Finder finder = new Finder(this, forward);
    	Thread finderThread = new Thread(finder);
    	finderThread.start();
    }
    
    // #ifdef pro
    /**
     * Build and display dialog containing table of contents.
     * @param outline root of TOC tree
     */
  /*  private void showTableOfContentsDialog(Outline outline) {
    	if (outline == null) throw new IllegalArgumentException("nothing to show");
    	final Dialog dialog = new Dialog(this);
    	dialog.setTitle(R.string.toc_dialog_title);
    	LinearLayout contents = new LinearLayout(this);
    	contents.setOrientation(LinearLayout.VERTICAL);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    	params.leftMargin = 5;
    	params.rightMargin = 5;
    	params.bottomMargin = 2;
    	params.topMargin = 2;
    	final TreeView tocTree = new TreeView(this);
    	tocTree.setCacheColorHint(0);
    	tocTree.setTree(outline);
    	DocumentOptions documentOptions = new DocumentOptions(this.getApplicationContext());
    	try {
	    	String openNodesString = documentOptions.getValue(this.filePath, "toc_open_nodes");
	    	if (openNodesString != null) {
		    	String[] openNodes = documentOptions.getValue(this.filePath, "toc_open_nodes").split(",");
		    	for(String openNode: openNodes) {
		    		long nodeId = -1;
		    		try {
		    			nodeId = Long.parseLong(openNode);
		    		} catch (NumberFormatException e) {
		    			Log.w(TAG, "failed to parse " + openNode + " as long: " + e);
		    			continue;
		    		}
		    		tocTree.open(nodeId);
		    	}
	    	}
    	} finally {
    		documentOptions.close();
    	}
    	tocTree.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			Log.d(TAG, "onItemClick(" + parent + ", " + view + ", " + position + ", " + id);
    			TreeView treeView = (TreeView)parent;
    			TreeView.TreeNode treeNode = treeView.getTreeNodeAtPosition(position);
    			Outline outline = (Outline) treeNode;
    			int pageNumber = outline.page;
    			OpenFileActivity.this.gotoPage(pageNumber);
    			dialog.dismiss();
    		}
    	});
    	contents.addView(tocTree, params);
    	dialog.setContentView(contents);
    	dialog.setOnDismissListener(new OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				// save state 
				Log.d(TAG, "saving TOC tree state");
				Map<Long,Boolean> state = tocTree.getState();
    			String openNodes = "";
    			for(long key: state.keySet()) {
    				if (state.get(key)) {
    					if (openNodes.length() > 0) openNodes += ",";
    					openNodes += key;
    				}
    			}
    			DocumentOptions documentOptions = new DocumentOptions(OpenFileActivity.this.getApplicationContext());
    			try {
    				documentOptions.setValue(filePath, "toc_open_nodes", openNodes);
    			} finally {
    				documentOptions.close();
    			}
			}
    	});
    	dialog.show();
    }

   */ 
    /**
     * Quick and dirty way to reduce tree to two lists for TOC dialog.
     */
    private void outlineToArrayList(List<String> list, List<Integer> pages, Outline outline, int level) {
    	final class Pair {
    		public Pair(int level, Outline outline) {
    			this.level = level;
    			this.outline = outline;
    		}
    		public int level;
    		public Outline outline;
    	};
    	
    	Stack<Pair> stack = new Stack<Pair>();
    	stack.push(new Pair(0, outline));
    	
    	Log.d(TAG, "converting table of contents...");
    	
    	while(! stack.empty()) {
    		Pair p = stack.pop();
    		//Log.d(TAG, "got " + p.outline.title + " / " + p.outline.page + " from stack");
    		//Log.d(TAG, "  down points to: " + ((p.outline.down != null) ? p.outline.down.title : null));
    		//Log.d(TAG, "  next points to: " + ((p.outline.next != null) ? p.outline.next.title : null));
    		String s = "";
    		for(int i = 0; i < p.level; ++i) s += " ";
    		s += p.outline.title;
    		list.add(s);
    		pages.add(p.outline.page);
    		if (p.outline.next != null) {
    			stack.push(new Pair(p.level, p.outline.next));
    		}
    		if (p.outline.down != null) {
    			stack.push(new Pair(p.level + 1, p.outline.down));
    		}
    	}
    	
    	/*
    	if (outline == null) return;
    	String s = "";
    	for(int i = 0; i < level; ++i) s += " ";
    	s += outline.title;
    	list.add(s);
    	pages.add(outline.page);
    	if (outline.down != null) this.outlineToArrayList(list, pages, outline.down, level+1);
    	if (outline.next != null) this.outlineToArrayList(list, pages, outline.next, level);
    	*/
    }
    // #endif
    
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		gravity[0] = 0.8f * gravity[0] + 0.2f * event.values[0];
		gravity[1] = 0.8f * gravity[1] + 0.2f * event.values[1];
		gravity[2] = 0.8f * gravity[2] + 0.2f * event.values[2];

		float sq0 = gravity[0]*gravity[0];
		float sq1 = gravity[1]*gravity[1];
		float sq2 = gravity[2]*gravity[2];
		
/*		if (sq1 > 3 * (sq0 + sq2)) {
			if (gravity[1] > 4) {
				setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		//	else if (gravity[1] < -4 && Integer.parseInt(Build.VERSION.SDK) >= 9) 
			//	setOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
		}
		else if (sq0 > 3 * (sq1 + sq2)) {
			if (gravity[0] > 4)
				setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//	else if (gravity[0] < -4 && Integer.parseInt(Build.VERSION.SDK) >= 9) 
		//		setOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
		}*/
	}
	
	
// #ifdef pro
	/**
	 * Switch text reflow mode and set this.textReflowMode by hiding and showing relevant interface elements.
	 * @param mode if true ten show text reflow view, otherwise hide text reflow view
	 */
	private void setTextReflowMode(boolean mode) {
		if (mode) {
			Log.d(TAG, "text reflow");
			int page = this.pagesView.getCurrentPage();
			String text = this.pdf.getText(page);
			if (text == null) text = "";
			text = text.trim();
			Log.d(TAG, "text of page " + page + " is: " + text);
			this.textReflowTextView.setText(text);
			this.textReflowScrollView.scrollTo(0,0);
			this.textReflowMenuItem.setTitle("Close Text Reflow");
			this.pagesView.setVisibility(View.GONE);
	    	this.menuLayout.clearAnimation();
	    	this.drawHandler.removeCallbacks(zoomRunnable);
	    	this.menuHandler.removeCallbacks(zoomRunnable);
			//this.zoomLayout.setVisibility(View.GONE);
			this.textReflowView.setVisibility(View.VISIBLE);
			this.textReflowMode = true;
		} else {
			this.textReflowMenuItem.setTitle("Text Reflow");
			this.textReflowView.setVisibility(View.GONE);
			this.pagesView.setVisibility(View.VISIBLE);
			this.textReflowMode = false;
			this.showMenu();
		}
	}
	
	/**
	 * Change to next or prev page.
	 * Called from text reflow mode buttons.
	 * @param offset if 1 then go to next page, if -1 then go to prev page, otherwise raise IllegalArgumentException
	 */
	private void nextPage(int offset) {
		if (offset == 1) {
			this.pagesView.doAction(Actions.ACTION_FULL_PAGE_DOWN);
		} else if (offset == -1) {
			this.pagesView.doAction(Actions.ACTION_FULL_PAGE_UP);
		} else {
			throw new IllegalArgumentException("invalid offset: " + offset);
		}
		if (this.textReflowMode) {
			int page = this.pagesView.getCurrentPage();
			String text = this.pdf.getText(page);
			if (text == null) text = "";
			text = text.trim();
			this.textReflowTextView.setText(text);
			this.textReflowScrollView.scrollTo(0,0);
		}
	}
// #endif

	   public boolean onTouch(View v, MotionEvent event) {
		   
		   DisplayMetrics metrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metrics);
	     
	        if((pdfMode==READMODE || drawMode==NORMALMODE) && pdfMode != BOOKMARKMODE){

			   switch (event.getAction() & MotionEvent.ACTION_MASK) {
				   case MotionEvent.ACTION_DOWN:
				    //  savedMatrix.set(matrix);
				      start.set(event.getX(), event.getY());
				   //   Log.d(TAG, "mode=DRAG" );
				      mode = DRAG;
				      break;
				   case MotionEvent.ACTION_UP:
				   case MotionEvent.ACTION_POINTER_UP:
				     
				      Log.d(TAG, "mode=NONE" );
				     
				     if(mode!=SWIPE){
				    	  swipeDirection=PointCal.sameDirection(start, event,200);
				    	  if(swipeDirection!=NOSWIPE){
				    		  mode=SWIPE;
				    	  }
				      }
				      
				      if(mode==ZOOM){
				    	  pagesView.zoomWithPinchDistance(distanceZoom);
					    /*  if(isZoomIn){
					    	  //pagesView.doAction(actions.getAction(Actions.ZOOM_OUT));
					    	  
					    	  isZoomIn=false;
					      }else{
					    	 // pagesView.doAction(actions.getAction(Actions.ZOOM_IN));
					    	  isZoomOut=false;
					      }*/
				    	  pagesView.setRedraw();
				      }else if(mode==SWIPE){
				    	  if(swipeDirection==SWIPELEFT){
				    		  pagesView.goNextPage();
		
				    	  }else if(swipeDirection==SWIPERIGHT){
				    		  pagesView.goPreviousPage();
		
				    	  }
				    	  
				    	 
		/*		    	  if(prevOrientation==ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
				    		  pagesView.zoomWidth();
		
				    	  }else{*/
				    		  pagesView.zoomFit();
				    //	  }
				    	  pagesView.newSurface();
				    	  swipeDirection=NOSWIPE;
				      }
				      mode = NONE;
				      break;
				   case MotionEvent.ACTION_POINTER_DOWN:
					   oldDist = PointCal.spacing(event);
					   Log.d(TAG, "oldDist=" + oldDist);
					   if (oldDist > 10f) {
					   //   savedMatrix.set(matrix);
					      PointCal.midPoint(mid, event);
					      mode = ZOOM;
					      Log.d(TAG, "mode=ZOOM" );
					   }
					   break;
		
					case MotionEvent.ACTION_MOVE:
					   if (mode == DRAG) {
						/*
						   matrix.set(savedMatrix);
						   matrix.postTranslate(event.getX(0)-start.x, event.getY(0)-start.y);
						   savedMatrix.set(matrix);*/
						  
						   if(event.getX()-start.x>100){
							   mode=SWIPE;
							   swipeDirection=SWIPERIGHT;
						   }else if(event.getX()-start.x<-100){
							   mode=SWIPE;
							   swipeDirection=SWIPELEFT;
						   }else{
							   pagesView.setXY((int)(event.getX(0)-start.x),(int)(event.getY(0)-start.y));

							   start.set(event.getX(), event.getY());
						   }
						  // surfaceMatrix.postTranslate(event.getX(0)-start.x, event.getY(0)-start.y);
						   
					   }
					   else if (mode == ZOOM) {
					      float newDist = PointCal.spacing(event);
					   //   Log.d(TAG, "newDist=" + newDist);
					     /* if (newDist > 10f) {
					         matrix.set(savedMatrix);
					         float scale = newDist / oldDist;
					         matrix.postScale(scale, scale, mid.x, mid.y);
					      }*/
					      distanceZoom = (int)(newDist-oldDist);
					   //   if(newDist > metrics.widthPixels/4){
					    	  if(distanceZoom>0){
					    		  
					    		  isZoomIn=true;
					    	  }else if(distanceZoom<0){
					    		  isZoomOut=true;
					    	  }
					    	  pagesView.zoomWithPinchDistance(distanceZoom);
					   //   }
					   }
					   
					   break;
		
				}
			   menuLayout.invalidate();
	        }else if(pdfMode == DRAWMODE){
	        	int posX = pagesView.getOriginX(event.getX());
        		int posY = pagesView.getOriginY(event.getY());
        		if(pagesView.checkIsOnPDF(event.getX(), event.getY())){
	        	if(drawMode==PENMODE || drawMode==HIGHLIGHTMODE || drawMode==ERASORMODE){
	        		
	        		if(event.getAction() == MotionEvent.ACTION_DOWN){
	        	        drawingSurface.isDrawing = true;

	        	        currentDrawingPath = new DrawingPath();
	        	        currentBrush = new PenBrush();
	        	        Paint paint;
	        	        if(drawMode==PENMODE){
	        	        	paint = generatePenPaint();
	        	        }else if(drawMode==HIGHLIGHTMODE){
	        	        	paint = generateHighlightPaint();
	        	        }else {
	        	        	paint = generateErasorPaint();
	        	        }
	        	        currentDrawingPath.paint = paint;
	        	        currentDrawingPath.path = new Path();
	        	        drawingSurface.previewPath.paint = getPreviewPaint();

	        	        currentBrush.mouseDown(currentDrawingPath.path, posX, posY);
	        	        currentBrush.mouseDown(drawingSurface.previewPath.path, posX, posY);
	        	
	        	        
	        	    }else if(event.getAction() == MotionEvent.ACTION_MOVE){
	        	        drawingSurface.isDrawing = true;
	        	        
	        	        	currentBrush.mouseMove( currentDrawingPath.path, posX, posY );
	        	        	currentBrush.mouseMove(drawingSurface.previewPath.path, posX, posY);
	        	        
	        	
	        	    }else if(event.getAction() == MotionEvent.ACTION_UP){
	        	
	        	
	        	        currentBrush.mouseUp(drawingSurface.previewPath.path, posX, posY);
	        	        drawingSurface.previewPath.path = new Path();
	        	        drawingSurface.addDrawingPath(currentDrawingPath);
	        	
	        	        currentBrush.mouseUp( currentDrawingPath.path, posX, posY);
	        	
	        	    }
	        	}else if(drawMode==CIRCLEMODE || drawMode==SQUAREMODE){
	        		if(event.getAction() == MotionEvent.ACTION_DOWN){
	        	        drawingSurface.isDrawing = true;
	        	
	        	        currentDrawingPath = new DrawingPath();
	        	        Paint paint;
	        	        paint = generatePenPaint();
	        	        start.set(event.getX(), event.getY());
	        	        currentDrawingPath.paint = paint;
	        	        currentDrawingPath.path = new Path();
	        	        drawingSurface.previewPath.paint = getPreviewPaint();
	        	       
	        	        if(drawMode==CIRCLEMODE){
	        	        	currentBrush = new CircleBrush();
	        	        }else if(drawMode==SQUAREMODE){
	        	        	currentBrush = new SquareBrush();
	        	        }else{
	        	        	
	        	        }

	        	
	        	        
	        	    }else if(event.getAction() == MotionEvent.ACTION_MOVE){
	        	        drawingSurface.isDrawing = true;
	        	        currentBrush.mouseMove( currentDrawingPath.path, posX, posY);
	        	        currentBrush.mouseMove(drawingSurface.previewPath.path,posX, posY);

	        	
	        	    }else if(event.getAction() == MotionEvent.ACTION_UP){
	        	
	        	
	        	        currentBrush.mouseUp(drawingSurface.previewPath.path, posX, posY);
	        	        drawingSurface.previewPath.path = new Path();
	        	        drawingSurface.addDrawingPath(currentDrawingPath);
	        	
	        	        currentBrush.mouseUp( currentDrawingPath.path,posX, posY );
	        	
	        	    }
	        	}
        		}
        		drawLayout.invalidate();
		      // Perform the transformation
		 //     pdfPageView.setImageMatrix(matrix);

	        }else if(pdfMode==BOOKMARKMODE){
	        	if(bookmarkCenterLayout.getLeft()>event.getX() || bookmarkCenterLayout.getRight()<event.getX() 
	        			|| bookmarkCenterLayout.getTop()<event.getY() || bookmarkCenterLayout.getBottom()>event.getY()){
	        		bookmarkLayout.setVisibility(View.GONE);
	        		pdfMode = READMODE;
	        	}
	        }else{
	        	
	        }
			return true;
	   
	   }


	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		Bookmark b = new Bookmark(this.getApplicationContext()).open();
		List<BookmarkEntry> entryList = b.getBookmarks(filePath);
		BookmarkEntry entry = entryList.get(pos);
		pagesView.jumpToPage(entry.page);
		pagesView.invalidate();
		b.close();
	}
	
	public void loadBookmarkToListView(){
		Bookmark b = new Bookmark(this.getApplicationContext()).open();
		bookmarkListAdaptor = new BookmarkCell(b.getBookmarks(filePath),this);
		bookmarkListAdaptor.setBookmarkListListener(this);
		bookMarkListView.setOnItemClickListener(this);
		bookMarkListView.setAdapter(bookmarkListAdaptor);
		b.close();
	}


	public void onDeleteBookmarkClick(View v, BookmarkEntry entry) {
		Bookmark b = new Bookmark(this.getApplicationContext()).open();
		b.deleteBookmark(filePath, entry);
		loadBookmarkToListView();
		bookMarkListView.invalidateViews();
		bookmarkButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[1]));
		b.close();
		isBookmarked=false;
	}


	public void onCheckBookMarkOnNewPage(View v, int currentPage) {
		Bookmark b = new Bookmark(this.getApplicationContext()).open();
		List<BookmarkEntry> entryList = b.getBookmarks(filePath);
		for (int i=0;i<entryList.size();i++){
			if(entryList.get(i).page==currentPage){
				isBookmarked=true;
				bookmarkButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[2]));

				return;
			}
		}
		isBookmarked=false;
		bookmarkButton.setBackgroundDrawable(getResources().getDrawable(menuButtonId[1]));
		b.close();
	}

	public void onGenerateFirstPageFinish(View v,int x,int y, int pageWith, int pageHeight) {
	//	System.out.println("size "+pagesView.getWidth()+ " "+pagesView.getHeight());
		 RelativeLayout.LayoutParams dlp = new RelativeLayout.LayoutParams( pagesView.getWidth(),pagesView.getHeight());
	       drawingSurface.setLayoutParams(dlp);
	       
		 drawingSurface.setBookInitial(x,y,pageWith,pageHeight);
	}

	public void doPageMove(int diffX, int diffY) {
		drawingSurface.setXY(diffX, diffY);
	}

	public void doPageZoom(float ratio) {
		
		drawingSurface.zoomWithRatio(ratio);
	}

	public void onChangePage() {
		drawingSurface.clearDraw();
	}
	

}
