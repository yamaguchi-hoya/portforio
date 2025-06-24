package com.example.demo.entity;

import lombok.Data;

@Data
public class ItemFormEntity {
	private int itemId;
	private String itemName;
	private int categoryId;
	private int price;
}
