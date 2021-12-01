package com.noah.app.quant.calculator.portfolioStrategy.filterStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.constants.BusinessDays;
import com.noah.app.constants.ItemConst;
import com.noah.app.quant.dao.BatchDao;
import com.noah.app.quant.mapper.BalanceSheetMapper;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.util.QuantUtils;
import com.noah.app.vo.BalanceSheetDto;
import com.noah.app.vo.ItemDto;
import com.noah.app.wrapper.StockWrapper;

@Component
public class ThreeFactorModel {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	QuantUtils quantUtil;
	
	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	BatchDao batchDao;
	
	@Autowired
	BalanceSheetMapper balanceSheetDao;
	
	public List<StockWrapper> filter(Map<String, Object> inParam){
//		/*
//		 * 모멤텀 : 3개월 리턴, 6개월 리턴, 12개월 리턴
//		 * 밸류 : per, pbr
//		 * 퀄리티 : roe, 매출 종이익률
//		 * 모두 구한다음에  표준화 하여서 합친다
//		 * 표준화 한다음에 합친다.
//		 */
		logger.debug("================================");
		logger.debug("fiter start");
		logger.debug("================================");
		
		logger.debug("convert itemList to itemMap");
		inParam.put("category", ItemConst.Category.St.getValue());
		inParam.put("overTheYear", true);
		System.out.println(ItemConst.Category.St.getValue());
		List<ItemDto> itemDtoList = itemMapper.selectItemDtoList(inParam);
		System.out.println(itemDtoList.toString());
		HashMap<String, ItemDto> itemMap = new HashMap<String,ItemDto>(itemDtoList.stream().collect(Collectors.toMap(ItemDto::getId,  Function.identity())));
		
		HashMap<String, BalanceSheetDto> balanceSheetMap =  batchDao.selectBalanceSheetMap(itemDtoList);
		HashMap<String, TreeMap<Date,Float>> priceListMap = batchDao.selectPriceDataTreeMap(itemDtoList);
		
		Set<String> balanceSheetKey =  balanceSheetMap.keySet();
		Set<String> historyDataDtoKey = priceListMap.keySet();
		historyDataDtoKey.retainAll(balanceSheetKey);
		
		
		//momentTumValue 3m, 6m, 12m rate of return		
		HashMap<String, Double> threeMonthCumReturnMap = new HashMap<>(); 
		HashMap<String, Double> sixMonthCumReturnMap = new HashMap<>(); 
		HashMap<String, Double> oneYearCumReturnMap = new HashMap<>(); 
		
		//value : per, pbr
		HashMap<String, Double> perMap = new HashMap<>();
		HashMap<String, Double> pbrMap = new HashMap<>();
		
		//quality
		HashMap<String, Double> roeMap = new HashMap<>();
		HashMap<String, Double> opmMap = new HashMap<>();
		
		logger.debug("==================================");
		logger.debug("start to make momentum Map");
		logger.debug("==================================");
		for(String item : historyDataDtoKey) {
			BigDecimal threeMonthCumRet =quantUtil.calCumRet(priceListMap.get(item), BusinessDays.THREEMONTHS);
			BigDecimal sixMonthCumRet = quantUtil.calCumRet(priceListMap.get(item), BusinessDays.SIXMONTHS);
			BigDecimal oneYearCumRet = quantUtil.calCumRet(priceListMap.get(item), BusinessDays.ONEYEAR);
			
			threeMonthCumReturnMap.put(item, threeMonthCumRet.doubleValue());
			sixMonthCumReturnMap.put(item, sixMonthCumRet.doubleValue());
			oneYearCumReturnMap.put(item, oneYearCumRet.doubleValue());
		}
		logger.debug("==================================");
		logger.debug("end to make momentum Map");
		logger.debug("==================================");
		
		logger.debug("==================================");
		logger.debug("start to make value Map");
		logger.debug("==================================");
		for(String item : historyDataDtoKey) {
			double avgPrice = 0;
			int len = priceListMap.get(item).size();
			Date[] dateArr = priceListMap.get(item).keySet().toArray(new Date[len]);
			Date fromDate = dateArr[len-1-BusinessDays.THREEMONTHS.getDates()];
			TreeMap<Date, Float> tailMap = new TreeMap<>(priceListMap.get(item).tailMap(fromDate));
			for(Date date : tailMap.keySet()) {
				avgPrice += tailMap.get(date)/(len-1);
			}
			ItemDto itemDto = itemMap.get(item);
			BalanceSheetDto balanceSheetDto = balanceSheetMap.get(item);
			BigInteger listedShare = itemDto.getListedShare();
			double earningPerShare =  balanceSheetDto.getNetIncome()/itemDto.getListedShare().doubleValue();
			double per = avgPrice/earningPerShare;
			double bps = balanceSheetDto.getEquity()/itemDto.getListedShare().doubleValue();
			double pbr = avgPrice/bps;
			if(!Double.isInfinite(per)&&!Double.isNaN(per)&& per>0) perMap.put(item, per);
			if(!Double.isInfinite(pbr)&&!Double.isNaN(pbr)&&pbr>0) pbrMap.put(item, pbr);
		}
		logger.debug("==================================");
		logger.debug("end to make value Map");
		logger.debug("==================================");
		
		logger.debug("==================================");
		logger.debug("start to make quality Map");
		logger.debug("==================================");
		for(String item : historyDataDtoKey) {
			BalanceSheetDto balanceSheetDto = balanceSheetMap.get(item);
			ItemDto itemDto = itemMap.get(itemMap);
			double roe = balanceSheetDto.getNetIncome()/balanceSheetDto.getEquity();
			if(!Double.isInfinite(roe)&&!Double.isNaN(roe)) roeMap.put(item, roe);
			double opm = balanceSheetDto.getOperatingIncome()/balanceSheetDto.getRevenue();
			if(!Double.isInfinite(opm)&&!Double.isNaN(opm)) opmMap.put(item, opm);
		}
		logger.debug("==================================");
		logger.debug("end to make quality Map");
		logger.debug("==================================");
		
		HashMap<String, Double> threeMonthCumReturnZScore = quantUtil.calZScore(threeMonthCumReturnMap);

		HashMap<String, Double> sixMonthCumReturnZScore = quantUtil.calZScore(sixMonthCumReturnMap); 

		HashMap<String, Double> oneYearCumReturnZScore= quantUtil.calZScore(oneYearCumReturnMap); 

		//value : per, pbr
		HashMap<String, Double> perMapZScore =quantUtil.calZScore(perMap);

		HashMap<String, Double> pbrMapZScore = quantUtil.calZScore(pbrMap);

		//quality
		HashMap<String, Double> roeMapZScore = quantUtil.calZScore(roeMap);

		HashMap<String, Double> opmMapZScore = quantUtil.calZScore(opmMap);

		

		HashMap<String, Double> momentumMap = quantUtil.mergeZScore(threeMonthCumReturnZScore, sixMonthCumReturnZScore,oneYearCumReturnZScore);
		HashMap<String, Double> valueMap = quantUtil.mergeZScore(perMapZScore, pbrMapZScore);
		HashMap<String, Double> qualityMap = quantUtil.mergeZScore(roeMapZScore, opmMapZScore);
		
		Set<String> keySet = momentumMap.keySet();
		logger.debug("three month ret map size: "+threeMonthCumReturnZScore.size());
		logger.debug("six month ret map size : "+sixMonthCumReturnZScore.size());
		logger.debug("oneYear ret map size:  : "+ oneYearCumReturnZScore.size());
		logger.debug("per map size:  "+perMapZScore.size());
		logger.debug("pbr map size:  "+pbrMapZScore.size());
		logger.debug("roe map size:   "+roeMapZScore.size());
		logger.debug("opm map size:   "+opmMapZScore.size());
		logger.debug("mom map size:  "+momentumMap.size()+" valMapmap size:   "+valueMap.size()+" qualMap map size:   "+qualityMap.size());
		logger.debug("keySet size :" + keySet.size());
		keySet.retainAll(valueMap.keySet());

		keySet.retainAll(qualityMap.keySet());
		logger.debug("total key size "+ keySet.size());
		HashMap<String, Double> totalScoreMap = new HashMap<>();
		
		if(keySet.size()==0) return null;
		for(String item : keySet) {
			double totalScore = momentumMap.get(item)/3-valueMap.get(item)/3+qualityMap.get(item);
//			System.out.println(totalScore);
			totalScoreMap.put(item, totalScore);
		}
		int len = (int) inParam.getOrDefault("length", 30);
		List<String> keySetList = new ArrayList<>(totalScoreMap.keySet());
		Collections.sort(keySetList,(o1,o2)->(totalScoreMap.get(o2).compareTo(totalScoreMap.get(o1))));
		List<StockWrapper> pickedItemList = new ArrayList<>();
		for(int i = 0 ; i < len; i++) {
			String item =keySetList.get(i);
			StockWrapper stockWrapper = new StockWrapper();
			stockWrapper.setItemDto(itemMap.get(item));
			pickedItemList.add(stockWrapper);
		}
		logger.debug("==================================");
		logger.debug("end to filter stock");
		logger.debug("==================================");
		return pickedItemList;
	}
}
