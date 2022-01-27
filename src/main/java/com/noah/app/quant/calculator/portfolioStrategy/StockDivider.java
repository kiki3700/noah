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
	
	public PortfolioWrapper calculateWeightList(List<StockWrapper> stockWrapperList, Map<String, Object> inParam){
		String divideStrategy = (String) inParam.getOrDefault("detailStrategy", "");
		PortfolioWrapper portfolioWrapper = new PortfolioWrapper();
		switch(divideStrategy) {
		case "Makowitz":
			portfolioWrapper = markowitz.calculateWeightList(stockWrapperList, inParam);
		}
		return portfolioWrapper;
	}
	
	public PortfolioWrapper getAmountOfStock(PortfolioWrapper portfolioWrapper) {
		List<StockWrapper> stockWrapperList = portfolioWrapper.getStockList();
		int len = stockWrapperList.size();
		double limit =portfolioWrapper.getLimit();
		for(int i = 0 ; i < len; i++) {
			StockWrapper stockWrapper = stockWrapperList.get(i);
			String itemId = stockWrapper.getItemDto().getId();
			HashMap<String, Object> queryParam  = new HashMap<>();
			queryParam.put("itemId", itemId);
			queryParam.put("order", "DESC");
			float curPrice=0;
			if(itemMapper.selectCurHistroyData(queryParam)==null) {
				System.out.println(queryParam);
			}else {
				curPrice = itemMapper.selectCurHistroyData(queryParam).getClose();
			}
			
			stockWrapper.setCurPrice(curPrice);
			double expectTotalPrice = limit * stockWrapper.getWeight();
			int amount = (int) (expectTotalPrice/curPrice);
			stockWrapper.setAmount(amount);
			System.out.println("expTotal" +expectTotalPrice+ "cur price "+ curPrice+" amount "+ amount);
		}
		return portfolioWrapper;
	}
}
