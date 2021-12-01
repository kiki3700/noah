package com.noah.app.quant.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.noah.app.vo.ItemDto;

public interface QuantService {

	List<ItemDto> pickStocks(Map<String, Object> inParams);

	List<HashMap<String, Object>> selectStock(Map<String, Object> inParam);

}
