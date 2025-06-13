package com.example.demo.entity;

import lombok.Data;

@Data
public class DeliveryContent {
	private String item;
	private String size;
	private int price;
	private int amount;
	private int priceSum;
}
