package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemDto {
	private int itemId;
	private String itemName;
	private int categoryId;
//	private String categoryName;
	private int price;
}
