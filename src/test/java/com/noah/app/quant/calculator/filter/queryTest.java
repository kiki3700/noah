package com.noah.app.quant.calculator.filter;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.dto.BalanceSheetDto;
import com.noah.app.dto.HistoryDataDto;
import com.noah.app.quant.mapper.BalanceSheetMapper;
import com.noah.app.quant.mapper.ItemMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class queryTest {
	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	BalanceSheetMapper balanceSheetDao;
	
	HashMap<String, Object> inParam = new HashMap<>();
	
	@Test
	public void queryTesr() {
		inParam.put("itemId", "A067160");
		HistoryDataDto hi = itemMapper.selectCurHistroyData(inParam);
		System.out.println(hi);
	}
	@Test
	public void balTest() {
		List<BalanceSheetDto> balanceSheetList = balanceSheetDao.selectBalanceSheetByYearHorizontally();
		HashMap<String, BalanceSheetDto> balanceSheetMap =  new HashMap<>();
		for(int i = 0 ; i<balanceSheetList.size();i++) {
			BalanceSheetDto balanceSheetDto = balanceSheetList.get(i);
			String itemId = balanceSheetDto.getItemId();
			balanceSheetMap.put(itemId, balanceSheetDto);
			System.out.println(itemId);
		}
		balanceSheetList.clear();
	}
}
