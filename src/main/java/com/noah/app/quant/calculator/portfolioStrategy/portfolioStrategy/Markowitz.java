package com.noah.app.quant.calculator.portfolioStrategy.portfolioStrategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.ojalgo.finance.portfolio.MarkowitzModel;
import org.ojalgo.matrix.Primitive64Matrix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.constants.IndexConst;
import com.noah.app.dto.HistoryDataDto;
import com.noah.app.dto.IndexHistoryDataDto;
import com.noah.app.quant.mapper.IndexHistoryDataMapper;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.util.QuantUtils;
import com.noah.app.wrapper.PortfolioWrapper;
import com.noah.app.wrapper.StockWrapper;

@Component
public class Markowitz {
	@Autowired
	IndexHistoryDataMapper indexHistoryMapper;
	@Autowired
	ItemMapper itmMapper;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	public PortfolioWrapper calculateWeightList(List<StockWrapper> stockWrapperList,  Map<String, Object> inParam){
		logger.debug("=================================");
		logger.debug("===markowitz logger start========");
		logger.debug("=================================");
		HashMap<String, Object> queryMap = new HashMap<>();
		queryMap.put("indexName", IndexConst.KorIndex.Kospi.getValue());
		
		List<IndexHistoryDataDto> kospiList = indexHistoryMapper.selectIndexHistoryDataListByYear(queryMap);
		logger.debug(kospiList.toString());
		queryMap.clear();
		queryMap.put("indexName", IndexConst.BokIndex.BaseRate.getValue());
		List<IndexHistoryDataDto> baseRate =indexHistoryMapper.selectIndexHistoryDataListByYear(queryMap);
		logger.debug(baseRate.toString());
		TreeMap<Date, Float> kospiPriceMap = QuantUtils.toHistoryDataMap(kospiList);
		
		logger.debug("get price list");
		List<TreeMap<Date, Float>> stockPricemapList  = new ArrayList<TreeMap<Date, Float>>(); 
		for(StockWrapper stockWrapper: stockWrapperList) {
			List<HistoryDataDto> historyDataDtoList = itmMapper.selectHistoryDataListByYear(stockWrapper.getItemDto());
			TreeMap<Date,Float> treeMap = QuantUtils.toHistoryDataMap(historyDataDtoList);
			stockPricemapList.add(treeMap);
		}
		
		logger.debug("get weight");
		Primitive64Matrix covMat =QuantUtils.toCovMatrix(stockPricemapList);
		Primitive64Matrix retMat = QuantUtils.toExpVector(stockPricemapList, kospiPriceMap, baseRate);
		MarkowitzModel model = new  MarkowitzModel(covMat,retMat);
		

		//must constraint edit 
		if(inParam.containsKey("LB")){
			BigDecimal lb = BigDecimal.valueOf((Double) inParam.get("LB"));
			for(int i = 0 ; i < stockPricemapList.size();i++) {
				model.setLowerLimit(i, lb);
			}
		}
		if(inParam.containsKey("UB")){
			BigDecimal ub = BigDecimal.valueOf((Double) inParam.get("UB"));
			for(int i = 0 ; i < stockPricemapList.size();i++) {
				model.setLowerLimit(i, ub);
			}
		}
		
		model.setTargetVariance(new BigDecimal(0));
		logger.debug("optimize portfolio");
		PortfolioWrapper portfolioWrapper = new PortfolioWrapper();
		Primitive64Matrix weightMat = model.getAssetWeights();
		
		for(int i = 0; i< stockWrapperList.size();i++) {
			stockWrapperList.get(i).setWeight(weightMat.get(i));
		}
		portfolioWrapper.setExpReturn(model.getMeanReturn());
		portfolioWrapper.setVar(model.getReturnVariance());
		
		portfolioWrapper.setStockList(stockWrapperList);
		return portfolioWrapper;
	}
	
}
