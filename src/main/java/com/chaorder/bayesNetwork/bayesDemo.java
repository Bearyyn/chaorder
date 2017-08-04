package com.chaorder.bayesNetwork;

import com.github.vangj.jbayes.inf.prob.*;
import com.github.vangj.jbayes.inf.prob.Node;

import java.util.ArrayList;

import org.json.JSONObject;



public class bayesDemo{
	public bayesDemo() {
	
	}
	
	public JSONObject goldBayesDemo(String states) {
		JSONObject probList = new JSONObject();
		try {
			// 这里定义节点状态和对黄金的影响因素(vix, cpi, usd)
			Node vix = Node.newBuilder().name("vix").value("low").value("high").build();
			Node cpi = Node.newBuilder().name("cpi").value("low").value("high").build();
			Node usd = Node.newBuilder().name("usd").value("low").value("high").build();
			Node gold = Node.newBuilder().name("gold").value("low").value("high").build();

			// 定义影响
			gold.addParent(vix);
			gold.addParent(cpi);
			gold.addParent(usd);

			// 定义条件概率
			vix.setCpt(new double[][] {
			    {0.9124, 0.0876}
			});
			cpi.setCpt(new double[][] {
			    {0.437, 0.563}
			});
			usd.setCpt(new double[][] {
			    {0.9624, 0.0376}
			});
			
			gold.setCpt(new double[][]{
				{0.8, 0.2},   // low low low
				{0.1, 0.9},   // low low high
				{0.4, 0.6},   // low high high
				{0.7, 0.3},   // low high low
				{0.1, 0.9},   // high high low
				{0.75, 0.25}, // high low low
				{0.4, 0.6},   // high low high
				{0.3, 0.7},   // high high high 
			});

			// 创建贝叶斯网络
			Graph g = new Graph();
			g.addNode(vix);
			g.addNode(cpi);
			g.addNode(usd);
			g.addNode(gold);
		
			String[] nameList = new String[]{"vix", "cpi", "usd", "gold"};
			String[] statesList = new String[]{"low","high"};
			// 设置观测结果
			for(int i=0; i<states.length(); i++){
				if(states.charAt(i) != '2'){
					g.observe(nameList[i], statesList[Integer.valueOf(states.charAt(i)-'0')]);
				}
			}
			
			g.sample(10000);
			
			ArrayList<Double> lowProb = new ArrayList<Double>();
			ArrayList<Double> highProb = new ArrayList<Double>();
			
			double[] probsVix = vix.probs();
			double[] probsCpi = cpi.probs();
			double[] probsUsd = usd.probs();
			double[] probsGold = gold.probs();
			
			// 获取状态为low的概率
			lowProb.add(Double.valueOf(String.format("%.2f",probsGold[0])));
			lowProb.add(Double.valueOf(String.format("%.2f",probsUsd[0])));
			lowProb.add(Double.valueOf(String.format("%.2f",probsCpi[0])));
			lowProb.add(Double.valueOf(String.format("%.2f",probsVix[0])));
			
			//获取状态为high的概率
			highProb.add(Double.valueOf(String.format("%.2f",probsGold[1])));
			highProb.add(Double.valueOf(String.format("%.2f",probsUsd[1])));
			highProb.add(Double.valueOf(String.format("%.2f",probsCpi[1])));
			highProb.add(Double.valueOf(String.format("%.2f",probsVix[1])));
			
			// 添加到JsonObject中
			probList.put("low", lowProb);
			probList.put("high", highProb);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return probList;
	}
}