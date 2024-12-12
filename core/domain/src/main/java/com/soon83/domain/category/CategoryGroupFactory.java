package com.soon83.domain.category;

import com.soon83.dtos.category.CategoryGroupCreateCommand;
import com.soon83.dtos.category.CategoryGroupUpdateCommand;
import com.soon83.entities.BaseEntity;
import com.soon83.entities.category.CategoryGroup;
import com.soon83.utils.AssertUtil;

public class CategoryGroupFactory extends BaseEntity {
    /**
     * 카테고리 그룹 생성
     */
    public static CategoryGroup createCategoryGroup(CategoryGroupCreateCommand command) {
        validateCreateCategoryGroup(command);
        return CategoryGroup.builder()
                .ordering(command.getOrdering())
                .name(command.getName())
                .description(command.getDescription())
                .icon(command.getIcon())
                .build();
    }

    /**
     * 카테고리 그룹 수정
     */
    public static void updateCategoryGroup(CategoryGroup categoryGroup, CategoryGroupUpdateCommand command) {
        validateUpdateCategoryGroup(command);
        categoryGroup.update(
                command.getOrdering(),
                command.getName(),
                command.getDescription(),
                command.getIcon()
        );
    }

    private static void validateCreateCategoryGroup(CategoryGroupCreateCommand command) {
        AssertUtil.notNull(command.getOrdering(), "ordering");
        AssertUtil.notNull(command.getName(), "name");
        AssertUtil.notNull(command.getDescription(), "description");
        AssertUtil.notNull(command.getIcon(), "icon");
    }

    private static void validateUpdateCategoryGroup(CategoryGroupUpdateCommand command) {
        AssertUtil.notNull(command.getOrdering(), "ordering");
        AssertUtil.notNull(command.getName(), "name");
        AssertUtil.notNull(command.getDescription(), "description");
        AssertUtil.notNull(command.getIcon(), "icon");
    }
}
