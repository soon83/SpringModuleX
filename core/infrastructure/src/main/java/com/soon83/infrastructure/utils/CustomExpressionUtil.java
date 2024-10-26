package com.soon83.infrastructure.utils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.function.Supplier;

public class CustomExpressionUtil {
    public static OrderSpecifier<Double> rand() {
        return Expressions.numberTemplate(Double.class, "function('rand')").asc();
    }

    public static BooleanExpression eq(BooleanPath domainValue, Boolean value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.eq(value);
    }

    public static BooleanExpression notEq(BooleanPath domainValue, Boolean value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.ne(value);
    }

    public static BooleanExpression eq(StringPath domainValue, String value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.eq(value);
    }

    public static <T extends Enum<T>> BooleanExpression eq(EnumPath<T> domainValue, T value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.eq(value);
    }

    public static <T extends Enum<T>> BooleanExpression eq(EnumPath<T> domainValue1, EnumPath<T> domainValue2) {
        if (ObjectUtils.isEmpty(domainValue1) || ObjectUtils.isEmpty(domainValue2))
            return null;
        return domainValue1.eq(domainValue2);
    }

    public static BooleanExpression notEq(StringPath domainValue, String value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.ne(value);
    }

    public static <T extends Enum<T>> BooleanExpression notEq(EnumPath<T> domainValue, T value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.ne(value);
    }

    public static BooleanExpression like(StringPath domainValue, String value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.likeIgnoreCase("%" + value + "%");
    }

    public static <T extends Number & Comparable<?>> BooleanExpression eq(NumberPath<T> domainValue, T value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.eq(value);
    }

    public static <T extends Number & Comparable<?>> BooleanExpression notEq(NumberPath<T> domainValue, T value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.ne(value);
    }

    public static <T extends String> BooleanExpression in(StringPath domainValue, Collection<T> valueList) {
        if (valueList == null) return null;
        return domainValue.in(valueList);
    }

    public static <T extends String> BooleanExpression notIn(StringPath domainValue, Collection<T> valueList) {
        if (valueList == null) return null;
        return domainValue.notIn(valueList);
    }

    public static <T extends Number & Comparable<?>> BooleanExpression in(NumberPath<T> domainValue, Collection<T> valueList) {
        if (valueList == null) return null;
        return domainValue.in(valueList);
    }

    public static <T extends Number & Comparable<?>> BooleanExpression notIn(NumberPath<T> domainValue, Collection<T> valueList) {
        if (valueList == null) return null;
        return domainValue.notIn(valueList);
    }

    public static <T extends Enum<T>> BooleanExpression in(EnumPath<T> domainValue, Collection<T> valueList) {
        if (valueList == null) return null;
        return domainValue.in(valueList);
    }

    public static <T extends Enum<T>> BooleanExpression notIn(EnumPath<T> domainValue, Collection<T> valueList) {
        if (valueList == null) return null;
        return domainValue.notIn(valueList);
    }

    public static <T extends Number & Comparable<?>> BooleanExpression between(NumberPath<T> domainValue, T start, T end) {
        if (ObjectUtils.isEmpty(start) || ObjectUtils.isEmpty(end)) return null;
        return domainValue.goe(start).and(domainValue.loe(end));
    }

    public static <T extends Comparable<?>> BooleanExpression between(DatePath<T> domainValue, T start, T end) {
        if (ObjectUtils.isEmpty(start) || ObjectUtils.isEmpty(end)) return null;
        return domainValue.goe(start).and(domainValue.loe(end));
    }

    public static <T extends Comparable<?>> BooleanExpression between(DateTimePath<T> domainValue, T start, T end) {
        if (ObjectUtils.isEmpty(start) || ObjectUtils.isEmpty(end)) return null;
        return domainValue.goe(start).and(domainValue.loe(end));
    }

    public static <T extends Comparable<T>> BooleanExpression goe(DateTimePath<T> domainValue, T value) {
        if (ObjectUtils.isEmpty(value)) return null;
        return domainValue.goe(value);
    }

    public static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }

    // TODO 아래꺼 사용해보자
    public <T extends Number & Comparable<?>> BooleanBuilder _eq(NumberPath<T> domainValue, T value) {
        return nullSafeBuilder(() -> domainValue.eq(value));
    }
}
