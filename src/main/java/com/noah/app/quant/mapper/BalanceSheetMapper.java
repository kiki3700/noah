package com.noah.app.quant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.noah.app.dto.BalanceSheetDto;
import com.noah.app.dto.ItemDto;

@Mapper
public interface BalanceSheetMapper {
	BalanceSheetDto selectBalanceSheetByYear(ItemDto itemDto);
	List<BalanceSheetDto> selectBalanceSheetByYearHorizontally();
}
