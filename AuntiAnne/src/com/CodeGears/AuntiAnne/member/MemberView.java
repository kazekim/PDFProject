package com.CodeGears.AuntiAnne.member;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.CodeGears.AuntiAnne.R;

public class MemberView  extends LinearLayout{
	
	private Context context;
	private MemberDetailView memberDetailView;
	private LinearLayout detailView;

	
	
	public MemberView(Context context) {
		super(context);
		createView();
		this.context=context;
		
		detailView = (LinearLayout) findViewById(R.id.detailview);
		memberDetailView = new MemberDetailView(context);
		detailView.addView(memberDetailView);

	}

	
	
	public void createView(){
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l = (LinearLayout)layoutInflater.inflate(R.layout.memberlayout, this, true);
		l.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}
	
	public void setOnItemClickListener(OnItemClickListener listener){
		memberDetailView.setOnItemClickListener(listener);
	}
	
	public int getIndexLink(int index){
		
		return memberDetailView.getIndexMemberMenu(index);
	}
	
	public void recycleBitmap(){
		memberDetailView.recycleBitmap();
	}
	
}
