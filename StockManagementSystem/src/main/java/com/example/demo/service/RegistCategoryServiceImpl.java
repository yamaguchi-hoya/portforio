package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.CategoryFormEntity;
import com.example.demo.repository.RegistCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistCategoryServiceImpl implements RegistCategoryService {
	private final RegistCategoryRepository repository;

	@Override
	public void regist(CategoryFormEntity form) {
		repository.regist(form);

	}

	@Override
	public void remove(CategoryFormEntity form) {
		repository.remove(form);
		
	}

	@Override
	public void changeName(CategoryFormEntity form) {
		repository.changeName(form);
	}

	@Override
	public List<CategoryDto> getAll() {
		List<CategoryDto> list = repository.getAll();
		return list;
	}

	@Override
	public List<CategoryDto> getSelect(int id) {
		List<CategoryDto> list = repository.getSelect(id);
		return list;
	}

}
