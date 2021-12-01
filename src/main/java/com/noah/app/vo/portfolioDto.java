package com.noah.app.vo;

import java.util.Date;

public class portfolioDto {
	private String id;
	private String strategy;
	private String detailStrategy;
	private String status;
	private double limit;
	private Date rebalncingDate;
	private Date portfolioDate;
	private Date updateDate;
	
	
	public portfolioDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	public String getDetailStrategy() {
		return detailStrategy;
	}
	public void setDetailStrategy(String detailStrategy) {
		this.detailStrategy = detailStrategy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getLimit() {
		return limit;
	}
	public void setLimit(double limit) {
		this.limit = limit;
	}
	public Date getRebalncingDate() {
		return rebalncingDate;
	}
	public void setRebalncingDate(Date rebalncingDate) {
		this.rebalncingDate = rebalncingDate;
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
		return "portfolioDto [id=" + id + ", strategy=" + strategy + ", detailStrategy=" + detailStrategy + ", status="
				+ status + ", limit=" + limit + ", rebalncingDate=" + rebalncingDate + ", portfolioDate="
				+ portfolioDate + ", updateDate=" + updateDate + "]";
	}
	
}
