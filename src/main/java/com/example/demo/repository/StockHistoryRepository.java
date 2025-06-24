package com.example.demo.repository;

import java.util.List;

import com.example.demo.dto.HistoryDto;

public interface StockHistoryRepository{
	List<HistoryDto> getAllHistory();	
}
