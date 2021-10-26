package com.noah.app.quant.calculator.portfolioStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.noah.app.quant.dao.ItemDao;
import com.noah.app.util.Statistics;
import com.noah.app.vo.ItemDto;
import com.noah.app.vo.StockWrapper;

public class StockDivder {

	@Autowired
	Statistics statistics;
	
	@Autowired
	ItemDao itemDao;
	
	public List<ItemDto> divideWeight(List<ItemDto> itemList, Map<String, Object> inParam){
		String divideStrategy = (String) inParam.getOrDefault("divideStrategy", "");
		List<StockWrapper> filteredStockList = new ArrayList<>();
		switch(divideStrategy) {
		case "minVar":
			
		}
		return null;
	}
}
