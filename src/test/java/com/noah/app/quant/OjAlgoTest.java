package com.noah.app.quant;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ojalgo.series.CalendarDateSeries;
import org.ojalgo.type.CalendarDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.quant.dao.ItemMapper;
import com.noah.app.util.Statistics;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OjAlgoTest {
	@Autowired
	ItemMapper itemDao;
	
	@Autowired
	Statistics statistics;
	
	ItemDto itemDto ;
	
	@Before
	public void init() {
		itemDto = new ItemDto();
		itemDto.setId("A005930");
		
	}
	
//	@Test
//	public void calanderDateTest() {
//		CalendarDateSeries<Float> c = new CalendarDateSeries<>(); 
//		HashMap<String, Object> inParam = new HashMap<>();
//		inParam.put("itemDto", itemDto);
//		List<HistoryDataDto> cal = itemDao.selectClosingPrice(inParam);
//		for(HistoryDataDto key : cal) {
//			c.put( new CalendarDate(key.getTradingDate()), key.getClose());
//		}
//
//		
//		Assert.assertEquals(c, statistics.makeCalenderDateSeries(cal));
//	}
	
	@Test
	public void calendarSize1() {
		HashMap<String, Object> inParam = new HashMap<>();
		inParam.put("itemDto", itemDto);
		inParam.put("period",200);
		List<HistoryDataDto> historyDataDtoList = itemDao.selectClosingPrice(inParam);
		CalendarDateSeries cal =statistics.makeCalenderDateSeries(historyDataDtoList, 200);
		System.out.println(cal.size());
		Assert.assertEquals(cal.size(),200);
	}
	@Test
	public void calendarSize2() {
		HashMap<String, Object> inParam = new HashMap<>();
		inParam.put("itemDto", itemDto);
		List<HistoryDataDto> historyDataDtoList = itemDao.selectClosingPrice(inParam);
		CalendarDateSeries cal =statistics.makeCalenderDateSeries(historyDataDtoList,255);
		Assert.assertEquals(cal.size(),255);
	}
}
