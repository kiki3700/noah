package com.noah.app.quant.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.noah.app.vo.IndexHistoryDataDto;

@Mapper
public interface IndexHistoryDataMapper {
	List<IndexHistoryDataDto> selectIndexHistoryDataList (HashMap<String, Object> inParam);
}
