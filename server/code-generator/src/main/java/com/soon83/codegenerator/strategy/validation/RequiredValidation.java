package com.soon83.codegenerator.strategy.validation;

import com.soon83.codegenerator.util.GeneratorUtil;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequiredValidation implements ValidationStrategy {
    @Override
    public List<Map<String, String>> validate(Field field, String classComment, String fieldComment, Set<String> imports) {
        List<Map<String, String>> validations = new ArrayList<>();

        String postPosition = GeneratorUtil.getPostposition(fieldComment);

        if (field.getType().equals(String.class)) {
            String notBlankAnnotation = String.format(
                    "@NotBlank(message = \"%s %s%s 필수값 입니다.\")",
                    classComment,
                    fieldComment,
                    postPosition
            );
            validations.add(Map.of("validationAnnotation", notBlankAnnotation));
            imports.add("jakarta.validation.constraints.NotBlank");
        } else if (field.isAnnotationPresent(Id.class) || field.getType().isEnum()) {
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
