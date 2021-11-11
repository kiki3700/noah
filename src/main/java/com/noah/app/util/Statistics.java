package com.noah.app.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
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
import com.noah.app.vo.IndexHistoryDataDto;
import com.noah.app.vo.ItemDto;

/*스트림 공부하고 리팰터링*/
@Component
public class Statistics {
	
	@Autowired
	ItemMapper itemMapper;
	
	public TreeMap<Date, Float> toPriceMap(List<HistoryDataDto> historyDataDtoList){
		TreeMap<Date, Float> treeMap = new TreeMap<>();
		for(HistoryDataDto historyDataDto : historyDataDtoList) {
			treeMap.put(historyDataDto.getTradingDate(), historyDataDto.getClose());
		}
		return treeMap;
	}
	public TreeMap<Date, Float> toIndexMap(List<IndexHistoryDataDto> indexHistoryDataDtoList){
		TreeMap<Date, Float> treeMap = new TreeMap<>();
		for(IndexHistoryDataDto indexHistoryDataDto : indexHistoryDataDtoList) {
			treeMap.put(indexHistoryDataDto.getIndexDate(), indexHistoryDataDto.getClose());
		}
		return treeMap;
	}
	
	public TreeMap<Date, Double> toReturnTreeMap(TreeMap<Date, Float> treeMap){
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
	
	
	public Primitive64Matrix toCovMatrix(List<TreeMap<Date, Float>> stocpPriceMapList) {
		Primitive64Matrix.Factory matrixFactory = Primitive64Matrix.FACTORY;
		Primitive64Matrix mat;
		
		int len = stocpPriceMapList.size();
		BigDecimal[][] covArr = new BigDecimal[len][len];
		for(int i = 0 ; i < len; i++) {
			for(int j = 0; j <len ; j++) {
				BigDecimal cov = calCov(toReturnTreeMap(stocpPriceMapList.get(i)), toReturnTreeMap(stocpPriceMapList.get(j)));
				covArr[i][j] = cov;
			}
		}
		mat= matrixFactory.rows(covArr);
		return mat;
	}
	
	public Primitive64Matrix toExpVector(List<TreeMap<Date, Float>> stockPriceMapList, TreeMap<Date,Float> kospiPriceMap ,List<IndexHistoryDataDto> baseRateList) {
		Primitive64Matrix.Factory matrixFactory = Primitive64Matrix.FACTORY;
		Primitive64Matrix mat;
		int n = stockPriceMapList.size();
		BigDecimal[] retArr = new BigDecimal[n];
		double baseRate = calBaseRate(baseRateList);
		for(int i = 0; i < n ; i++) {
			retArr[i] = calExpRet(stockPriceMapList.get(i),kospiPriceMap,baseRate);
		}
		mat = matrixFactory.rows(retArr);
		return mat;
	}
	
	public double calBaseRate(List<IndexHistoryDataDto> baseRateList) {
		TreeMap<Date, Float> map = toIndexMap(baseRateList);
		double geoMean = 1;
		for(Date date : map.keySet()) {
			geoMean *= map.get(date)/100+1;
		}
		geoMean = Math.pow(geoMean, (float) 1/map.size());
		double effectiveReturn = Math.pow(geoMean, (float) 1/ 365)-1;
		return effectiveReturn;
	}
	
	public BigDecimal calBeta(TreeMap<Date, Double> treeMapList, TreeMap<Date, Double> kospiReturnMap) {
		return (calCor(treeMapList, kospiReturnMap).divide(calStdv(kospiReturnMap),MathContext.DECIMAL128).multiply(calStdv(treeMapList)));
	}
	public BigDecimal calB(TreeMap<Date, Double> treeMapList, TreeMap<Date, Double> kospiReturnMap) {
		return calCov(treeMapList, kospiReturnMap).divide(calVol(kospiReturnMap),MathContext.DECIMAL128);
	}
	
	public BigDecimal calCumRet(TreeMap<Date, Double> returnMap) {
		BigDecimal cumReturn = new BigDecimal(1l);
		int len = returnMap.size();
		for(Date date : returnMap.keySet()) {
			cumReturn=cumReturn.multiply(new BigDecimal(1+ returnMap.get(date)));
		}
		return cumReturn.add(new BigDecimal(-1));
	}
	
	public BigDecimal calGeoMean(TreeMap<Date, Double> returnMap) {
		BigDecimal cumReturn = new BigDecimal(1l);
		int len = returnMap.size();
		for(Date date : returnMap.keySet()) {
			cumReturn= cumReturn.multiply(new BigDecimal(1+ returnMap.get(date)));
		}
		cumReturn =new BigDecimal(Math.pow(cumReturn.doubleValue(),(float)1/len)-1);
		return cumReturn;
	}
	public BigDecimal calExpRet(TreeMap<Date,Float> stockPriceMap, TreeMap<Date,Float> kospiPriceMap, double riskFreeRate) {
		TreeMap<Date, Double> stockRetMap = toReturnTreeMap(stockPriceMap);
		TreeMap<Date, Double> kospiRetMap = toReturnTreeMap(kospiPriceMap);
		BigDecimal rm = calGeoMean(kospiRetMap);
		BigDecimal beta = calBeta(stockRetMap, kospiRetMap);
		return new BigDecimal(riskFreeRate).add(beta.multiply(rm.add(new BigDecimal(-riskFreeRate))));
		
	}
	
	public BigDecimal calVol(TreeMap<Date, Double> returnMap) {
		BigDecimal mean = calGeoMean(returnMap);
		BigDecimal sum = new BigDecimal(0);
		for(Date date : returnMap.keySet()) {
			sum=sum.add(mean.add(new BigDecimal(-returnMap.get(date))).pow(2));
		}
		int n = returnMap.size();
		return sum.divide(new BigDecimal(n-1),MathContext.DECIMAL128);
		
	}
	public BigDecimal calStdv(TreeMap<Date, Double> returnMap) {
		BigDecimal vol = calVol(returnMap);
		
		return new BigDecimal(Math.pow(vol.doubleValue(),0.5));
	}
	public BigDecimal calCov(TreeMap<Date, Double> stock1, TreeMap<Date, Double> stock2) {
		BigDecimal mean1 = calGeoMean(stock1);
		BigDecimal mean2 = calGeoMean(stock2);
		BigDecimal sum =new BigDecimal(0);
		Set<Date> keySet = stock1.keySet();
		keySet.retainAll(stock2.keySet());
		for(Date date : keySet) {
			
			sum = sum.add(mean1.add(new BigDecimal(-stock1.get(date))).multiply(mean2.add(new BigDecimal(-stock2.get(date)))));
		}
		return sum.divide(new BigDecimal(keySet.size()-1), MathContext.DECIMAL128);
	}
	
	public BigDecimal calCor(TreeMap<Date, Double> stock1, TreeMap<Date, Double> stock2){
		BigDecimal cov = calCov(stock1, stock2);
		BigDecimal vol1 = calStdv(stock1);
		BigDecimal vol2 = calStdv(stock2);
		return cov.divide(vol1,MathContext.DECIMAL128).divide(vol2,MathContext.DECIMAL128);
	}

	public Primitive64Matrix toCovMat(List<TreeMap<Date, Double>> indexMapList) {
		Primitive64Matrix.Factory matrixFactory = Primitive64Matrix.FACTORY;
		Primitive64Matrix mat;
		BigDecimal[][] covArr = new BigDecimal[indexMapList.size()][indexMapList.size()];
		for(int i = 0; i<indexMapList.size();i++) {
			for(int j = 0; j<indexMapList.size();j++) {
				BigDecimal cov = calCor(indexMapList.get(i), indexMapList.get(j));
				covArr[i][j] = cov;
			}
		}
		mat= matrixFactory.rows(covArr);
		return mat;
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
	public BigInteger calSqrt(BigInteger val) {
	    BigInteger half = BigInteger.ZERO.setBit(val.bitLength() / 2);
	    BigInteger cur = half;

	    while (true) {
	        BigInteger tmp = half.add(val.divide(half)).shiftRight(1);

	        if (tmp.equals(half) || tmp.equals(cur))
	            return tmp;

	        cur = half;
	        half = tmp;
	    }
	}
}
