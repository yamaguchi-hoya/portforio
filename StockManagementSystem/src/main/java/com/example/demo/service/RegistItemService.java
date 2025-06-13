package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.ItemDto;
import com.example.demo.entity.ItemFormEntity;

public interface RegistItemService {
	void regist(ItemFormEntity form);
	void remove(ItemFormEntity form);
	void change(ItemFormEntity form);
	void registToStock(ItemFormEntity form);
	List<ItemDto> getAll();
	List<ItemDto> getSelectByItemId(int id);
	List<ItemDto> getSelectByCategoryId(int id);
	List<ItemDto> getSelectByLatestId();
}
