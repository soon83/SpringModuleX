package com.soon83.codegenerator.settings;

import com.soon83.codegenerator.strategy.field.ExcludeIdField;
import com.soon83.codegenerator.strategy.field.FieldInclusionStrategy;
import com.soon83.codegenerator.strategy.field.IncludeAllFields;
import com.soon83.codegenerator.strategy.field.IncludeOnlyIdField;
import com.soon83.codegenerator.strategy.validation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TemplateCondition {
    SEARCH_REQUEST(new IncludeAllFields(), new OptionalValidation()),
    REGISTER_REQUEST(new ExcludeIdField(), new DefaultValidation()),
    EDIT_REQUEST(new IncludeAllFields(), new DefaultValidation()),
    REMOVE_REQUEST(new IncludeOnlyIdField(), new RequiredValidation()),
    CREATE_COMMAND(new ExcludeIdField(), new NoneValidation()),
    DELETE_COMMAND(new IncludeOnlyIdField(), new NoneValidation()),
    FACTORY(new ExcludeIdField(), new NoneValidation()),
    ENTITY(new ExcludeIdField(), new NoneValidation()),
    NONE(new IncludeAllFields(), new NoneValidation());

    private final FieldInclusionStrategy fieldInclusionStrategy;
    private final ValidationStrategy validationStrategy;

    /**
     * 템플릿에 정의한 객체에 필드와 Validation 을 어떻게 포함할지에 대한 조건
     */
    public static TemplateCondition fromTemplate(String template) {
        if (template.contains("Command")) {
            if (template.contains("Create")) return CREATE_COMMAND;
            if (template.contains("Delete")) return DELETE_COMMAND;

        } else if (template.contains("Request")) {
            if (template.contains("Register")) return REGISTER_REQUEST;
            if (template.contains("Edit")) return EDIT_REQUEST;
            if (template.contains("Remove")) return REMOVE_REQUEST;
            if (template.contains("Search")) return SEARCH_REQUEST;

        } else if (template.contains("Factory")) {
            return FACTORY;

        } else if (template.contains("Entity")) {
            return ENTITY;
        }
        return NONE;
    }
}
