package com.soon83.utils;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

public class JooqUtil {
	public static <T> Condition eq(Field<T> field, T value) {
		return value == null ? DSL.noCondition() : field.eq(value);
	}

	public static Condition like(Field<String> field, String value) {
		return value == null ? DSL.noCondition() : field.likeIgnoreCase("%" + value + "%");
	}

	public static Condition likeStartsWith(Field<String> field, String value) {
		return value == null ? DSL.noCondition() : field.likeIgnoreCase(value + "%");
	}

	public static Condition likeEndsWith(Field<String> field, String value) {
		return value == null ? DSL.noCondition() : field.likeIgnoreCase("%" + value);
	}
}