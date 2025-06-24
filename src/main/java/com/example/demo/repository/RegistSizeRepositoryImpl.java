package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.SizeDto;
import com.example.demo.entity.SizeFormEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RegistSizeRepositoryImpl implements RegistSizeRepository {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public void regist(SizeFormEntity form) {
		String sql = 
				"INSERT INTO size (size_name, item_id) VALUES(?,?)";
		jdbcTemplate.update(sql,form.getSizeName(),
								form.getItemId()
								);
	}

	@Override
	public void remove(SizeFormEntity form) {
		String sql = 
				"DELETE FROM size WHERE size_id = ?";
		jdbcTemplate.update(sql, form.getSizeId());
	}
	
	@Override
	public void removeByItemId(int itemId) {
		String sql = "DELETE FROM size WHERE item_id = ?";
		jdbcTemplate.update(sql, itemId);
		
	}
	
	@Override
	public void changeName(SizeFormEntity form) {
		String sql = 
				"UPDATE size SET size_name = ? WHERE size_id = ?";
		jdbcTemplate.update(sql, form.getSizeName(), form.getSizeId());
		
	}

	@Override
	public List<SizeDto> getAll() {
		String sql = "SELECT size_id, size_name, item_id FROM size";
		List<Map<String,Object>> sizeList = jdbcTemplate.queryForList(sql);
		List<SizeDto> list = new ArrayList<>();
		for (Map<String,Object> size : sizeList) {
			list.add(new SizeDto(
					(int)size.get("size_id"),
					(String)size.get("size_name"),
					(int)size.get("item_id")
					));
		}
		return list;
	}

	@Override
	public List<SizeDto> getSelectBySizeId(int id) {
		String sql = "SELECT size_id, size_name, item_id FROM size WHERE size_id = ?";
		List<Map<String,Object>> sizeList = jdbcTemplate.queryForList(sql,id);
		List<SizeDto> list = new ArrayList<>();
		for (Map<String,Object> size : sizeList) {
			list.add(new SizeDto(
					(int)size.get("size_id"),
					(String)size.get("size_name"),
					(int)size.get("item_id")
					));
		}
		return list;
	}

	@Override
	public List<SizeDto> getSelectByItemId(int id) {
		String sql = "SELECT size_id, size_name, item_id FROM size WHERE item_id = ?";
		List<Map<String,Object>> sizeList = jdbcTemplate.queryForList(sql,id);
		List<SizeDto> list = new ArrayList<>();
		for (Map<String,Object> size : sizeList) {
			list.add(new SizeDto(
					(int)size.get("size_id"),
					(String)size.get("size_name"),
					(int)size.get("item_id")
					));
		}
		return list;
	}

	@Override
	public SizeDto getSelectDtoByItemId(int id) {
		String sql = "SELECT size_id, size_name, item_id FROM size WHERE size_id = ?";
		Map<String, Object> sizeList = jdbcTemplate.queryForMap(sql,id);
		if (sizeList == null) {
			return null;
		}
		SizeDto size = new SizeDto(
				(int)sizeList.get("size_id"),
				(String)sizeList.get("size_name"),
				(int)sizeList.get("item_id")
				);
		return size;
	}

}
