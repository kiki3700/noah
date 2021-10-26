package com.noah.app.quant.dao;

import org.apache.ibatis.annotations.Mapper;

import com.noah.app.vo.BalanceSheetDto;
import com.noah.app.vo.ItemDto;

@Mapper
public interface BalanceSheetDao {
	BalanceSheetDto selectBalanceSheet(ItemDto itemDto);
}
