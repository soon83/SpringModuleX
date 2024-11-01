package com.soon83.codegenerator.strategy;

import com.soon83.codegenerator.strategy.field.ExcludeIdField;
import com.soon83.codegenerator.strategy.field.FieldInclusionStrategy;
import com.soon83.codegenerator.strategy.field.IncludeAllFields;
import com.soon83.codegenerator.strategy.field.IncludeOnlyIdField;
import com.soon83.codegenerator.strategy.validation.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TemplateType {
    SEARCH_REQUEST(new IncludeAllFields(), new OptionalValidation()),
    REGISTER_REQUEST(new ExcludeIdField(), new DefaultValidation()),
    EDIT_REQUEST(new IncludeAllFields(), new DefaultValidation()),
    REMOVE_REQUEST(new IncludeOnlyIdField(), new RequiredValidation()),
    CREATE_COMMAND(new ExcludeIdField(), new NoneValidation()),
    DELETE_COMMAND(new IncludeOnlyIdField(), new NoneValidation()),
    NONE(new IncludeAllFields(), new NoneValidation());

    private final FieldInclusionStrategy fieldInclusionStrategy;
    private final ValidationStrategy validationStrategy;

    public static TemplateType fromTemplate(String template) {
        if (template.contains("Command")) {
            if (template.contains("Create")) return CREATE_COMMAND;
            if (template.contains("Delete")) return DELETE_COMMAND;

        } else if (template.contains("Request")) {
            if (template.contains("Register")) return REGISTER_REQUEST;
            if (template.contains("Edit")) return EDIT_REQUEST;
            if (template.contains("Remove")) return REMOVE_REQUEST;
            if (template.contains("Search")) return SEARCH_REQUEST;
        }
        return NONE;
    }
}
