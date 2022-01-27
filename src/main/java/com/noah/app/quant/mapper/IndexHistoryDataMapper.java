package com.noah.app.quant.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.noah.app.dto.IndexHistoryDataDto;

@Mapper
public interface IndexHistoryDataMapper {
	List<IndexHistoryDataDto> selectIndexHistoryDataList (HashMap<String, Object> inParam);
	List<IndexHistoryDataDto> selectIndexHistoryDataListByYear(HashMap<String, Object> inParam);
}
