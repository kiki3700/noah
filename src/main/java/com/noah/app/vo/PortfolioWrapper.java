package com.noah.app.vo;

import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;

public class PortfolioWrapper {
	private List<StockWrapper> stockList;
	private RealMatrix cov;
	private double limit;
}
