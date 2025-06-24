package com.example.demo.converter;

import java.io.IOException;
import java.util.Map;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class DeliveryConverter implements AttributeConverter<Map<Integer, Integer>, String> {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Map<Integer, Integer> attribute) {
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Json変換に失敗しました", e);
		}
	}

	@Override
	public Map<Integer, Integer> convertToEntityAttribute(String dbData) {
		try {
			return objectMapper.readValue(dbData, new TypeReference<Map<Integer, Integer>>() {});
		} catch (IOException e) {
			throw new RuntimeException("Json読み取りに失敗しました", e);
		}
	}

}
