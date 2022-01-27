package com.noah.app.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.noah.app.dto.parentClass.HistoryData;

public class IndexHistoryDataDto extends HistoryData{
	private String id;
	private String indexName;
	private Date indexDate;
	private float open;
	private float close;
	private float low;
	private float high;
	private BigDecimal volume;	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public Date getIndexDate() {
		return indexDate;
	}
	public void setIndexDate(Date indexDate) {
		this.indexDate = indexDate;
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
		return "HistoryDataDto [id=" + id + ", indexName=" + indexName + ", indexDate=" + indexDate + ", open=" + open
				+ ", close=" + close + ", low=" + low + ", high=" + high + ", volume=" + volume + "]";
	}
}
