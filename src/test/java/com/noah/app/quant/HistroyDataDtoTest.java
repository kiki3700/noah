package com.noah.app.quant;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.quant.dao.ItemMapper;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HistroyDataDtoTest {

	@Autowired
	ItemMapper itemMapper;
	
	ItemDto itemDto ;
	HashMap<String, Object> map ;
	String[] arr = new String[] {};
	@Before
	public void init() {
		map = new HashMap<>();
		itemDto = new ItemDto();
		itemDto.setId("A005930");
		map.put("itemDto", itemDto);
		map.put("period", 300);
		
	}
	@Test
	public void test() {
		List<HistoryDataDto> list = itemMapper.selectClosingPrice(map);
		for(HistoryDataDto data : list) {
			System.out.println(data);
		}
	}
}
