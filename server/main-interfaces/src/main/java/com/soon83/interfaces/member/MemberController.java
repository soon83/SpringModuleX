package com.soon83.interfaces.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon83.application.member.MemberFacade;
import com.soon83.dtos.member.*;
import com.soon83.dtos.utils.Sortable;
import com.soon83.interfaces.configs.ResponseHeaderUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/member-list")
@RequiredArgsConstructor
public class MemberController {
    private final ObjectMapper objectMapper;
    private final MemberFacade memberFacade;
    private final MemberMapper memberMapper;

    /**
     * 회원 목록 조회 - 페이징
     */
    @GetMapping("/paging")
    public List<MemberInfoResponse> searchPageMemberInfoList(
            @ModelAttribute @Valid MemberSearchRequest request,
            Pageable pageable,
            HttpServletResponse response
    ) {
        log.debug("# searchPageMemberInfoList # request: {}, pageable: {}", request, pageable);
        MemberSearchCondition searchCondition = memberMapper.toMemberSearchCondition(request);
        Page<MemberInfo> pageMemberInfoList = memberFacade.searchPageMemberInfoList(searchCondition, pageable);
        ResponseHeaderUtil.setPageableResponseHeader(response, objectMapper, pageMemberInfoList);
        return pageMemberInfoList.getContent().stream()
                .map(memberMapper::toMemberInfoResponse)
                .toList();
    }

    /**
     * 회원 목록 조회 - 전체
     */
    @GetMapping("/all")
    public List<MemberInfoResponse> searchAllMemberInfoList(
            @ModelAttribute @Valid MemberSearchRequest request,
            Sortable sortable
    ) {
        log.debug("# searchAllMemberInfoList # request: {}, # sortable: {}", request, sortable);
        MemberSearchCondition searchCondition = memberMapper.toMemberSearchCondition(request);
        List<MemberInfo> allMemberInfoList = memberFacade.searchAllMemberInfoList(searchCondition, sortable);
        return allMemberInfoList.stream()
                .map(memberMapper::toMemberInfoResponse)
                .toList();
    }

    /**
     * 회원 단건 조회
     */
    @GetMapping("/{userId}")
    public MemberInfoResponse searchMemberInfo(@PathVariable Long userId) {
        log.debug("# searchUserInfo # userId: {}", userId);
        MemberInfo memberInfo = memberFacade.searchMemberInfo(userId);
        return memberMapper.toMemberInfoResponse(memberInfo);
    }

    /**
     * 회원 단건 등록
     */
    @PostMapping
    public MemberInfoResponse registerMemberInfo(@RequestBody @Valid MemberRegisterRequest request) {
        log.debug("# registerMemberInfo # request: {}", request);
        MemberCreateCommand command = memberMapper.toMemberCreateCommand(request);
        MemberInfo registeredMember = memberFacade.registerMemberInfo(command);
        return memberMapper.toMemberInfoResponse(registeredMember);
    }

    /**
     * 회원 대량 등록
     */
    @PostMapping("/bulk")
    public void registerBulkMemberInfo(@RequestBody @Valid MemberBulkRegisterRequest request) {
        log.debug("# registerBulkMemberInfo # request: {}", request);
        MemberBulkCreateCommand bulkCommand = memberMapper.toMemberBulkCreateCommand(request);
        memberFacade.registerBulkMemberInfo(bulkCommand);
    }

    /**
     * 회원 단건 수정
     */
    @PutMapping("/{memberId}")
    public void editMemberInfo(@RequestBody @Valid MemberEditRequest request) {
        log.debug("# editMemberInfo # request: {}", request);
        MemberUpdateCommand command = memberMapper.toMemberUpdateCommand(request);
        memberFacade.editMemberInfo(command);
    }

    /**
     * 회원 대량 수정
     */
    @PutMapping("/bulk")
    public void editBulkMemberInfo(@RequestBody @Valid MemberBulkEditRequest request) {
        MemberBulkUpdateCommand bulkCommand = memberMapper.toMemberBulkUpdateCommand(request);
        memberFacade.editBulkMemberInfo(bulkCommand);
    }

    /**
     * 회원 단건 삭제
     */
    @DeleteMapping("/{memberId}")
    public void removeMemberInfo(@RequestBody @Valid MemberRemoveRequest request) {
        MemberDeleteCommand command = memberMapper.toMemberDeleteCommand(request);
        memberFacade.removeMemberInfo(command);
    }

    /**
     * 회원 대량 삭제
     */
    @DeleteMapping("/bulk")
    public void removeBulkMemberInfo(@RequestBody @Valid MemberBulkRemoveRequest request) {
        MemberBulkDeleteCommand bulkCommand = memberMapper.toMemberBulkDeleteCommand(request);
        memberFacade.removeBulkMemberInfo(bulkCommand);
    }
}
