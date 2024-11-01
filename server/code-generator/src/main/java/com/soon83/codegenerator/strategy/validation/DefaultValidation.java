package com.soon83.codegenerator.strategy.validation;

import com.soon83.codegenerator.util.GeneratorUtil;
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

        // String 필드에 대해 NotBlank와 Size 검증을 수행
        if (field.getType().equals(String.class)) {
            validations.addAll(new RequiredValidation().validate(field, classComment, fieldComment, imports));
            validations.addAll(new OptionalValidation().validate(field, classComment, fieldComment, imports));
        }

        // Id 또는 Enum 필드에 대해 NotNull 검증
        if (field.isAnnotationPresent(Id.class) || field.getType().isEnum()) {
            String notNullAnnotation = String.format(
                    "@NotNull(message = \"%s %s%s 필수값 입니다.\")",
                    classComment,
                    fieldComment,
                    postPosition
            );
            validations.add(Map.of("validationAnnotation", notNullAnnotation));
            imports.add("jakarta.validation.constraints.NotNull");
        }

        return validations;
    }
}
