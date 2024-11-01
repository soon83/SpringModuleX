package com.soon83.codegenerator.strategy.validation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NoneValidation implements ValidationStrategy {
    @Override
    public List<Map<String, String>> validate(Field field, String classComment, String fieldComment, Set<String> imports) {
        return new ArrayList<>(); // validation 없이 빈 리스트 반환
    }
}