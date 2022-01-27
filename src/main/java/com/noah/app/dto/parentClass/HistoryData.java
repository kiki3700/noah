package com.noah.app.dto.parentClass;

import java.math.BigDecimal;
import java.util.Date;

public class HistoryData {
	private String id;
	private Date tradingDate;
	private String itemId;
	private float open;
	private float close;
	private float low;
	private float high;
	private BigDecimal volume;
	public HistoryData() {
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
	public void setOpen(float open) {
		this.open = open;
	}
	public float getClose() {
		return close;
	}
	public void setClose(float close) {
		this.close = close;
	}
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	@Override
	public String toString() {
		return "HistoryData [id=" + id + ", tradingDate=" + tradingDate + ", itemId=" + itemId + ", open=" + open
				+ ", close=" + close + ", low=" + low + ", high=" + high + ", volume=" + volume + "]";
	}
	
}
