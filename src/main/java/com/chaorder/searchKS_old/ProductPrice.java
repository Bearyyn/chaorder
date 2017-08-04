package com.chaorder.searchKS_old;

import java.util.ArrayList;

import org.json.JSONObject;


/**
 * 存储价格序列的结构体类
 */
public class ProductPrice{
	
	public ArrayList<String> time;
	public ArrayList<Double> price;
	
	public ProductPrice(){
		time = new ArrayList<String>();
		price = new ArrayList<Double>();
	}
	
	public JSONObject toJsonObject(){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("time", time);
		jsonObject.put("price", price);
		return jsonObject;
	}
}