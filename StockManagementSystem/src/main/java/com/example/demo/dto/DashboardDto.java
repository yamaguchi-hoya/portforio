package com.example.demo.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DashboardDto {
	private int month;
	private int year;
	private List<Integer> dayList;
	private Map<Integer, Integer> shippingCountMap;
	private Map<Integer, Integer> arrivalCountMap;
	private Map<String, Integer> shippingCategoryMap;
	private Map<String, Integer> arrivalCategoryMap;
}
