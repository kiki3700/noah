package com.noah.app.testUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.noah.app.dto.HistoryDataDto;

public class CsvUtils {
	private final static String PATH = "src/test/java/com/noah/app/resource/";
	
	public static BufferedReader readCsv(String fileRoute) throws ParseException, NumberFormatException, IOException{
		String path = PATH +fileRoute;
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		return br;
	}
	public static BigDecimal getBigDecimal(String fileName) throws NumberFormatException, ParseException, IOException {
		String fileRoute = "stockCsv/"+fileName;
		BufferedReader br = readCsv(fileRoute);
		return new BigDecimal(br.readLine());
	}
	public static Map<String, Object> getStatistic(String fileName) throws NumberFormatException, ParseException, IOException{
		String fileRoute = "stockCsv/"+fileName;
		BufferedReader br = readCsv(fileRoute);
		Map<String, Object> map = new HashMap<>();
		String line = new String();
		while((line = br.readLine())!=null) {
			StringTokenizer st = new StringTokenizer(line, ",");
			String key = st.nextToken();
			String val = st.nextToken();
			map.put(key, val);
		}
		return map;
	}
	public static void writeMapCsv(String fileName, Map<String, Object> map) throws IOException {
		fileName = PATH + "stockCsv/"+fileName;
		File file = new File(fileName);
		FileWriter fw = new FileWriter(file, false);
		for(String key : map.keySet()) {
			fw.write(key+","+map.get(key)+"\n");
		}
		fw.flush();
		fw.close();
	}
	
	public static TreeMap<Date, Double> getTreeMap(String fileName) throws NumberFormatException, ParseException, IOException{
		String fileRoute = "stockCsv/"+fileName;
		BufferedReader br =readCsv(fileRoute);
		TreeMap<Date, Double> treeMap = new TreeMap<>();
		String line  = new String();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		while((line = br.readLine())!=null) {
			StringTokenizer st = new StringTokenizer(line, ",");
			Date date = formatter.parse(st.nextToken());
			double value = Double.parseDouble(st.nextToken());
			treeMap.put(date, value);
		}
		return treeMap;
	}
	public static List<HistoryDataDto> getHistoryDataDtoList(String fileName) throws NumberFormatException, ParseException, IOException{
		String fileRoute = "stockCsv/"+fileName;
		String line = new String();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		BufferedReader br = readCsv(fileRoute);
		List<HistoryDataDto> historyDataDtolist = new ArrayList<>();
		while((line = br.readLine())!=null) {
			StringTokenizer st = new StringTokenizer(line,",");
			Date date = formatter.parse(st.nextToken());
			int close = Integer.parseInt(st.nextToken());
			HistoryDataDto historyDataDto = new HistoryDataDto();
			historyDataDto.setTradingDate(date);
			historyDataDto.setClose(close);
			historyDataDtolist.add(historyDataDto);
		}
		return historyDataDtolist;
	}
}
