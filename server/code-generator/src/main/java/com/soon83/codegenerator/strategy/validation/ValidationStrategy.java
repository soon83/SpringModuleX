package com.soon83.codegenerator.strategy.validation;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ValidationStrategy {
    List<Map<String, String>> validate(Field field, String classComment, String fieldComment, Set<String> imports);
}