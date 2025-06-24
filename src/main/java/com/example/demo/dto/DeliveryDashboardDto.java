package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDashboardDto {
	private LocalDate deliveryDate;
	private String category;
	private Integer price;
}
