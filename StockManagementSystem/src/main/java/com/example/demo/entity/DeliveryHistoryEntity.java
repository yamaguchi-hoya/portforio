package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "delivery_record")
public class DeliveryHistoryEntity {
	@Id
	private String deliveryId;	
	
	@Column(name = "delivery_destination")
	private String destination;	
	
	@Column(name = "delivery_date")
	private LocalDate deliveryDate;
	
	@Column(name = "delivery_slip_id")
	private Integer deliverySlipId;
}
