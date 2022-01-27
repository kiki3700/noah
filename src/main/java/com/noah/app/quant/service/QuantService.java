package com.noah.app.quant.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.noah.app.vo.ItemDto;
@Service
public interface QuantService {

	List<ItemDto> pickStocks(Map<String, Object> inParams);

}
