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
	
	private double pbr;
	private double per;
	
	private double opm;
	private double roa;
	private double roe;
	
	private double oneYearReturn;
	private double sixMonthsReturn;
	private double oneMonthReturn;
	
	private double oneYearVariance;
	private double sixMonthsVariance;
	private double oneMonthVariance;
	
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


	public double getPbr() {
		return pbr;
	}


	public void setPbr(double pbr) {
		this.pbr = pbr;
	}


	public double getPer() {
		return per;
	}


	public void setPer(double per) {
		this.per = per;
	}


	public double getOpm() {
		return opm;
	}


	public void setOpm(double opm) {
		this.opm = opm;
	}


	public double getRoa() {
		return roa;
	}


	public void setRoa(double roa) {
		this.roa = roa;
	}


	public double getRoe() {
		return roe;
	}


	public void setRoe(double roe) {
		this.roe = roe;
	}


	public double getOneYearReturn() {
		return oneYearReturn;
	}


	public void setOneYearReturn(double oneYearReturn) {
		this.oneYearReturn = oneYearReturn;
	}


	public double getSixMonthsReturn() {
		return sixMonthsReturn;
	}


	public void setSixMonthsReturn(double sixMonthsReturn) {
		this.sixMonthsReturn = sixMonthsReturn;
	}


	public double getOneMonthReturn() {
		return oneMonthReturn;
	}


	public void setOneMonthReturn(double oneMonthReturn) {
		this.oneMonthReturn = oneMonthReturn;
	}


	public double getOneYearVariance() {
		return oneYearVariance;
	}


	public void setOneYearVariance(double oneYearVariance) {
		this.oneYearVariance = oneYearVariance;
	}


	public double getSixMonthsVariance() {
		return sixMonthsVariance;
	}


	public void setSixMonthsVariance(double sixMonthsVariance) {
		this.sixMonthsVariance = sixMonthsVariance;
	}


	public double getOneMonthVariance() {
		return oneMonthVariance;
	}


	public void setOneMonthVariance(double oneMonthVariance) {
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
				+ ", targetPrice=" + targetPrice + ", curPrice=" + curPrice + ", amount=" + amount + ", weight="
				+ weight + ", pbr=" + pbr + ", per=" + per + ", opm=" + opm + ", roa=" + roa + ", roe=" + roe
				+ ", oneYearReturn=" + oneYearReturn + ", sixMonthsReturn=" + sixMonthsReturn + ", oneMonthReturn="
				+ oneMonthReturn + ", oneYearVariance=" + oneYearVariance + ", sixMonthsVariance=" + sixMonthsVariance
				+ ", oneMonthVariance=" + oneMonthVariance + ", historydataDtoList=" + historydataDtoList
				+ ", returnSeries=" + returnSeries + "]";
	}


}
