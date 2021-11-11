package com.noah.app.vo;


import java.math.BigDecimal;
import java.util.Date;

public class HistoryDataDto {
	private String id;
	private Date tradingDate;
	private String itemId;
	private float open;
	private float close;
	private float low;
	private float high;
	private BigDecimal volume;
	
	public HistoryDataDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getTradingDate() {
		return tradingDate;
	}
	public void setTradingDate(Date tradingDate) {
		this.tradingDate = tradingDate;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public float getOpen() {
		return open;
	}
	public void setOpen(Number open) {
		this.open = open.floatValue();
	}
	public float getClose() {
		return close;
	}
	public void setClose(Number close) {
		this.close = close.floatValue();
	}
	public float getLow() {
		return low;
	}
	public void setLow(Number low) {
		this.low = low.floatValue();
	}
	public float getHigh() {
		return  high;
	}
	public void setHigh(Number high) {
		this.high = high.floatValue();
	}
	public void setOpen(float open) {
		this.open = open;
	}
	public void setClose(float close) {
		this.close = close;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(Number volume) {
		this.volume = new BigDecimal(volume.toString());
	}
	@Override
	public String toString() {
		return "HistoryDataDto [id=" + id + ", tradingDate=" + tradingDate + ", itemId=" + itemId + ", open=" + open
				+ ", close=" + close + ", low=" + low + ", high=" + high + ", volume=" + volume + "]";
	}
}
