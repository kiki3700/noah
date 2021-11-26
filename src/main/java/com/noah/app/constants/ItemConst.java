package com.noah.app.constants;

public class ItemConst {
	public enum CorpSize {
		Large("CPC_CAPITAL_LARGE"),
		Middle("CPC_CAPITAL_MIDDLE"),
		Small("CPC_CAPITAL_SMALL"),
		Null("CPC_CAPITAL_NULL"),
		Total(null);
		private final String value;
		
		CorpSize(String value){
			this.value= value;
		}
		public String getValue(){ return value; }
	}
	
	public enum Market{
		Kospi("CPC_MARKET_KOSPI"),
		Kosdaq("CPC_MARKET_KOSDAQ");
		private final String value;
		
		Market(String value){
			this.value= value;
		}
		public String getValue(){ return value; }
	}
	
	public enum Status {
		Active("CPC_STOCK_STATUS_NORMAL"),
		Stop("CPC_STOCK_STATUS_STOP");
		
		private final String value;
		
		Status(String value){
			this.value= value;
		}
		public String getValue(){ 
			return value; 
		}
	}
}
