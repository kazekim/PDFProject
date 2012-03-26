package com.CodeGears.AuntiAnne.News;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

import com.CodeGears.AuntiAnne.MainApp;
import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;

public class NewsDetailView  extends LinearLayout{

	//private Context context;
	
	private LinearLayout detailView;
	
	public NewsDetailView(Context context) {
		super(context);
		createView();
		
		//this.context = context;
		
		WebView detailWebView = new WebView(context);
        detailWebView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        MarginLayoutParams p = ((ViewGroup.MarginLayoutParams) layout.getLayoutParams());
        p.setMargins(20, 0, 20, 0);
        layout.setBackgroundColor(0x00FFFF00);
        layout.addView(detailWebView);
        
        detailWebView.loadUrl(MainApp.NEWSDETAIL_URL+""+InitVal.detailPageID);
        
        detailView = (LinearLayout) findViewById(R.id.detailview);
        detailView.addView(layout);
		
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		
	}
	
	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.detail, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
}
