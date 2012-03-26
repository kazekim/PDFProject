package com.CodeGears.AuntiAnne.appdata;

public class UserData {

	private String phone;
	private String name;
	private String surname;
	private int point;
	private int quota;
	private boolean isBirthday;
	private boolean isRegister;
	
	public UserData(){
		
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setSurname(String surname){
		this.surname=surname;
	}
	
	public void setPhone(String phone){
		this.phone=phone;
	}
	
	public void setPoint(int point){
		this.point=point;
	}
	
	public void setQuota(int quota){
		this.quota=quota;
	}
	
	public void setIsBirthDay(String stringBoolean){
		if(stringBoolean=="true"){
			this.isBirthday=true;
		}else{
			this.isBirthday=false;
		}
	}
	
	public void setIsRegister(String stringBoolean){
		if(stringBoolean=="true"){
			this.isRegister=true;
		}else{
			this.isRegister=false;
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public String getPhone(){
		return phone;
	}
	
	public int getPoint(){
		return point;
	}
	
	public int getQuota(){
		return quota;
	}
	
	public boolean checkIsBirthday(){
		return isBirthday;
	}
	
	public boolean checkIsRegister(){
		return isRegister;
	}
}
