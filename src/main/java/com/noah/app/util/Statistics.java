package com.noah.app.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.ojalgo.matrix.Primitive64Matrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.quant.dao.ItemMapper;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

/*스트림 공부하고 리팰터링*/
@Component
public class Statistics {
	
	@Autowired
	ItemMapper itemMapper;
	
	public TreeMap<Date, Float> toTreeMap(List<HistoryDataDto> historyDataDtoList){
		TreeMap<Date, Float> treeMap = new TreeMap<>();
		for(HistoryDataDto historyDataDto : historyDataDtoList) {
			treeMap.put(historyDataDto.getTradingDate(), historyDataDto.getClose());
		}
		return treeMap;
	}
	
	public TreeMap<Date, Double> toReturnTreeMap(List<HistoryDataDto> historyDataDtoList){
		TreeMap<Date, Double> returnTreeMap = new TreeMap<>();
		TreeMap<Date, Float> treeMap = toTreeMap(historyDataDtoList);
		Entry<Date, Float> preData = treeMap.pollFirstEntry();
		float preVal = preData.getValue();
		Date preDate = preData.getKey();
		
		for(Date date : treeMap.keySet()) {
			float curVal= treeMap.get(date);
			double returnRate = (curVal-preVal)/preVal;
			returnTreeMap.put(date, returnRate);
			preVal = curVal;
			preDate = date;
		}
		return returnTreeMap;
	}
	
	
	
	public TreeMap<Date, Double> toReturnTreeMap(TreeMap<Date, Float> treeMap ){
		TreeMap<Date, Double> returnTreeMap = new TreeMap<>();
		Entry<Date, Float> preData = treeMap.pollFirstEntry();
		float preVal = preData.getValue();
		Date preDate = preData.getKey();
		
		for(Date date : treeMap.keySet()) {
			float curVal= treeMap.get(date);
			double returnRate = (curVal-preVal)/preVal;
			returnTreeMap.put(date, returnRate);
			preVal = curVal;
			preDate = date;
		}
		return returnTreeMap;
	}
	
	public Primitive64Matrix toCovMatrix(List<TreeMap<Date, Float>> treeMapList) {
		List<TreeMap<Date, Double>> returnMapList = new ArrayList<>();
		Primitive64Matrix.Factory matrixFactory = Primitive64Matrix.FACTORY;
		Primitive64Matrix mat;
		for(int i = 0; i<treeMapList.size();i++) {
			returnMapList.add(toReturnTreeMap(treeMapList.get(i)));
		}
		int len = returnMapList.get(0).size();
		double[][] covArr = new double[len][len];
		for(int i = 0 ; i < len; i++) {
			for(int j = 0; j <len ; j++) {
				double cov = calCov(returnMapList.get(i), returnMapList.get(j));
				covArr[i][j] = cov;
			}
		}
		mat= matrixFactory.rows(covArr);
		return mat;
	}
	
	public double calBeta(TreeMap<Date, Double> treeMapList, TreeMap<Date, Double> kospiRetrunMap) {
		return calCov(treeMapList, kospiRetrunMap);
	}
	
	
	public double calGeoMean(TreeMap<Date, Double> returnMap) {
		double cumReturn = 1;
		int len = returnMap.size();
		for(Date date : returnMap.keySet()) {
			cumReturn *= (1+ returnMap.get(date));
		}
		cumReturn = Math.pow(cumReturn,(float)1/len)-1;
		return cumReturn;
	}
	public double calExpRet(TreeMap<Date,Float> stockPriceMap, TreeMap<Date,Float> kospiPriceMap, double riskFreeRate) {
		TreeMap<Date, Double> stockRetMap = toReturnTreeMap(stockPriceMap);
		TreeMap<Date, Double> kospiRetMap = toReturnTreeMap(kospiPriceMap);
		double rm = calGeoMean(kospiRetMap);
		double beta = calBeta(stockRetMap, kospiRetMap);
		return riskFreeRate + beta*(rm - riskFreeRate);
		
	}
	
