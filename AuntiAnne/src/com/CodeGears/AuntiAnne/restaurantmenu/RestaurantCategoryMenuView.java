package com.CodeGears.AuntiAnne.restaurantmenu;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.TextView;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.InitVal;


public class RestaurantCategoryMenuView  extends LinearLayout {


	private static Activity activity;
	
	private TextView coreMenuTitle;
	private static GridView categoryMenuGridView;
	
	private RelativeLayout menuLayout;
	
	public RestaurantCategoryMenuView(Context _context,Activity _activity,CategoryMenuData categoryMenuData) {
		super(_context);
		createView();
		
		activity=_activity;
		
		coreMenuTitle = (TextView) findViewById(R.id.coremenutitle);
		coreMenuTitle.setTypeface(InitVal.Font);
		coreMenuTitle.setTextSize(InitVal.fontSizeCoreMenuTitle);
		coreMenuTitle.setTextColor(InitVal.YELLOWCOLOR);
		coreMenuTitle.setText(categoryMenuData.getCategoryName());
		menuLayout = (RelativeLayout) findViewById(R.id.menulayout);
		
		categoryMenuGridView = (GridView) findViewById(R.id.coremenugridview);
		
		categoryMenuGridView.setAdapter(new CategoryMenuGridView(activity,categoryMenuData));

		DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
		categoryMenuGridView.getLayoutParams().height = (int) (202*metrics.density*Math.ceil((categoryMenuData.getFoodNum()+1)/2));
		
	}

	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.categorymenulayout, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
}
