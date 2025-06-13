package com.example.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "record")
public class HistoryFormEntity {
	@Id
	private Integer recordId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="stock_id")
	private StockEntity stock;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="category_id")
//	private Category category;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="item_id")
//	private Item item;
//	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name="size_id", nullable = true)
//	private SizeFormEntity size;
	
	private Integer amount;
	private Date registerDate;
	private String person;
	private String comment;
	private String execute;
}
