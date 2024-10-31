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
                    MODULE_SERVER_INTERFACES + "/SearchRequest.mustache"
            );

            for (Class<?> entityClass : entityClasses) {
                String entityName = entityClass.getSimpleName();
                String comment = getComment(entityClass).orElse(entityName);

                for (String template : templates) {
                    String type = determineTypeFromTemplate(template);
                    List<Map<String, Object>> fields = getEntityFieldsForType(entityClass, comment, type);
                    Set<String> sortedImports = getUsedImports(entityClass, fields);

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
        if (template.contains("RegisterRequest")) return "Register";
        if (template.contains("EditRequest")) return "Edit";
        if (template.contains("RemoveRequest")) return "Remove";
        if (template.contains("SearchRequest")) return "Search";
        return "Edit";
    }

    private static List<Map<String, Object>> getEntityFieldsForType(Class<?> entityClass, String classComment, String type) {
        List<Map<String, Object>> fields = new ArrayList<>();
        Field[] declaredFields = entityClass.getDeclaredFields();

        for (Field field : declaredFields) {
            boolean isIdField = field.isAnnotationPresent(Id.class);

            if ("Register".equals(type) && isIdField) continue;
            if ("Remove".equals(type) && !isIdField) continue;

            Map<String, Object> fieldInfo = new HashMap<>();
            fieldInfo.put("type", field.getType().getSimpleName());
            fieldInfo.put("name", field.getName());

            String fieldComment = getComment(field).orElse("");
            String postPosition = getPostposition(fieldComment);
            List<Map<String, String>> validations = new ArrayList<>();

            if ("Search".equals(type)) {
                String sizeAnnotation = generateSizeAnnotation(field, classComment, fieldComment);
                if (sizeAnnotation != null) {
                    validations.add(Map.of("validationAnnotation", sizeAnnotation));
                }
            } else {
                if (field.getType().equals(String.class)) {
                    validations.add(Map.of("validationAnnotation", String.format(
                            "@NotBlank(message = \"%s %s%s 필수값 입니다.\")",
                            classComment, fieldComment, postPosition)));
                    String sizeAnnotation = generateSizeAnnotation(field, classComment, fieldComment);
                    if (sizeAnnotation != null) {
                        validations.add(Map.of("validationAnnotation", sizeAnnotation));
                    }
                } else if (isIdField || field.getType().isEnum()) {
                    validations.add(Map.of("validationAnnotation", String.format(
                            "@NotNull(message = \"%s %s%s 필수값 입니다.\")",
                            classComment, fieldComment, postPosition)));
                }
            }

            fieldInfo.put("validations", validations);
            fieldInfo.put("newline", "\n");
            fields.add(fieldInfo);
        }
        return fields;
    }

    private static String generateSizeAnnotation(Field field, String classComment, String fieldComment) {
        Column column = field.getAnnotation(Column.class);
        if (field.getType().equals(String.class) && column != null && column.length() > 0) {
            return String.format("@Size(max = %d, message = \"%s %s의 최대 길이는 %d 입니다.\")",
                    column.length(), classComment, fieldComment, column.length());
        }
        return null;
    }

    private static Set<String> getUsedImports(Class<?> entityClass, List<Map<String, Object>> fields) {
        Set<String> imports = new TreeSet<>();

        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getType().isEnum() && fields.stream().anyMatch(f -> f.get("type").equals(field.getType().getSimpleName()))) {
                imports.add(field.getType().getName());
            }
        }

        for (Map<String, Object> field : fields) {
            List<Map<String, String>> validations = (List<Map<String, String>>) field.get("validations");
            String jakartaValidationConstraints = "jakarta.validation.constraints.";

            for (Map<String, String> validation : validations) {
                String annotation = validation.get("validationAnnotation");
                if (annotation.contains("@NotBlank")) {
                    imports.add(jakartaValidationConstraints + "NotBlank");
                }
                if (annotation.contains("@NotNull")) {
                    imports.add(jakartaValidationConstraints + "NotNull");
                }
                if (annotation.contains("@Size")) {
                    imports.add(jakartaValidationConstraints + "Size");
                }
            }
        }
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
