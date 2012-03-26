package com.kazekim.ModPageView;

import android.view.View;

public interface PageViewListener {
	public void onCheckBookMarkOnNewPage(View v,int currentPage); 
	public void onGenerateFirstPageFinish(View v,int x,int y,int pageWidth,int pageHeight);
	public void doPageMove(int diffX,int diffY);
	public void doPageZoom(float ratio);
	public void onChangePage();
}
