package com.example.project_pas.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("meals")
	private List<MealsItem> meals;

	public List<MealsItem>getMeals(){
		return meals;
	}
}