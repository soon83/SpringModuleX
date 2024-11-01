package com.soon83;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Comment;
import org.reflections.Reflections;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@SpringBootApplication
public class CodeGenerator {
    public static final String PACKAGE_GROUP_NAME = "com.soon83";
    public static final String MODULE_COMMON_DTOS = "dtos";
    public static final String MODULE_CORE_APPLICATION = "application";
    public static final String MODULE_CORE_ENTITIES = "entities";
    public static final String MODULE_SERVER_INTERFACES = "interfaces";

    public static void main(String[] args) {
        try {
            Set<Class<?>> entityClasses = findEntityClasses(PACKAGE_GROUP_NAME + "." + MODULE_CORE_ENTITIES);
            List<String> templates = Arrays.asList(
                    MODULE_SERVER_INTERFACES + "/Controller.mustache",
                    MODULE_SERVER_INTERFACES + "/BulkRegisterRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/BulkEditRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/BulkRemoveRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/RegisterRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/EditRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/RemoveRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/SearchRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/InfoResponse.mustache",
                    MODULE_SERVER_INTERFACES + "/InterfaceMapper.mustache",
                    MODULE_COMMON_DTOS + "/BulkCreateCommand.mustache",
                    MODULE_COMMON_DTOS + "/BulkUpdateCommand.mustache",
                    MODULE_COMMON_DTOS + "/BulkDeleteCommand.mustache",
                    MODULE_COMMON_DTOS + "/CreateCommand.mustache",
                    MODULE_COMMON_DTOS + "/UpdateCommand.mustache",
                    MODULE_COMMON_DTOS + "/DeleteCommand.mustache",
                    MODULE_COMMON_DTOS + "/SearchCondition.mustache",
                    MODULE_COMMON_DTOS + "/Info.mustache"
            );

            for (Class<?> entityClass : entityClasses) {
                String entityName = entityClass.getSimpleName();
                String comment = getComment(entityClass).orElse(entityName);
                System.out.println("\nProcessing entity: " + entityName);

                for (String template : templates) {
                    String type = determineTypeFromTemplate(template);
                    System.out.println("==============================================================================================");

                    Set<String> imports = new TreeSet<>();
                    List<Map<String, Object>> fields = getEntityFieldsForType(entityClass, comment, type, imports);
                    Set<String> sortedImports = getUsedImports(entityClass, fields, imports);

                    Path outputPath = getOutputPathForTemplate(template, entityName);
                    generateCodeFromTemplate(template, outputPath, entityName, comment, fields, sortedImports);
                }
            }

            System.out.println("Code generation completed successfully!");
        } catch (Exception e) {
            System.err.println("Code generation failed: " + e.getMessage());
        }
    }

    private static String determineTypeFromTemplate(String template) {
        System.out.println("  템플릿: " + template);
        if (
                template.contains("Command")
                        || template.contains("Info")
                        || template.contains("Details")
                        || template.contains("Condition")
        ) {
            if (template.contains("Create")) return "Register|None";
            if (template.contains("Delete")) return "Remove|None";

        } else if (template.contains("Request")) {
            if (template.contains("Register")) return "Register";
            if (template.contains("Edit")) return "Edit";
            if (template.contains("Remove")) return "Remove";
            if (template.contains("Search")) return "Search";
        }
        return "None";
    }

    private static List<Map<String, Object>> getEntityFieldsForType(Class<?> entityClass, String classComment, String type, Set<String> imports) {
        List<Map<String, Object>> fields = new ArrayList<>();

        Field[] declaredFields = entityClass.getDeclaredFields();
        List<Field> applicableFields = new ArrayList<>();
        for (Field field : declaredFields) {
            boolean isIdField = field.isAnnotationPresent(Id.class);

            // Register에서는 ID 필드를 제외하고, Remove에서는 ID 필드만 포함
            if (type.contains("Register") && isIdField) continue;
            if (type.contains("Remove") && !isIdField) continue;

            applicableFields.add(field);
        }

        int processedFields = 0;
        for (Field field : applicableFields) {
            boolean isIdField = field.isAnnotationPresent(Id.class);

            Map<String, Object> fieldInfo = new HashMap<>();
            fieldInfo.put("type", field.getType().getSimpleName());
            fieldInfo.put("name", field.getName());
            fieldInfo.put("capitalizedName", capitalize(field.getName()));

            String fieldComment = getComment(field).orElse("");
            String postPosition = getPostposition(fieldComment);
            List<Map<String, String>> validations = new ArrayList<>();

            System.out.println("\nProcessing field: " + field.getName() + " (" + field.getType().getSimpleName() + ")");
            System.out.println("  객체 타입: " + type);
            if (!type.contains("None")) {
                if ("Search".equals(type)) {
                    String sizeAnnotation = generateSizeAnnotation(field, classComment, fieldComment);
                    if (sizeAnnotation != null) {
                        validations.add(Map.of("validationAnnotation", sizeAnnotation));
                        imports.add("jakarta.validation.constraints.Size"); // Size가 필요한 경우 import 추가
                        System.out.println("  Added validation and import for Size");
                    }
                } else {
                    if (field.getType().equals(String.class)) {
                        String notBlankAnnotation = String.format(
                                "@NotBlank(message = \"%s %s%s 필수값 입니다.\")",
                                classComment, fieldComment, postPosition);
                        validations.add(Map.of("validationAnnotation", notBlankAnnotation));
                        imports.add("jakarta.validation.constraints.NotBlank"); // NotBlank가 필요한 경우 import 추가
                        System.out.println("  Added validation and import for NotBlank: " + notBlankAnnotation);

                        String sizeAnnotation = generateSizeAnnotation(field, classComment, fieldComment);
                        if (sizeAnnotation != null) {
                            validations.add(Map.of("validationAnnotation", sizeAnnotation));
                            imports.add("jakarta.validation.constraints.Size"); // Size가 필요한 경우 import 추가
                            System.out.println("  Added validation and import for Size: " + sizeAnnotation);
                        }
                    } else if (isIdField || field.getType().isEnum()) {
                        String notNullAnnotation = String.format(
                                "@NotNull(message = \"%s %s%s 필수값 입니다.\")",
                                classComment, fieldComment, postPosition);
                        validations.add(Map.of("validationAnnotation", notNullAnnotation));
                        imports.add("jakarta.validation.constraints.NotNull"); // NotNull이 필요한 경우 import 추가
                        System.out.println("  Added validation and import for NotNull: " + notNullAnnotation);
                    }
                }
            }

            fieldInfo.put("validations", validations);
            fieldInfo.put("isEnum", field.getType().isEnum());

            if (++processedFields < applicableFields.size()) {
                fieldInfo.put("newline", "\n");
            }

            fields.add(fieldInfo);
        }
        return fields;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static String generateSizeAnnotation(Field field, String classComment, String fieldComment) {
        Column column = field.getAnnotation(Column.class);
        if (field.getType().equals(String.class) && column != null && column.length() > 0) {
            return String.format("@Size(max = %d, message = \"%s %s의 최대 길이는 %d 입니다.\")",
                    column.length(), classComment, fieldComment, column.length());
        }
        return null;
    }

    private static Set<String> getUsedImports(Class<?> entityClass, List<Map<String, Object>> fields, Set<String> imports) {
        // Enum 타입 필드가 실제로 존재하는 경우에만 import 추가
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getType().isEnum() && fields.stream().anyMatch(f -> f.get("type").equals(field.getType().getSimpleName()))) {
                imports.add(field.getType().getName());
                System.out.println("  Added import for enum type: " + field.getType().getName());
            }
        }

