package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.CompanyDto;
import com.example.demo.form.CompanyForm;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {
	private final JdbcTemplate jdbcTemplate;

	@Override
	public boolean companyExists() {
		String sql = "SELECT * FROM company WHERE id = '1'";
		List<Map<String,Object>> exist = jdbcTemplate.queryForList(sql);
		
		return !exist.isEmpty();
	}

	@Override
	public void registCompany(CompanyForm form) {
		String sql = "INSERT INTO company (company_name, address_number, address, phone_number, mail) VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql,
				form.getCompanyName(),
				form.getAddressNumber(),
				form.getAddress(),
				form.getPhoneNumber(),
				form.getMail());
	}

	@Override
	public void overWriteCompany(CompanyForm form) {
		String sql = "UPDATE company SET company_name = ?, address_number = ?, address = ?, phone_number = ?, mail = ? WHERE id = '1'";
		jdbcTemplate.update(sql,
				form.getCompanyName(),
				form.getAddressNumber(),
				form.getAddress(),
				form.getPhoneNumber(),
				form.getMail());
	}

	@Override
	public CompanyDto getCompany() {
		String sql = "SELECT * FROM company WHERE id = '1'";
		Map<String,Object> companyMap = jdbcTemplate.queryForMap(sql);
		CompanyDto company = new CompanyDto();
		company.setCompanyName((String)companyMap.get("company_name"));
		company.setAddressNumber((String)companyMap.get("address_number"));
		company.setAddress((String)companyMap.get("address"));
		company.setPhoneNumber((String)companyMap.get("phone_number"));
		company.setMail((String)companyMap.get("mail"));
		
		return company;
	}

}
