//package com.noah.app.quant;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.TreeMap;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.ojalgo.matrix.Primitive64Matrix;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.noah.app.quant.mapper.ItemMapper;
//import com.noah.app.util.QuantUtils;
//import com.noah.app.vo.HistoryDataDto;
//import com.noah.app.vo.ItemDto;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class StatisticsTest {
//	@Autowired
//	ItemMapper itemMapper;
//	
//	@Autowired
//	QuantUtils statistics;
//	
//	ItemDto itemDto ;
//	
//	HashMap<String, Object> inParam;
//	List<HistoryDataDto> historyList1;
//	List<HistoryDataDto> historyList2;
//	ItemDto[] itemArr;
//	@Before
//	public void init() {
//		inParam = new HashMap<>();
//		itemDto = new ItemDto();
//		itemArr = new ItemDto[6];
//		itemDto.setId("A035720");
//		String[] strArr = new String[] {"A005930","A001550", "A001680", "A001740", "A001790","A002390"};
//		inParam.put("itemDto",itemDto);
//		inParam.put("period", 254);
//		historyList1 = itemMapper.selectHistoryDataList(inParam);
//		itemDto.setId("A001550");
//		historyList2 = itemMapper.selectHistoryDataList(inParam);
//		for(int i =0 ; i<6; i++) {
//			ItemDto dto = new ItemDto();
//			dto.setId(strArr[i]);
//			itemArr[i] = dto;
//		}
//		
//		
//	}
//	
//	@Test
//	public void getStat() {
//		
//		TreeMap<Date, Float> treeMap = statistics.toPriceMap(historyList1);
//		
//		TreeMap<Date, Double> returnMap = statistics.toReturnMap(treeMap);
//		TreeMap<Date, Double> returnMap2 = statistics.toReturnMap(treeMap);
//		for(Date date : treeMap.keySet()) {
//			System.out.println(date + " : "+treeMap.get(date));
//		}
//		for(Date date : returnMap.keySet()) {
////			System.out.println(date+" : " +returnMap.get(date));
//		}
//		
//		double mean = 1;
//		int l = returnMap.size();
//		for(Date date : returnMap.keySet()) {
//			mean *= (returnMap.get(date)+1);
//		}
//		mean = Math.pow(mean, (float)1/l)-1;
//		
//		
//		System.out.println("geoMea "+mean);
//		System.out.println("geoMean "+statistics.calGeoMean(returnMap));
//		
//		double cumRet = 1;
//		l = returnMap.size();
//		for(Date date : returnMap.keySet()) {
//			cumRet*= (returnMap.get(date)+1);
//		}
//		cumRet -=1;
//		System.out.println("cumRet "+cumRet);
//		System.out.println("cumRet "+statistics.calCumRet(returnMap));
//		
//		double vol = 0;
//		for(Date date : returnMap.keySet()) {
//			vol += Math.pow((returnMap.get(date)-mean),2);
//		}
//		vol = vol/(l-1);
//		
//		System.out.println("var "+ vol);
//		System.out.println("var " + statistics.calVol(returnMap));
//		
//		System.out.println("stdv "+Math.pow(vol, 0.5));
//		System.out.println("stdv " + statistics.calStdv(returnMap));
//		
//		
//		
//		System.out.println("cov "+ statistics.calCov(returnMap, returnMap2));
//		System.out.println("cor "+ (statistics.calCor(returnMap, returnMap2)));
//	}
//	
//	@Test
//	public void getCovMat() {
//		List<TreeMap<Date,Double>> treeMapList = new ArrayList<>();
//		Primitive64Matrix.Factory matrixFactory = Primitive64Matrix.FACTORY;
//		Primitive64Matrix mat;
//		
//		for(int i = 0 ; i < itemArr.length;i++) {
//			inParam.put("itemDto", itemArr[i]);
//			System.out.println(itemArr[i].getId());
//			historyList1 = itemMapper.selectHistoryDataList(inParam);
//			treeMapList.add(statistics.toReturnMap(statistics.toPriceMap(historyList1)));
//		}
//		BigDecimal[][] covArr = new BigDecimal[itemArr.length][itemArr.length];
//		for(int i = 0; i<treeMapList.size();i++) {
//			for(int j = 0; j<treeMapList.size();j++) {
//				BigDecimal cov = statistics.calCov(treeMapList.get(i), treeMapList.get(j));
//				covArr[i][j] = cov;
//			}
//		}
//		mat= matrixFactory.rows(covArr);
//		System.out.println(mat.toString());		
//	}
//
//}
