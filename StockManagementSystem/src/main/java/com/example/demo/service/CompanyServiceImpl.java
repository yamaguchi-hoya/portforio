package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CompanyDto;
import com.example.demo.form.CompanyForm;
import com.example.demo.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
	private final CompanyRepository repository;

	@Override
	public void registCompany(CompanyForm form) {
		boolean exist = repository.companyExists();
		if (exist) {
			repository.overWriteCompany(form);
		} else {
			repository.registCompany(form);			
		}

	}

	@Override
	public CompanyDto getCompany() {
		CompanyDto company = repository.getCompany();
		return company;
	}

}
