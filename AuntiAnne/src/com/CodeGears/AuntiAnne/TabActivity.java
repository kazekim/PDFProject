package com.CodeGears.AuntiAnne;

import com.CodeGears.AuntiAnne.News.NewsCategoryView;
import com.CodeGears.AuntiAnne.appdata.InitVal;
import com.CodeGears.AuntiAnne.contact.ContactView;
import com.CodeGears.AuntiAnne.eCard.EcardView;
import com.CodeGears.AuntiAnne.eCoupon.ECouponView;
import com.CodeGears.AuntiAnne.layout.HeadView;
import com.CodeGears.AuntiAnne.location.LocationView;
import com.CodeGears.AuntiAnne.member.LoginView;
import com.CodeGears.AuntiAnne.member.MemberBirthdayView;
import com.CodeGears.AuntiAnne.member.MemberView;
import com.CodeGears.AuntiAnne.restaurantmenu.RestaurantMenuView;
import com.google.android.maps.MapActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

public class TabActivity extends MapActivity implements OnClickListener,OnItemClickListener{
	
	private NewsCategoryView newsCategoryView;
	private ECouponView ecouponView;
	private EcardView ecardView;
	private MemberView memberView;
	private LoginView loginView;
	private LocationView locationView;
	private ContactView contactView;
	private RestaurantMenuView menuView;
	private MemberBirthdayView memberBirthdayView;
	
	private HeadView headView;
	//private MenuView menuView;
	
	private FrameLayout headLayout;
	//private LinearLayout menuLayout;
	private LinearLayout screenLayout;
	
