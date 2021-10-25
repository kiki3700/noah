package com.noah.app.quant.calculator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.quant.dao.ItemDao;
import com.noah.app.vo.ItemDto;
import com.noah.app.vo.StockWrapper;

@Component
public class StaticStrategey {
	
	@Autowired
	ItemDao itemDao;
	
	public List<StockWrapper> getAllStockList(Map<String, Object> inParams){
		List<ItemDto> itemList = new LinkedList<>();
		List<StockWrapper> stockList = new ArrayList<>();
		for(ItemDto item : itemList) {
			StockWrapper stock = new StockWrapper(item);
			stock.setHistorydataDtoList(itemDao.selectHistoryDataByItemId(item));
		}
		return stockList;
	}
}
