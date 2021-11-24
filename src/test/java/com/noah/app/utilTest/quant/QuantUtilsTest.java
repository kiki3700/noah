package com.noah.app.utilTest.quant;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.constants.BusinessDays;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.util.QuantUtils;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuantUtilsTest {
	@Autowired
	QuantUtils quantUtil;
	
	@Autowired
	ItemMapper itemMapper;
	
	List<HistoryDataDto> historyDataDtoList;
	
	@Before
	public void init() {
		HashMap<String, Object> inParam = new HashMap<>();
		inParam.put("itemId", "A003240");
		historyDataDtoList =itemMapper.selectHistroyData(inParam);
	}
//	@Test
//	public void keySort() {
//
//		TreeMap<Date, Float> treeMap = quantUtil.toPriceMap(historyDataDtoList);
//		Date[] dArr = treeMap.keySet().toArray(new Date[treeMap.size()]);
//		for(int i = 0 ; i<dArr.length;i++) {
//			System.out.println(dArr[i]);
//		}
//		
//	}
//	@Test
//	public void cumRet() {
//		TreeMap<Date, Float> priceMap = quantUtil.toPriceMap(historyDataDtoList);
//		BigDecimal cumRet = quantUtil.calCumRet(priceMap, BusinessDays.THREEMONTHS);
//		assertEquals(cumRet.floatValue(),-0.05201177625,0.004 );
//	}
//	
//	@Test
//	public void hashMap() {
//		List<ItemDto> itemDtoList = itemMapper.selectItemDtoList(null);
//		HashMap<String, ItemDto> itemMap = new HashMap<String,ItemDto>(itemDtoList.stream().collect(Collectors.toMap(ItemDto::getId,  Function.identity())));
//		for(String key : itemMap.keySet()) {
//			System.out.println(itemMap.get(key));
//		}
//	}
	@Test
	public void operate() {
		double a = 0;
		double b =1;
		System.out.println(b/a);
	}
}
