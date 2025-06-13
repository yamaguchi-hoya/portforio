package com.example.demo.repository;

import com.example.demo.dto.CompanyDto;
import com.example.demo.form.CompanyForm;

public interface CompanyRepository {
	boolean companyExists();
	void registCompany(CompanyForm form);
	void overWriteCompany(CompanyForm form);
	CompanyDto getCompany();
}
