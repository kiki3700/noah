package com.noah.app.wrapper;

import java.util.Date;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;

public class PortfolioWrapper {
	private String name;
	private List<StockWrapper> stockList;
	private String strategey;
	private String detailStrategy;
	private String managementStrategy;
	private RealMatrix cov;
	private double limit;
	private double expReturn;
	private double var;
	private Date portfolioDate;
	private Date rebalncingDate;
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
	
	public String getStrategey() {
		return strategey;
	}
	public void setStrategey(String strategey) {
		this.strategey = strategey;
	}
	public String getDetailStrategy() {
		return detailStrategy;
	}
	public void setDetailStrategy(String detailStrategy) {
		this.detailStrategy = detailStrategy;
	}
	
	public String getManagementStrategy() {
		return managementStrategy;
	}
	public void setManagementStrategy(String managementStrategy) {
		this.managementStrategy = managementStrategy;
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
	
	public double getVar() {
		return var;
	}
	public void setVar(double var) {
		this.var = var;
	}
	public Date getPortfolioDate() {
		return portfolioDate;
	}
	public void setPortfolioDate(Date portfolioDate) {
		this.portfolioDate = portfolioDate;
	}
	
	public Date getRebalncingDate() {
		return rebalncingDate;
	}
	public void setRebalncingDate(Date rebalncingDate) {
		this.rebalncingDate = rebalncingDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return "PortfolioWrapper [name=" + name + ", stockList=" + stockList + ", strategey=" + strategey
				+ ", detailStrategy=" + detailStrategy + ", managementStrategy=" + managementStrategy + ", cov=" + cov
				+ ", limit=" + limit + ", expReturn=" + expReturn + ", portfolioDate=" + portfolioDate
				+ ", rebalncingDate=" + rebalncingDate + ", updateDate=" + updateDate + "]";
	}
}
