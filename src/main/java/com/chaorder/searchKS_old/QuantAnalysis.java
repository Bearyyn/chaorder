package com.chaorder.searchKS_old;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import com.csvreader.CsvReader;

import blade.kit.json.JSON;


/* Return the profits of top-10 Industry Index */
public class QuantAnalysis{
	/* date : 'YYYY-MM-DD'*/
	private ArrayList<String> dateList;
	public QuantAnalysis(ArrayList<String> dateList){
		this.dateList = new ArrayList<String>();
		for(String date: dateList){
			this.dateList.add(date.substring(0, 4)+"-"+
							  date.substring(4, 6)+"-"+
							  date.substring(6, 8));
		}
		Collections.sort(this.dateList, new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				return (o1.compareTo(o2));
			}
		});
	}
	public QuantAnalysis(){}
	/* Return ROI */
	public ArrayList<JSONObject> get_Roi(){
		try {
			/* Load Csv Data */
			ArrayList<String[]> csvList = new ArrayList<String[]>();
			ArrayList<String[]> csvList2 = new ArrayList<String[]>();
			String URL =this.getClass().getResource("/").toString();
			URL = URL.substring(5,URL.length());
			CsvReader reader1 = new CsvReader(URL+"industry_profit.csv",',',Charset.forName("utf-8"));
			CsvReader reader2 = new CsvReader(URL+"industry_std.csv",',',Charset.forName("utf-8"));
			while(reader1.readRecord()){ //逐行读入数据      
                csvList.add(reader1.getValues());  
            }
			while(reader2.readRecord()){ //逐行读入除表头的数据      
                csvList2.add(reader2.getValues());  
            }
			int index_size = csvList.get(0).length-1;     //第一行是时间
			/* Sort by Cumulative Profit */
			ArrayList<JSONObject> index_list = new ArrayList<JSONObject>();
			ArrayList<String> trade_date = new ArrayList<String>();
			ArrayList<Double> profit = new ArrayList<Double>();
			Double cumProfit = new Double(0);
			Double cumStd = new Double(0);
			for(int i=1; i<=index_size; i++){   //对于每一个指数
				JSONObject temp = new JSONObject();
				cumProfit = 0.0;
				cumStd = 0.0;
				trade_date.clear();
				profit.clear();
				for(String date:this.dateList){           //事件的每一个日期
					for(int j=1; j<csvList.size(); j++){  //计算累计收益
						if(csvList.get(j)[0].equals(date)){
							trade_date.add(date);
							try{
							    cumProfit += Double.valueOf(csvList.get(j)[i])*100;
							    profit.add(cumProfit);
							    cumStd +=  Double.valueOf(csvList2.get(j)[i]);
							}catch (Exception e) {
								continue;
							}				
						}
					}
				}
				temp.put("name", csvList.get(0)[i]);
				temp.put("trade_date", trade_date);
				temp.put("profit", profit);
				temp.put("final_profit", cumProfit);
				temp.put("final_std", cumStd);
				index_list.add(temp);
			}
			Collections.sort(index_list, new Comparator<JSONObject>(){
				@Override
				public int compare(JSONObject o1, JSONObject o2) {
					if ((Double)o1.get("final_profit") < (Double)o2.get("final_profit")) 
						return 1;
					else
						return -1;
				}
			});
			System.out.println(index_list.toString());
			return index_list;
		} catch (Exception e) {
			System.out.println("error:"+e.getMessage());
		}
		return null;
	}
	
	/**
	 * 返回主营占比的Json数据
	 * @return 
	 * 		主营占比的Json数据
	 */
	public JSONObject getMainProductOccupation() throws IOException{
		Map<String, ArrayList<JSONObject>> mainProduct = new HashMap<String, ArrayList<JSONObject>>();
		String URL =this.getClass().getResource("/").toString();
		URL = URL.substring(5,URL.length());
		CsvReader reader = new CsvReader(URL+"mainProductOccupation.csv",',',Charset.forName("utf-8"));
		while(reader.readRecord()){ //逐行读入数据      
              String[] line = reader.getValues();
              JSONObject company = new JSONObject();
              company.put("company", line[0]);
              company.put("occupation", line[3]);
              if(mainProduct.containsKey(line[2])){
            	  ArrayList<JSONObject> companyList = (ArrayList<JSONObject>) mainProduct.get(line[2]);
            	  if(companyList.size() <5){
            		  companyList.add(company);
            	  }
            	  mainProduct.put(line[2], companyList);
              }else{
            	  ArrayList<JSONObject> companyList = new ArrayList<JSONObject>();
            	  companyList.add(company);
            	  mainProduct.put(line[2], companyList);
              }
        }
		JSONObject mainProductJson = new JSONObject(mainProduct);
		return mainProductJson;
	}
	
	/**
	 * 返回价格序列的JSON数据
	 * @return
	 * 		价格序列JSON数据
	 */
	public JSONObject getMainProductPrice() throws IOException{
		Map<String, ProductPrice> mainProductPrice = new HashMap<String, ProductPrice>();
		String URL =this.getClass().getResource("/").toString();
		URL = URL.substring(5,URL.length());
		CsvReader reader = new CsvReader(URL+"mainProductPrice.csv",',',Charset.forName("utf-8"));
		// 创建序列
		reader.readRecord();
		String[] head = reader.getValues();
		for(int i=1; i<head.length; i++){
			mainProductPrice.put(head[i], new ProductPrice());
		}
		// 添加数据
		// date 日期, price 价格数据(注意操作可能为空)
		while(reader.readRecord()){
			String[] line = reader.getValues();
			String date = line[0];
			for(int i=1; i< line.length; i++){
				ProductPrice priceArrayList = mainProductPrice.get(head[i]);
				if( line[i].length() >=1 ){
					priceArrayList.time.add(date);
					priceArrayList.price.add(Double.valueOf(line[i]));
					mainProductPrice.put(head[i], priceArrayList);
				} else {}
			}
		}
		JSONObject mainProductPriceJson = new JSONObject();
		for(String key:mainProductPrice.keySet()){
			mainProductPriceJson.put(key, mainProductPrice.get(key).toJsonObject());
		}
		return mainProductPriceJson;
	}
}