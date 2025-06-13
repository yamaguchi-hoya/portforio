package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.HistoryDto;

public interface StockHistoryService {
	List<HistoryDto> getAllHistory();
	List<HistoryDto> getSearch(@RequestParam(required = false) Integer categoryId,
							  @RequestParam(required = false) Integer itemId,
							  @RequestParam(required = false) Integer sizeId,
							  @RequestParam(required = false) LocalDate startDate,
							  @RequestParam(required = false) LocalDate endDate,
							  @RequestParam(required = false) String execute);
}
