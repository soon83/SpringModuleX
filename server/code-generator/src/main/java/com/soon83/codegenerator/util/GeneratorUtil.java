package com.soon83.codegenerator.util;

import com.soon83.codegenerator.strategy.TemplateType;
import com.soon83.codegenerator.strategy.field.FieldInclusionStrategy;
import com.soon83.codegenerator.strategy.validation.ValidationStrategy;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Comment;
import org.reflections.Reflections;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.soon83.CodeGenerator.PACKAGE_GROUP_NAME;

public class GeneratorUtil {
    public static List<Map<String, Object>> getEntityFieldsForType(Class<?> entityClass, String classComment, TemplateType templateType, Set<String> imports) {
        List<Map<String, Object>> fields = new ArrayList<>();

        FieldInclusionStrategy inclusionStrategy = templateType.getFieldInclusionStrategy();
        ValidationStrategy validationStrategy = templateType.getValidationStrategy();

        Field[] declaredFields = entityClass.getDeclaredFields();
        List<Field> includedFields = new ArrayList<>();

        // 필터링된 필드 리스트 생성
        for (Field field : declaredFields) {
            if (inclusionStrategy.shouldInclude(field)) {
                includedFields.add(field);
            }
        }

        for (int i = 0; i < includedFields.size(); i++) {
            Field field = includedFields.get(i);
            String fieldComment = GeneratorUtil.getComment(field).orElse("");
            List<Map<String, String>> validations = validationStrategy.validate(field, classComment, fieldComment, imports);

            Map<String, Object> fieldInfo = new HashMap<>();
            fieldInfo.put("type", field.getType().getSimpleName());
            fieldInfo.put("name", field.getName());
            fieldInfo.put("capitalizedName", GeneratorUtil.capitalize(field.getName()));
            fieldInfo.put("isEnum", field.getType().isEnum());
            fieldInfo.put("validations", validations);

            // 필드가 여러 개일 때 마지막 필드가 아니면 newLine 추가
            if (includedFields.size() > 1 && i < includedFields.size() - 1) {
                fieldInfo.put("newline", "\n");
            }

            fields.add(fieldInfo);
        }

        return fields;
    }

    public static Set<String> getUsedImports(Class<?> entityClass, List<Map<String, Object>> fields, Set<String> imports) {
        // Enum 타입 필드가 실제로 존재하는 경우에만 import 추가
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getType().isEnum() && fields.stream().anyMatch(f -> f.get("type").equals(field.getType().getSimpleName()))) {
                imports.add(field.getType().getName());
                System.out.println("  Added import for enum type: " + field.getType().getName());
            }
        }

        return imports;
    }

    public static String generateSizeAnnotation(Field field, String classComment, String fieldComment) {
        Column column = field.getAnnotation(Column.class);
        if (field.getType().equals(String.class) && column != null && column.length() > 0) {
            return String.format("@Size(max = %d, message = \"%s %s의 최대 길이는 %d 입니다.\")",
                    column.length(), classComment, fieldComment, column.length());
        }
        return null;
    }

    public static String getPostposition(String word) {
        char lastChar = word.charAt(word.length() - 1);
        return (lastChar - 0xAC00) % 28 == 0 ? "는" : "은";
    }

    public static Set<Class<?>> findEntityClasses(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Entity.class);
    }

    public static Optional<String> getComment(Class<?> entityClass) {
        for (Annotation annotation : entityClass.getAnnotations()) {
            if (annotation instanceof Comment comment) {
                return Optional.of(comment.value());
            }
        }
        return Optional.empty();
    }

    public static Optional<String> getComment(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            if (annotation instanceof Comment comment) {
                return Optional.of(comment.value());
            }
        }
        return Optional.empty();
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static void createDirectoryIfNeeded(Path path) throws IOException {
        File directory = path.toFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create directory: " + directory.getPath());
        }
    }

    public static Path getOutputPathForTemplate(String templateName, String entityName) {
        String basePath = "build/generated";
        String templateModule = templateName.split("/")[0];
        String packagePath = String.format("%s.%s.%s", PACKAGE_GROUP_NAME, templateModule, entityName.toLowerCase());
        return Paths.get(basePath, packagePath.replace(".", "/"));
    }
}
