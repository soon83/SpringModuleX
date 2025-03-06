package com.soon83.domain.category;

import com.soon83.dtos.category.CategoryGroupCreateCommand;
import com.soon83.dtos.category.CategoryGroupInfo;
import com.soon83.dtos.category.CategoryGroupUpdateCommand;
import com.soon83.entities.category.CategoryGroup;
import org.springframework.stereotype.Component;

@Component
public class CategoryGroupDomainMapper {
    public CategoryGroupInfo toCategoryGroupInfo(CategoryGroup categoryGroup) {
        return new CategoryGroupInfo(
                categoryGroup.getCategoryGroupId(),
                categoryGroup.getOrdering(),
                categoryGroup.getName(),
                categoryGroup.getDescription()
        );
    }

    public CategoryGroup toCategoryGroupEntity(CategoryGroupCreateCommand command) {
        return new CategoryGroup(
                command.getOrdering(),
                command.getName(),
                command.getDescription()
        );
    }

    public void updateCategoryGroup(CategoryGroup categoryGroup, CategoryGroupUpdateCommand command) {
        categoryGroup.update(
                command.getOrdering(),
                command.getName(),
                command.getDescription()
        );
    }
}
