package com.example.demo.dto;

import lombok.Data;

@Data
public class DeliveryItemDto {
	private int itemNo;
	private Integer categoryId;
	private Integer itemId;
	private String itemName;
	private Integer sizeId;
	private String sizeName;
	private Integer unitPrice;
	private Integer amount;
	private Integer price;
}
