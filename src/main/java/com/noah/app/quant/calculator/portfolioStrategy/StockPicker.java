package com.noah.app.quant.calculator.portfolioStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.quant.calculator.portfolioStrategy.filterStrategy.TreeFactorModel;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.vo.ItemDto;
@Component
public class StockPicker {
	
	
	@Autowired
	ItemMapper itemDao;
	
	@Autowired
	TreeFactorModel treeFactorModel;
	/*
	 * 언러키 컨트롤러로 inParam
	 */
	public List<ItemDto> sortStockByStrategy(Map<String, Object> inParam){
		List<ItemDto> itemList = itemDao.selectItemDtoList(inParam);
		String pickUpStrategy =(String) inParam.getOrDefault("pickUpStrategy", "");
		List<ItemDto> filteredStockList = new ArrayList<>();
		switch(pickUpStrategy) {
		case "3factor":
			filteredStockList=treeFactorModel.filter(inParam, itemList);
		}
		return filteredStockList;
	}
	
}
