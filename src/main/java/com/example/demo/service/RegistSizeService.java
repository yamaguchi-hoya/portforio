package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.SizeDto;
import com.example.demo.entity.SizeFormEntity;


public interface RegistSizeService {
	void regist(SizeFormEntity form);
	void remove(SizeFormEntity form);
	void changeName(SizeFormEntity form);		
	List<SizeDto> getAll();
	List<SizeDto> getSelectBySizeId(int id);
	List<SizeDto> getSelectByItemId(int id);
}
