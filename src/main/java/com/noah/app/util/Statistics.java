package com.noah.app.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.ojalgo.series.CalendarDateSeries;
import org.ojalgo.type.CalendarDate;
import org.springframework.stereotype.Component;

import com.noah.app.vo.HistoryDataDto;

/*스트림 공부하고 리팰터링*/
@Component
public class Statistics {
	
	public <N extends Comparable<N>> CalendarDateSeries<N> makeCalenderDateSeries(List<HistoryDataDto> historyDataDtoList){
		CalendarDateSeries<N> calendaDateSeries = new CalendarDateSeries<>();
		for(int i = 0 ; i< historyDataDtoList.size();i++) {
			HistoryDataDto historyDataDto = historyDataDtoList.get(i);
			CalendarDate calendarDate = new CalendarDate(historyDataDto.getTradingDate());
			calendaDateSeries.put(calendarDate, historyDataDto.getClose());
		}
		return calendaDateSeries;
	}
	public <N extends Comparable<N>> CalendarDateSeries<N> makeCalenderDateSeries(List<HistoryDataDto> historyDataDtoList, int len){
		CalendarDateSeries<N> calendaDateSeries = new CalendarDateSeries<>();
		for(int i = 0 ; i< len;i++) {
			HistoryDataDto historyDataDto = historyDataDtoList.get(i);
			CalendarDate calendarDate = new CalendarDate(historyDataDto.getTradingDate());
			calendaDateSeries.put(calendarDate, historyDataDto.getClose());
		}
		return calendaDateSeries;
	}

	public <N extends Comparable<N>> CalendarDateSeries<N> makeReturnCalenderDateSeries(List<HistoryDataDto> historyDataDtoList){
		CalendarDateSeries<N> calendaDateSeries = new CalendarDateSeries<>();
		HistoryDataDto preHistory = historyDataDtoList.get(historyDataDtoList.size()-1);
		for(int i = historyDataDtoList.size()-2 ; i>=0 ;i--) {
			HistoryDataDto historyDataDto = historyDataDtoList.get(i);
			float prePrice = preHistory.getClose();
			float curPrice= historyDataDto.getClose();
			double ret = (curPrice-prePrice)/prePrice; 
			CalendarDate calendarDate = new CalendarDate(historyDataDto.getTradingDate());
			calendaDateSeries.put(calendarDate, ret);
		}
		return calendaDateSeries;
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
