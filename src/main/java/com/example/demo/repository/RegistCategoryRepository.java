package com.example.demo.repository;

import java.util.List;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.CategoryFormEntity;

public interface RegistCategoryRepository {
	void regist(CategoryFormEntity form);
	void remove(CategoryFormEntity form);
	void changeName(CategoryFormEntity form);
	List<CategoryDto> getAll();
	List<CategoryDto> getSelect(int id);
	

}
