package com.soon83.utils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.function.Function;

import org.mapstruct.Named;

public class MapperUtil {
	// Long 타입 처리
	@Named("mapObjectToLong")
	public static <T> Long mapObjectToLong(T object, Function<T, Long> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// Integer 타입 처리
	@Named("mapObjectToInteger")
	public static <T> Integer mapObjectToInteger(T object, Function<T, Integer> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// Double 타입 처리
	@Named("mapObjectToDouble")
	public static <T> Double mapObjectToDouble(T object, Function<T, Double> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// BigDecimal 타입 처리
	@Named("mapObjectToBigDecimal")
	public static <T> BigDecimal mapObjectToBigDecimal(T object, Function<T, BigDecimal> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// String 타입 처리
	@Named("mapObjectToString")
	public static <T> String mapObjectToString(T object, Function<T, String> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// Boolean 타입 처리
	@Named("mapObjectToBoolean")
	public static <T> Boolean mapObjectToBoolean(T object, Function<T, Boolean> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// Enum 타입 처리
	@Named("mapObjectToEnum")
	public static <T extends Enum<T>> T mapObjectToEnum(T object, Function<T, T> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// LocalDate 타입 처리
	@Named("mapObjectToLocalDate")
	public static LocalDate mapObjectToLocalDate(Object object, Function<Object, LocalDate> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// LocalTime 타입 처리
	@Named("mapObjectToLocalTime")
	public static LocalTime mapObjectToLocalTime(Object object, Function<Object, LocalTime> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// LocalDateTime 타입 처리
	@Named("mapObjectToLocalDateTime")
	public static LocalDateTime mapObjectToLocalDateTime(Object object, Function<Object, LocalDateTime> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// ZonedDateTime 타입 처리
	@Named("mapObjectToZonedDateTime")
	public static ZonedDateTime mapObjectToZonedDateTime(Object object, Function<Object, ZonedDateTime> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// OffsetDateTime 타입 처리
	@Named("mapObjectToOffsetDateTime")
	public static OffsetDateTime mapObjectToOffsetDateTime(Object object, Function<Object, OffsetDateTime> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// OffsetTime 타입 처리
	@Named("mapObjectToOffsetTime")
	public static OffsetTime mapObjectToOffsetTime(Object object, Function<Object, OffsetTime> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// Instant 타입 처리
	@Named("mapObjectToInstant")
	public static Instant mapObjectToInstant(Object object, Function<Object, Instant> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// Year 타입 처리
	@Named("mapObjectToYear")
	public static Year mapObjectToYear(Object object, Function<Object, Year> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// YearMonth 타입 처리
	@Named("mapObjectToYearMonth")
	public static YearMonth mapObjectToYearMonth(Object object, Function<Object, YearMonth> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// MonthDay 타입 처리
	@Named("mapObjectToMonthDay")
	public static MonthDay mapObjectToMonthDay(Object object, Function<Object, MonthDay> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// Duration 타입 처리
	@Named("mapObjectToDuration")
	public static Duration mapObjectToDuration(Object object, Function<Object, Duration> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// Period 타입 처리
	@Named("mapObjectToPeriod")
	public static Period mapObjectToPeriod(Object object, Function<Object, Period> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}

	// Generic 타입 처리 (어떤 타입이든 받을 수 있게, 될 수 있으면 사용하지 마십쇼)
	@Named("mapObject")
	public static <T, R> R mapObject(T object, Function<T, R> value) {
		return Optional.ofNullable(object).map(value).orElse(null);
	}
}