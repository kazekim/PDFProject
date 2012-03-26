package com.kazekim.function;

import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;

public class LanguageSetting {
	
	private static final String thai="th";
	
	public static void changeThaiLanguage(Context context){
		changeLanguage(thai,context);
	}
	
	private static void changeLanguage(String languageCode,Context context){
		Locale locale = new Locale(languageCode); 
	    Locale.setDefault(locale);
	    Configuration config = new Configuration();
	    config.locale = locale;
	    context.getResources().updateConfiguration(config, 
	    context.getResources().getDisplayMetrics());
	}
}
