package com.noah.app.quant.calculator.portfolioStrategy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.quant.dao.BalanceSheetDao;
import com.noah.app.quant.dao.ItemDao;
import com.noah.app.util.Statistics;
import com.noah.app.vo.BalanceSheetDto;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;
import com.noah.app.vo.StockWrapper;
@Component
public class StockPicker {
	
	@Autowired
	Statistics statistics;
	
	@Autowired
	ItemDao itemDao;
	
	@Autowired
	BalanceSheetDao balanceSheetDao;
	/*
	 * 언러키 컨트롤러로 inParam
	 */
	public List<ItemDto> sortStockByStrategy(Map<String, Object> inParam, List<ItemDto> itemList){
		String pickUpStrategy =(String) inParam.getOrDefault("pickUpStrategy", "");
		List<ItemDto> filteredStockList = new ArrayList<>();
		switch(pickUpStrategy) {
		case "3factor":
			filteredStockList=threeFactorStrategy(inParam, itemList);
		}
		return filteredStockList;
	}
	public List<ItemDto> threeFactorStrategy(Map<String, Object> inParam, List<ItemDto> itemList){
		/*
		 * 모멤텀 : 3개월 리턴, 6개월 리턴, 12개월 리턴
		 * 밸류 : per, pbr
		 * 퀄리티 : roe, 매출 종이익률
		 * 모두 구한다음에  표준화 하여서 합친다
		 * 표준화 한다음에 합친다.
		 */
		//momentTumValue 3m, 6m, 12m rate of return
		HashMap<String, Float> threeMonthReturnMap = new HashMap<>(); 
		HashMap<String, Float> sixMonthReturnMap = new HashMap<>(); 
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
			sixMonthReturnMap.put(item.getId(), (recentPrice-sixMonthPrePrice)/sixMonthPrePrice);
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
		/* 
		 * z-score 맵 구하기
		 */
		double threeMonthReturnMean = statistics.calculateMean(threeMonthReturnMap);
		double threeMonthReturnStd = statistics.calculateStd(threeMonthReturnMap);
		HashMap<String, Double> threeMonthReturnZScoreMap = statistics.calculateZScore(threeMonthReturnMap, threeMonthReturnMean, threeMonthReturnStd);
		double sixMonthReturnMean = statistics.calculateMean(sixMonthReturnMap);
		double sixMonthReturnStd = statistics.calculateStd(sixMonthReturnMap);
		HashMap<String, Double> sixMonthsReturnZScoreMap = statistics.calculateZScore(sixMonthReturnMap, sixMonthReturnMean, sixMonthReturnStd);
		double oneYearReturnMean = statistics.calculateMean(oneYearReturnMap);
		double oneYearReturnStd = statistics.calculateStd(oneYearReturnMap);
		HashMap<String, Double> oneYearReturnZScoreMap = statistics.calculateZScore(oneYearReturnMap, oneYearReturnMean, oneYearReturnStd);
		
		double perMean = statistics.calculateMean(perMap);
		double perStd = statistics.calculateMean(perMap);
		HashMap<String, Double> perZScoreMap = statistics.calculateZScore(perMap, perMean, perStd);
		double pbrMean = statistics.calculateMean(pbrMap);
		double pbrStd = statistics.calculateMean(pbrMap);
		HashMap<String, Double> pbrZScoreMap = statistics.calculateZScore(pbrMap, pbrMean, pbrStd);
		
		double roeMean = statistics.calculateMean(roeMap);
		double roeStd = statistics.calculateMean(roeMap);
		HashMap<String, Double> roeZScoreMap = statistics.calculateZScore(roeMap, roeMean, roeStd);
		double opmMean = statistics.calculateMean(opmMap);
		double opmStd = statistics.calculateMean(opmMap);
		HashMap<String, Double> opmZScoreMap = statistics.calculateZScore(opmMap, opmMean, opmStd);
		
		HashMap<String, Double> momentumMap = statistics.mergeZScore(threeMonthReturnZScoreMap, sixMonthsReturnZScoreMap,oneYearReturnZScoreMap);
		HashMap<String, Double> valueMap = statistics.mergeZScore(perZScoreMap, pbrZScoreMap);
		HashMap<String, Double> qualityMap = statistics.mergeZScore(roeZScoreMap, opmZScoreMap);
		
		HashMap<String, Double> totalScoreMap = statistics.mergeZScore(momentumMap, valueMap,qualityMap);
		
		/*
		 * ranking 줄세우기
		 */
		int len = (int) inParam.getOrDefault("length", 30);
		len = Math.min(len, totalScoreMap.size());
		List<String> keySetList = new ArrayList<>(totalScoreMap.keySet());
		Collections.sort(keySetList,(o1,o2)->(totalScoreMap.get(o2).compareTo(totalScoreMap.get(o1))));
		
		List<ItemDto> pickedItemList = new ArrayList<>();
		HashMap<String, ItemDto> itemMap = new HashMap<>();
		for(ItemDto item : itemList) {
			itemMap.put(item.getId(), item);
		}
		
		
		for(int i = 0; i < len; i++) {
			String key = keySetList.get(i);
			pickedItemList.add(itemMap.get(key));
		}
		
		return pickedItemList;
	}
}
