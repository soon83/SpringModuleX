package com.soon83.codegenerator.strategy.validation;

import com.soon83.codegenerator.util.GeneratorUtil;

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
            String sizeAnnotation = GeneratorUtil.generateSizeAnnotation(field, classComment, fieldComment);
            if (sizeAnnotation != null) {
                validations.add(Map.of("validationAnnotation", sizeAnnotation));
                imports.add("jakarta.validation.constraints.Size");
            }
        }

        return validations;
    }
}
