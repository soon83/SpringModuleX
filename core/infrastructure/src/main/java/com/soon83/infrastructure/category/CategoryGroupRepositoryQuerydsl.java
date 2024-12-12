package com.soon83.infrastructure.category;

import com.soon83.dtos.category.CategoryGroupSearchCondition;
import com.soon83.dtos.utils.Sortable;
import com.soon83.entities.category.CategoryGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryGroupRepositoryQuerydsl {
    Page<CategoryGroup> getPageCategoryGroupList(CategoryGroupSearchCondition searchCond, Pageable pageable);

    List<CategoryGroup> getAllCategoryGroupList(CategoryGroupSearchCondition searchCond, Sortable sortable);

    Optional<CategoryGroup> getCategoryGroup(Long categoryGroupId);
}
