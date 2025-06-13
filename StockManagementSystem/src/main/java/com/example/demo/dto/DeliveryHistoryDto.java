package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DeliveryHistoryDto {
	private String destination;	
	private LocalDate deliveryDate;
	private Integer deliverySlipId;
	private byte[] document;
}
