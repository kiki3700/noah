package com.noah.app.quant.calculator.portfolioStrategy.filterStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

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
	
	public List<ItemDto> filter(Map<String, Object> inParam){
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
		inParam.put("isCopCode", true);
		inParam.put("category", ItemConst.Category.St.getValue());
		inParam.put("overTheYear", true);
		List<ItemDto> itemDtoList = itemMapper.selectItemDtoList(inParam);
		HashMap<String, ItemDto> itemMap = new HashMap<String,ItemDto>(itemDtoList.stream().collect(Collectors.toMap(ItemDto::getId,  Function.identity())));
		HashMap<String, BalanceSheetDto> balanceSheetMap =  batchDao.selectBalanceSheetMap(itemDtoList);
		/*
		 * 공사 들어가야 될 부분 
		 * 설계 일자 별 필요한 가격 가져오기
		 * 
		 */
		
		Set<String> commonKeySet =balanceSheetMap.keySet();
		List<HistoryDataDto> historyDataDtoListOneYearAgo = itemMapper.selectHistoryDataListByMonthHorizontally(12);
		HashMap<String, Float> oneYearHistoryDataDtoMap = new HashMap<>();
		for(HistoryDataDto historyDataDto : historyDataDtoListOneYearAgo) {
			if(commonKeySet.contains(historyDataDto.getItemId())) oneYearHistoryDataDtoMap.put(historyDataDto.getItemId(), historyDataDto.getClose());
		}
		historyDataDtoListOneYearAgo.clear();
		List<HistoryDataDto> historyDataDtoListSixMonthAgo = itemMapper.selectHistoryDataListByMonthHorizontally(6);
		HashMap<String, Float> sixMonthHistoryDataDtoMap = new HashMap<>();
		for(HistoryDataDto historyDataDto : historyDataDtoListSixMonthAgo) {
			if(commonKeySet.contains(historyDataDto.getItemId())) sixMonthHistoryDataDtoMap.put(historyDataDto.getItemId(), historyDataDto.getClose());
		}
		historyDataDtoListSixMonthAgo.clear();
		List<HistoryDataDto> historyDataDtoListThreeMonthAgo= itemMapper.selectHistoryDataListByMonthHorizontally(3);
		HashMap<String, Float> threeMonthHistoryDataDtoMap = new HashMap<>();
		for(HistoryDataDto historyDataDto : historyDataDtoListThreeMonthAgo) {
			if(commonKeySet.contains(historyDataDto.getItemId())) threeMonthHistoryDataDtoMap.put(historyDataDto.getItemId(), historyDataDto.getClose());
		}
		historyDataDtoListThreeMonthAgo.clear();
		List<HistoryDataDto> historyDataDtoListToday = itemMapper.selectHsitoryDataListByTodayHorizontally();
		HashMap<String, Float> todayHistoryDataDtoMap = new HashMap<>();
		for(HistoryDataDto historyDataDto : historyDataDtoListToday) {
			if(commonKeySet.contains(historyDataDto.getItemId())) todayHistoryDataDtoMap.put(historyDataDto.getItemId(), historyDataDto.getClose());
		}
		historyDataDtoListToday.clear();
		
		commonKeySet.retainAll(oneYearHistoryDataDtoMap.keySet());
		commonKeySet.retainAll(sixMonthHistoryDataDtoMap.keySet());
		commonKeySet.retainAll(threeMonthHistoryDataDtoMap.keySet());
		commonKeySet.retainAll(todayHistoryDataDtoMap.keySet());		
		
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
		HashMap<String, Double> roaMap = new HashMap<>();
 		
		logger.debug("==================================");
		logger.debug("start to make momentum Map");
		logger.debug("==================================");
		for(String item : commonKeySet) {
			double threeMonthCumRet =(todayHistoryDataDtoMap.get(item)-threeMonthHistoryDataDtoMap.get(item))/(threeMonthHistoryDataDtoMap.get(item));
			double sixMonthCumRet = (todayHistoryDataDtoMap.get(item)-sixMonthHistoryDataDtoMap.get(item))/(sixMonthHistoryDataDtoMap.get(item));
			double oneYearCumRet = (todayHistoryDataDtoMap.get(item)-oneYearHistoryDataDtoMap.get(item))/(oneYearHistoryDataDtoMap.get(item));
			
			threeMonthCumReturnMap.put(item, threeMonthCumRet);
			sixMonthCumReturnMap.put(item, sixMonthCumRet);
			oneYearCumReturnMap.put(item, oneYearCumRet);
		}
		logger.debug("==================================");
		logger.debug("end to make momentum Map");
		logger.debug("==================================");
		
		logger.debug("==================================");
		logger.debug("start to make value Map");
		logger.debug("==================================");
		List<HashMap<String, Object>> avgPriceList = itemMapper.selectAveragePriceByMonth(3);
		HashMap<String, Double> avgPriceMap = new HashMap<>();
		for(HashMap<String, Object> map : avgPriceList) {
			avgPriceMap.put((String) map.get("ITEM_ID"), ((BigDecimal)map.get("AVERAGEPRICE")).doubleValue());
			
		}
		avgPriceList.clear();

		for(String item : commonKeySet) {
			double avgPrice = avgPriceMap.get(item);
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
		for(String item : commonKeySet) {
			BalanceSheetDto balanceSheetDto = balanceSheetMap.get(item);
			ItemDto itemDto = itemMap.get(itemMap);
			double roe = balanceSheetDto.getNetIncome()/balanceSheetDto.getEquity();
			if(!Double.isInfinite(roe)&&!Double.isNaN(roe)) roeMap.put(item, roe);
			double opm = balanceSheetDto.getOperatingIncome()/balanceSheetDto.getRevenue();
			if(!Double.isInfinite(opm)&&!Double.isNaN(opm)) opmMap.put(item, opm);
			double roa = balanceSheetDto.getNetIncome()/balanceSheetDto.getAsset();
			if(!Double.isInfinite(roa)&&!Double.isNaN(roa)) roaMap.put(item, roa);
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
		HashMap<String, Double> roaMapZScore = quantUtil.calZScore(roaMap);
		HashMap<String, Double> opmMapZScore = quantUtil.calZScore(opmMap);

		

		HashMap<String, Double> momentumMap = quantUtil.mergeZScore(threeMonthCumReturnZScore, sixMonthCumReturnZScore,oneYearCumReturnZScore);
		HashMap<String, Double> valueMap = quantUtil.mergeZScore(perMapZScore, pbrMapZScore);
		HashMap<String, Double> qualityMap = quantUtil.mergeZScore(roeMapZScore, opmMapZScore, roaMapZScore);
		
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
		List<ItemDto> pickedItemList = new ArrayList<>();
		for(int i = 0 ; i < len; i++) {
			String item =keySetList.get(i);
			pickedItemList.add(itemMap.get(item));
		}
		logger.debug("==================================");
		logger.debug("end to filter stock");
		logger.debug("==================================");
		System.out.println("sss"+pickedItemList.size());
		return pickedItemList;
	}
}
