package com.noah.app.quant.service;

import java.util.List;
import java.util.Map;

import com.noah.app.wrapper.PortfolioWrapper;
import com.noah.app.wrapper.StockWrapper;

public interface QuantService {
	List<StockWrapper> calculateStocks(Map<String, Object> inParam);
	PortfolioWrapper calculatePortfolio(Map<String, Object> inParam);
	void enrollPortfolio(PortfolioWrapper portfolioWrapper);
}
