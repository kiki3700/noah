package com.noah.app.quant.calculator.portfolioStrategy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.constants.BusinessDays;
import com.noah.app.constants.ItemConst;
import com.noah.app.quant.calculator.portfolioStrategy.filterStrategy.ThreeFactorModel;
import com.noah.app.quant.dao.BatchDao;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.util.QuantUtils;
import com.noah.app.vo.BalanceSheetDto;
import com.noah.app.vo.ItemDto;
import com.noah.app.wrapper.StockWrapper;
@Component
public class StockPicker {
	
	
	@Autowired
	ItemMapper itemMapper;

	@Autowired
	BatchDao batchDao;
	
	@Autowired
	QuantUtils quantUtil;
	
	@Autowired
	ThreeFactorModel threeFactorModel;
	/*
	 * 언러키 컨트롤러로 inParam
	 */
	public List<ItemDto> sortStockByStrategy(Map<String, Object> inParam){
//		String corpSize = (String)inParam.getOrDefault("corpSize", null);


		String pickUpStrategy =(String) inParam.getOrDefault("selectionModel", "");
		List<ItemDto> filteredStockList = new ArrayList<>();
		switch(pickUpStrategy) {
		case "3factor":
			filteredStockList=threeFactorModel.filter(inParam);
		}
		return filteredStockList;
	}
	/*
	 * 수정해야함 => 
	 */
	public List<StockWrapper> getSelectStockTalbe(List<ItemDto> itemDtoList){
		HashMap<String, ItemDto> itemMap = new HashMap<String,ItemDto>(itemDtoList.stream().collect(Collectors.toMap(ItemDto::getId,  Function.identity())));
		HashMap<String, BalanceSheetDto> balanceSheetMap =  batchDao.selectBalanceSheetMap(itemDtoList);
		HashMap<String, TreeMap<Date,Float>> priceListMap = batchDao.selectPriceDataTreeMap(itemDtoList);
		
		//momentTumValue 3m, 6m, 12m rate of return		
		HashMap<String, Double> oneMonthCumReturnMap = new HashMap<>(); 
		HashMap<String, Double> sixMonthCumReturnMap = new HashMap<>(); 
		HashMap<String, Double> oneYearCumReturnMap = new HashMap<>(); 
		
		//value : per, pbr
		HashMap<String, Double> perMap = new HashMap<>();
		HashMap<String, Double> pbrMap = new HashMap<>();
		
		//quality
		HashMap<String, Double> roeMap = new HashMap<>();
		HashMap<String, Double> roaMap = new HashMap<>();
		HashMap<String, Double> opmMap = new HashMap<>();
		
		Set<String> balanceSheetKey =  balanceSheetMap.keySet();
		Set<String> historyDataDtoKey = priceListMap.keySet();
		historyDataDtoKey.retainAll(balanceSheetKey);
		
		for(String item : historyDataDtoKey) {
			BigDecimal oneMonthCumRet =quantUtil.calCumRet(priceListMap.get(item), BusinessDays.ONEMONTH);
			BigDecimal sixMonthCumRet = quantUtil.calCumRet(priceListMap.get(item), BusinessDays.SIXMONTHS);
			BigDecimal oneYearCumRet = quantUtil.calCumRet(priceListMap.get(item), BusinessDays.ONEYEAR);
			
			oneMonthCumReturnMap.put(item, oneMonthCumRet.doubleValue());
			sixMonthCumReturnMap.put(item, sixMonthCumRet.doubleValue());
			oneYearCumReturnMap.put(item, oneYearCumRet.doubleValue());
		}
		
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
		
		for(String item : historyDataDtoKey) {
			BalanceSheetDto balanceSheetDto = balanceSheetMap.get(item);
			ItemDto itemDto = itemMap.get(itemMap);
			double roe = balanceSheetDto.getNetIncome()/balanceSheetDto.getEquity();
			if(!Double.isInfinite(roe)&&!Double.isNaN(roe)) roeMap.put(item, roe);
			double opm = balanceSheetDto.getOperatingIncome()/balanceSheetDto.getRevenue();
			if(!Double.isInfinite(opm)&&!Double.isNaN(opm)) opmMap.put(item, opm);
			double roa = balanceSheetDto.getNetIncome()/balanceSheetDto.getAsset();
			if(!Double.isInfinite(roa)&&!Double.isNaN(roa)) roaMap.put(item, roa);
		}
		
		
		List<StockWrapper> stockWrapperList = new ArrayList<>();
		for(String item : historyDataDtoKey) {
			StockWrapper stockWrapper = new StockWrapper();
			TreeMap<Date, Float> priceTreeMap = priceListMap.get(item);			
			stockWrapper.setItemDto(itemMap.get(item));
			stockWrapper.setCurPrice(priceTreeMap.get(priceTreeMap.lastKey()));
			stockWrapper.setRoe(roeMap.get(item));
			stockWrapper.setRoa(roaMap.get(item));
			stockWrapper.setOpm(opmMap.get(item));
			stockWrapper.setOneMonthReturn(oneYearCumReturnMap.get(item));
			stockWrapper.setSixMonthsReturn(sixMonthCumReturnMap.get(item));
			stockWrapper.setOneMonthReturn(oneMonthCumReturnMap.get(item));
			stockWrapper.setPer(perMap.get(item));
			stockWrapper.setPbr(pbrMap.get(item));
			stockWrapperList.add(stockWrapper);
		}
		return stockWrapperList;
	}
}
