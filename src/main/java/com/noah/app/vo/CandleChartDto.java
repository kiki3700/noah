package com.noah.app.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CandleChartDto {
	private List<List<Object>> array;
	private List<Object> cols;
	
	public CandleChartDto() {
		this.array = new ArrayList<List<Object>>();
		//this.cols = new ArrayList<Object>();
		//this.array.add(cols);
	}
	
	public void addCol(String date, float low, float open, float close, float high) {
		List<Object> col = new ArrayList<>();
		col.add(date);
		col.add(low);
		col.add(open);
		col.add(close);
		col.add(high);
		this.array.add(col);
	}
	public List<List<Object>> getResult(){
		return this.array;
	}
}
