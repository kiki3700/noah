package com.noah.app.quant.controller;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.quant.service.serviceImpl.QuantServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuantContService {
	@Autowired
	QuantServiceImpl quant;
	
	@Test
	public void quantTest() {
		HashMap<String, Object> inParam = new HashMap<>();
		inParam.put("selectionStrategy", "3factor");
		inParam.put("length", 30);
		quant.calculateStocks(inParam);
	}
}