	public double calVol(TreeMap<Date, Double> returnMap) {
		double mean = calGeoMean(returnMap);
		double sum = 0;
		for(Date date : returnMap.keySet()) {
			sum += Math.pow((returnMap.get(date)-mean), 2);
		}
		int n = returnMap.size();
		
		return sum/(n-1);
		
	}
	public double calStdv(TreeMap<Date, Double> returnMap) {
		double mean = calGeoMean(returnMap);
		double sum = 0;
		for(Date date : returnMap.keySet()) {
			sum += Math.pow((returnMap.get(date)-mean), 2);
		}
		int n = returnMap.size();
		
		return Math.pow(sum/(n-1),0.5);
	}
	public double calCov(TreeMap<Date, Double> stock1, TreeMap<Date, Double> stock2) {
		double mean1 = calGeoMean(stock1);
		double mean2 = calGeoMean(stock2);
		double sum =0;
		Set<Date> keySet = stock1.keySet();
		keySet.retainAll(stock2.keySet());
		for(Date date : keySet) {
			sum += (stock1.get(date)-mean1)*(stock2.get(date)-mean2);
		}
		return sum/(keySet.size()-1);
	}
	
	public double calCor(TreeMap<Date, Double> stock1, TreeMap<Date, Double> stock2){
		double cov = calCov(stock1, stock2);
		double vol1 = calStdv(stock1);
		double vol2 = calStdv(stock2);
		return cov/(vol1*vol2);
	}

	public Primitive64Matrix toCovMat(List<ItemDto> itemDtoList) {
		
		return null;
	}
	
	public <N extends Number> double calculateMean(HashMap<String, N > map) {
		int len = map.size();
		double mean = 0;
		for(String key : map.keySet()) {
			mean += (Double) map.get(key)/len;
		}
		return mean;
	}
	
	public <N extends Number> double calculateRetMean(LinkedHashMap<String, N > map) {
		int len = map.size();
		double mean = 1;
		for(String key : map.keySet()) {
			mean *= (1+(float) map.get(key));
		}
		return Math.pow(mean, 1/(double)map.size())-1;
	}
	
	public <N extends Number> double calculateVar(HashMap<String, N> map) {
		double mean = calculateMean(map);
		int len = map.size();
		double sum = 0;
		for(String key : map.keySet()) {
			double x = (double) map.get(key);
			sum = Math.pow(x-mean, 2);
		}
		return sum/(len-1);
	}
	public <N extends Number> double calculateStd(HashMap<String, N> map) {
		return Math.sqrt(calculateVar(map));
	}
	
	public <N extends Number> HashMap<String, Double> calculateZScore(HashMap<String, N> map , double mean, double std) {
		HashMap<String, Double> zScoreMap = new HashMap<>();
		for(String key : map.keySet()) {
			double val = (double) map.get(key);
			double zScore = (val-mean)/std;
			zScoreMap.put(key, zScore);
		}
		return zScoreMap;
	}
	public <N extends Number> HashMap<String, Double> mergeZScore(HashMap<String, N> map1, HashMap<String, N> map2){
		Set<String> key1 = map1.keySet();
		Set<String> key2 = map2.keySet();
		key1.retainAll(key2);
		HashMap<String, Double> mergedMap = new HashMap<>();
		for(String key : key1) {
			double val1 = (double) map1.get(key);
			double val2 = (double) map2.get(key);
			mergedMap.put(key, (val1+val2)/2);
		}
		return mergedMap;
	}
	public <N extends Number> HashMap<String, Double> mergeZScore(HashMap<String, N> map1, HashMap<String, N> map2, HashMap<String, N> map3){
		Set<String> key1 = map1.keySet();
		Set<String> key2 = map2.keySet();
		Set<String> key3 = map3.keySet();
		key1.retainAll(key2);
		key1.retainAll(key3);
		HashMap<String, Double> mergedMap = new HashMap<>();
		for(String key : key1) {
			double val1 = (double) map1.get(key);
			double val2 = (double) map2.get(key);
			double val3 = (double) map3.get(key);
			mergedMap.put(key, (val1+val2+val3)/3);
		}
		return mergedMap;
	}
}
