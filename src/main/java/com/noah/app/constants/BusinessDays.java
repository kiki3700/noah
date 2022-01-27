package com.noah.app.constants;

public enum BusinessDays {
	ONEYEAR(12, 255),
	SIXMONTHS(6, 120),
	THREEMONTHS(3, 60),
	ONEMONTH(1, 20);
	
	private final int idx;
	private final int dates;
	
	
	BusinessDays(int idx, int dates){
		this.idx = idx;
		this.dates = dates;
	}
	
	public int getDates() {return dates;}
}
