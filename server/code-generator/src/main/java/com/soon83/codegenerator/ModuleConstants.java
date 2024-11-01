package com.soon83.codegenerator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModuleConstants {
    PACKAGE_GROUP_NAME("com.soon83", "packageGroupName"),
    MODULE_CORE_ENTITIES("entities", "moduleCoreEntities"),
    MODULE_CORE_APPLICATION("application", "moduleCoreApplication"),
    MODULE_CORE_DOMAIN("domain", "moduleCoreDomain"),
    MODULE_COMMON_DTOS("dtos", "moduleCommonDtos"),
    MODULE_COMMON_EXCEPTIONS("exceptions", "moduleCommonExceptions"),
    MODULE_SERVER_INTERFACES("interfaces", "moduleServerInterfaces"),
    ;

    private final String value;
    private final String contextKey;
}
