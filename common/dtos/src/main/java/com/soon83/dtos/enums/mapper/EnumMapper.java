package com.soon83.dtos.enums.mapper;

import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
public class EnumMapper {
	private final Map<String, List<EnumMapperValue>> factory = new LinkedHashMap<>();

	public void put(String key, Class<? extends EnumMapperType> e) {
		factory.put(key, toEnumValues(e));
	}

	public List<EnumMapperValue> get(String key) {
		return factory.get(key);
	}

	private List<EnumMapperValue> toEnumValues(Class<? extends EnumMapperType> e) {
		return Arrays.stream(e.getEnumConstants())
			.map(EnumMapperValue::new)
			.toList();
	}

	public Map<String, List<EnumMapperValue>> get(List<String> keys) {
		if (keys == null || keys.isEmpty()) {
			return new LinkedHashMap<>();
		}

		return keys.stream().collect(Collectors.toMap(Function.identity(), factory::get));
	}

	public Map<String, List<EnumMapperValue>> getAll() {
		return factory;
	}
}
