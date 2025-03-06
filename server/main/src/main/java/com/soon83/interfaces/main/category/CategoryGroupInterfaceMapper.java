package com.soon83.interfaces.main.category;

import com.soon83.dtos.category.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryGroupInterfaceMapper {
    public CategoryGroupSearchCondition toCategoryGroupSearchCondition(CategoryGroupSearchRequest request) {
        return new CategoryGroupSearchCondition(
                request.getCategoryGroupId(),
                request.getOrdering(),
                request.getName(),
                request.getDescription()
        );
    }

    public CategoryGroupInfoResponse toCategoryGroupInfoResponse(CategoryGroupInfo categoryGroupInfo) {
        return new CategoryGroupInfoResponse(
                categoryGroupInfo.getCategoryGroupId(),
                categoryGroupInfo.getOrdering(),
                categoryGroupInfo.getName(),
                categoryGroupInfo.getDescription()
        );
    }

    public CategoryGroupCreateCommand toCategoryGroupCreateCommand(CategoryGroupRegisterRequest request) {
        return new CategoryGroupCreateCommand(
                request.getOrdering(),
                request.getName(),
                request.getDescription()
        );
    }

    public CategoryGroupBulkCreateCommand toCategoryGroupBulkCreateCommand(CategoryGroupBulkRegisterRequest request) {
        List<CategoryGroupCreateCommand> commandList = request.getRequestList().stream()
                .map(this::toCategoryGroupCreateCommand)
                .toList();
        return new CategoryGroupBulkCreateCommand(commandList);
    }

    public CategoryGroupUpdateCommand toCategoryGroupUpdateCommand(CategoryGroupEditRequest request) {
        return new CategoryGroupUpdateCommand(
                request.getCategoryGroupId(),
                request.getOrdering(),
                request.getName(),
                request.getDescription()
        );
    }

    public CategoryGroupBulkUpdateCommand toCategoryGroupBulkUpdateCommand(CategoryGroupBulkEditRequest request) {
        List<CategoryGroupUpdateCommand> commandList = request.getRequestList().stream()
                .map(this::toCategoryGroupUpdateCommand)
                .toList();
        return new CategoryGroupBulkUpdateCommand(commandList);
    }

    public CategoryGroupDeleteCommand toCategoryGroupDeleteCommand(Long categoryGroupId) {
        return new CategoryGroupDeleteCommand(categoryGroupId);
    }

    public CategoryGroupBulkDeleteCommand toCategoryGroupBulkDeleteCommand(CategoryGroupBulkRemoveRequest request) {
        List<CategoryGroupDeleteCommand> commandList = request.getRequestList().stream()
                .map(CategoryGroupRemoveRequest::getCategoryGroupId)
                .map(this::toCategoryGroupDeleteCommand)
                .toList();
        return new CategoryGroupBulkDeleteCommand(commandList);
    }
}