	 private int clickPage;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabactivity);

        runFadeAnimation();
        
        headView = new HeadView(this);
        headLayout = (FrameLayout) findViewById(R.id.headview);
        headLayout.addView(headView);

        

      //  menuView = new MenuView(this);
        
        screenLayout = (LinearLayout) findViewById(R.id.pageview);
        
       clickPage=InitVal.curPage;
        
      /*  
        menuLayout = (LinearLayout) findViewById(R.id.menuview);
        menuLayout.addView(menuView);
        menuView.setButton(InitVal.curPage);
       */
        
    //    menuView.setListener(this);
        
        headView.setListener(this);
        openPage(InitVal.curPage);
       
    }
	
	private void runFadeAnimation() {
	    Animation a = AnimationUtils.loadAnimation(this, R.anim.fadeout);
	    a.reset();
	    LinearLayout ll = (LinearLayout) findViewById(R.id.tabpage);
	    ll.clearAnimation();
	    ll.startAnimation(a);   
	}
	
	private void runChangePageAnimation(int slideDirection){
		Animation slidein;
		if(slideDirection!=0){

			if(slideDirection>0){
				slidein = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
			}else{
				slidein = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
			}
			
			slidein.reset();
		    LinearLayout ll = (LinearLayout) findViewById(R.id.pageview);
		    ll.clearAnimation();
		    ll.startAnimation(slidein);
		}
	}
	
	public void openPage(int pageNumber){
		screenLayout.removeAllViews();
		InitVal.curPage=pageNumber;
				runChangePageAnimation(clickPage-pageNumber);
				switch(pageNumber){
						case InitVal.NEWS: 
								newsCategoryView = new NewsCategoryView(this,this);
								screenLayout.addView(newsCategoryView);
								newsCategoryView.setOnItemClickListener(this);
						        headView.setHeadText("HIGHLIGHT");
								break;
						case InitVal.MEMBER: 
							if(InitVal.appSettings.getUsername().equals("")){
								loginView = new LoginView(this,this);
								screenLayout.addView(loginView);
						        loginView.setOnClickListener(this);
							}else{
								memberView = new MemberView(this);      
								screenLayout.addView(memberView);
								memberView.setOnItemClickListener(this);
							}
							headView.setHeadText("MEMBER");
								break;
						
						case InitVal.ECOUPON: 
							ecouponView = new ECouponView(this,this,InitVal.NORMALCOUPON);
							screenLayout.addView(ecouponView);
							ecouponView.loadCoupon();
							ecouponView.setOnItemClickListener(this);
							ecouponView.setOnClickListener(this);
							headView.setHeadText("E COUPON");
								break;
						case InitVal.ECARD: 
							ecardView = new EcardView(this,this);
							
							screenLayout.addView(ecardView);
							headView.setHeadText("E CARD");
							
							ecardView.setOnClickListener(this);
								break;
						
						case InitVal.LOCATION: 
							locationView = new LocationView(this,this);
							locationView.loadLocation();
							screenLayout.addView(locationView);
							
							locationView.setOnClickListener(this);
							headView.setHeadText("LOCATION");
							break;
						case InitVal.CONTACT: 
							contactView = new ContactView(this);
							
							screenLayout.addView(contactView);
							contactView.setOnClickListener(this);
							headView.setHeadText("CONTACT US");
							break;
						case InitVal.COREMENU:
							menuView = new RestaurantMenuView(this,this);

							screenLayout.addView(menuView);
							headView.setHeadText("CORE MENU");
						//	menuView.setOnItemClickListener(this);
							break;
						case InitVal.MEMBERBIRTHDAY:
							memberBirthdayView = new MemberBirthdayView(this);

							screenLayout.addView(memberBirthdayView);
							headView.setHeadText("MEMBER'S BIRTHDAY");
							memberBirthdayView.setOnClickListener(this);
							break;
						case InitVal.MEMBERECOUPON:
							ecouponView = new ECouponView(this,this,InitVal.MEMBERCOUPON);
							screenLayout.addView(ecouponView);
							ecouponView.loadCoupon();
							ecouponView.setOnItemClickListener(this);
							ecouponView.setOnClickListener(this);
							headView.setHeadText("MEMBER'S E COUPON");
							break;
							
				}
	  }
	
	public void recycleBitmap(){
		switch(InitVal.curPage){
		case InitVal.NEWS: 
				newsCategoryView.recycleBitmap();
				break;
		case InitVal.MEMBER: 
				memberView.recycleBitmap();
				break;
		
		case InitVal.ECOUPON: 
				ecouponView.recycleBitmap();
				break;
		case InitVal.ECARD: 
				ecardView.recycleBitmap();
				break;
		
		case InitVal.LOCATION: 
			
			break;
		case InitVal.CONTACT: 
			
			break;
		case InitVal.COREMENU:
			menuView.recycleBitmap();
			break;
		case InitVal.MEMBERBIRTHDAY:

			break;
		case InitVal.MEMBERECOUPON:
			ecouponView.recycleBitmap();
			break;
			
}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
		// TODO Auto-generated method stub
		if(InitVal.curPage==InitVal.NEWS){
			Intent myIntent;
			myIntent = new Intent(TabActivity.this,
					NewsListActivity.class);
			InitVal.newsListPage=index;
			
			Bundle b = new Bundle();
			b.putString("newscategoryname", newsCategoryView.getCategoryDataAtIndex(index).getNewsTitle());
			b.putInt("categoryindex", newsCategoryView.getCategoryDataAtIndex(index).getIndex());
			myIntent.putExtras(b);
			
			startActivityForResult(myIntent,1);
			overridePendingTransition (R.anim.fadeout, R.anim.fadein);
		}else if(InitVal.curPage == InitVal.ECOUPON || InitVal.curPage==InitVal.MEMBERECOUPON){	
			
			if(!ecouponView.checkCouponIsUsed(index)){
				ecouponView.clickOnGridNumber(index);
				
			}
		}else if(InitVal.curPage == InitVal.MEMBER){	

			Intent myIntent;
			myIntent = new Intent(TabActivity.this,TabActivity.class);
			
			InitVal.curPage=memberView.getIndexLink(index);
			
			if(InitVal.curPage == InitVal.MEMBERACTIVITY){
				myIntent = new Intent(TabActivity.this,NewsListActivity.class);
				InitVal.newsListPage=InitVal.NEWSMEMBERACTIVITY;
			}
			
			startActivityForResult(myIntent,1);
			overridePendingTransition (R.anim.fadeout, R.anim.fadein);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	/*	clickPage = menuView.checkOnClick();
		if(clickPage > -1){
			changePage(clickPage);
		}else{
	*/	
			if(headView.backButtonisClick()){
				
				if(InitVal.curPage == InitVal.MEMBERECOUPON && !ecouponView.canClick()){
					return;
				}
				
				this.finish();
				if(InitVal.curPage==InitVal.ECOUPON){
					ecouponView.closeDatabase();
				}else if(InitVal.curPage == InitVal.MEMBERBIRTHDAY || InitVal.curPage == InitVal.MEMBERECOUPON 
						|| InitVal.curPage == InitVal.MEMBERACTIVITY){
					InitVal.curPage=InitVal.MEMBER;
				}else if(InitVal.curPage == InitVal.ECARD){
					ecardView.cancelTask();
				}

			}else if(headView.homeButtonisClick()){
				Intent intent = new Intent( TabActivity.this, MainActivity.class );
		        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		        this.startActivity( intent );
			
			}else if(loginView!=null && loginView.loginButtonIsClick()){
			
				loginView.doLogin();
			}else if(InitVal.curPage==InitVal.CONTACT){
				int linkButtonClick = contactView.checkLinkButtonIsClick();
				if(linkButtonClick>-1){
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(contactView.getLinkURLAtIndex(linkButtonClick)));
					startActivity(browserIntent);
				}else if(contactView.checkTelButtonIsClick()){
					Uri callUri = Uri.parse("tel://"+contactView.getTel());
					Intent returnIt = new Intent(Intent.ACTION_CALL, callUri);
					returnIt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					startActivity(returnIt);
				}else if(contactView.checkTelButton2IsClick()){
					Uri callUri = Uri.parse("tel://"+contactView.getTel2());
					Intent returnIt = new Intent(Intent.ACTION_CALL, callUri);
					returnIt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
					startActivity(returnIt);
				}
				
			}else if(InitVal.curPage==InitVal.ECOUPON || InitVal.curPage==InitVal.MEMBERECOUPON){
				if(ecouponView.confirmButtonIsClick()){
					ecouponView.useCoupon();
					
				}else if(ecouponView.cancelButtonIsClick()){
					ecouponView.closeConfirmCouponBox();
				}
				else if(ecouponView.confirmButton2IsClick()){
					ecouponView.loadNewCoupon();
					ecouponView.closeCouponBox();
				}
			}else if(InitVal.curPage==InitVal.ECARD){
				if(ecardView.leftButtonIsClick()){
					ecardView.moveBackgroundPrevious();
				}else if(ecardView.rightButtonIsClick()){
					ecardView.moveBackgroundForward();
				}
				else if(ecardView.selectButtonIsClick()){
					Intent myIntent;
					myIntent = new Intent(TabActivity.this,
							EcardDecorateActivity.class);

						
					startActivityForResult(myIntent,1);
					overridePendingTransition (R.anim.fadeout, R.anim.fadein);
				}
			}else if(InitVal.curPage==InitVal.MEMBERBIRTHDAY){
				if(memberBirthdayView.confirmButtonIsClick()){
					memberBirthdayView.useCoupon();
					
				}else if(memberBirthdayView.cancelButtonIsClick()){
					memberBirthdayView.closeConfirmCouponBox();
				}else if(memberBirthdayView.useBirthdayButtonIsClick()){
					memberBirthdayView.showConfirmBox();
				}

			}else if(InitVal.curPage == InitVal.MEMBER){
				if(loginView!=null && loginView.registerButtonIsClick()){
					Intent myIntent;
					myIntent = new Intent(TabActivity.this,
							DetailsActivity.class);
					startActivityForResult(myIntent,1);
					overridePendingTransition (R.anim.fadeout, R.anim.fadein);
					
				}
			}

			
		}
		
		
	//}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
