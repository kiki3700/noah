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
import org.ojalgo.matrix.Primitive64Matrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.quant.dao.IndexHistoryDataMapper;
import com.noah.app.quant.dao.ItemMapper;
import com.noah.app.util.Statistics;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.IndexHistoryDataDto;
import com.noah.app.vo.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MakovizTest {
	@Autowired
	Statistics sta;
	@Autowired
	IndexHistoryDataMapper idxMapper;
	@Autowired
	ItemMapper itmMapper;
	
	List<TreeMap<Date, Float>> stockPricemapList;
	List<IndexHistoryDataDto> baseRateList;
	TreeMap<Date, Float> kospiPriceMap;
	HashMap<String, Object> inParam;	
	
	
	
	@Before
	public void in() {
		inParam = new HashMap<>();
		HashMap<String, Object> map = new HashMap<>();
		map.put("indexName", "BOK BASE RATE");
		map.put("period", 12);
		baseRateList = idxMapper.selectIndexHistoryDataList(map);
		map.put("indexName", "코스피");
		map.put("period", 255);
		List<IndexHistoryDataDto> kospiList = idxMapper.selectIndexHistoryDataList(map);
		kospiPriceMap = sta.toIndexMap(kospiList);
		String[] arr = new String[] {"A005930","A001550", "A001680", "A001740", "A001790","A002390"};
		inParam.put("period", 255);
		stockPricemapList = new ArrayList<TreeMap<Date, Float>>(); 
		for(int i = 0 ; i <arr.length;i++) {
			ItemDto item = new ItemDto();
			item.setId(arr[i]);
			inParam.put("itemDto", item);
			List<HistoryDataDto> histList = itmMapper.selectClosingPrice(inParam);
			TreeMap<Date,Float> treeMap = sta.toPriceMap(histList);
			stockPricemapList.add(treeMap);
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
}
