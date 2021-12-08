package com.noah.app.quant.service.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.noah.app.quant.calculator.portfolioStrategy.StockDivider;
import com.noah.app.quant.calculator.portfolioStrategy.StockPicker;
import com.noah.app.quant.mapper.ItemMapper;
import com.noah.app.quant.mapper.QuantMapper;
import com.noah.app.quant.service.QuantService;
import com.noah.app.vo.ItemDto;
import com.noah.app.vo.PortfolioDto;
import com.noah.app.vo.PortfolioTargetListDto;
import com.noah.app.wrapper.PortfolioWrapper;
import com.noah.app.wrapper.StockWrapper;

@Service
@Primary
public class QuantServiceImpl implements QuantService{
	
	@Autowired
	ItemMapper itemMapper;
	
	@Autowired
	QuantMapper quantMapper;
	
	@Autowired
	StockPicker stockPicker;
	
	@Autowired
	StockDivider stockDivider;
	
	/*포트폴리오 생성기
	 * Author : 이성현
	 * 파라미터를 통해 포트폴리오를 생성한다.
	 * 프로세스 1 : 조건에 맞는 종목 리스트를 생성한다.
	 * 프로세스 2 : 전략에  정렬하고 랭크별로 소팅하고 30 내지 20개만 뱉는다.
	 * 프로세스 3 : 포트폴리오 비중을 구한다.
	 * 프로세스4 : 포트폴리오 투자 가능 금액 범위 내에 종목등의 개수를 구한
	 */

	@Override
	public List<StockWrapper> calculateStocks(Map<String, Object> inParam){
		List<ItemDto> itemDtoList = stockPicker.sortStockByStrategy(inParam);
		List<StockWrapper> stockWrapperList = stockPicker.getSelectStockTalbe(itemDtoList);
		return stockWrapperList;
	}
	
	@Override
	public PortfolioWrapper calculatePortfolio(Map<String, Object> inParam){
		List<StockWrapper> stockWrapperList = (List<StockWrapper>) inParam.get("stockWrapperList");
		int limit = (int) inParam.getOrDefault("limit", 1000000);
		PortfolioWrapper protfolioWrapper = stockDivider.calculateWeightList(stockWrapperList, inParam);
		protfolioWrapper.setLimit(limit);
		protfolioWrapper = stockDivider.getAmountOfStock(protfolioWrapper);
		return protfolioWrapper;
	}
	/*
	 * 
	 */
	@Override
	public void enrollPortfolio(PortfolioWrapper portfolioWrapper) {
		List<StockWrapper> stockWrapperList = portfolioWrapper.getStockList();
		int seq = quantMapper.selectPortfolioSeqNextVal();
		PortfolioDto portfolioDto = new PortfolioDto();
		String name = portfolioWrapper.getName();
		String strategy = portfolioWrapper.getStrategey();
		String id = name+strategy+seq;
		portfolioDto.setId(id);
		portfolioDto.setName(name);
		portfolioDto.setStrategy(strategy);
		portfolioDto.setDetailStrategy(portfolioWrapper.getDetailStrategy());
		portfolioDto.setManagementStrategy(portfolioWrapper.getManagementStrategy());
		portfolioDto.setStatus("active");
		portfolioDto.setLimit(portfolioWrapper.getLimit());
		portfolioDto.setRebalncingDate(portfolioWrapper.getRebalncingDate());
		quantMapper.insertPortfolio(portfolioDto);
		for(StockWrapper stockWrapper : stockWrapperList) {
			if(stockWrapper.getAmount()<=0) continue;
			PortfolioTargetListDto portfolioTargetListDto = new PortfolioTargetListDto();
			portfolioTargetListDto.setItemid(stockWrapper.getItemDto().getId());
			portfolioTargetListDto.setAmount(stockWrapper.getAmount());
			portfolioTargetListDto.setPortfolioId(id);
			portfolioTargetListDto.setStatus("Ative");
			portfolioTargetListDto.setWeight(stockWrapper.getWeight());
			quantMapper.insertPorrtfolioTarget(portfolioTargetListDto);
		}
	}
}
