package com.soon83.interfaces.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon83.application.category.CategoryGroupFacade;
import com.soon83.dtos.category.*;
import com.soon83.dtos.utils.Sortable;
import com.soon83.interfaces.configs.ResponseHeaderUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/category-group-list")
@RequiredArgsConstructor
public class CategoryGroupController {
    private final ObjectMapper objectMapper;
    private final CategoryGroupFacade categoryGroupFacade;
    private final CategoryGroupInterfaceMapper categoryGroupInterfaceMapper;

    /**
     * 카테고리 그룹 목록 조회 - 페이징
     */
    @GetMapping("/paging")
    public List<CategoryGroupInfoResponse> searchPageCategoryGroupInfoList(
            @ModelAttribute @Valid CategoryGroupSearchRequest request,
            Pageable pageable,
            HttpServletResponse response
    ) {
        log.debug("# searchPageCategoryGroupInfoList # request: {}, pageable: {}", request, pageable);
        CategoryGroupSearchCondition searchCondition = categoryGroupInterfaceMapper.toCategoryGroupSearchCondition(request);
        Page<CategoryGroupInfo> pageCategoryGroupInfoList = categoryGroupFacade.searchPageCategoryGroupInfoList(searchCondition, pageable);
        ResponseHeaderUtil.setPageableResponseHeader(response, objectMapper, pageCategoryGroupInfoList);
        return pageCategoryGroupInfoList.getContent().stream()
                .map(categoryGroupInterfaceMapper::toCategoryGroupInfoResponse)
                .toList();
    }

    /**
     * 카테고리 그룹 목록 조회 - 전체
     */
    @GetMapping("/all")
    public List<CategoryGroupInfoResponse> searchAllCategoryGroupInfoList(
            @ModelAttribute @Valid CategoryGroupSearchRequest request,
            Sortable sortable
    ) {
        log.debug("# searchAllCategoryGroupInfoList # request: {}, sortable: {}", request, sortable);
        CategoryGroupSearchCondition searchCondition = categoryGroupInterfaceMapper.toCategoryGroupSearchCondition(request);
        List<CategoryGroupInfo> allCategoryGroupInfoList = categoryGroupFacade.searchAllCategoryGroupInfoList(searchCondition, sortable);
        return allCategoryGroupInfoList.stream()
                .map(categoryGroupInterfaceMapper::toCategoryGroupInfoResponse)
                .toList();
    }

    /**
     * 카테고리 그룹 단건 조회
     */
    @GetMapping("/{categoryGroupId}")
    public CategoryGroupInfoResponse searchCategoryGroupInfo(@PathVariable Long categoryGroupId) {
        log.debug("# searchCategoryGroupInfo # categoryGroupId: {}", categoryGroupId);
        CategoryGroupInfo categoryGroupInfo = categoryGroupFacade.searchCategoryGroupInfo(categoryGroupId);
        return categoryGroupInterfaceMapper.toCategoryGroupInfoResponse(categoryGroupInfo);
    }

    /**
     * 카테고리 그룹 단건 등록
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryGroupInfoResponse registerCategoryGroupInfo(@RequestBody @Valid CategoryGroupRegisterRequest request) {
        log.debug("# registerCategoryGroupInfo # request: {}", request);
        CategoryGroupCreateCommand command = categoryGroupInterfaceMapper.toCategoryGroupCreateCommand(request);
        CategoryGroupInfo categoryGroupInfo = categoryGroupFacade.registerCategoryGroupInfo(command);
        return categoryGroupInterfaceMapper.toCategoryGroupInfoResponse(categoryGroupInfo);
    }

    /**
     * 카테고리 그룹 대량 등록
     */
    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<CategoryGroupInfoResponse> registerBulkCategoryGroupInfo(@RequestBody @Valid CategoryGroupBulkRegisterRequest request) {
        log.debug("# registerBulkCategoryGroupInfo # request: {}", request);
        CategoryGroupBulkCreateCommand bulkCommand = categoryGroupInterfaceMapper.toCategoryGroupBulkCreateCommand(request);
        List<CategoryGroupInfo> categoryGroupInfoList = categoryGroupFacade.registerBulkCategoryGroupInfo(bulkCommand);
        return categoryGroupInfoList.stream()
                .map(categoryGroupInterfaceMapper::toCategoryGroupInfoResponse)
                .toList();
    }

    /**
     * 카테고리 그룹 단건 수정
     */
    @PutMapping("/{categoryGroupId}")
    public CategoryGroupInfoResponse editCategoryGroupInfo(@RequestBody @Valid CategoryGroupEditRequest request) {
        log.debug("# editCategoryGroupInfo # request: {}", request);
        CategoryGroupUpdateCommand command = categoryGroupInterfaceMapper.toCategoryGroupUpdateCommand(request);
        CategoryGroupInfo categoryGroupInfo = categoryGroupFacade.editCategoryGroupInfo(command);
        return categoryGroupInterfaceMapper.toCategoryGroupInfoResponse(categoryGroupInfo);
    }

    /**
     * 카테고리 그룹 대량 수정
     */
    @PutMapping("/bulk")
    public List<CategoryGroupInfoResponse> editBulkCategoryGroupInfo(@RequestBody @Valid CategoryGroupBulkEditRequest request) {
        log.debug("# editBulkCategoryGroupInfo # request: {}", request);
        CategoryGroupBulkUpdateCommand bulkCommand = categoryGroupInterfaceMapper.toCategoryGroupBulkUpdateCommand(request);
        List<CategoryGroupInfo> categoryGroupInfoList = categoryGroupFacade.editBulkCategoryGroupInfo(bulkCommand);
        return categoryGroupInfoList.stream()
                .map(categoryGroupInterfaceMapper::toCategoryGroupInfoResponse)
                .toList();
    }

    /**
     * 카테고리 그룹 단건 삭제
     */
    @DeleteMapping("/{categoryGroupId}")
    public void removeCategoryGroupInfo(@PathVariable Long categoryGroupId) {
        log.debug("# removeCategoryGroupInfo # categoryGroupId: {}", categoryGroupId);
        CategoryGroupDeleteCommand command = categoryGroupInterfaceMapper.toCategoryGroupDeleteCommand(categoryGroupId);
        categoryGroupFacade.removeCategoryGroupInfo(command);
    }

    /**
     * 카테고리 그룹 대량 삭제
     */
    @PostMapping("/bulk-remove")
    public void removeBulkCategoryGroupInfo(@RequestBody @Valid CategoryGroupBulkRemoveRequest request) {
        log.debug("# removeBulkCategoryGroupInfo # request: {}", request);
        CategoryGroupBulkDeleteCommand bulkCommand = categoryGroupInterfaceMapper.toCategoryGroupBulkDeleteCommand(request);
        categoryGroupFacade.removeBulkCategoryGroupInfo(bulkCommand);
    }
}
