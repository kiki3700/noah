package com.noah.app.vo;

import org.apache.ibatis.type.Alias;

@Alias("PriceVo")
public class PriceVo {
	private String name;
	private float open;
	private float close;
	private float high;
	private float low;
	private String time;
	public PriceVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PriceVo(String name, float open, float close, float high, float low, String time) {
		super();
		this.name = name;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public float getHigh() {
		return high;
	}
	public void setHigh(float high) {
		this.high = high;
	}
	public float getLow() {
		return low;
	}
	public void setLow(float low) {
		this.low = low;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "PriceVo [name=" + name + ", open=" + open + ", close=" + close + ", high=" + high + ", low=" + low
				+ ", time=" + time + "]";
	}
	
}
