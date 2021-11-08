package com.noah.app.quant;

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

import com.noah.app.quant.dao.ItemMapper;
import com.noah.app.util.Statistics;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsTest {
	@Autowired
	ItemMapper itemDao;
	
	@Autowired
	Statistics statistics;
	
	ItemDto itemDto ;
	
	HashMap<String, Object> inParam;
	List<HistoryDataDto> historyList;
	@Before
	public void init() {
		inParam = new HashMap<>();
		itemDto = new ItemDto();
		itemDto.setId("A005930");
		inParam.put("itemDto",itemDto);
		inParam.put("period", 254);
		historyList = itemDao.selectClosingPrice(inParam);
		System.out.println(historyList.get(1));
	}
	
	@Test
	public void getStat() {
		
		TreeMap<Date, Float> treeMap = statistics.toTreeMap(historyList);
		TreeMap<Date, Double> returnMap = statistics.getRerturnTreeMap(historyList);
		for(Date date : treeMap.keySet()) {
			System.out.println(date + " : "+treeMap.get(date));
		}
		for(Date date : returnMap.keySet()) {
			System.out.println(date+" : " +returnMap.get(date)*100+"%");
		}
		System.out.println("geoMean "+statistics.calGeoMean(returnMap));
		System.out.println("var" + statistics.calVol(returnMap));
	}
	

}
