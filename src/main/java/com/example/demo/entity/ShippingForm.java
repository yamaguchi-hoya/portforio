package com.example.demo.entity;

import java.sql.Date;

import lombok.Data;

@Data
public class ShippingForm {
	private String category;
	private String item;
	private String size;
	private Integer amount;
	private Date registerDate;
	private String person;
	private String comment;
}
