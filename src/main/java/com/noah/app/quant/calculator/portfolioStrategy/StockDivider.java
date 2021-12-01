package com.noah.app.quant.calculator.portfolioStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.quant.calculator.portfolioStrategy.portfolioStrategy.Markowitz;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.util.QuantUtils;
import com.noah.app.wrapper.PortfolioWrapper;
import com.noah.app.wrapper.StockWrapper;
@Component
public class StockDivider {

	@Autowired
	QuantUtils statistics;
	
	@Autowired
	Markowitz markowitz;
	
	@Autowired
	ItemMapper itemMapper;
	
	public List<StockWrapper> divideWeight(List<StockWrapper> stockWrapperList, Map<String, Object> inParam){
		String divideStrategy = (String) inParam.getOrDefault("divideStrategy", "");
		List<StockWrapper> filteredStockList = new ArrayList<>();
		switch(divideStrategy) {
		case "Makowtiz":
			filteredStockList = markowitz.optimizePortfolio(stockWrapperList, inParam);
		}
		return filteredStockList;
	}
	
	public PortfolioWrapper getAmountOfStock(PortfolioWrapper portfolioWrapper) {
		int len = portfolioWrapper.getStockList().size();
		double limit = portfolioWrapper.getLimit();
		List<StockWrapper> stockWrapperList = portfolioWrapper.getStockList();
		for(int i = 0 ; i < len; i++) {
			StockWrapper stockWrapper = stockWrapperList.get(i);
			String itemId = stockWrapper.getItemDto().getId();
			HashMap<String, Object> inParam  = new HashMap<>();
			inParam.put("itemId", itemId);
			inParam.put("order", "DESC");
			float curPrice = itemMapper.selectCurHistroyData(inParam).getClose();
			stockWrapper.setCurPrice(curPrice);
			double expectTotalPrice = limit * stockWrapper.getWeight();
			int amount = (int) (expectTotalPrice/curPrice);
			stockWrapper.setAmount(amount);
		}
		return portfolioWrapper;
	}
}
