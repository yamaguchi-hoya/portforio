package com.example.demo.service;

import com.example.demo.dto.CompanyDto;
import com.example.demo.form.CompanyForm;

public interface CompanyService {
	void registCompany(CompanyForm form);
	CompanyDto getCompany();
}