        // 필드의 검증 어노테이션 사용 여부를 체크하고 필요한 경우에만 import 추가
        /*String jakartaValidationConstraints = "jakarta.validation.constraints.";
        for (Map<String, Object> field : fields) {
            List<Map<String, String>> validations = (List<Map<String, String>>) field.get("validations");

            for (Map<String, String> validation : validations) {
                String annotation = validation.get("validationAnnotation");
                if (annotation.contains("NotBlank")) {
                    imports.add(jakartaValidationConstraints + "NotBlank");
                    System.out.println("  Added import for NotBlank (Field: " + field.get("name") + ")");
                }
                if (annotation.contains("NotNull")) {
                    imports.add(jakartaValidationConstraints + "NotNull");
                    System.out.println("  Added import for NotNull (Field: " + field.get("name") + ")");
                }
                if (annotation.contains("Size")) {
                    imports.add(jakartaValidationConstraints + "Size");
                    System.out.println("  Added import for Size (Field: " + field.get("name") + ")");
                }
            }
        }*/

        return imports;
    }

    private static Set<Class<?>> findEntityClasses(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Entity.class);
    }

    private static Optional<String> getComment(Class<?> entityClass) {
        for (Annotation annotation : entityClass.getAnnotations()) {
            if (annotation instanceof Comment comment) {
                return Optional.of(comment.value());
            }
        }
        return Optional.empty();
    }

    private static Optional<String> getComment(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            if (annotation instanceof Comment comment) {
                return Optional.of(comment.value());
            }
        }
        return Optional.empty();
    }

    private static String getPostposition(String word) {
        char lastChar = word.charAt(word.length() - 1);
        return (lastChar - 0xAC00) % 28 == 0 ? "는" : "은";
    }

    private static void generateCodeFromTemplate(
            String templateName,
            Path outputPath,
            String entityName,
            String comment,
            List<Map<String, Object>> fields,
            Set<String> imports
    ) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory("templates/");
        Mustache mustache = mf.compile(templateName);

        Map<String, Object> context = new HashMap<>();
        context.put("entityName", entityName);
        context.put("entityNameLower", entityName.toLowerCase());
        context.put("comment", comment);
        context.put("fields", fields);
        context.put("imports", imports);
        context.put("openCurlyBraces", "{");
        context.put("closeCurlyBraces", "}");
        context.put("packageGroupName", PACKAGE_GROUP_NAME);
        context.put("moduleCommonDtos", MODULE_COMMON_DTOS);
        context.put("moduleCoreEntities", MODULE_CORE_ENTITIES);
        context.put("moduleCoreApplication", MODULE_CORE_APPLICATION);
        context.put("moduleServerInterfaces", MODULE_SERVER_INTERFACES);

        String fileName = entityName + templateName
                .substring(templateName.lastIndexOf('/') + 1)
                .replace(".mustache", ".java");

        createDirectoryIfNeeded(outputPath);
        try (Writer writer = new FileWriter(new File(outputPath.toFile(), fileName))) {
            mustache.execute(writer, context).flush();
            System.out.println("Created file: " + outputPath.resolve(fileName));
        }
    }

    private static Path getOutputPathForTemplate(String templateName, String entityName) {
        String basePath = "build/generated";
        String templateModule = templateName.split("/")[0];
        String packagePath = String.format("%s.%s.%s", PACKAGE_GROUP_NAME, templateModule, entityName.toLowerCase());
        return Paths.get(basePath, packagePath.replace(".", "/"));
    }

    private static void createDirectoryIfNeeded(Path path) throws IOException {
        File directory = path.toFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create directory: " + directory.getPath());
        }
    }
}
