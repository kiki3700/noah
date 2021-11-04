package com.noah.app.quant;

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
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OjAlgoTest {
	@Autowired
	ItemMapper itemDao;
	
	ItemDto itemDto ;
	
	@Before
	public void init() {
		itemDto = new ItemDto();
		itemDto.setId("A005930");
		
	}
	
	@Test
	public void calanderDateTest() {
		CalendarDateSeries<Float> c = new CalendarDateSeries<>(); 
		List<HistoryDataDto> cal = itemDao.selectClosingPrice(itemDto);
		for(HistoryDataDto key : cal) {
			c.put(key.getTradingDate(), key.getClose());
		}
		for(CalendarDate key : c.keySet()) {
			System.out.println(key+" : "+c.get(key));
		}
	}
}
