package com.soon83.codegenerator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TemplateFiles {
    // Application
    FACADE(ModuleConstants.MODULE_CORE_APPLICATION, "Facade"),

    // Interfaces
    CONTROLLER(ModuleConstants.MODULE_SERVER_INTERFACES, "Controller"),
    BULK_REGISTER_REQUEST(ModuleConstants.MODULE_SERVER_INTERFACES, "BulkRegisterRequest"),
    BULK_EDIT_REQUEST(ModuleConstants.MODULE_SERVER_INTERFACES, "BulkEditRequest"),
    BULK_REMOVE_REQUEST(ModuleConstants.MODULE_SERVER_INTERFACES, "BulkRemoveRequest"),
    REGISTER_REQUEST(ModuleConstants.MODULE_SERVER_INTERFACES, "RegisterRequest"),
    EDIT_REQUEST(ModuleConstants.MODULE_SERVER_INTERFACES, "EditRequest"),
    REMOVE_REQUEST(ModuleConstants.MODULE_SERVER_INTERFACES, "RemoveRequest"),
    SEARCH_REQUEST(ModuleConstants.MODULE_SERVER_INTERFACES, "SearchRequest"),
    INFO_RESPONSE(ModuleConstants.MODULE_SERVER_INTERFACES, "InfoResponse"),
    INTERFACE_MAPPER(ModuleConstants.MODULE_SERVER_INTERFACES, "InterfaceMapper"),

    // Domain Dto
    BULK_CREATE_COMMAND(ModuleConstants.MODULE_COMMON_DTOS, "BulkCreateCommand"),
    BULK_UPDATE_COMMAND(ModuleConstants.MODULE_COMMON_DTOS, "BulkUpdateCommand"),
    BULK_DELETE_COMMAND(ModuleConstants.MODULE_COMMON_DTOS, "BulkDeleteCommand"),
    CREATE_COMMAND(ModuleConstants.MODULE_COMMON_DTOS, "CreateCommand"),
    UPDATE_COMMAND(ModuleConstants.MODULE_COMMON_DTOS, "UpdateCommand"),
    DELETE_COMMAND(ModuleConstants.MODULE_COMMON_DTOS, "DeleteCommand"),
    SEARCH_CONDITION(ModuleConstants.MODULE_COMMON_DTOS, "SearchCondition"),
    INFO(ModuleConstants.MODULE_COMMON_DTOS, "Info"),

    // Exception
    NOT_FOUND_EXCEPTION(ModuleConstants.MODULE_COMMON_EXCEPTIONS, "NotFoundException");

    private final ModuleConstants module;
    private final String fileName;

    public String getPath() {
        return module.getValue() + "/" + fileName + ".mustache";
    }
}
