package com.noah.app.quant.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.noah.app.quant.calculator.portfolioStrategy.StockPicker;
import com.noah.app.quant.service.QuantService;
import com.noah.app.vo.ItemDto;
import com.noah.app.vo.PortfolioWrapper;

@RequestMapping("/investment")
@RestController
public class QuantController {
	
	@Autowired
	QuantService quantService;
	
	@Autowired
	StockPicker stockPicer;
	
	@PostMapping(value="portfolio")
	public @ResponseBody PortfolioWrapper investPortfolio(@RequestBody HashMap<String, Object> inParam) {
		/*포트폴리오 생성기
		 * Author : 이성현
		 * 파라미터를 통해 포트폴리오를 생성한다.
		 * 프로세스 1 : 조건에 맞는 종목 리스트를 생성한다.
		 * 프로세스 2 : 전략에  정렬하고 랭크별로 소팅하고 30 내지 20개만 뱉는다.
		 * 프로세스 3 : 포트폴리오 비중을 구한다.
		 * 프로세스4 : 포트폴리오 투자 가능 금액 범위 내에 종목등의 개수를 구한
		 */
		
		List<ItemDto> itemList = quantService.pickStocks(inParam);
		
		return null;
	}
	
	@GetMapping(value="stock")
	@ResponseBody
	public List<HashMap<String, Object>> test(@RequestParam Optional<String> selectionStrategy, @RequestParam Optional<Integer> length, @RequestParam Optional<String> market, @RequestParam Optional<String> corpSize, @RequestParam Optional<List<String>> industry) {
		
		HashMap<String, Object> inParam = new HashMap<>();
		inParam.put("selectionStrategy", selectionStrategy.orElse("3factor"));
		inParam.put("length",length.orElse(30));
//		System.out.println(targetMarket);
//		inParam.put("targetMarket", targetMarket);
//		inParam.put("size",size);
//		inParam.put("industry", industry);
//		
		return quantService.selectStock(inParam);
	}
}
