package com.soon83.utils;

import org.springframework.util.Assert;

public class AssertUtil {
	public static void notNull(Object object, String name) {
		Assert.notNull(object, String.format("%s must not be null.", name));
	}

	public static void isTrue(boolean expression, String name) {
		Assert.isTrue(expression, String.format("필수항목[%s]을 반드시 입력해 주세요.", name));
	}
}