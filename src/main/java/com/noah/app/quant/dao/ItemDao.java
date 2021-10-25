package com.noah.app.quant.dao;

import java.util.List;
import java.util.Map;

import com.noah.app.vo.HistoryDataDto;
import com.noah.app.vo.ItemDto;

public interface ItemDao {
	List<ItemDto> selectItemDtoList(Map<String, Object> inParams);
	List<HistoryDataDto> selectHistoryDataByItemId(ItemDto itemDto);
}
