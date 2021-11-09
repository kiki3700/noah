package com.noah.app.quant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ojalgo.matrix.Primitive64Matrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.quant.dao.ItemMapper;
import com.noah.app.util.Statistics;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsTest {
	@Autowired
	ItemMapper itemDao;
	
	@Autowired
	Statistics statistics;
	
	ItemDto itemDto ;
	
	HashMap<String, Object> inParam;
	List<HistoryDataDto> historyList1;
	List<HistoryDataDto> historyList2;
	ItemDto[] itemArr;
	@Before
	public void init() {
		inParam = new HashMap<>();
		itemDto = new ItemDto();
		itemArr = new ItemDto[6];
		itemDto.setId("A005930");
		String[] strArr = new String[] {"A005930","A001550", "A001680", "A001740", "A001790","A002390"};
		inParam.put("itemDto",itemDto);
		inParam.put("period", 254);
		historyList1 = itemDao.selectClosingPrice(inParam);
		itemDto.setId("A001550");
		historyList2 = itemDao.selectClosingPrice(inParam);
		for(int i =0 ; i<6; i++) {
			ItemDto dto = new ItemDto();
			dto.setId(strArr[i]);
			itemArr[i] = dto;
		}
		
		
	}
	
	@Test
	public void getStat() {
		
		TreeMap<Date, Float> treeMap = statistics.toTreeMap(historyList1);
		
		TreeMap<Date, Double> returnMap = statistics.toReturnTreeMap(historyList1);
		TreeMap<Date, Double> returnMap2 = statistics.toReturnTreeMap(historyList1);
		for(Date date : treeMap.keySet()) {
			System.out.println(date + " : "+treeMap.get(date));
		}
		for(Date date : returnMap.keySet()) {
			System.out.println(date+" : " +returnMap.get(date)*100+"%");
		}
		System.out.println("geoMean "+statistics.calGeoMean(returnMap));
		System.out.println("var " + statistics.calVol(returnMap));
		System.out.println("cov "+ statistics.calCov(returnMap, returnMap2));
		System.out.println("cor "+ (statistics.calCor(returnMap, returnMap2)));
	}
	
	@Test
	public void getCovMat() {
		List<TreeMap<Date,Double>> treeMapList = new ArrayList<>();
		Primitive64Matrix.Factory matrixFactory = Primitive64Matrix.FACTORY;
		Primitive64Matrix mat;
		
		for(int i = 0 ; i < itemArr.length;i++) {
			inParam.put("itemDto", itemArr[i]);
			System.out.println(itemArr[i].getId());
			historyList1 = itemDao.selectClosingPrice(inParam);
			treeMapList.add(statistics.toReturnTreeMap(statistics.toTreeMap(historyList1)));
		}
		double[][] covArr = new double[itemArr.length][itemArr.length];
		for(int i = 0; i<treeMapList.size();i++) {
			for(int j = 0; j<treeMapList.size();j++) {
				double cov = statistics.calCor(treeMapList.get(i), treeMapList.get(j));
				covArr[i][j] = cov;
			}
		}
		mat= matrixFactory.rows(covArr);
		System.out.println(mat.toString());		
	}

}
