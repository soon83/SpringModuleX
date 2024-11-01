package com.soon83.codegenerator.strategy.validation;

import com.soon83.codegenerator.GeneratorUtil;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultValidation implements ValidationStrategy {
    @Override
    public List<Map<String, String>> validate(Field field, String classComment, String fieldComment, Set<String> imports) {
        List<Map<String, String>> validations = new ArrayList<>();

        String postPosition = GeneratorUtil.getPostposition(fieldComment);

        if (field.getType().equals(String.class)) {
            validations.addAll(new RequiredValidation().validate(field, classComment, fieldComment, imports));
            validations.addAll(new OptionalValidation().validate(field, classComment, fieldComment, imports));
        }

        if (field.isAnnotationPresent(Id.class) || field.getType().isEnum()) {
            String notNull = "NotNull";
            String notNullAnnotation = String.format(
                    "@%s(message = \"%s %s%s 필수값 입니다.\")",
                    notNull,
                    classComment,
                    fieldComment,
                    postPosition
            );
            validations.add(Map.of("validationAnnotation", notNullAnnotation));
            imports.add("jakarta.validation.constraints.%s".formatted(notNull));
        }

        return validations;
    }
}
