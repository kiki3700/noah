package com.noah.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.constants.BusinessDays;
import com.noah.app.constants.ItemConst;
@RunWith(SpringRunner.class)
@SpringBootTest
public class EnumTest2 {
	@Test
	public  void teset() {
		BusinessDays date =  BusinessDays.ONEMONTH;
		System.out.println(date);
	}
	@Test
	public void itemEnum() {
		System.out.println(ItemConst.CorpSize.Large.getValue());
		System.out.println(ItemConst.CorpSize.valueOf("Total").getValue()==null);
		
	}
}
