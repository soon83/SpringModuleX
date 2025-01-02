package com.soon83.codegenerator.strategy.field;

import jakarta.persistence.Id;

import java.lang.reflect.Field;

public class ExcludeIdField implements FieldInclusionStrategy {
    @Override
    public boolean shouldInclude(Field field) {
        return !field.isAnnotationPresent(Id.class);
    }
}
