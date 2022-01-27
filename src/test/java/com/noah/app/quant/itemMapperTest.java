package com.noah.app.quant;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.dto.ItemDto;
import com.noah.app.quant.calculator.portfolioStrategy.StockPicker;
import com.noah.app.quant.mapper.ItemMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class itemMapperTest {
	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	StockPicker stockPicker;
	
	HashMap<String, Object> inParam = new HashMap<>();
	
	@Test
	public void marketTest() {
		inParam.put("market", "CPC_MARKET_KOSPI");
		List<ItemDto> itemList =itemMapper.selectItemDtoList(inParam);
		for(ItemDto item : itemList) {
			if(!item.getMarket().equals("CPC_MARKET_KOSPI")) System.out.println(item.getMarket());
		}
	}
	@Test
	public void marketSector() {
		inParam.put("market", "CPC_MARKET_KOSPI");
		inParam.put("industry","화학");
		List<ItemDto> itemList =itemMapper.selectItemDtoList(inParam);
		for(ItemDto item : itemList) {
			 System.out.println(item.getIndustry());
		}
	}
	@Test
	public void sizeTest() {
		inParam.put("market", "CPC_MARKET_KOSPI");
		inParam.put("industry","화학");
		inParam.put("corpSize","CPC_CAPITAL_MIDDLE");
		List<ItemDto> itemList =itemMapper.selectItemDtoList(inParam);
		for(ItemDto item : itemList) {
			 System.out.println(item.getCorpSize());
		}
	}
	
	
}
