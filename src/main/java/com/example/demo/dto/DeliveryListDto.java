package com.example.demo.dto;

import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryListDto {
	
	private LocalDate deliveryDate;
	private Map<Integer, Integer> itemList;
}
