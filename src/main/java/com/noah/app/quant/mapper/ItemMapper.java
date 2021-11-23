package com.noah.app.quant.mapper;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

@Mapper
public interface ItemMapper {
	List<ItemDto> selectItemDtoList(Map<String, Object> inParams);
	List<HistoryDataDto> selectHistoryDataList (Map<String, Object> inParam);
	List<HistoryDataDto> selectHistoryDataListUnion(List<ItemDto> itemDtoList);
	List<HistoryDataDto> selectHistoryDataByYear();
	List<HistoryDataDto> selectHistoryDataL(ItemDto itemDto);
	int selectBusinessDates();
}
