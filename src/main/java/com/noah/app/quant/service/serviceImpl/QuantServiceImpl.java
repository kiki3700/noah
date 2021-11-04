package com.noah.app.quant.service.serviceImpl;

import java.util.List;
import java.util.Map;

import org.ojalgo.series.CalendarDateSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.noah.app.quant.calculator.portfolioStrategy.StockPicker;
import com.noah.app.quant.dao.ItemMapper;
import com.noah.app.quant.service.QuantService;
import com.noah.app.vo.ItemDto;

@Component
public class QuantServiceImpl implements QuantService{
	
	@Autowired
	ItemMapper itemDao;
	
	@Autowired
	StockPicker StockPicker;
	
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
		List<ItemDto> itemDtoList = itemDao.selectItemDtoList(inParams);
		return itemDtoList;
	}
	
}
