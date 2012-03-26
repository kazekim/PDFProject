package com.CodeGears.AuntiAnne.function;

import java.util.ArrayList;


public class DatabaseQueryData {
	private ArrayList<String> data;
	
	public DatabaseQueryData(){
		data =new ArrayList<String>();
	}
	
	public void addData(String value){
		data.add(value);
	}
	
	public ArrayList<String> getData(){
		return data;
	}
}
