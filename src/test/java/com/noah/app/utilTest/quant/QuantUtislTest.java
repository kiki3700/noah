package com.noah.app.utilTest.quant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.noah.app.dto.HistoryDataDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuantUtislTest {
	
	@Test
	public void test() throws FileNotFoundException {
		List<HistoryDataDto> list;
		File csv = new File("/noah/src/test/java/com/noah/app/utilTest/quant/stocka.csv");
		BufferedReader br = new BufferedReader(new FileReader(csv));
	}
}
