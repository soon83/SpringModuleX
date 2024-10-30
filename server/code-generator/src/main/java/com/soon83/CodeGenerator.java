package com.soon83;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import jakarta.persistence.Entity;
import org.hibernate.annotations.Comment;
import org.reflections.Reflections;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
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
                    MODULE_SERVER_INTERFACES + "/BulkRemoveRequest.mustache"
            );

            // 3. 각 엔티티 클래스에 대해 코드 생성
            for (Class<?> entityClass : entityClasses) {
                String entityName = entityClass.getSimpleName();
                String comment = getComment(entityClass).orElse(entityName);

                // 4. 각 템플릿에 대해 코드 생성
                for (String template : templates) {
                    Path outputPath = getOutputPathForTemplate(template, entityName);
                    generateCodeFromTemplate(template, outputPath, entityName, comment);
                }
            }

            System.out.println("Code generation completed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Code generation failed.");
        }
    }

    // @Entity 어노테이션이 있는 클래스 찾기
    private static Set<Class<?>> findEntityClasses(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        return reflections.getTypesAnnotatedWith(Entity.class);
    }

    // @Comment 어노테이션 값 가져오기
    private static Optional<String> getComment(Class<?> entityClass) {
        for (Annotation annotation : entityClass.getAnnotations()) {
            if (annotation instanceof Comment comment) {
                return Optional.of(comment.value());
            }
        }
        return Optional.empty();
    }

    // Mustache 템플릿으로 코드 생성
    private static void generateCodeFromTemplate(
            String templateName,
            Path outputPath,
            String entityName,
            String comment
    ) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory("templates/");
        Mustache mustache = mf.compile(templateName);

        // 템플릿에 전달할 데이터 설정
        Map<String, Object> context = new HashMap<>();
        context.put("entityName", entityName);
        context.put("entityNameLower", entityName.toLowerCase());
        context.put("comment", comment);
        context.put("openCurlyBraces", "{");
        context.put("closeCurlyBraces", "}");
        context.put("packageGroupName", PACKAGE_GROUP_NAME);
        context.put("moduleCommonDtos", MODULE_COMMON_DTOS);
        context.put("moduleCoreEntities", MODULE_CORE_ENTITIES);
        context.put("moduleCoreApplication", MODULE_CORE_APPLICATION);
        context.put("moduleServerInterfaces", MODULE_SERVER_INTERFACES);

        // 생성할 파일 이름 설정
        String fileName = entityName + templateName.substring(templateName.lastIndexOf('/') + 1).replace(".mustache", ".java");

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
