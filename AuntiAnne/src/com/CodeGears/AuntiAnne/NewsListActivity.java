package com.CodeGears.AuntiAnne;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.CodeGears.AuntiAnne.News.NewsCell;
import com.CodeGears.AuntiAnne.News.NewsData;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.function.DialogFunction;
import com.CodeGears.AuntiAnne.function.DocumentFunction;
import com.CodeGears.AuntiAnne.layout.HeadView;
import com.CodeGears.AuntiAnne.stats.Stats.StatsTask;

public class NewsListActivity extends Activity implements OnClickListener,OnItemClickListener{
	
	private HeadView headView;
//	private MenuView menuView;
	
	private FrameLayout headLayout;
	private LinearLayout menuLayout;
	private ListView newsListListView;
	

	 private int clickMenu=0;
	 
	 private ArrayList<NewsData> newsDataList;
	
	 private CategoryNewsTask categoryNewsTask;
	 private int categoryIndex;
	 
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newslistactivity);

        runFadeAnimation();
        
        Bundle b = getIntent().getExtras();
        
        String categoryName = b.getString("newscategoryname");
        categoryIndex = b.getInt("categoryindex");
        
        newsDataList= new ArrayList<NewsData>();
        
        headView = new HeadView(this);
        headLayout = (FrameLayout) findViewById(R.id.headview);
        headLayout.addView(headView);


/*
        menuView = new MenuView(this);
        menuLayout = (LinearLayout) findViewById(R.id.menuview);
        menuLayout.addView(menuView);
        menuView.setButton(InitVal.curPage);
  */      
        newsListListView = (ListView) findViewById(R.id.newslistlistview);
        newsListListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
//        menuView.setListener(this);
        
        
        //promoEDTView.setOnListViewClickListener(this);
      //  superDealView.setOnListViewClickListener(this);
        headView.setHeadText(categoryName);
        
        headView.setListener(this);
       
        categoryNewsTask = new CategoryNewsTask(this);
        categoryNewsTask.execute( this );
        newsListListView.setOnItemClickListener(this);
        
    }
	
	private void runFadeAnimation() {
	    Animation a = AnimationUtils.loadAnimation(this, R.anim.fadeout);
	    a.reset();
	    LinearLayout ll = (LinearLayout) findViewById(R.id.tabpage);
	    ll.clearAnimation();
	    ll.startAnimation(a);   
	}
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int row, long arg3) {
		// TODO Auto-generated method stub
		if(InitVal.curPage==InitVal.NEWS){
			Intent myIntent;
			myIntent = new Intent(NewsListActivity.this,
					DetailsActivity.class);
			InitVal.detailPageID=newsDataList.get(row).getNewsID();
			
			Bundle b = new Bundle();
			b.putString("newstitle",newsDataList.get(row).getNewsTitle());
			myIntent.putExtras(b);

			startActivityForResult(myIntent,1);
			overridePendingTransition (R.anim.fadeout, R.anim.fadein);
			recycleBitmap();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
/*		clickMenu = menuView.checkOnClick();
		if(clickMenu > -1){
			
			Intent returnIntent = new Intent(NewsListActivity.this,TabActivity.class);
			
			InitVal.curPage=clickMenu;
			setResult(1, returnIntent);
			
			returnIntent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );

	        this.startActivity( returnIntent );
			overridePendingTransition (R.anim.fadeout, R.anim.fadein);
			//runFadeAnimation();
		}else{*/
			if(headView.backButtonisClick()){
				
				recycleBitmap();
				finish();
				
				if(InitVal.curPage == InitVal.MEMBERACTIVITY){
					InitVal.curPage=InitVal.MEMBER;
				}
				
				InitVal.newsListPage=-1;
			}else if(headView.homeButtonisClick()){
				Intent intent = new Intent( NewsListActivity.this, MainActivity.class );
		        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		        this.startActivity( intent );
		        recycleBitmap();
			}
			
//		}
	}
	
	public void recycleBitmap(){
		for(int i=0;i<newsDataList.size();i++){
			newsDataList.get(i).recycle();
		}
		
	}

	public class CategoryNewsTask extends AsyncTask<Context, Integer, String>
	{
		private String data;
		private Activity activity;
		private int status;
		
		public CategoryNewsTask(Activity activity){
			data="category="+categoryIndex;
			this.activity=activity;

		}
		
		@Override
		protected String doInBackground( Context... params ) 
		{
			//-- on every iteration
			//-- runs a while loop that causes the thread to sleep for 50 milliseconds 
			//-- publishes the progress - calls the onProgressUpdate handler defined below
			//-- and increments the counter variable i by one
			/** Handling XML */
			try{
				URL urls = new URL(MainApp.NEWSLIST_URL);
				HttpURLConnection connection = (HttpURLConnection) urls.openConnection();
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setUseCaches(false);
				
				
				
				// Send Post Variable through these Commands
				
				OutputStream outStream = connection.getOutputStream();
				Writer writer = new OutputStreamWriter(outStream, "UTF-8");
				writer.write(data);
				writer.flush();
				writer.close();
				outStream.close();
			
				String response= "";

				Scanner inStream = new Scanner(connection.getInputStream());
				while(inStream.hasNextLine()){
					response+=(inStream.nextLine());
				}
				
				Document d = DocumentFunction.stringToDocument(response);
				connection.disconnect();
				
				
				NodeList nodeList = d.getElementsByTagName("news_list");
				
				Node item = nodeList.item(0);
				NodeList news = item.getChildNodes();
				//   System.out.println("NodeName "+news.item(0).getNodeName());
				
				
				
				
				for (int j = 0; j < news.getLength(); j++) {
					newsDataList.add(new NewsData());
					Element element =(Element)news.item(j);
					newsDataList.get(j).setNewsID(element.getAttribute("id"));
					newsDataList.get(j).setNewsTitle(element.getAttribute("title"));
					newsDataList.get(j).setNewsThumb(element.getAttribute("picture"));

					//System.out.println("id "+newsDataList.get(j).getNewsTitle()+" "+newsDataList.get(j).getNewsThumb());
				}
				status=InitVal.SUCCESS;
			}
			catch (UnknownHostException e){
				status=InitVal.CONNECTIONERROR;
				e.printStackTrace();
			}
			catch (IOException e) {
				status=InitVal.CONNECTIONERROR;
				e.printStackTrace();
			}
			catch (FactoryConfigurationError e) {
				status=InitVal.DATAERROR;
				e.printStackTrace();
			} catch (SAXException e) {
				status=InitVal.DATAERROR;
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				status=InitVal.DATAERROR;
				e.printStackTrace();
			} catch (NullPointerException e){
				e.printStackTrace();
			}
			
			
			return "COMPLETE!";
		}
		
		// -- gets called just before thread begins
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			
			
		}
		
		// -- called from the publish progress 
		// -- notice that the datatype of the second param gets passed to this method
		@Override
		protected void onProgressUpdate(Integer... values) 
		{
			super.onProgressUpdate(values);
		}
		
		// -- called if the cancel button is pressed
		@Override
		protected void onCancelled()
		{
			super.onCancelled();
			
		}

		// -- called as soon as doInBackground method completes
		// -- notice that the third param gets passed to this method
		@Override
		protected void onPostExecute( String result ) 
		{
			super.onPostExecute(result);
			if(status==InitVal.SUCCESS){
				newsListListView.setAdapter(new NewsCell(newsDataList,activity));
			
				activity =null;
			}else{
				DialogFunction.showError(activity,status);
			}
		}
		
		
	}  
}
