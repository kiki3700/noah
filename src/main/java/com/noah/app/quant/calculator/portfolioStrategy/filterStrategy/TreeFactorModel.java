package com.noah.app.quant.calculator.portfolioStrategy.filterStrategy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.constants.BusinessDays;
import com.noah.app.quant.dao.BatchDao;
import com.noah.app.quant.mapper.BalanceSheetMapper;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.util.QuantUtils;
import com.noah.app.vo.BalanceSheetDto;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

@Component
public class TreeFactorModel {
	
	@Autowired
	QuantUtils quant;
	
	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	BatchDao batchDto;
	
	@Autowired
	BalanceSheetMapper balanceSheetDao;
	
	public List<ItemDto> filt(Map<String, Object> inParam, List<ItemDto> itemList){

		return null;
	}
	
	public List<ItemDto> filter(Map<String, Object> inParam, List<ItemDto> itemList){
		/*
		 * 모멤텀 : 3개월 리턴, 6개월 리턴, 12개월 리턴
		 * 밸류 : per, pbr
		 * 퀄리티 : roe, 매출 종이익률
		 * 모두 구한다음에  표준화 하여서 합친다
		 * 표준화 한다음에 합친다.
		 */
		//momentTumValue 3m, 6m, 12m rate of return
		
		
		HashMap<String, Double> threeMonthReturnMap = new HashMap<>(); 
		HashMap<String, Double> sixMonthReturnMap = new HashMap<>(); 
		HashMap<String, Double> oneYearReturnMap = new HashMap<>(); 
		
		//value : per, pbr
		HashMap<String, Double> perMap = new HashMap<>();
		HashMap<String, Double> pbrMap = new HashMap<>();
		
		//quality
		HashMap<String, Double> roeMap = new HashMap<>();
		HashMap<String, Double> opmMap = new HashMap<>();
		List<ItemDto> nullItemDtoList = new ArrayList<>();
		int cnt = 0;
		for(ItemDto item : itemList) {
			BalanceSheetDto balanceSheet = balanceSheetDao.selectBalanceSheetByYear(item);
			if(balanceSheet == null) {
				continue;
			}
			HashMap<String, Object> inParams = new HashMap<>();
			inParams.put("period", BusinessDays.ONEYEAR.getDates());
			inParams.put("itemDto", item);			
			List<HistoryDataDto> historyDataDtoList = itemMapper.selectHistoryDataList(inParams);
			if(historyDataDtoList.size()<255) {
				
				continue;
			}
			TreeMap<Date, Float> priceMap = quant.toPriceMap(historyDataDtoList);
			TreeMap<Date, Double> returnMap = quant.toReturnMap(priceMap);

			/*밸런스 시트 관련해서 로직 생각해보기*/
			
			//get momentum Value;

			threeMonthReturnMap.put(item.getId(), quant.calCumRet(returnMap, BusinessDays.THREEMONTHS).doubleValue()); 
			sixMonthReturnMap.put(item.getId(), quant.calCumRet(returnMap,BusinessDays.SIXMONTHS).doubleValue());
			oneYearReturnMap.put(item.getId(), quant.calCumRet(returnMap, BusinessDays.ONEYEAR).doubleValue());
			
			int averagePrice  = 0;
			for(int i = 0; i<BusinessDays.THREEMONTHS.getDates(); i++) {
				averagePrice+=historyDataDtoList.get(i).getClose()/BusinessDays.THREEMONTHS.getDates();
			}
			
			BigInteger listedShare = item.getListedShare();
			double ni = balanceSheet.getNetIncome();
			double equity = balanceSheet.getEquity();
			
			double per = listedShare.multiply(BigInteger.valueOf(averagePrice)).doubleValue()/ni;
			double pbr = listedShare.multiply(BigInteger.valueOf(averagePrice)).doubleValue()/equity;
			
			perMap.put(item.getId(), per);
			pbrMap.put(item.getId(), pbr);
			
			double operateIncome = balanceSheet.getOperatingIncome();
			double revenue = balanceSheet.getRevenue();
			
			double roe = ni/equity;
			double opm = operateIncome/revenue;
			
			roeMap.put(item.getId(), roe);
			opmMap.put(item.getId(), opm);
			cnt++;
			
		}
		/*
		 * z-score 맵 구하기
		 */
		double threeMonthReturnMean = quant.calMean(threeMonthReturnMap);
		double threeMonthReturnStd = quant.calStdv(threeMonthReturnMap);
		HashMap<String, Double> threeMonthReturnZScoreMap = quant.calZScore(threeMonthReturnMap, threeMonthReturnMean, threeMonthReturnStd);
		double sixMonthReturnMean = quant.calMean(sixMonthReturnMap);
		double sixMonthReturnStd = quant.calStdv(sixMonthReturnMap);
		HashMap<String, Double> sixMonthsReturnZScoreMap = quant.calZScore(sixMonthReturnMap, sixMonthReturnMean, sixMonthReturnStd);
		double oneYearReturnMean = quant.calMean(oneYearReturnMap);
		double oneYearReturnStd = quant.calStdv(oneYearReturnMap);
		HashMap<String, Double> oneYearReturnZScoreMap = quant.calZScore(oneYearReturnMap, oneYearReturnMean, oneYearReturnStd);
		
		double perMean = quant.calMean(perMap);
		double perStd = quant.calMean(perMap);
		HashMap<String, Double> perZScoreMap = quant.calZScore(perMap, perMean, perStd);
		
		double pbrMean = quant.calMean(pbrMap);
		double pbrStd = quant.calMean(pbrMap);
		HashMap<String, Double> pbrZScoreMap = quant.calZScore(pbrMap, pbrMean, pbrStd);
		
		double roeMean = quant.calMean(roeMap);
		double roeStd = quant.calMean(roeMap);
		HashMap<String, Double> roeZScoreMap = quant.calZScore(roeMap, roeMean, roeStd);
		
		double opmMean = quant.calMean(opmMap);
		double opmStd = quant.calMean(opmMap);
		HashMap<String, Double> opmZScoreMap = quant.calZScore(opmMap, opmMean, opmStd);
		HashMap<String, Double> momentumMap = quant.mergeZScore(threeMonthReturnZScoreMap, sixMonthsReturnZScoreMap,oneYearReturnZScoreMap);
		
		HashMap<String, Double> valueMap = quant.mergeZScore(perZScoreMap, pbrZScoreMap);
		HashMap<String, Double> qualityMap = quant.mergeZScore(roeZScoreMap, opmZScoreMap);
		HashMap<String, Double> totalScoreMap = quant.mergeZScore(momentumMap, valueMap, qualityMap);
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
