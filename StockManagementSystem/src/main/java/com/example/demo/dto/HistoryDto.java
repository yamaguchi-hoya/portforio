package com.example.demo.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryDto {
	private String categoryName;
	private String itemName;
	private String sizeName;
	private Integer amount;
	private Date registerDate;
	private String person;
	private String comment;
	private String execute;
}
