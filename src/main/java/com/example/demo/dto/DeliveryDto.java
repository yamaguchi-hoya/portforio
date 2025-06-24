package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.entity.Company;
import com.example.demo.entity.DeliveryContentList;

import lombok.Data;

@Data
public class DeliveryDto {
	private Company company;
	private String destination;
	private Integer deliveryNumber;
	private LocalDate date;
	private DeliveryContentList contentList;
	private String note;
}