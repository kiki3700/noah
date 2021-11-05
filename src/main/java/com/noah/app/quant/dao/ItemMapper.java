package com.noah.app.quant.dao;

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
	List<HistoryDataDto> selectHistoryDataByItemId(ItemDto itemDto);
	List<HistoryDataDto> selectClosingPrice (Map<String, Object> inParam);
}
