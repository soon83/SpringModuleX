package com.soon83;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
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
            // 1. @Entity 어노테이션이 있는 클래스 탐색
            Set<Class<?>> entityClasses = findEntityClasses(PACKAGE_GROUP_NAME + "." + MODULE_CORE_ENTITIES);

            // 2. 생성할 템플릿 목록
            List<String> templates = Arrays.asList(
                    MODULE_SERVER_INTERFACES + "/Controller.mustache",
                    MODULE_SERVER_INTERFACES + "/BulkRegisterRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/BulkEditRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/BulkRemoveRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/RegisterRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/EditRequest.mustache",
                    MODULE_SERVER_INTERFACES + "/RemoveRequest.mustache"
            );

            // 3. 각 엔티티 클래스에 대해 코드 생성
            for (Class<?> entityClass : entityClasses) {
                String entityName = entityClass.getSimpleName();
                String comment = getComment(entityClass).orElse(entityName);

                for (String template : templates) {
                    String type = determineTypeFromTemplate(template);
                    List<Map<String, String>> fields = getEntityFieldsForType(entityClass, comment, type);
                    Set<String> sortedImports = getUsedImports(entityClass, fields);

                    Path outputPath = getOutputPathForTemplate(template, entityName);
                    generateCodeFromTemplate(template, outputPath, entityName, comment, fields, sortedImports);
                }
            }

            System.out.println("Code generation completed successfully!");
        } catch (Exception e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            System.err.println("Code generation failed.");
        }
    }

    private static String determineTypeFromTemplate(String template) {
        if (template.contains("RegisterRequest")) return "Register";
        if (template.contains("EditRequest")) return "Edit";
        if (template.contains("RemoveRequest")) return "Remove";
        return "Edit";
    }

    private static List<Map<String, String>> getEntityFieldsForType(Class<?> entityClass, String classComment, String type) {
        List<Map<String, String>> fields = new ArrayList<>();
        Field[] declaredFields = entityClass.getDeclaredFields();

        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            boolean isIdField = field.isAnnotationPresent(Id.class);

            if ("Edit".equals(type) ||
                    ("Register".equals(type) && !isIdField) ||
                    ("Remove".equals(type) && isIdField)) {

                Map<String, String> fieldInfo = new HashMap<>();
                fieldInfo.put("type", field.getType().getSimpleName());
                fieldInfo.put("name", field.getName());

                String fieldComment = getComment(field).orElse("");
                String postPosition = getPostposition(fieldComment);

                String validationAnnotation = field.getType().equals(String.class)
                        ? String.format("@NotBlank(message = \"%s %s%s 필수값 입니다.\")", classComment, fieldComment, postPosition)
                        : String.format("@NotNull(message = \"%s %s%s 필수값 입니다.\")", classComment, fieldComment, postPosition);

                fieldInfo.put("validationAnnotation", validationAnnotation);

                if (i < declaredFields.length - 1) {
                    fieldInfo.put("newline", "\n");
                }

                fields.add(fieldInfo);
            }
        }
        return fields;
    }

    // 사용되는 필드 타입과 Enum 타입의 import 만 추가
    private static Set<String> getUsedImports(Class<?> entityClass, List<Map<String, String>> fields) {
        Set<String> imports = new TreeSet<>(); // TreeSet 을 사용하여 자동 정렬

        // 실제 사용되는 Enum 타입 필드의 import 만 추가
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getType().isEnum() && fields.stream().anyMatch(f -> f.get("type").equals(field.getType().getSimpleName()))) {
                imports.add(field.getType().getName());
            }
        }

        // 실제 사용되는 타입 import 추가
        for (Map<String, String> field : fields) {
            String typeName = field.get("type");
            String jakartaValidationConstraints = "jakarta.validation.constraints.";
            switch (typeName) {
                case "String" -> imports.add(jakartaValidationConstraints + "NotBlank");
                default -> imports.add(jakartaValidationConstraints + "NotNull");
            }
        }

        return imports;
    }

    // @Entity 어노테이션이 있는 클래스 찾기
    private static Set<Class<?>> findEntityClasses(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Entity.class);
    }

    // 클래스에 대한 @Comment 어노테이션 값 가져오기
    private static Optional<String> getComment(Class<?> entityClass) {
        for (Annotation annotation : entityClass.getAnnotations()) {
            if (annotation instanceof Comment comment) {
                return Optional.of(comment.value());
            }
        }
        return Optional.empty();
    }

    // 필드에 대한 @Comment 어노테이션 값 가져오기
    private static Optional<String> getComment(Field field) {
        for (Annotation annotation : field.getAnnotations()) {
            if (annotation instanceof Comment comment) {
                return Optional.of(comment.value());
            }
        }
        return Optional.empty();
    }

    // 필드 코멘트 단어 맨 뒷자리가 받침에 따라 은/는 으로 구분
    private static String getPostposition(String word) {
        char lastChar = word.charAt(word.length() - 1);
        return (lastChar - 0xAC00) % 28 == 0 ? "는" : "은"; // 받침이 없으면 "는", 받침이 있으면 "은"
    }

    // 필드 추가 로직 변경
    private static List<Map<String, String>> getEntityFieldsWithValidation(Class<?> entityClass, String classComment) {
        List<Map<String, String>> fields = new ArrayList<>();
        Field[] declaredFields = entityClass.getDeclaredFields();

        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            Map<String, String> fieldInfo = new HashMap<>();
            fieldInfo.put("type", field.getType().getSimpleName());
            fieldInfo.put("name", field.getName());

            // 필드의 @Comment 내용 가져오기
            String fieldComment = getComment(field).orElse("");
            String postPosition = getPostposition(fieldComment);

            // 검증 어노테이션 동적 추가
            String validationAnnotation = field.getType().equals(String.class)
                    ? String.format("@NotBlank(message = \"%s %s%s 필수값 입니다.\")", classComment, fieldComment, postPosition)
                    : String.format("@NotNull(message = \"%s %s%s 필수값 입니다.\")", classComment, fieldComment, postPosition);

            fieldInfo.put("validationAnnotation", validationAnnotation);

            // 마지막 필드가 아니라면 줄바꿈 추가
            if (i < declaredFields.length - 1) {
                fieldInfo.put("newline", "\n");
            }

            fields.add(fieldInfo);
        }
        return fields;
    }

    // Enum 타입의 필드를 찾아 import 문 생성
    private static Set<String> getEnumImports(Class<?> entityClass) {
        Set<String> imports = new HashSet<>();
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getType().isEnum()) {
                imports.add(field.getType().getName());
            }
        }
        return imports;
    }

    // Mustache 템플릿으로 코드 생성
    private static void generateCodeFromTemplate(
            String templateName,
            Path outputPath,
            String entityName,
            String comment,
            List<Map<String, String>> fields,
            Set<String> imports
    ) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory("templates/");
        Mustache mustache = mf.compile(templateName);

        // 템플릿에 전달할 데이터 설정
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

        // 생성할 파일 이름 설정
        String fileName = entityName + templateName
                .substring(templateName.lastIndexOf('/') + 1)
                .replace(".mustache", ".java");

        // 디렉터리 생성 및 파일 작성
        createDirectoryIfNeeded(outputPath);
        try (Writer writer = new FileWriter(new File(outputPath.toFile(), fileName))) {
            mustache.execute(writer, context).flush();
            System.out.println("Created file: " + outputPath.resolve(fileName));
        }
    }

    // 템플릿 경로에 맞는 출력 경로 생성
    private static Path getOutputPathForTemplate(String templateName, String entityName) {
        // 예: templates/interfaces/Controller.mustache -> build/generated/com/soon83/interfaces/member/
        String basePath = "build/generated";
        String templateModule = templateName.split("/")[0]; // 템플릿의 첫 번째 경로 추출
        String packagePath = String.format("%s.%s.%s", PACKAGE_GROUP_NAME, templateModule, entityName.toLowerCase());
        return Paths.get(basePath, packagePath.replace(".", "/"));
    }

    // 디렉터리 생성
    private static void createDirectoryIfNeeded(Path path) throws IOException {
        File directory = path.toFile();
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Created directory: " + directory.getPath());
            } else {
                throw new IOException("Failed to create directory: " + directory.getPath());
            }
        }
    }
}
