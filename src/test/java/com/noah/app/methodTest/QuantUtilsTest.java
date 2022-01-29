package com.noah.app.methodTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.dto.HistoryDataDto;
import com.noah.app.testUtil.CsvUtils;
import com.noah.app.util.QuantUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuantUtilsTest {
	static TreeMap<Date,Float> stockAMap;
	static TreeMap<Date,Float> stockBMap;
	static TreeMap<Date,Float> stockCMap;
	
	@Before
	public void init() throws IOException, ParseException {
		List<HistoryDataDto> stockAList = CsvUtils.getHistoryDataDtoList("aPrice.csv");
		List<HistoryDataDto> stockBList = CsvUtils.getHistoryDataDtoList("bPrice.csv");
		List<HistoryDataDto> stockCList = CsvUtils.getHistoryDataDtoList("cPrice.csv");
		
		stockAMap = QuantUtils.toHistoryDataMap(stockAList);
		stockBMap = QuantUtils.toHistoryDataMap(stockBList);
		stockCMap = QuantUtils.toHistoryDataMap(stockCList);
	}
	
	
		
	//hisotryDataMapTest
	@Test
	public void historyDatamapTest(){
		//나중에 해도 될듯
	
	}
	
	//toReturnMap
	@Test
	public void toReturnMapTest() throws NumberFormatException, ParseException, IOException {
		TreeMap<Date,Double> returnMap = QuantUtils.toReturnMap(stockAMap);
		TreeMap<Date,Double> stockAReturn = CsvUtils.getTreeMap("aReturn.csv");
		assertEquals(returnMap.size(),stockAReturn.size());
		for(Map.Entry<Date, Double> value : returnMap.entrySet()) {
			double actualValue = stockAReturn.get(value.getKey());
			assertNotNull(actualValue);
			assertEquals(value.getValue(), actualValue, 0.000001);
		}
	}
	//toCovMatirx
	
	//toExpVector
	
	//calBaseRate
	
	//calBeta
	
	//calCumRet
	
	//calGeoMean
	
	//calExpRet
	
	//calVol
	
	//calStdv
	
	//calCov
	
	//calCor
	
	//toCovMat
	
	//calMean
	
	//calVar
	
	//calStdv
	
	//calZscore
	
	//mergeZscore
}
