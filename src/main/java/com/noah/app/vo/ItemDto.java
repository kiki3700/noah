package com.noah.app.vo;

import java.util.Date;

public class ItemDto {
	private int id;
	
	private String ticker;
	private String corpId;
	private String name;
	
	private int currencyId;
	private String market;
	private String isActive;
	private String category;
	private String industry;
	private Date listingDate;
	private String corpSize;
	
	
	public ItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getListingDate() {
		return listingDate;
	}

	public void setListingDate(Date listingDate) {
		this.listingDate = listingDate;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public int getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(int currencyId) {
		this.currencyId = currencyId;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getCorpSize() {
		return corpSize;
	}

	public void setCorpSize(String corpSize) {
		this.corpSize = corpSize;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@Override
	public String toString() {
		return "ItemDto [id=" + id + ", listingDate=" + listingDate + ", ticker=" + ticker + ", corpId=" + corpId
				+ ", name=" + name + ", isActive=" + isActive + ", currencyId=" + currencyId + ", market=" + market
				+ ", corpSize=" + corpSize + ", category=" + category + ", industry=" + industry + "]";
	}
}
