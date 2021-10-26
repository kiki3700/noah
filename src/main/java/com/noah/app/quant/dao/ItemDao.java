package com.noah.app.quant.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;
import com.noah.app.vo.StockWrapper;

@Mapper
public interface ItemDao {
	List<ItemDto> selectItemDtoList(Map<String, Object> inParams);
	List<HistoryDataDto> selectHistoryDataByItemId(ItemDto itemDto);
	List<HistoryDataDto> selectHistoryDataByItemId(StockWrapper StockWrapper);
}
