package com.noah.app.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/*추가로 넣어줘야하는 기능 
 * 가격가져오고 수익률을 구해준다. 가져왔으면 평균 분산을 구해준다.
 * 
 */

public class StockWrapper {
	private String itemId;
	private String name;
	private String market;

	private boolean position; // 판매
	private String portfolioId;
	private String industry;
	private double targetPrice;
	private int amount;
	
	private Float oneYearMean;
	private Float sixMonthsMean;
	private Float oneMonthMean;
	
	private Float oneYearVariance;
	private Float sixMonthsVariance;
	private Float oneMonthVariance;
	
	private List<HistoryDataDto> historydataDtoList;
	private LinkedHashMap<Date, Float> returnSeries;
	
	
	public StockWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public StockWrapper(ItemDto item) {
		this.itemId = item.getId();
		this.name = item.getName();
		this.industry =item.getIndustry();
	}
	
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}
	
	
	public boolean isPosition() {
		return position;
	}
	public void setPosition(boolean position) {
		this.position = position;
	}
	public String getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}

	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
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

	public Float getOneYearMean() {
		if(this.returnSeries==null) return null;
		if(this.oneYearMean!=null) return this.oneYearMean;
		int len = this.returnSeries.size();
		if(len <250) return null;
		float sum = 0;
		for(int i = len-250; i < len; i++) {
			sum += returnSeries.get(i);
		}
		this.oneYearMean = sum/250;
		return this.oneYearMean;
	}

	public Float getSixMonthsMean() {
		if(this.returnSeries==null) return null;
		if(this.sixMonthsMean!=null) return this.sixMonthsMean;
		int len = this.returnSeries.size();
		if(len <125) return null;
		float sum = 0;
		for(int i = len-125; i < len; i++) {
			sum += returnSeries.get(i);
		}
		this.sixMonthsMean = sum/125;
		return this.sixMonthsMean;
	}
	public Float getOneMonthMean() {
		if(this.returnSeries==null) return null;
		if(this.oneMonthMean!=null) return this.oneMonthMean;
		int len = this.returnSeries.size();
		if(len <20) return null;
		float sum = 0;
		for(int i = len-20; i < len; i++) {
			sum += returnSeries.get(i);
		}
		this.oneMonthMean = sum/20;
		return this.oneMonthMean;
	}
	public Float getOneYearVariance() {
		if(this.returnSeries==null) return null;
		if(this.oneYearVariance!=null) return this.oneYearVariance; 
		if(this.oneYearMean!=null) getOneYearMean();
		float mean = this.oneMonthMean;
		int len = this.returnSeries.size();
		if(len <250) return null;
		float sum = 0;
		List<Float> price = new ArrayList<Float>(returnSeries.values());
		for(int i = len - 250; i<len; i++) {
			sum += Math.pow(price.get(i)-mean, 2);
		}
		this.oneMonthVariance = sum/250;
		return this.oneMonthVariance;
	}
	public Float getSixMonthsVariance() {
		if(this.returnSeries==null) return null;
		if(this.sixMonthsVariance!=null) return this.sixMonthsVariance; 
		if(this.sixMonthsMean!=null) getSixMonthsMean();
		float mean = this.sixMonthsMean;
		int len = this.returnSeries.size();
		if(len <125) return null;
		float sum = 0;
		List<Float> price = new ArrayList<Float>(returnSeries.values());
		for(int i = len - 125; i<len; i++) {
			sum += Math.pow(price.get(i)-mean, 2);
		}
		this.oneMonthVariance = sum/125;
		return this.oneMonthVariance;
	}
	public Float getOneMontVariance() {
		if(this.returnSeries==null) return null;
		if(this.oneMonthVariance!=null) return this.oneMonthVariance; 
		if(this.oneMonthMean!=null) getOneMonthMean();
		float mean = this.oneMonthMean;
		int len = this.returnSeries.size();
		if(len <20) return null;
		float sum = 0;
		List<Float> price = new ArrayList<Float>(returnSeries.values());
		for(int i = len - 20; i<len; i++) {
			sum += Math.pow(price.get(i)-mean, 2);
		}
		this.oneMonthVariance = sum/20;
		return this.oneMonthVariance;
	}
	public LinkedHashMap<Date, Float> getReturnSeries() {
		if(this.returnSeries!=null) return this.returnSeries;
		LinkedHashMap<Date, Float> returnSeries = new LinkedHashMap<>();
		float prePrice = this.historydataDtoList.get(0).getClose();
		for(int i = 1; i < this.historydataDtoList.size(); i++) {
			float price =this.historydataDtoList.get(i).getClose();
			Date date =this.historydataDtoList.get(i).getTradingDate();
			float rateOfReturn = (price-prePrice)/prePrice;
			prePrice = price;
			returnSeries.put(date, rateOfReturn);
		}
		this.returnSeries = returnSeries;
		return returnSeries;
	}
	public List<HistoryDataDto> getHistorydataDtoList() {
		return historydataDtoList;
	}
	public void setHistorydataDtoList(List<HistoryDataDto> historydataDtoList) {
		this.historydataDtoList = historydataDtoList;
	}

	
	
}
