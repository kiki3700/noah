package com.noah.app.quant.calculator.portfolioStrategy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.quant.dao.BalanceSheetDao;
import com.noah.app.quant.dao.ItemDao;
import com.noah.app.vo.BalanceSheetDto;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;
import com.noah.app.vo.StockWrapper;
@Component
public class PortfolioCalulator {
	
	@Autowired
	ItemDao itemDao;
	
	@Autowired
	BalanceSheetDao balanceSheetDao;
	/*
	 * 언러키 컨트롤러로 inParam
	 */
	public List<StockWrapper> sortStockByStrategy(Map<String, Object> inParam, List<ItemDto> itemList){
		String strategy =(String) inParam.getOrDefault("strategy", "");
		List<StockWrapper> filteredStockList = new ArrayList<>();
		switch(strategy) {
		case "3factor":
			filteredStockList=threeFactorStrategy(inParam, itemList);
		}
		return null;
	}
	public List<StockWrapper> threeFactorStrategy(Map<String, Object> inParam, List<ItemDto> itemList){
		/*
		 * 모멤텀 : 3개월 리턴, 6개월 리턴, 12개월 리턴
		 * 밸류 : per, pbr
		 * 퀄리티 : roe, 매출 종이익률
		 * 모두 구한다음에  표준화 하여서 합친다
		 * 표준화 한다음에 합친다.
		 */
		//momentTumValue 3m, 6m, 12m rate of return
		HashMap<String, Float> threeMonthReturnMap = new HashMap<>(); 
		HashMap<String, Float> SixMonthReturnMap = new HashMap<>(); 
		HashMap<String, Float> oneYearReturnMap = new HashMap<>(); 
		
		//value : per, pbr
		HashMap<String, Double> perMap = new HashMap<>();
		HashMap<String, Double> pbrMap = new HashMap<>();
		
		//quality
		HashMap<String, Double> roeMap = new HashMap<>();
		HashMap<String, Double> opmMap = new HashMap<>();
		
		for(ItemDto item : itemList) {
			List<HistoryDataDto> historyDataDtoList = itemDao.selectHistoryDataByItemId(item);
			BalanceSheetDto balanceSheet = balanceSheetDao.selectBalanceSheet(item);
			//get momentum Value;
			if(historyDataDtoList.size()<255) {
				itemList.remove(item);
				continue;
			}
			float recentPrice = historyDataDtoList.get(0).getClose();
			float threeMonthPrePrice = historyDataDtoList.get(59).getClose();
			float sixMonthPrePrice = historyDataDtoList.get(120).getClose();
			float oneYearPrePrice = historyDataDtoList.get(249).getClose();
			threeMonthReturnMap.put(item.getId(), (recentPrice-threeMonthPrePrice)/threeMonthPrePrice); 
			SixMonthReturnMap.put(item.getId(), (recentPrice-sixMonthPrePrice)/sixMonthPrePrice);
			oneYearReturnMap.put(item.getId(), (recentPrice-oneYearPrePrice)/oneYearPrePrice);
			
			int averagePrice  = 0;
			for(int i = 0; i<60; i++) {
				averagePrice+=historyDataDtoList.get(i).getClose()/60;
			}
			
			BigInteger listedShare = item.getListedShare();
			double ni = balanceSheet.getNetIncome();
			double equity = balanceSheet.getEquity();
			
			double per = listedShare.multiply(BigInteger.valueOf(averagePrice)).doubleValue()/ni;
			double pbr = listedShare.multiply(BigInteger.valueOf(averagePrice)).doubleValue()/equity;
			
			perMap.put(item.getId(), per);
			pbrMap.put(item.getId(), pbr);
			
			double operateIncome = balanceSheet.getOperatinIncome();
			double revenue = balanceSheet.getRevenue();
			
			double roe = ni/equity;
			double opm = operateIncome/revenue;
			
			roeMap.put(item.getId(), roe);
			opmMap.put(item.getId(), opm);
		}
		return null;
	}
}
