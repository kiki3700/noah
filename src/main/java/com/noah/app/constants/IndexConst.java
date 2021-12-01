package com.noah.app.constants;

public class IndexConst {
	public enum KorIndex{
		Kospi("코스피"),
		Kosdaq("코스닥");
		private final String value;
		KorIndex(String value){
			this.value = value;
		}
		public String getValue(){ return value; }
	}
	
	public enum BokIndex{
		BaseRate("BOK BASE RATE"),
		Ppi("PPI"),
		Cpi("CPI"),
		Gdp("GDP");
		private final String value;
		BokIndex(String value){
			this.value = value;
		}
		public String getValue(){ return value; }
	}
}
