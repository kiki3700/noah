package com.noah.app.quant.mapperTest;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.quant.mapper.BalanceSheetMapper;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.vo.BalanceSheetDto;
import com.noah.app.vo.ItemDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BalanceSheetTest {
	
	@Autowired
	BalanceSheetMapper balanceSheetMapper;
	
	@Autowired
	ItemMapper itemMapper;
	
	List<ItemDto> itemDtoList;
	
	@Before
	public void init() {
		itemDtoList = itemMapper.selectItemDtoList(null);
	}
	
	@Test
	public void getBal() {
		for(ItemDto item : itemDtoList) {
			BalanceSheetDto bal = balanceSheetMapper.selectBalanceSheetByYear(item);
			if(bal==null) continue;
				System.out.println(bal);
			
		}
	}
}
