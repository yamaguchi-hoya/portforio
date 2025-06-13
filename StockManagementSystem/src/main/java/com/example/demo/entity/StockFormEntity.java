package com.example.demo.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class StockFormEntity {
	private Integer categoryId;
	private Integer itemId;
	private Integer sizeId;
	private Integer amount;
	private Date registerDate;
	private String person;
	private String comment;
	private Integer price;
}
