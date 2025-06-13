package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.CategoryFormEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RegistCategoryRepositoryImpl implements RegistCategoryRepository {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public void regist(CategoryFormEntity form) {
		String sql = 
				"INSERT INTO category " +
				"(category_name)" +
				"VALUES(?)";
		jdbcTemplate.update(sql, form.getCategoryName());

	}

	@Override
	public void remove(CategoryFormEntity form) {
		String sql =
				"DELETE FROM category " +
				"WHERE category_id = ?";
		jdbcTemplate.update(sql,form.getCategoryId());
		
	}
	
	@Override
	public void changeName(CategoryFormEntity form) {
		String sql = 
				"UPDATE category SET category_name = ? WHERE category_id = ?";
		jdbcTemplate.update(sql, form.getCategoryName(), form.getCategoryId());
		
	}

	@Override
	public List<CategoryDto> getAll() {
		String sql = "select category_id,category_name from category";
		List<Map<String, Object>> categoryList =  jdbcTemplate.queryForList(sql);
		List<CategoryDto> list = new ArrayList<>();
		for (Map<String, Object> category: categoryList) {
			list.add(new CategoryDto(
					(int) category.get("category_id"),
					(String) category.get("category_name")));
		}
		return list;
	}

	@Override
	public List<CategoryDto> getSelect(int id) {
		String sql = "select category_id,category_name from category where category_id = ?";	
		List<Map<String, Object>> categoryList =  jdbcTemplate.queryForList(sql,id);
		List<CategoryDto> list = new ArrayList<>();
		for (Map<String, Object> category: categoryList) {
			list.add(new CategoryDto(
					(int) category.get("category_id"),
					(String) category.get("category_name")));
		}
		return list;
	}
}
