package com.soon83.domain.category;

import com.soon83.dtos.category.*;
import com.soon83.dtos.utils.Sortable;
import com.soon83.entities.category.CategoryGroup;
import com.soon83.infrastructure.category.CategoryGroupReader;
import com.soon83.infrastructure.category.CategoryGroupStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryGroupService {
    private final CategoryGroupDomainMapper categoryGroupDomainMapper;
    private final CategoryGroupStore categoryGroupStore;
    private final CategoryGroupReader categoryGroupReader;

    /**
     * 카테고리 그룹 목록 조회 - 페이징
     */
    public Page<CategoryGroupInfo> getPageCategoryGroupInfoList(CategoryGroupSearchCondition searchCondition, Pageable pageable) {
        return categoryGroupReader.getPageCategoryGroupList(searchCondition, pageable)
                .map(categoryGroupDomainMapper::toCategoryGroupInfo);
    }

    /**
     * 카테고리 그룹 목록 조회 - 전체
     */
    public List<CategoryGroupInfo> getAllCategoryGroupInfoList(CategoryGroupSearchCondition searchCondition, Sortable sortable) {
        return categoryGroupReader.getAllCategoryGroupList(searchCondition, sortable).stream()
                .map(categoryGroupDomainMapper::toCategoryGroupInfo)
                .toList();
    }

    /**
     * 카테고리 그룹 단건 조회
     */
    public CategoryGroupInfo getCategoryGroupInfo(Long categoryGroupId) {
        CategoryGroup categoryGroup = categoryGroupReader.getCategoryGroupOrThrow(categoryGroupId);
        return categoryGroupDomainMapper.toCategoryGroupInfo(categoryGroup);
    }

    /**
     * 카테고리 그룹 단건 등록
     */
    @Transactional
    public CategoryGroupInfo createCategoryGroupInfo(CategoryGroupCreateCommand command) {
        CategoryGroup categoryGroupEntity = categoryGroupDomainMapper.toCategoryGroupEntity(command);
        CategoryGroup createdCategoryGroup = categoryGroupStore.create(categoryGroupEntity);
        return categoryGroupDomainMapper.toCategoryGroupInfo(createdCategoryGroup);
    }

    /**
     * 카테고리 그룹 대량 등록
     */
    @Transactional
    public List<CategoryGroupInfo> createBulkCategoryGroupInfo(CategoryGroupBulkCreateCommand bulkCommand) {
        return bulkCommand.getCommandList().stream()
                .map(this::createCategoryGroupInfo)
                .toList();
    }

    /**
     * 카테고리 그룹 단건 수정
     */
    @Transactional
    public CategoryGroupInfo updateCategoryGroupInfo(CategoryGroupUpdateCommand command) {
        CategoryGroup categoryGroup = categoryGroupReader.getCategoryGroupOrThrow(command.getCategoryGroupId());
        categoryGroupDomainMapper.updateCategoryGroup(categoryGroup, command);
        return categoryGroupDomainMapper.toCategoryGroupInfo(categoryGroup);
    }

    /**
     * 카테고리 그룹 대량 수정
     */
    @Transactional
    public List<CategoryGroupInfo> updateBulkCategoryGroupInfo(CategoryGroupBulkUpdateCommand bulkCommand) {
        return bulkCommand.getCommandList().stream()
                .map(this::updateCategoryGroupInfo)
                .toList();
    }

    /**
     * 카테고리 그룹 단건 삭제
     */
    @Transactional
    public void deleteCategoryGroupInfo(CategoryGroupDeleteCommand command) {
        CategoryGroup categoryGroup = categoryGroupReader.getCategoryGroupOrNull(command.getCategoryGroupId());
        categoryGroupStore.delete(categoryGroup);
    }

    /**
     * 카테고리 그룹 대량 삭제
     */
    @Transactional
    public void deleteBulkCategoryGroupInfo(CategoryGroupBulkDeleteCommand bulkCommand) {
        bulkCommand.getCommandList().forEach(this::deleteCategoryGroupInfo);
    }
}
