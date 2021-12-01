package com.noah.app.wrapper;

import java.util.Date;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;

public class PortfolioWrapper {
	private String name;
	private List<StockWrapper> stockList;
	private RealMatrix cov;
	private double limit;
	private double expReturn;
	private Date portfolioDate;
	private Date updateDate;
	public PortfolioWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<StockWrapper> getStockList() {
		return stockList;
	}
	public void setStockList(List<StockWrapper> stockList) {
		this.stockList = stockList;
	}
	public RealMatrix getCov() {
		return cov;
	}
	public void setCov(RealMatrix cov) {
		this.cov = cov;
	}
	public double getLimit() {
		return limit;
	}
	public void setLimit(double limit) {
		this.limit = limit;
	}
	public double getExpReturn() {
		return expReturn;
	}
	public void setExpReturn(double expReturn) {
		this.expReturn = expReturn;
	}
	public Date getPortfolioDate() {
		return portfolioDate;
	}
	public void setPortfolioDate(Date portfolioDate) {
		this.portfolioDate = portfolioDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return "PortfolioWrapper [name=" + name + ", stockList=" + stockList + ", cov=" + cov + ", limit=" + limit
				+ ", expReturn=" + expReturn + ", portfolioDate=" + portfolioDate + ", updateDate=" + updateDate + "]";
	}
}
