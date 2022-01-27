package com.noah.app.quant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.noah.app.dto.PortfolioDto;
import com.noah.app.dto.PortfolioTargetListDto;

@Mapper
public interface QuantMapper {
	void insertPortfolio(PortfolioDto portfolioDto);
	void insertPorrtfolioTarget(PortfolioTargetListDto portfolioTargetListDto);
	int selectPortfolioSeqNextVal();
}
