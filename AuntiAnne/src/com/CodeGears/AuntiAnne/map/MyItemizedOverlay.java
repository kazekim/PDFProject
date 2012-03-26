/***
 * Copyright (c) 2010 readyState Software Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.CodeGears.AuntiAnne.map;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.CodeGears.AuntiAnne.MainApp;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends BalloonItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();
	private Context c;
	private MapActivity activity;
	private boolean isClickable=true;
	
	public MyItemizedOverlay(Drawable defaultMarker, MapView mapView, MapActivity activity) {
		super(boundCenter(defaultMarker), mapView);
		c = mapView.getContext();
		this.activity=activity;
	}
	
	public MyItemizedOverlay(Drawable defaultMarker, MapView mapView, MapActivity activity,boolean isClickable) {
		super(boundCenter(defaultMarker), mapView);
		c = mapView.getContext();
		this.activity=activity;
		this.isClickable=isClickable;
	}

	public void addOverlay(OverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}

	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
	/*	MainApp.selectedGarageID= MainApp.garrageDataList.get(index).getGarrageID();
		
		if(isClickable){
			Intent myIntent;
	
					myIntent = new Intent(c,
							GarageDetailActivity.class);
					
					
					activity.startActivityForResult(myIntent,1);
					activity.overridePendingTransition (R.anim.fadeout, R.anim.fadein);
					activity=null;
		}*/
		return true;
	}
	
}
