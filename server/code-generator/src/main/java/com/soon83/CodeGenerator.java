package com.soon83;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.soon83.codegenerator.GeneratorUtil;
import com.soon83.codegenerator.settings.ModuleConstants;
import com.soon83.codegenerator.settings.TemplateCondition;
import com.soon83.codegenerator.settings.TemplateFiles;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.*;

@SpringBootApplication
public class CodeGenerator {
    public static void main(String[] args) {
        try {
            // 엔티티 클래스 검색
            Set<Class<?>> entityClasses = GeneratorUtil.findEntityClasses(ModuleConstants.PACKAGE_GROUP_NAME.getValue() + "." + ModuleConstants.MODULE_CORE_ENTITIES.getValue());
            entityClasses.forEach(System.out::println);

            // 템플릿 검색
            List<String> templates = Arrays.stream(TemplateFiles.values())
                    .map(TemplateFiles::getPath)
                    .toList();
            templates.forEach(System.out::println);

            for (Class<?> entityClass : entityClasses) {
                // 엔티티 클래스의 필드 및 Validation 정보 분류
                String entityName = entityClass.getSimpleName();
                String comment = GeneratorUtil.getComment(entityClass).orElse(entityName);
                System.out.println("\nProcessing entity: " + entityName);

                // 템플릿에 엔티티 클래스의 필드 와 Validation 및 Import 넣기
                for (String template : templates) {
                    TemplateCondition templateCondition = TemplateCondition.fromTemplate(template);
                    System.out.println("Processing template as type: " + templateCondition);

                    Set<String> imports = new TreeSet<>();
                    List<Map<String, Object>> fields = GeneratorUtil.getEntityFieldsForType(entityClass, comment, templateCondition, imports);
                    Set<String> sortedImports = GeneratorUtil.getUsedImports(entityClass, fields, imports);

                    // 파일 생성
                    Path outputPath = GeneratorUtil.getOutputPathForTemplate(template, entityName);
                    generateCodeFromTemplate(template, outputPath, entityName, comment, fields, sortedImports);
                }
            }

            System.out.println("Code generation completed successfully!");
        } catch (Exception e) {
            System.err.println("Code generation failed: " + e.getMessage());
        }
    }

    /**
     * 템플릿에 key:value 넘기기
     */
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

        fields.forEach(field -> {
            if (!field.containsKey("getter")) {
                String fieldName = (String) field.get("name");
                String capitalizedFieldName = GeneratorUtil.capitalize(fieldName);
                String getter = (field.get("type").equals("boolean") ? "is" : "get") + capitalizedFieldName;
                field.put("getter", getter);
            }
        });

        Map<String, Object> context = new HashMap<>();
        context.put("entityName", entityName);
        context.put("entityNameLower", entityName.toLowerCase());
        context.put("entityNameUpper", entityName.toUpperCase());
        context.put("comment", comment);
        context.put("fields", fields);
        context.put("imports", imports);
        context.put("openCurlyBraces", "{");
        context.put("closeCurlyBraces", "}");

        for (ModuleConstants constant : ModuleConstants.values()) {
            context.put(constant.getContextKey(), constant.getValue());
        }

        String fileName = entityName + templateName
                .substring(templateName.lastIndexOf('/') + 1)
                .replace(".mustache", ".java");

        GeneratorUtil.createDirectoryIfNeeded(outputPath);
        try (Writer writer = new FileWriter(new File(outputPath.toFile(), fileName))) {
            mustache.execute(writer, context).flush();
            System.out.println("Created file: " + outputPath.resolve(fileName));
        }
    }
}
