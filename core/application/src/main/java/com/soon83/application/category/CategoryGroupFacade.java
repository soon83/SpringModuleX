package com.soon83.application.category;

import com.soon83.domain.category.CategoryGroupService;
import com.soon83.dtos.category.*;
import com.soon83.dtos.utils.Sortable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryGroupFacade {
    private final CategoryGroupService categoryGroupService;

    /**
     * 카테고리 그룹 목록 조회 - 페이징
     */
    public Page<CategoryGroupInfo> searchPageCategoryGroupInfoList(CategoryGroupSearchCondition searchCondition, Pageable pageable) {
        return categoryGroupService.getPageCategoryGroupInfoList(searchCondition, pageable);
    }

    /**
     * 카테고리 그룹 목록 조회 - 전체
     */
    public List<CategoryGroupInfo> searchAllCategoryGroupInfoList(CategoryGroupSearchCondition searchCondition, Sortable sortable) {
        return categoryGroupService.getAllCategoryGroupInfoList(searchCondition, sortable);
    }

    /**
     * 카테고리 그룹 단건 조회
     */
    public CategoryGroupInfo searchCategoryGroupInfo(Long categoryGroupId) {
        return categoryGroupService.getCategoryGroupInfo(categoryGroupId);
    }

    /**
     * 카테고리 그룹 단건 등록
     */
    public CategoryGroupInfo registerCategoryGroupInfo(CategoryGroupCreateCommand command) {
        return categoryGroupService.createCategoryGroupInfo(command);
    }

    /**
     * 카테고리 그룹 대량 등록
     */
    public List<CategoryGroupInfo> registerBulkCategoryGroupInfo(CategoryGroupBulkCreateCommand bulkCommand) {
        return categoryGroupService.createBulkCategoryGroupInfo(bulkCommand);
    }

    /**
     * 카테고리 그룹 단건 수정
     */
    public CategoryGroupInfo editCategoryGroupInfo(CategoryGroupUpdateCommand command) {
        return categoryGroupService.updateCategoryGroupInfo(command);
    }

    /**
     * 카테고리 그룹 대량 수정
     */
    public List<CategoryGroupInfo> editBulkCategoryGroupInfo(CategoryGroupBulkUpdateCommand bulkCommand) {
        return categoryGroupService.updateBulkCategoryGroupInfo(bulkCommand);
    }

    /**
     * 카테고리 그룹 단건 삭제
     */
    public void removeCategoryGroupInfo(CategoryGroupDeleteCommand command) {
        categoryGroupService.deleteCategoryGroupInfo(command);
    }

    /**
     * 카테고리 그룹 대량 삭제
     */
    public void removeBulkCategoryGroupInfo(CategoryGroupBulkDeleteCommand bulkCommand) {
        categoryGroupService.deleteBulkCategoryGroupInfo(bulkCommand);
    }
}
