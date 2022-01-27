package com.noah.app.quant.calculator.portfolioStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.util.QuantUtils;
import com.noah.app.vo.ItemDto;
import com.noah.app.vo.StockWrapper;
@Component
public class StockDivder {

	@Autowired
	QuantUtils statistics;
	
	@Autowired
	ItemMapper itemMapper;
	
	public List<ItemDto> divideWeight(List<ItemDto> itemList, Map<String, Object> inParam){
		String divideStrategy = (String) inParam.getOrDefault("divideStrategy", "");
		List<StockWrapper> filteredStockList = new ArrayList<>();
		switch(divideStrategy) {
		case "minVar":
			
		}
		return null;
	}
}
