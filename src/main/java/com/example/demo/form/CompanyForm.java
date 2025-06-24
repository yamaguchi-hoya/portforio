package com.example.demo.form;

import jakarta.validation.constraints.Size;

import com.example.demo.validator.AddressNumber;
import com.example.demo.validator.Mail;
import com.example.demo.validator.PhoneNumber;

import lombok.Data;

@Data
public class CompanyForm {
	@Size(min=1, max=20,message="入力してください。")
	private String companyName;
	
	@AddressNumber
//	@Size(min=7, max=7,message="正しい桁数で入力してください。")
	private String addressNumber; 
	
	@Size(min=1, max=30,message="入力してください。")
	private String address;
	
	@PhoneNumber
//	@NotBlank(message = "入力してください。")
	private String phoneNumber;
	
//	@Size(min=1, max=30,message="入力してください。")
	@Mail
	private String mail;
	
}
