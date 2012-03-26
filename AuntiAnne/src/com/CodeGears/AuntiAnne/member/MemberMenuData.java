package com.CodeGears.AuntiAnne.member;

public class MemberMenuData {
	private int index;
	private String name;
	private String memberType;
	private String thumbURL;
	
	public MemberMenuData(){
		
	}
	
	public void setIndex(int index){
		this.index=index;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setMemberType(String memberType){
		this.memberType=memberType;
	}
	
	public void setThumbURL(String thumbURL){
		this.thumbURL=thumbURL;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String getName(){
		return name;
	}
	
	public String getMemberType(){
		return memberType;
	}
	
	public String getThumbURL(){
		return thumbURL;
	}
	
	public void recycle(){
		
	}
}
