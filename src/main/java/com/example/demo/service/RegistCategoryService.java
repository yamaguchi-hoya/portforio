package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.CategoryFormEntity;

public interface RegistCategoryService {
	void regist(CategoryFormEntity form);
	void remove(CategoryFormEntity form);
	void changeName(CategoryFormEntity form);
	List<CategoryDto> getAll();
	List<CategoryDto> getSelect(int id);
}
