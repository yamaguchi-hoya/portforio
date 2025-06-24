package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SizeFormEntity;

public interface SizeRepositorySerchiExixts extends JpaRepository<SizeFormEntity, Integer> {
	boolean existsByItemId(int itemId);
}
