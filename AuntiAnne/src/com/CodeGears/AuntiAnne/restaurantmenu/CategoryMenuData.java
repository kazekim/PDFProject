package com.CodeGears.AuntiAnne.restaurantmenu;

import java.util.ArrayList;

public class CategoryMenuData {
	private String categoryName;
	private ArrayList<FoodData> foodDataList;
	
	public CategoryMenuData(){
		foodDataList = new ArrayList<FoodData>();
	}
	
	public void setCategoryName(String categoryName){
		this.categoryName=categoryName;
	}
	
	public void addFood(FoodData foodData){
		foodDataList.add(foodData);
	}
	
	public int getFoodNum(){
		return foodDataList.size();
	}
	
	public FoodData getFoodDataAtIndex(int index){
		return foodDataList.get(index);
	}
	
	public String getCategoryName(){
		return categoryName;
	}
	
	public void recycle(){
		for(int i=0;i<foodDataList.size();i++){
			foodDataList.get(i).recycle();
    	}
	}
	
}
