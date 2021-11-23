package com.noah.app.quant.strategy;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.constants.CorpSize;
import com.noah.app.quant.calculator.portfolioStrategy.StockPicker;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.vo.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockFilter {
	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	StockPicker stockPicker;
	List<ItemDto> itemList;
	HashMap<String, Object> inParams = new HashMap<>();
	@Test
	public void selectItemDtoList() {
		inParams.put("corpSize", CorpSize.Large.toString());
		inParams.put("baseYear", true);
		itemList = itemMapper.selectItemDtoList(inParams);
		for(ItemDto dto : itemList) {
			System.out.println(dto);
		}
	}
	@Test 
	public void stockFilter() {
		inParams.put("pickUpStrategy", "3factor");
		
	}
}
