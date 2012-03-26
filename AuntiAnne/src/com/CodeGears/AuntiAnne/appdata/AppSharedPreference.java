package com.CodeGears.AuntiAnne.appdata;

import com.CodeGears.AuntiAnne.function.DialogFunction;
import com.CodeGears.AuntiAnne.member.LoginView;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSharedPreference {
	private static Context context;
	
	private String lastLoadCouponDate="0000-00-00";
	private String lastLoadMemberCouponDate="0000-00-00";
	private String username="";
	private SharedPreferences preferenceOptions;
	
	public AppSharedPreference(Context _context){
		context=_context;
		
		preferenceOptions = context.getSharedPreferences(InitVal.USERPREFERENCE, 0);
		
		load(context);
	}
	
	public void load(Context context){
		lastLoadCouponDate = preferenceOptions.getString("lastloadcoupondate", "0000-00-00");
		lastLoadMemberCouponDate = preferenceOptions.getString("lastloadmembercoupondate", "0000-00-00");
		username = preferenceOptions.getString("username", "");
		
		

	}
	
	public void save(Context context){
		SharedPreferences.Editor editoption = preferenceOptions.edit();
		
		editoption.putString("lastloadcoupondate",lastLoadCouponDate);
		editoption.putString("lastloadmembercoupondate",lastLoadCouponDate);
		editoption.putString("username",username );
		editoption.commit();
	}
	
	public void setLastLoadCouponDate(String date){
		this.lastLoadCouponDate=date;
		save(context);
	}
	
	public String getLastLoadCouponDate(){
		return lastLoadCouponDate;
	}
	
	public void setLastLoadMemberCouponDate(String date){
		this.lastLoadMemberCouponDate=date;
		save(context);
	}
	
	public String getLastLoadMemberCouponDate(){
		return lastLoadMemberCouponDate;
	}
	
	public void setUsername(String username){
		this.username=username;
		save(context);
		
	}
	
	public String getUsername(){
		return username;
	}
}
