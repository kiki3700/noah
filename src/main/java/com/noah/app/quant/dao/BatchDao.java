package com.noah.app.quant.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.noah.app.util.QuantUtils;
import com.noah.app.vo.BalanceSheetDto;
import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

@Repository
public class BatchDao {
	@Autowired
	protected SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	QuantUtils quantUtil;
	
	//수정 요망
	public HashMap<String, TreeMap<Date, Double>> selectReturnDataTreeMap(List<ItemDto> itemDtoList, HashMap<String, Object> inParam){
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		HashMap<String, TreeMap<Date, Double>> resultMap = new HashMap<>();
		try {
			for(int i =0; i<itemDtoList.size();i++) {
				inParam.put("itemDto", itemDtoList.get(i));
				List<HistoryDataDto> historyDataDtoList = sqlSession.selectList("com.noah.app.quant.mapper.ItemMapper.selectHistoryDataList",inParam);
				if(historyDataDtoList.size()==(int) inParam.get("period")) {
					TreeMap<Date, Float> priceMap = quantUtil.toPriceMap(historyDataDtoList);
					TreeMap<Date, Double> returnMap = quantUtil.toReturnMap(priceMap);
					resultMap.put(itemDtoList.get(i).getId(), returnMap);
				}
			}
		}finally {
			sqlSession.flushStatements();
			sqlSession.close();
			sqlSession.clearCache();
		}
		return resultMap;
	}

	public HashMap<String, TreeMap<Date, Float>> selectPriceDataTreeMap(List<ItemDto> itemDtoList){
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		HashMap<String, TreeMap<Date, Float>> resultMap = new HashMap<>();
		int BusinessDates = sqlSession.selectOne("com.noah.app.quant.mapper.ItemMapper.selectBusinessDates");
		try {
			for(ItemDto itemDto : itemDtoList) {
				List<HistoryDataDto> historyDataDtoList = sqlSession.selectList("com.noah.app.quant.mapper.ItemMapper.selectHistoryDataL",itemDto);
				if(historyDataDtoList.size()==(int) BusinessDates) {
					TreeMap<Date, Float> priceMap = quantUtil.toPriceMap(historyDataDtoList);
					resultMap.put(itemDto.getId(), priceMap);
				}
			}
		}finally {
			sqlSession.flushStatements();
			sqlSession.close();
			sqlSession.clearCache();
		}
		return resultMap;
	}
	//수정요망
	public HashMap<String, List<HistoryDataDto>> selectHistoryDataListMap(List<ItemDto> itemDtoList, HashMap<String, Object> inParam){
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		HashMap<String, List<HistoryDataDto>> resultMap = new HashMap<>();
		int BusinessDates = sqlSession.selectOne("com.noah.app.quant.mapper.ItemMapper.selectBusinessDates");
		System.out.println("영업일 "+BusinessDates);
		try {
			for(ItemDto itemDto : itemDtoList) {
				List<HistoryDataDto> historyDataDtoList = sqlSession.selectList("com.noah.app.quant.mapper.ItemMapper.selectHistoryDataL",itemDto);
				if(historyDataDtoList.size()==(int) BusinessDates) {
					resultMap.put(itemDto.getId(), historyDataDtoList);
				}
			}
		}finally {
			sqlSession.flushStatements();
			sqlSession.close();
			sqlSession.clearCache();
		}
		return resultMap;
	}
	
	public HashMap<String, BalanceSheetDto> selectBalanceSheetMap(List<ItemDto> itemDtoList){
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
		HashMap<String, BalanceSheetDto> resultMap = new HashMap<>();
		try {
			for(int i =0; i<itemDtoList.size();i++) {
			BalanceSheetDto balanceSheetDto = sqlSession.selectOne("com.noah.app.quant.mapper.BalanceSheetMapper.selectBalanceSheetByYear",itemDtoList.get(i));
			if(balanceSheetDto!=null) resultMap.put(itemDtoList.get(i).getId(), balanceSheetDto);
			}
		}finally{
			sqlSession.flushStatements();
			sqlSession.close();
			sqlSession.clearCache();
		}
		return resultMap;
	}
}