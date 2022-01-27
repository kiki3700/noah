//package com.noah.app.quant;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.math.MathContext;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.TreeMap;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.noah.app.quant.mapper.IndexHistoryDataMapper;
//import com.noah.app.quant.mapper.ItemMapper;
//import com.noah.app.util.QuantUtils;
//import com.noah.app.vo.HistoryDataDto;
//import com.noah.app.vo.IndexHistoryDataDto;
//import com.noah.app.vo.ItemDto;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class indexMapperTest {
//	@Autowired
//	IndexHistoryDataMapper mapper;
//	
//	@Autowired
//	ItemMapper itemMapper;
//	
//	@Autowired
//	QuantUtils sta;
//	
//	HashMap<String, Object> map;
//	HashMap<String, Object> map1;
//	@Before
//	public void init() {
//		map = new HashMap<>();
//		map.put("indexName", "코스피");
//		map.put("period", 255);
//		
//		ItemDto item = new ItemDto();
//		item.setId("A005930");
//		map1 = new HashMap<>();
//		map1.put("itemDto", item);
//		map1.put("period", 255);
//	}
//	@Test
//	public void te() {
//		List<IndexHistoryDataDto> list = mapper.selectIndexHistoryDataList(map);
//		for(IndexHistoryDataDto dto : list) {
//			System.out.println(dto);
//		}
//		
//		
//		
//		List<HistoryDataDto> stock = itemMapper.selectHistoryDataList(map1);
//		TreeMap<Date, Float> sTM = sta.toHistoryDataMap(stock);
//		
//		
//		
//		TreeMap<Date, Double> sRM = sta.toReturnMap(sTM);
//		
//		System.out.println(sta.calGeoMean(sRM));
//		System.out.println(sta.calVol(sRM));
//				
//		TreeMap<Date, Float> ma = sta.toHistoryDataMap(list);
//		TreeMap<Date, Double> turn = sta.toReturnMap(ma);
//		
//		
//		BigDecimal beta = sta.calBeta(sRM, turn);
//		BigDecimal b = sta.calB(sRM, turn);
//		System.out.println(beta);
//		System.out.println(b);
//		System.out.println("이거니"+beta.divide(b,MathContext.DECIMAL128));
//		
//		HashMap<String, Object> p = new HashMap<>();
//		p.put("indexName", "BOK BASE RATE");
//		p.put("period", 12);
//		List<IndexHistoryDataDto> idxList = mapper.selectIndexHistoryDataList(p);
//		for(IndexHistoryDataDto idx : idxList) {
//			System.out.println(idx);
//		}
//		double rf = sta.calBaseRate(idxList);
//		System.out.println("base rate "+rf);
//		BigDecimal ret = sta.calExpRet(sTM, ma, rf);
//		System.out.println(ret);
//		System.out.print(Math.pow(ret.doubleValue()+1,12)-1);
//	}
//}
