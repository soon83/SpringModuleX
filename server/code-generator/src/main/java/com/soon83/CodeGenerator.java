package com.soon83;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.soon83.codegenerator.strategy.TemplateType;
import com.soon83.codegenerator.util.GeneratorUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
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
            Set<Class<?>> entityClasses = GeneratorUtil.findEntityClasses(PACKAGE_GROUP_NAME + "." + MODULE_CORE_ENTITIES);
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

            // 엔티티 클래스 찾아서 필드 및 Validation 정보 수집
            for (Class<?> entityClass : entityClasses) {
                String entityName = entityClass.getSimpleName();
                String comment = GeneratorUtil.getComment(entityClass).orElse(entityName);
                System.out.println("\nProcessing entity: " + entityName);

                // 템플릿에 필드 및 Validation 넣기
                for (String template : templates) {
                    TemplateType templateType = TemplateType.fromTemplate(template);
                    System.out.println("Processing template as type: " + templateType);

                    Set<String> imports = new TreeSet<>();
                    List<Map<String, Object>> fields = GeneratorUtil.getEntityFieldsForType(entityClass, comment, templateType, imports);
                    Set<String> sortedImports = GeneratorUtil.getUsedImports(entityClass, fields, imports);

                    // 파일 출력
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

        GeneratorUtil.createDirectoryIfNeeded(outputPath);
        try (Writer writer = new FileWriter(new File(outputPath.toFile(), fileName))) {
            mustache.execute(writer, context).flush();
            System.out.println("Created file: " + outputPath.resolve(fileName));
        }
    }
}
