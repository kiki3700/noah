package com.noah.app.dto;
import java.math.BigInteger;
import java.util.Date;

public class ItemDto {
	private String id;
	
	private String corpCode;
	private String name;
	
	private int currencyId;
	private String market;
	private String isActive;
	private String category;
	private String industry;
	private Date listingDate;
	private String corpSize;
	private BigInteger listedShare; //상장 주식수 
	private BigInteger marketCap; //시가총액

	
	public ItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getListingDate() {
		return listingDate;
	}

	public void setListingDate(Date listingDate) {
		this.listingDate = listingDate;
	}



	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
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

	public BigInteger getListedShare() {
		return listedShare;
	}

	public void setListedShare(BigInteger listedShare) {
		this.listedShare = listedShare;
	}

	public BigInteger getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(BigInteger marketCap) {
		this.marketCap = marketCap;
	}

	@Override
	public String toString() {
		return "ItemDto [id=" + id + ", corpCode=" + corpCode + ", name=" + name + ", currencyId=" + currencyId
				+ ", market=" + market + ", isActive=" + isActive + ", category=" + category + ", industry=" + industry
				+ ", listingDate=" + listingDate + ", corpSize=" + corpSize + ", listedShare=" + listedShare
				+ ", marketCap=" + marketCap + "]";
	}


}
