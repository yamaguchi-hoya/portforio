package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ItemDto;
import com.example.demo.entity.ItemFormEntity;
import com.example.demo.repository.RegistItemRepository;
import com.example.demo.repository.RegistSizeRepository;
import com.example.demo.repository.StockRecordRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RegistItemServiceImpl implements RegistItemService {
	private final RegistItemRepository repository;
	private final StockRecordRepository stockRecordRepository;
	private final RegistSizeRepository registSizeRepository;

	@Override
	public void regist(ItemFormEntity form) {
		repository.regist(form);
	}

	@Override
	public void remove(ItemFormEntity form) {
		repository.remove(form);
		
//		item削除時stockとsizeを削除する機能
//		追加したほうがいい？？？？？？？？追加しないほうがいい？？？？？？
//		stockRecordRepository.deleteStockByItemId(form.getItemId());
//		registSizeRepository.removeByItemId(form.getItemId());
	}

	@Override
	public void change(ItemFormEntity form) {
		repository.change(form);		
	}

	@Override
	public void registToStock(ItemFormEntity form) {
		repository.registToStock(form);		
	}

	@Override
	public List<ItemDto> getAll() {
		List<ItemDto> list = repository.getAll();
		return list;
	}

	@Override
	public List<ItemDto> getSelectByItemId(int id) {
		List<ItemDto> list = repository.getSelectByItemId(id);
		return list;
	}

	@Override
	public List<ItemDto> getSelectByCategoryId(int id) {
		List<ItemDto> list = repository.getSelectByCategoryId(id);
		return list;
	}

	@Override
	public List<ItemDto> getSelectByLatestId() {
		List<ItemDto> list = repository.getSelectByLatestId();
		return list;
	}

}
