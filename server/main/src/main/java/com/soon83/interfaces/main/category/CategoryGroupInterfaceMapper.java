package com.soon83.interfaces.main.category;

import com.soon83.dtos.category.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryGroupInterfaceMapper {
    CategoryGroupSearchCondition toCategoryGroupSearchCondition(CategoryGroupSearchRequest request);

    CategoryGroupInfoResponse toCategoryGroupInfoResponse(CategoryGroupInfo info);

    CategoryGroupCreateCommand toCategoryGroupCreateCommand(CategoryGroupRegisterRequest request);

    CategoryGroupUpdateCommand toCategoryGroupUpdateCommand(CategoryGroupEditRequest request);

    CategoryGroupDeleteCommand toCategoryGroupDeleteCommand(Long categoryGroupId);

    @Mapping(source = "requestList", target = "commandList")
    CategoryGroupBulkCreateCommand toCategoryGroupBulkCreateCommand(CategoryGroupBulkRegisterRequest request);

    @Mapping(source = "requestList", target = "commandList")
    CategoryGroupBulkUpdateCommand toCategoryGroupBulkUpdateCommand(CategoryGroupBulkEditRequest request);

    @Mapping(source = "requestList", target = "commandList")
    CategoryGroupBulkDeleteCommand toCategoryGroupBulkDeleteCommand(CategoryGroupBulkRemoveRequest request);
}
