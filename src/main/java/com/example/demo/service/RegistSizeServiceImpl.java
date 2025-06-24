package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SizeDto;
import com.example.demo.entity.SizeFormEntity;
import com.example.demo.repository.RegistSizeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistSizeServiceImpl implements RegistSizeService {
	private final RegistSizeRepository repository;
	@Override
	public void regist(SizeFormEntity form) {
		repository.regist(form);
	}

	@Override
	public void remove(SizeFormEntity form) {
		repository.remove(form);

	}

	@Override
	public void changeName(SizeFormEntity form) {
		repository.changeName(form);
		
	}

	@Override
	public List<SizeDto> getAll() {
		List<SizeDto> list = repository.getAll();
		return list;
	}

	@Override
	public List<SizeDto> getSelectBySizeId(int id) {
		List<SizeDto> list = repository.getSelectBySizeId(id);
		return list;
	}

	@Override
	public List<SizeDto> getSelectByItemId(int id) {
		List<SizeDto> list = repository.getSelectByItemId(id);
		return list;
	}

}
