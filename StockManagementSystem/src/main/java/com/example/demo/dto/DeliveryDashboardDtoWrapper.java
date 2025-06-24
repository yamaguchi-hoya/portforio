package com.example.demo.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDashboardDtoWrapper {
	private Map<Integer, Integer> dailyPrice;
	private Map<Integer, Integer> totalDailyAmount;
	private Map<String, Map<Integer, Integer>> categoryDailyPriceList;
	private Map<String, Integer> categoryAmountTotal;
	private Map<String, Integer> categoryPriceTotal;
	private List<Integer> dayList;
	private Integer year;
	private Integer month;
}
