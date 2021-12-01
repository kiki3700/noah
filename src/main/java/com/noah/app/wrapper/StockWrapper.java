package com.noah.app.wrapper;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

/*추가로 넣어줘야하는 기능 
 * 가격가져오고 수익률을 구해준다. 가져왔으면 평균 분산을 구해준다.
 * 
 */

public class StockWrapper {
	private ItemDto itemDto;
	
	private boolean position; // 판매

	private String portfolioId;

	private float targetPrice;
	private float curPrice;
	private int amount;
	private double weight;
	
	private float pbr;
	private float per;
	private float opm;
	private float roa;
	private float roe;
	
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


	public ItemDto getItemDto() {
		return itemDto;
	}


	public void setItemDto(ItemDto itemDto) {
		this.itemDto = itemDto;
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


	public float getTargetPrice() {
		return targetPrice;
	}


	public void setTargetPrice(float targetPrice) {
		this.targetPrice = targetPrice;
	}


	public float getCurPrice() {
		return curPrice;
	}


	public void setCurPrice(float curPrice) {
		this.curPrice = curPrice;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	public double getWeight() {
		return weight;
	}


	public void setWeight(double weight) {
		this.weight = weight;
	}


	public float getPbr() {
		return pbr;
	}


	public void setPbr(float pbr) {
		this.pbr = pbr;
	}


	public float getPer() {
		return per;
	}


	public void setPer(float per) {
		this.per = per;
	}


	public float getOpm() {
		return opm;
	}


	public void setOpm(float opm) {
		this.opm = opm;
	}


	public float getRoa() {
		return roa;
	}


	public void setRoa(float roa) {
		this.roa = roa;
	}


	public float getRoe() {
		return roe;
	}


	public void setRoe(float roe) {
		this.roe = roe;
	}


	public Float getOneYearMean() {
		return oneYearMean;
	}


	public void setOneYearMean(Float oneYearMean) {
		this.oneYearMean = oneYearMean;
	}


	public Float getSixMonthsMean() {
		return sixMonthsMean;
	}


	public void setSixMonthsMean(Float sixMonthsMean) {
		this.sixMonthsMean = sixMonthsMean;
	}


	public Float getOneMonthMean() {
		return oneMonthMean;
	}


	public void setOneMonthMean(Float oneMonthMean) {
		this.oneMonthMean = oneMonthMean;
	}


	public Float getOneYearVariance() {
		return oneYearVariance;
	}


	public void setOneYearVariance(Float oneYearVariance) {
		this.oneYearVariance = oneYearVariance;
	}


	public Float getSixMonthsVariance() {
		return sixMonthsVariance;
	}


	public void setSixMonthsVariance(Float sixMonthsVariance) {
		this.sixMonthsVariance = sixMonthsVariance;
	}


	public Float getOneMonthVariance() {
		return oneMonthVariance;
	}


	public void setOneMonthVariance(Float oneMonthVariance) {
		this.oneMonthVariance = oneMonthVariance;
	}


	public List<HistoryDataDto> getHistorydataDtoList() {
		return historydataDtoList;
	}


	public void setHistorydataDtoList(List<HistoryDataDto> historydataDtoList) {
		this.historydataDtoList = historydataDtoList;
	}


	public LinkedHashMap<Date, Float> getReturnSeries() {
		return returnSeries;
	}


	public void setReturnSeries(LinkedHashMap<Date, Float> returnSeries) {
		this.returnSeries = returnSeries;
	}


	@Override
	public String toString() {
		return "StockWrapper [itemDto=" + itemDto + ", position=" + position + ", portfolioId=" + portfolioId
				+ ", targetPrice=" + targetPrice + ", amount=" + amount + ", weight=" + weight + ", pbr=" + pbr
				+ ", per=" + per + ", opm=" + opm + ", roa=" + roa + ", roe=" + roe + ", oneYearMean=" + oneYearMean
				+ ", sixMonthsMean=" + sixMonthsMean + ", oneMonthMean=" + oneMonthMean + ", oneYearVariance="
				+ oneYearVariance + ", sixMonthsVariance=" + sixMonthsVariance + ", oneMonthVariance="
				+ oneMonthVariance + ", historydataDtoList=" + historydataDtoList + ", returnSeries=" + returnSeries
				+ "]";
	}
	

	
	
	
}
