package com.noah.app.quant.service.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.noah.app.quant.calculator.portfolioStrategy.StockPicker;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.quant.service.QuantService;
import com.noah.app.vo.ItemDto;
import com.noah.app.wrapper.StockWrapper;

@Service
@Primary
public class QuantServiceImpl implements QuantService{
	
	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	StockPicker stockPicker;
	
	/*포트폴리오 생성기
	 * Author : 이성현
	 * 파라미터를 통해 포트폴리오를 생성한다.
	 * 프로세스 1 : 조건에 맞는 종목 리스트를 생성한다.
	 * 프로세스 2 : 전략에  정렬하고 랭크별로 소팅하고 30 내지 20개만 뱉는다.
	 * 프로세스 3 : 포트폴리오 비중을 구한다.
	 * 프로세스4 : 포트폴리오 투자 가능 금액 범위 내에 종목등의 개수를 구한
	 */
	@Override
	public List<ItemDto> pickStocks(Map<String, Object> inParams){
		List<ItemDto> itemDtoList = itemMapper.selectItemDtoList(inParams);
		return itemDtoList;
	}
	@Override
	public List<HashMap<String, Object>> selectStock(Map<String, Object> inParam){
		List<StockWrapper> stockList = stockPicker.sortStockByStrategy(inParam);
		List<HashMap<String, Object>> selectedHashMapList = stockPicker.getSelectStockTalbe(stockList);
		return selectedHashMapList;
	}
	
}
