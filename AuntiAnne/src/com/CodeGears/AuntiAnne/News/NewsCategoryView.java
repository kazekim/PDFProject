package com.CodeGears.AuntiAnne.News;


import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.CodeGears.AuntiAnne.R;
import com.CodeGears.AuntiAnne.appdata.CategoryData;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class NewsCategoryView  extends LinearLayout{

	private GridView newsCategoryGridView;
	private NewsCategoryGridView newsCategoryAdaptor;
	
	private Context context;
	
	private ArrayList<CategoryData> categoryDataList;
	
	public NewsCategoryView(Context context,Activity activity) {
		super(context);
		createView();
		
		this.context=context;
		
		loadCategoryNews();
		
		newsCategoryGridView = (GridView) findViewById(R.id.newscategorygridview);
		
		newsCategoryAdaptor = new NewsCategoryGridView(categoryDataList,context,activity);
		newsCategoryGridView.setAdapter(newsCategoryAdaptor);

	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		newsCategoryGridView.setOnItemClickListener(listener);
	}
	
	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.newscategorylayout, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	public CategoryData getCategoryDataAtIndex(int index){
		return categoryDataList.get(index);
	}
	
	public void recycleBitmap(){
		for(int i=0;i<categoryDataList.size();i++){
			categoryDataList.get(i).recycle();
		}
	}
	
	public void loadCategoryNews(){
		try{
			   Resources res = context.getResources();
			   XmlResourceParser xpp = res.getXml(R.xml.news);
			   xpp.next();
			   int eventType = xpp.getEventType();
			   
			   categoryDataList = new ArrayList<CategoryData>();
			   
			   while (eventType != XmlPullParser.END_DOCUMENT)
			   {
			    if(eventType == XmlPullParser.START_TAG)
			    {

			    	if(xpp.getName().equals("category")){
			    		categoryDataList.add(new CategoryData());
			    		categoryDataList.get(categoryDataList.size()-1).setNewsTitle(xpp.getAttributeValue(null, "title"));
			    		categoryDataList.get(categoryDataList.size()-1).setNewsType(xpp.getAttributeValue(null, "cattype"));
			    		categoryDataList.get(categoryDataList.size()-1).setNewsThumb(xpp.getAttributeValue(null, "thumb"));
			    		categoryDataList.get(categoryDataList.size()-1).setIndex(xpp.getAttributeIntValue(null, "index", 0));
			    	}
			    }
			    eventType = xpp.next();
			   }
			   
			   
			   
			  	} catch (XmlPullParserException e) {
			  		// TODO Auto-generated catch block
			  		e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
 
}