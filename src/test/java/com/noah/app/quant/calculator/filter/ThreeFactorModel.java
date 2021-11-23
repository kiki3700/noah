package com.noah.app.quant.calculator.filter;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.constants.BusinessDays;
import com.noah.app.quant.calculator.portfolioStrategy.filterStrategy.TreeFactorModel;
import com.noah.app.quant.dao.BatchDao;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.util.QuantUtils;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreeFactorModel {
	@Autowired
	TreeFactorModel threeFactorModel;
	
	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	QuantUtils quant;
	
	@Autowired
	BatchDao batchDao;
	
	List<ItemDto> itemDtoList;
	HashMap<String, Object> inParam;
	
	
	
	@Before
	public void init() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("isCorpCode", true);
		
		itemDtoList = itemMapper.selectItemDtoList(map);
	}
//	@Test
//	public void fetByYear() {
//		//힙메모리 초과
//		List<HistoryDataDto> historyDataDtoList = itemMapper.selectHistoryDataByYear();
//		for(HistoryDataDto dto : historyDataDtoList) {
//		}
//	}
	
	
/*	너무 느려서 안사용할 예정
	@Test
	public void filterTest() {
		List<ItemDto> filterdItemDtoList = threeFactorModel.filter(null, itemDtoList);
		for(ItemDto item : filterdItemDtoList) {
			System.out.println(item);
		}
	}
	*/
//	@Test
//	public void selectUnionHistoryData() {
//		List<ItemDto> itemList = new ArrayList<>();
//		for(int i = 0 ; i<1000; i++) {
//			itemList.add(itemDtoList.get(i));
//		}
//		List<HistoryDataDto> historyDataDtoList =itemMapper.selectHistoryDataListUnion(itemList);
//		for(HistoryDataDto dt : historyDataDtoList) {
//			System.out.println(dt);
//		}
//	}
	
//	@Test
//	public void selectHistoryDataPerfomance() {
//		HashMap<String, Object> inParams = new HashMap<>();
//		inParams.put("period", BusinessDays.ONEYEAR.getDates());
//		for(ItemDto item : itemDtoList) {
//			System.out.println(item);
//			inParams.put("itemDto", item);			
//			List<HistoryDataDto> historyDataDtoList = itemMapper.selectHistoryDataList(inParams);
//			TreeMap<Date, Float> priceMap = quant.toPriceMap(historyDataDtoList);
//			TreeMap<Date, Double> returnMap = quant.toReturnMap(priceMap);
//			System.out.println(returnMap.toString());
//		}
//	}
//	@Test
//	public void selectHistoryDataBatchPerformance() {
//		//인덱스 추가로 11분=> 2분대로 성능향상
//		HashMap<String, Object> inParams = new HashMap<>();
//		inParams.put("period", BusinessDays.ONEYEAR.getDates());
//		HashMap<String, List<HistoryDataDto>> resultMap = batchDao.selectHistoryDataListMap(itemDtoList, inParams);
////		for(String key : resultMap.keySet()) {
//////			System.out.println(resultMap.get(key));
////		}
//	}
	@Test
	public void selectPriceTreeMapPerformance() {
		HashMap<String,TreeMap<Date,Float>> map = batchDao.selectPriceDataTreeMap(itemDtoList);
		for(String key : map.keySet()) {
			System.out.println(map.get(key));
		}
	}
	
//	@Test
//	public void selectBalanceSheetPerformance() {
//		HashMap<String, BalanceSheetDto> resultMap = batchDao.selectBalanceSheetMap(itemDtoList);
//		for(String key : resultMap.keySet()) {
//			System.out.println(resultMap.get(key));
//		}
//	}
}
