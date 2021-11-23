package com.noah.app.quant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.noah.app.vo.BalanceSheetDto;
import com.noah.app.vo.ItemDto;

@Mapper
public interface BalanceSheetMapper {
	BalanceSheetDto selectBalanceSheetByYear(ItemDto itemDto);
}
