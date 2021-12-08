package com.noah.app;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

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
	@Test
	public void optionalTest() {
		Optional<String> large = Optional.ofNullable("Large");
		Optional<String> nll = Optional.ofNullable(null);
		System.out.println(nll.orElse("Small"));
		assertEquals(large.get(),"Large");
		if(large.isPresent()) {
			assertEquals(ItemConst.CorpSize.valueOf(large.get()).getValue(),"CPC_CAPITAL_LARGE");
		}
	}
}
