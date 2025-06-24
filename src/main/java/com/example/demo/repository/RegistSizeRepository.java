package com.example.demo.repository;

import java.util.List;

import com.example.demo.dto.SizeDto;
import com.example.demo.entity.SizeFormEntity;

public interface RegistSizeRepository {
	void regist(SizeFormEntity form);
	void remove(SizeFormEntity form);
	void removeByItemId(int itemId);
	void changeName(SizeFormEntity form);
	List<SizeDto> getAll();
	List<SizeDto> getSelectBySizeId(int id);
	List<SizeDto> getSelectByItemId(int id);
	SizeDto getSelectDtoByItemId(int id);

}
