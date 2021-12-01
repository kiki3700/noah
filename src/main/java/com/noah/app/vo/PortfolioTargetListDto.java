package com.noah.app.vo;

public class PortfolioTargetListDto {
	private String id;
	private String itemid;
	private String portfolioId;
	private double weight;
	private String status;
	private int amount;
	public PortfolioTargetListDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getPortfolioId() {
		return portfolioId;
	}
	public void setPortfolioId(String portfolioId) {
		this.portfolioId = portfolioId;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "PortfolioTargetListDto [id=" + id + ", itemid=" + itemid + ", portfolioId=" + portfolioId + ", weight="
				+ weight + ", status=" + status + ", amount=" + amount + "]";
	}
	
}
