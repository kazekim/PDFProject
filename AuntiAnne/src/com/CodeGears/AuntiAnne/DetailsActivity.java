package com.CodeGears.AuntiAnne;


import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.layout.HeadView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class DetailsActivity extends Activity implements OnClickListener{
	
	private LinearLayout detailView;
	
	private FrameLayout headLayout;
	//private LinearLayout menuLayout;
	
	private HeadView headView;
//	private MenuView menuView;
	
	//private int clickMenu=0;
	
	private String newsTitle="";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsdetailactivity);

        if(InitVal.curPage==InitVal.NEWS){
        	Bundle b = getIntent().getExtras();
        
        	newsTitle = b.getString("newstitle");
        }

        WebView detailWebView = new WebView(this);
        detailWebView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        
        detailWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               return super.shouldOverrideUrlLoading(view, url);
            }
        });
        detailWebView.setBackgroundColor(0x00000000);
        detailWebView.clearSslPreferences();
        detailWebView.getSettings().setJavaScriptEnabled(true);
        
        detailView = (LinearLayout) findViewById(R.id.detailview);
        detailView.addView(detailWebView);
        

        if(InitVal.curPage==InitVal.NEWS){
       	
        	detailWebView.loadUrl(MainApp.NEWSDETAIL_URL+""+InitVal.detailPageID);
        }else if(InitVal.curPage==InitVal.MEMBER){
        	detailWebView.loadUrl(MainApp.REGISTER_URL);
        }else if(InitVal.curPage==InitVal.ECARD){
        	detailWebView.loadUrl(InitVal.cardURL);
        	detailWebView.getSettings().setLoadWithOverviewMode(true);
        	detailWebView.getSettings().setUseWideViewPort(true);
        }
        
        
        
        headView = new HeadView(this);
        headLayout = (FrameLayout) findViewById(R.id.headview);
        headLayout.addView(headView);
        
        if(InitVal.curPage==InitVal.NEWS){
        	headView.setHeadText(newsTitle);
        }else if(InitVal.curPage==InitVal.ECARD){
        	headView.setHeadText("E CARD");
        }
  
        headView.setListener(this);
     //   menuView.setListener(this);
}
	
	@Override
	public void onClick(View v) {

			if(headView.backButtonisClick()){
				if(InitVal.curPage==InitVal.ECARD){
					Intent intent = new Intent( DetailsActivity.this, TabActivity.class );
			        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
			        this.startActivity( intent );
				}
				this.finish();
				//System.out.print("CLick!");
				InitVal.detailPageID="0";
			}
			

	}
}
