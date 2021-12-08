package com.noah.app.quant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ojalgo.finance.portfolio.MarkowitzModel;
import org.ojalgo.machine.Hardware;
import org.ojalgo.matrix.Primitive64Matrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.quant.calculator.portfolioStrategy.StockDivider;
import com.noah.app.quant.mapper.IndexHistoryDataMapper;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.util.QuantUtils;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.IndexHistoryDataDto;
import com.noah.app.vo.ItemDto;
import com.noah.app.wrapper.PortfolioWrapper;
import com.noah.app.wrapper.StockWrapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MakovizTest {
	@Autowired
	QuantUtils sta;
	@Autowired
	IndexHistoryDataMapper idxMapper;
	@Autowired
	ItemMapper itmMapper;
	@Autowired
	StockDivider stockDivider;
	
	
	List<TreeMap<Date, Float>> stockPricemapList;
	List<IndexHistoryDataDto> baseRateList;
	TreeMap<Date, Float> kospiPriceMap;
	HashMap<String, Object> inParam;	
	
	List<StockWrapper> stockWrapperList = new ArrayList<>();
	
	@Before
	public void in() {
		inParam = new HashMap<>();
		HashMap<String, Object> map = new HashMap<>();
		map.put("indexName", "BOK BASE RATE");
		map.put("period", 12);
		baseRateList = idxMapper.selectIndexHistoryDataListByYear(map);
		map.put("indexName", "코스피");
		map.put("period", 255);
		List<IndexHistoryDataDto> kospiList = idxMapper.selectIndexHistoryDataList(map);
		kospiPriceMap = sta.toIndexMap(kospiList);
		String[] arr = new String[] {"A009810","A121800", "A036830", "A094480", "A086520","A090150", "A064260", "A013360", "A078600","A247540", "A016250", "A064550", "A052690","A078350","A006110","A094820","A009410","A182360","A130660","A096530"};
		inParam.put("period", 255);
		stockPricemapList = new ArrayList<TreeMap<Date, Float>>(); 
		for(int i = 0 ; i <arr.length;i++) {
			ItemDto item = new ItemDto();
			item.setId(arr[i]);
			inParam.put("itemDto", item);
			List<HistoryDataDto> histList = itmMapper.selectHistoryDataListByYear(item);
			TreeMap<Date,Float> treeMap = sta.toPriceMap(histList);
			stockPricemapList.add(treeMap);
			StockWrapper stockWrapper = new StockWrapper();
			stockWrapper.setItemDto(item);
			stockWrapperList.add(stockWrapper);
		}
	}
	@Test
	public void makeMatrix() {
		Primitive64Matrix covMat =sta.toCovMatrix(stockPricemapList);
		Primitive64Matrix retMat = sta.toExpVector(stockPricemapList, kospiPriceMap, baseRateList);
		System.out.println(covMat.toString());
		System.out.println(retMat.toString());
		MarkowitzModel model = new  MarkowitzModel(covMat,retMat);
		model.setTargetVariance(new BigDecimal(0));
		System.out.println(model.getAssetWeights().toString());
	}
	@Test
	public void dividerTest() {
		Hardware hardware = Hardware.makeSimple("amd", 4L, 1);
		org.ojalgo.OjAlgoUtils.ENVIRONMENT=	hardware.virtualise();
		inParam.put("divideStrategy", "Makowtiz");

		inParam.put("LB", 0.0);
		PortfolioWrapper list =  stockDivider.calculateWeightList(stockWrapperList, inParam);
		System.out.println(list.getStockList().size());
		List<StockWrapper> stockWrapperList = list.getStockList();
		for(StockWrapper stock : stockWrapperList) {
			System.out.println(stock);
		}
	}
}
