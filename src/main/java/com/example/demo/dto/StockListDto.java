package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockListDto {
	private String categoryName;
	private String itemName;
	private String sizeName;
	private Integer amount;
	private Integer price;
}
