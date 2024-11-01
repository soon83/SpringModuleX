package com.soon83.codegenerator.strategy.validation;

import jakarta.persistence.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OptionalValidation implements ValidationStrategy {
    @Override
    public List<Map<String, String>> validate(Field field, String classComment, String fieldComment, Set<String> imports) {
        List<Map<String, String>> validations = new ArrayList<>();

        if (field.getType().equals(String.class)) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && column.length() > 0) {
                String size = "Size";
                String sizeAnnotation = String.format("@%s(max = %d, message = \"%s %s의 최대 길이는 %d 입니다.\")",
                        size,
                        column.length(),
                        classComment,
                        fieldComment,
                        column.length()
                );
                validations.add(Map.of("validationAnnotation", sizeAnnotation));
                imports.add("jakarta.validation.constraints.%s".formatted(size));
            }
        }

        return validations;
    }
}
