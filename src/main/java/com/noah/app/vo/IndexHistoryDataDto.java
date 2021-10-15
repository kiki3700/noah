package com.noah.app.vo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IndexHistoryDataDto {
	private int id;
	private String INDEX_NAME;
	private int tradingDate;
	private float open;
	private float close;
	private float low;
	private float high;
	private BigDecimal volume;	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getINDEX_NAME() {
		return INDEX_NAME;
	}
	public void setINDEX_NAME(String iNDEX_NAME) {
		INDEX_NAME = iNDEX_NAME;
	}
	public int getTradingDate() {
		return tradingDate;
	}
	public void setTradingDate(int tradingDate) {
		this.tradingDate = tradingDate;
	}
	public void setTradingDate(Date tradingDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String formattedDate = format.format(tradingDate);
		this.tradingDate =  Integer.parseInt(formattedDate);
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
		return "HistoryDataDto [id=" + id + ", INDEX_NAME=" + INDEX_NAME + ", tradingDate=" + tradingDate + ", open=" + open
				+ ", close=" + close + ", low=" + low + ", high=" + high + ", volume=" + volume + "]";
	}
}
