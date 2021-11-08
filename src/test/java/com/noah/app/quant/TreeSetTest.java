package com.noah.app.quant;

import java.sql.Date;
import java.util.TreeMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TreeSetTest {
	
	
	@Test
	public void test() {
		TreeMap<Date, Double> map = new TreeMap<>();
		map.put(new Date(10, 1,3), (double) 3);
		map.put(new Date(10, 1,2), (double) 2);
		map.put(new Date(10, 1,1), (double) 1);
		for(Date key : map.keySet()) {
			System.out.println(key +" : "+ map.get(key));
		}
	}
}
