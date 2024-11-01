package com.soon83.codegenerator.strategy.field;

import java.lang.reflect.Field;

public class IncludeAllFields implements FieldInclusionStrategy {
    @Override
    public boolean shouldInclude(Field field) {
        return true;
    }
}