package com.soon83.infrastructure.category;

import com.soon83.dtos.category.CategoryGroupSearchCondition;
import com.soon83.dtos.utils.Sortable;
import com.soon83.entities.category.CategoryGroup;
import com.soon83.exceptions.category.CategoryGroupNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryGroupReader {
    private final CategoryGroupRepository categoryGroupRepository;

    /**
     * 카테고리 그룹 목록 조회 - 페이징
     */
    public Page<CategoryGroup> getPageCategoryGroupList(CategoryGroupSearchCondition searchCondition, Pageable pageable) {
        return categoryGroupRepository.getPageCategoryGroupList(searchCondition, pageable);
    }

    /**
     * 카테고리 그룹 목록 조회 - 전체
     */
    public List<CategoryGroup> getAllCategoryGroupList(CategoryGroupSearchCondition searchCondition, Sortable sortable) {
        return categoryGroupRepository.getAllCategoryGroupList(searchCondition, sortable);
    }

    /**
     * 카테고리 그룹 단건 조회 - NotFoundException 을 던짐
     */
    public CategoryGroup getCategoryGroupOrThrow(Long categoryGroupId) {
        return categoryGroupRepository.getCategoryGroup(categoryGroupId)
                .orElseThrow(CategoryGroupNotFoundException::new);
    }

    /**
     * 카테고리 그룹 단건 조회 - null 반환
     */
    public CategoryGroup getCategoryGroupOrNull(Long categoryGroupId) {
        return categoryGroupRepository.getCategoryGroup(categoryGroupId)
                .orElse(null);
    }
}
