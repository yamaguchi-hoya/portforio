package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.StockListDto;
import com.example.demo.entity.ItemFormEntity;
import com.example.demo.entity.StockFormEntity;
import com.example.demo.repository.StockRecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockRecordServiceImpl implements StockRecordService {
	private final StockRecordRepository repository;

	@Override
	public void shipping(StockFormEntity form) {
		int stockId = repository.serchStockId(form);
		int curentAmount = repository.currentAmount(stockId);
		int updateAmount = curentAmount - form.getAmount();
		repository.wrightRecordShipping(form,stockId);
		repository.stockShipping(updateAmount, stockId);
	}

	@Override
	public void arrival(StockFormEntity form) {
		int stockId = repository.serchStockId(form);
		int curentAmount = repository.currentAmount(stockId);
		int updateAmount = curentAmount + form.getAmount();
		repository.wrightRecordArrival(form,stockId);
		repository.stockArrival(updateAmount, stockId);
	}

	@Override
	public void registToStockBySize(ItemFormEntity form, List<String> sizes, int latestItemId) {
		repository.registToStockBySize(form, sizes, latestItemId);	
	}

	@Override
	public void registToStockByItem(ItemFormEntity form, int latestItemId) {
		repository.registToStockByItem(form, latestItemId);
		
	}

	@Override
	public int serchCurrentAmount(StockFormEntity form) {
		int stockId = repository.serchStockId(form);
		int currentAmount = repository.currentAmount(stockId);
		return currentAmount;
	}

	@Override
	public int serchCurrentAmount(int itemId, int sizeId) {
		int stockId = repository.serchStockId(itemId, sizeId);
		int currentAmount = repository.currentAmount(stockId);
		return currentAmount;
	}

	@Override
	public List<StockListDto> searchStockByCategoryId(int categoryId) {
		List<StockListDto> stockList = repository.searchStockByCategoryId(categoryId);
		return stockList;
	}

}
