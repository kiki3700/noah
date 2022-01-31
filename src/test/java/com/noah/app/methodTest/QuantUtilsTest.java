package com.noah.app.methodTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.dto.HistoryDataDto;
import com.noah.app.testUtil.CsvUtils;
import com.noah.app.util.QuantUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QuantUtilsTest {
	static TreeMap<Date,Float> stockAMap;
	static TreeMap<Date,Float> stockBMap;
	static TreeMap<Date,Float> stockCMap;
	List<HistoryDataDto> stockAList;
	List<HistoryDataDto> stockBList;
	List<HistoryDataDto> stockCList;
	static TreeMap<Date,Double> stockAReturn;
	static Map<String,Object> aStatisticsMap;
	@Before
	public void init() throws IOException, ParseException {
		stockAList = CsvUtils.getHistoryDataDtoList("aPrice.csv");
		stockBList = CsvUtils.getHistoryDataDtoList("bPrice.csv");
		stockCList = CsvUtils.getHistoryDataDtoList("cPrice.csv");
		stockAMap = QuantUtils.toHistoryDataMap(stockAList);
		stockBMap = QuantUtils.toHistoryDataMap(stockBList);
		stockCMap = QuantUtils.toHistoryDataMap(stockCList);
		aStatisticsMap = CsvUtils.getStatistic("aStatistics.csv");
	}
	
//	@Test
//	public void writeFile() throws NumberFormatException, ParseException, IOException {
//		HashMap<String, Object> aStatisticsMap = new HashMap<>();
//		HashMap<String, Object> bStatisticsMap = new HashMap<>();
//		HashMap<String, Object> cStatisticsMap = new HashMap<>();
//		
//		TreeMap<Date,Double>  aReturnMap = CsvUtils.getTreeMap("aReturn.csv");
//		TreeMap<Date,Double>  bReturnMap = CsvUtils.getTreeMap("bReturn.csv");
//		TreeMap<Date,Double>  cReturnMap = CsvUtils.getTreeMap("cReturn.csv");
//		
//		BigDecimal aCumRet = QuantUtils.calCumRet(aReturnMap);
//		BigDecimal bCumRet = QuantUtils.calCumRet(bReturnMap);
//		BigDecimal cCumRet = QuantUtils.calCumRet(cReturnMap);
//		
//		BigDecimal aGeoMean = QuantUtils.calGeoMean(aReturnMap);
//		BigDecimal bGeoMean = QuantUtils.calGeoMean(bReturnMap);
//		BigDecimal cGeoMean = QuantUtils.calGeoMean(cReturnMap);
//		
//		BigDecimal aVol = QuantUtils.calVol(aReturnMap);
//		BigDecimal bVol = QuantUtils.calVol(bReturnMap);
//		BigDecimal cVol = QuantUtils.calVol(cReturnMap);
//		
//		BigDecimal aStdv = QuantUtils.calStdv(aReturnMap);
//		BigDecimal bStdv = QuantUtils.calStdv(bReturnMap);
//		BigDecimal cStdv = QuantUtils.calStdv(cReturnMap);
//		
//		aStatisticsMap.put("cumRet", aCumRet);
//		aStatisticsMap.put("geoMean", aGeoMean);
//		aStatisticsMap.put("vol", aVol);
//		aStatisticsMap.put("stdv", aStdv);
//
//		bStatisticsMap.put("cumRet", bCumRet);
//		bStatisticsMap.put("geoMean", bGeoMean);
//		bStatisticsMap.put("vol", bVol);
//		bStatisticsMap.put("stdv", bStdv);
//		
//		cStatisticsMap.put("cumRet", cCumRet);
//		cStatisticsMap.put("geoMean", cGeoMean);
//		cStatisticsMap.put("vol", cVol);
//		cStatisticsMap.put("stdv", cStdv);
//		
//		CsvUtils.writeMapCsv("aStatistics.csv", aStatisticsMap);
//		CsvUtils.writeMapCsv("bStatistics.csv", bStatisticsMap);
//		CsvUtils.writeMapCsv("cStatistics.csv", cStatisticsMap);
//	}
		
//	//hisotryDataMapTest
// 람다와 포문을 이용한 맵만드는 메서드 폐기 확인 후 메서드
//	@Test
//	public void historyDatamapTest(){
//		TreeMap<Date, Float> originMap = QuantUtils.toHistoryDataMap(stockAList);
////		TreeMap<Date, Float> lamdaMap = QuantUtils.toHistoryDataLamda(stockAList);
////		assertEquals(originMap,lamdaMap);
//	}
	
	//toReturnMap
	@Test
	public void atoReturnMapTest() throws NumberFormatException, ParseException, IOException {
		stockAReturn = QuantUtils.toReturnMap(stockAMap);
		TreeMap<Date,Double>  returnMap = CsvUtils.getTreeMap("aReturn.csv");
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
	@Test
	public void calCumRetTest() throws NumberFormatException, ParseException, IOException {
		BigDecimal cumret = QuantUtils.calCumRet(stockAReturn);
		BigDecimal expVal = new BigDecimal((String) aStatisticsMap.get("cumRet")); 
//				CsvUtils.getBigDecimal("aCumRet.csv");
		assertEquals(cumret.doubleValue(),expVal.doubleValue(),0.00001);
	}
	//calGeoMean
	@Test
	public void calGeoMeanTest() throws NumberFormatException, ParseException, IOException {
		BigDecimal geoMean = QuantUtils.calGeoMean(stockAReturn);
		BigDecimal expVal = new BigDecimal((String) aStatisticsMap.get("geoMean"));
		assertEquals(geoMean.doubleValue(),expVal.doubleValue(),0.00001);
	}
	//calExpRet
	
	//calVol
	@Test
	public void calVolTest() {
		BigDecimal vol = QuantUtils.calVol(stockAReturn);
		BigDecimal expVal = new BigDecimal((String) aStatisticsMap.get("vol"));
		assertEquals(vol.doubleValue(),expVal.doubleValue(),0.00001);
	}
	//calStdv
	@Test
	public void calStdv() {
		BigDecimal stdv = QuantUtils.calStdv(stockAReturn);
		BigDecimal expVal = new BigDecimal((String) aStatisticsMap.get("stdv"));
		assertEquals(stdv.doubleValue(),expVal.doubleValue(),0.000001);
	}
	//calCov
	
	//calCor
	
	//toCovMat
	
	//calMean
	
	//calVar
	
	//calStdv
	
	//calZscore
	
	//mergeZscore
}
