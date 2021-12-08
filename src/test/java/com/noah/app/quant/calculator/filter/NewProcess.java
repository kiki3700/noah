package com.noah.app.quant.calculator.filter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.constants.ItemConst;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewProcess {
	
	@Autowired
	ItemMapper itemMapper;
	
	@Test
	public void itemService() {
		HashMap<String, Object> inParam = new HashMap<>();
		inParam.put("category", ItemConst.Category.St.getValue());
		inParam.put("overTheYear", true);
		List<ItemDto> itemDtoList = itemMapper.selectItemDtoList(inParam);
		HashMap<String, ItemDto> itemMap = new HashMap<String,ItemDto>(itemDtoList.stream().collect(Collectors.toMap(ItemDto::getId,  Function.identity())));
		List<HistoryDataDto> history12 = itemMapper.selectHistoryDataListByMonthHorizontally(12);
		
		HashMap<String, Float> oneYearHistoryDataDtoList = new HashMap<>();
		HashMap<String, Float> sixMonthHistoryDataDtoList = new HashMap<>();
		HashMap<String, Float> map3 = new HashMap<>();
		Set<String> itemKeySet = itemMap.keySet(); 
		for(HistoryDataDto hi : history12) {
			if(itemKeySet.contains(hi.getItemId())) oneYearHistoryDataDtoList.put(hi.getItemId(), hi.getClose());
		}
		history12.clear();
		List<HistoryDataDto> history6 = itemMapper.selectHistoryDataListByMonthHorizontally(6);
		
		for(HistoryDataDto hi : history6) {
			if(itemKeySet.contains(hi.getItemId())) sixMonthHistoryDataDtoList.put(hi.getItemId(), hi.getClose());
		}
		history6.clear();
		List<HistoryDataDto> history3= itemMapper.selectHistoryDataListByMonthHorizontally(3);
		for(HistoryDataDto hi : history3) {
			if(itemKeySet.contains(hi.getItemId())) map3.put(hi.getItemId(), hi.getClose());
		}
		history3.clear();
		List<HistoryDataDto> history1 = itemMapper.selectHsitoryDataListByTodayHorizontally();
		HashMap<String, Float> map = new HashMap<>();
		for(HistoryDataDto hi : history1) {
			if(itemKeySet.contains(hi.getItemId())) {map.put(hi.getItemId(), hi.getClose());
			System.out.println(hi.getItemId()+" "+hi.getTradingDate()+" "+hi.getClose());
			}
		}
		Set<String> keySet = map.keySet();
		System.out.println(keySet.size());
		keySet.retainAll(oneYearHistoryDataDtoList.keySet());
		System.out.println(keySet.size());
		keySet.retainAll(sixMonthHistoryDataDtoList.keySet());
		System.out.println(keySet.size());
		keySet.retainAll(map3.keySet());
		System.out.println(keySet.size());
		keySet.retainAll(itemMap.keySet());
		System.out.println(keySet.size());
		
		HashMap<String, Double> threeMonthCumReturnMap = new HashMap<>(); 
		HashMap<String, Double> sixMonthCumReturnMap = new HashMap<>(); 
		HashMap<String, Double> oneYearCumReturnMap = new HashMap<>(); 
		for(String key : keySet) {
			double cumRet12 = (map.get(key)-oneYearHistoryDataDtoList.get(key))/(oneYearHistoryDataDtoList.get(key));
			double cumRet6 = (map.get(key)-sixMonthHistoryDataDtoList.get(key))/(sixMonthHistoryDataDtoList.get(key));
			double cumRet3 = (map.get(key)-map3.get(key))/(map3.get(key));
			threeMonthCumReturnMap.put(key, cumRet3);
			sixMonthCumReturnMap.put(key, cumRet6);
			oneYearCumReturnMap.put(key, cumRet12);
//			System.out.println( key+" "+cumRet12 +" "+cumRet6+" "+cumRet3);
		}
	}
	
	@Test
	public void avePrice() {
		List<HashMap<String, Object>> mapList = itemMapper.selectAveragePriceByMonth(3);
		for(HashMap<String, Object> map : mapList) {
			System.out.println(map.get("ITEM_ID")+" "+map.get("AVERAGEPRICE"));
		}
	}
	
	
}
