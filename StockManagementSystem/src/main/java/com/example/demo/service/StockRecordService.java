package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.StockListDto;
import com.example.demo.entity.ItemFormEntity;
import com.example.demo.entity.StockFormEntity;

public interface StockRecordService {
	void shipping(StockFormEntity form);
	void arrival(StockFormEntity form);
	void registToStockByItem(ItemFormEntity form, int latestItemId);
	void registToStockBySize(ItemFormEntity form, List<String> sizes, int latestItemId);
	int serchCurrentAmount(StockFormEntity form);
	int serchCurrentAmount(int itemId, int sizeId);
	List<StockListDto> searchStockByCategoryId(int categoryId);
}
