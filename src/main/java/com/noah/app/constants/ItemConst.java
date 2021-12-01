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
	public enum Category{
		Rt("CPC_KSE_SECTION_KIND_RT"),
		Mf("CPC_KSE_SECTION_KIND_MF"),
		Foreign("CPC_KSE_SECTION_KIND_FOREIGN"),
		If("CPC_KSE_SECTION_KIND_IF"),
		Sc("CPC_KSE_SECTION_KIND_SC"),
		St("CPC_KSE_SECTION_KIND_ST"),
		Etf("CPC_KSE_SECTION_KIND_ETF"),
		Dr("CPC_KSE_SECTION_KIND_DR"),
		Etn("CPC_KSE_SECTION_KIND_ETN");
		
		private final String value;
		
		Category(String value){
			this.value= value;
		}
		public String getValue(){ 
			return value; 
		}
	}
}
