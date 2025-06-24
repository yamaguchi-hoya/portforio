package com.example.demo.repository;

import java.util.List;

import com.example.demo.dto.StockListDto;
import com.example.demo.entity.ItemFormEntity;
import com.example.demo.entity.StockFormEntity;

public interface StockRecordRepository {
	void wrightRecordShipping(StockFormEntity form, int stockId);
	void wrightRecordArrival(StockFormEntity form, int stockId);
	void stockShipping(int updateAmount, int stockId);
	void stockArrival(int updateAmount, int stockId);
	void registToStockByItem(ItemFormEntity form, int latestItemId);
	void registToStockBySize(ItemFormEntity form, List<String> sizes, int latestItemId);
	void deleteStockByItemId(int itemId);
	int serchStockId(StockFormEntity form);
	int serchStockId(int itemId, int sizeId);
	int currentAmount(int stockId);
	List<StockListDto> searchStockByCategoryId(int categoryId);
}
