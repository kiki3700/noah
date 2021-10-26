package com.noah.app.quant.service;

import java.util.List;
import java.util.Map;

import com.noah.app.vo.ItemDto;

public interface QuantService {

	List<ItemDto> pickStocks(Map<String, Object> inParams);

}
