package com.noah.app.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

/*스트림 공부하고 리팰터링*/
@Component
public class Statistics {
	public <N extends Number> double calculateMean(HashMap<String, N > map) {
		int len = map.size();
		double mean = 0;
		for(String key : map.keySet()) {
			mean += (Double) map.get(key)/len;
		}
		return mean;
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
