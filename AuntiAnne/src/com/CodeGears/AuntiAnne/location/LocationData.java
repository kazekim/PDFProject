package com.CodeGears.AuntiAnne.location;

public class LocationData {
	
	private String name;
	private String description;
	private double latitude;
	private double longtitude;
	private String tel;
	
	public LocationData(){
		
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setDescription(String description){
		this.description=description;
	}
	
	public void setLatitude(double latitude){
		this.latitude=latitude;
	}
	
	public void setLongtitude(double longtitude){
		this.longtitude=longtitude;
	}
	
	public void setTel(String tel){
		this.tel=tel;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
	public double getLongtitude(){
		return longtitude;
	}
	
	public String getTel(){
		return tel;
	}
}
