package com.noah.app.dto;

import java.util.Date;

public class PortfolioDto {
	private String id;
	private String name;
	private String strategy;
	private String detailStrategy;
	private String managementStrategy;
	private String status;
	private double limit;
	private Date rebalancingDate;
	private Date portfolioDate;
	private Date updateDate;
	
	
	public PortfolioDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String getManagementStrategy() {
		return managementStrategy;
	}

	public void setManagementStrategy(String managementStrategy) {
		this.managementStrategy = managementStrategy;
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
		return rebalancingDate;
	}
	public void setRebalncingDate(Date rebalancingDate) {
		this.rebalancingDate = rebalancingDate;
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
		return "PortfolioDto [id=" + id + ", name=" + name + ", strategy=" + strategy + ", detailStrategy="
				+ detailStrategy + ", managementStrategy=" + managementStrategy + ", status=" + status + ", limit="
				+ limit + ", rebalancingDate=" + rebalancingDate + ", portfolioDate=" + portfolioDate + ", updateDate="
				+ updateDate + "]";
	}

	
	
}
