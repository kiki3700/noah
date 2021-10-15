package com.noah.app.vo;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

/*추가로 넣어줘야하는 기능 
 * 가격가져오고 수익률을 구해준다. 가져왔으면 평균 분산을 구해준다.
 * 
 */

public class StockWrapper {
	private String ticker; // 종목명
	private boolean longOrShort; // 판매
	private String portfolio;
	private double targetPrice;
	private String indestury;
	private int amount;
	private float mean;
	private float variance;
	private LinkedHashMap<Date, Double> priceSeries;
	private LinkedHashMap<Date, Double> returnSeries;
	public LinkedHashMap<Date, Double> getreturnSeries() {
		return returnSeries;
	}
	public void setreturnSeries(LinkedHashMap<Date, Double> priceSeries) {
		LinkedHashMap<Date, Double> returnSeries = new LinkedHashMap<Date, Double>();
		Set<Date> dates = priceSeries.keySet();
		Double exPrice = null;
		for(Date date : dates) {
			double price =priceSeries.get(date);
			if(exPrice == null) {
				exPrice = price;
				continue;
			}else {
				double rateOfReturn = (price-exPrice)/exPrice;
				returnSeries.put(date, rateOfReturn);
			}
		}
		this.returnSeries = returnSeries;
	}
	private int lenghtOfSeries;
	public StockWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public boolean isLongOrShort() {
		return longOrShort;
	}
	public void setLongOrShort(boolean longOrShort) {
		this.longOrShort = longOrShort;
	}
	public String getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}
	public double getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(double targetPrice) {
		this.targetPrice = targetPrice;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public float getMean() {
		return mean;
	}
	public void setMean(float mean) {
		this.mean = mean;
	}
	public float getVariance() {
		return variance;
	}
	public void setVariance(float variance) {
		this.variance = variance;
	}
	public LinkedHashMap<Date, Double> getpriceSeries() {
		return priceSeries;
	}
	public void setpriceSeries(LinkedHashMap<Date, Double> priceSeries) {
		this.priceSeries = priceSeries;
	}
	public String getIndestury() {
		return indestury;
	}
	public void setIndestury(String indestury) {
		this.indestury = indestury;
	}
	public int getLenghtOfSeries() {
		return lenghtOfSeries;
	}
	public void setLenghtOfSeries(int lenghtOfSeries) {
		this.lenghtOfSeries = lenghtOfSeries;
	}
	
	
	
}
