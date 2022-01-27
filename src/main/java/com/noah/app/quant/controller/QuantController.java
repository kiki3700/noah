package com.noah.app.quant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.noah.app.constants.ItemConst;
import com.noah.app.quant.calculator.portfolioStrategy.StockPicker;
import com.noah.app.quant.service.QuantService;
import com.noah.app.wrapper.PortfolioWrapper;
import com.noah.app.wrapper.StockWrapper;

@RequestMapping("/investment")
@RestController
public class QuantController {
	
	@Autowired
	QuantService quantService;
	
	@Autowired
	StockPicker stockPicer;
	
	/*포트폴리오 생성기
	 * Author : 이성현
	 * 파라미터를 통해 포트폴리오를 생성한다.
	 * 프로세스 1 : 조건에 맞는 종목 리스트를 생성한다.
	 * 프로세스 2 : 전략에  정렬하고 랭크별로 소팅하고 30 내지 20개만 뱉는다.
	 * 프로세스 3 : 포트폴리오 비중을 구한다.
	 * 프로세스4 : 포트폴리오 투자 가능 금액 범위 내에 종목등의 개수를 구한
	 */
	@GetMapping(value="stock/candidate")
	@ResponseBody
	public List<StockWrapper> getFilteredStockWrapperList(@RequestParam Optional<String> selectionStrategy, @RequestParam Optional<Integer> length, @RequestParam Optional<String> market, @RequestParam Optional<String> corpSize, @RequestParam Optional<List<String>> industry) {
		HashMap<String, Object> inParam = new HashMap<>();
		inParam.put("selectionModel", selectionStrategy.orElse("3factor"));
		inParam.put("length",length.orElse(30));
		if(market.isPresent()) inParam.put("market", ItemConst.Market.valueOf(market.get()).getValue());
		if(corpSize.isPresent()) inParam.put("corpSize", ItemConst.CorpSize.valueOf(corpSize.get()).getValue());
		if(industry.isPresent()) inParam.put("industry", industry.get());
		return quantService.calculateStocks(inParam);
	}

	/*
	 * 화면에 맞는 데이터 다 넣어주기
	 * 
	 */
	@GetMapping(value="portfolio/candidate")
	@ResponseBody
	public PortfolioWrapper getCandidatedPortfolio(@RequestParam Optional<String> strategy, @RequestParam Optional<String> detailStrategy, @RequestParam Optional<Integer> limit,@RequestParam Optional<List<StockWrapper>> stockWrapperList){
		PortfolioWrapper portfolioWrapper = new PortfolioWrapper();
		Map<String, Object> inParam = new HashMap<>();
		inParam.put("limit", limit.get());
		List<StockWrapper> calculatedPortfolioList = new ArrayList<>();
		if(strategy.orElse("portfolioStrategy").equals("portfolioStrategy")) {
			/*
			 * 파라미터 추가하고 파라미터에 넣어주기
			 */
			inParam.put("detailStrategy", detailStrategy.orElse("Makowitz"));
			inParam.put("stockWrapperList", stockWrapperList.get());
			
			portfolioWrapper = quantService.calculatePortfolio(inParam);
		}
	
		return portfolioWrapper;
	}
	@PostMapping(value="portfolio")
	@ResponseBody
	public void postPortfolio(@RequestBody PortfolioWrapper portfolioWrapper) {
		quantService.enrollPortfolio(portfolioWrapper);
	}
}
