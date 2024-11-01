package com.soon83.codegenerator.settings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TemplateFiles {
    // Entities
    Entity(ModuleConstants.MODULE_CORE_ENTITIES, "Entity"),

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

    // Application
    FACADE(ModuleConstants.MODULE_CORE_APPLICATION, "Facade"),

    // Domain
    DOMAIN_MAPPER(ModuleConstants.MODULE_CORE_DOMAIN, "DomainMapper"),
    FACTORY(ModuleConstants.MODULE_CORE_DOMAIN, "Factory"),
    SERVICE(ModuleConstants.MODULE_CORE_DOMAIN, "Service"),

    // Infrastructure
    READER(ModuleConstants.MODULE_CORE_INFRASTRUCTURE, "Reader"),
    STORE(ModuleConstants.MODULE_CORE_INFRASTRUCTURE, "Store"),
    REPOSITORY(ModuleConstants.MODULE_CORE_INFRASTRUCTURE, "Repository"),
    REPOSITORY_JOOQ(ModuleConstants.MODULE_CORE_INFRASTRUCTURE, "RepositoryJooq"),
    REPOSITORY_JOOQ_IMPL(ModuleConstants.MODULE_CORE_INFRASTRUCTURE, "RepositoryJooqImpl"),
    REPOSITORY_QUERYDSL(ModuleConstants.MODULE_CORE_INFRASTRUCTURE, "RepositoryQuerydsl"),
    REPOSITORY_QUERYDSL_IMPL(ModuleConstants.MODULE_CORE_INFRASTRUCTURE, "RepositoryQuerydslImpl"),

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
