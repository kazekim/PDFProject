package com.CodeGears.AuntiAnne.contact;

import java.util.ArrayList;

public class ContactData {
	private String tel;
	private String telDescript;
	private String tel2;
	private String telDescript2;
	private String fax;
	private String address;
	private ArrayList<String> links;
	
	public ContactData(){
		links = new ArrayList<String>();
	}
	
	public void setTel(String tel){
		this.tel=tel;
	}
	
	public void setTelDescript(String telDescript){
		this.telDescript=telDescript;
	}
	
	public void setTel2(String tel2){
		this.tel2=tel2;
	}
	
	public void setTelDescript2(String telDescript2){
		this.telDescript2=telDescript2;
	}
	
	public void setFax(String fax){
		this.fax=fax;
	}
	
	public void setAddress(String address){
		this.address=address;
	}
	
	public void addLinks(String link){
		this.links.add(link);
	}
	
	public String getTel(){
		return tel;
	}
	
	public String getTel2(){
		return tel2;
	}
	
	public String getFax(){
		return fax;
	}
	
	public String getAddress(){
		return address;
	}
	
	public String getLinkAtIndex(int index){
		return links.get(index);
	}
	
	public int getLinksNum(){
		return links.size();
	}
	
	public String getTelDescript(){
		return telDescript;
	}
	
	public String getTelDescript2(){
		return telDescript2;
	}
}
