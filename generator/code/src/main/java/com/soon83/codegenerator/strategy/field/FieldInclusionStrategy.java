package com.soon83.codegenerator.strategy.field;

import java.lang.reflect.Field;

public interface FieldInclusionStrategy {
    boolean shouldInclude(Field field);
}
