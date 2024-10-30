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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/member-list")
@RequiredArgsConstructor
public class MemberController {
    private final ObjectMapper objectMapper;
    private final MemberFacade memberFacade;
    private final MemberInterfaceMapper memberInterfaceMapper;

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
        MemberSearchCondition searchCondition = memberInterfaceMapper.toMemberSearchCondition(request);
        Page<MemberInfo> pageMemberInfoList = memberFacade.searchPageMemberInfoList(searchCondition, pageable);
        ResponseHeaderUtil.setPageableResponseHeader(response, objectMapper, pageMemberInfoList);
        return pageMemberInfoList.getContent().stream()
                .map(memberInterfaceMapper::toMemberInfoResponse)
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
        log.debug("# searchAllMemberInfoList # request: {}, sortable: {}", request, sortable);
        MemberSearchCondition searchCondition = memberInterfaceMapper.toMemberSearchCondition(request);
        List<MemberInfo> allMemberInfoList = memberFacade.searchAllMemberInfoList(searchCondition, sortable);
        return allMemberInfoList.stream()
                .map(memberInterfaceMapper::toMemberInfoResponse)
                .toList();
    }

    /**
     * 회원 단건 조회
     */
    @GetMapping("/{memberId}")
    public MemberInfoResponse searchMemberInfo(@PathVariable Long memberId) {
        log.debug("# searchMemberInfo # memberId: {}", memberId);
        MemberInfo memberInfo = memberFacade.searchMemberInfo(memberId);
        return memberInterfaceMapper.toMemberInfoResponse(memberInfo);
    }

    /**
     * 회원 단건 등록
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemberInfoResponse registerMemberInfo(@RequestBody @Valid MemberRegisterRequest request) {
        log.debug("# registerMemberInfo # request: {}", request);
        MemberCreateCommand command = memberInterfaceMapper.toMemberCreateCommand(request);
        MemberInfo memberInfo = memberFacade.registerMemberInfo(command);
        return memberInterfaceMapper.toMemberInfoResponse(memberInfo);
    }

    /**
     * 회원 대량 등록
     */
    @PostMapping("/bulk")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MemberInfoResponse> registerBulkMemberInfo(@RequestBody @Valid MemberBulkRegisterRequest request) {
        log.debug("# registerBulkMemberInfo # request: {}", request);
        MemberBulkCreateCommand bulkCommand = memberInterfaceMapper.toMemberBulkCreateCommand(request);
        List<MemberInfo> memberInfoList = memberFacade.registerBulkMemberInfo(bulkCommand);
        return memberInfoList.stream()
                .map(memberInterfaceMapper::toMemberInfoResponse)
                .toList();
    }

    /**
     * 회원 단건 수정
     */
    @PutMapping("/{memberId}")
    public MemberInfoResponse editMemberInfo(@RequestBody @Valid MemberEditRequest request) {
        log.debug("# editMemberInfo # request: {}", request);
        MemberUpdateCommand command = memberInterfaceMapper.toMemberUpdateCommand(request);
        MemberInfo memberInfo = memberFacade.editMemberInfo(command);
        return memberInterfaceMapper.toMemberInfoResponse(memberInfo);
    }

    /**
     * 회원 대량 수정
     */
    @PutMapping("/bulk")
    public List<MemberInfoResponse> editBulkMemberInfo(@RequestBody @Valid MemberBulkEditRequest request) {
        log.debug("# editBulkMemberInfo # request: {}", request);
        MemberBulkUpdateCommand bulkCommand = memberInterfaceMapper.toMemberBulkUpdateCommand(request);
        List<MemberInfo> memberInfoList = memberFacade.editBulkMemberInfo(bulkCommand);
        return memberInfoList.stream()
                .map(memberInterfaceMapper::toMemberInfoResponse)
                .toList();
    }

    /**
     * 회원 단건 삭제
     */
    @DeleteMapping("/{memberId}")
    public void removeMemberInfo(@ModelAttribute @Valid MemberRemoveRequest request) {
        log.debug("# removeMemberInfo # request: {}", request);
        MemberDeleteCommand command = memberInterfaceMapper.toMemberDeleteCommand(request);
        memberFacade.removeMemberInfo(command);
    }

    /**
     * 회원 대량 삭제
     */
    @PostMapping("/bulk-remove")
    public void removeBulkMemberInfo(@RequestBody @Valid MemberBulkRemoveRequest request) {
        log.debug("# removeBulkMemberInfo # request: {}", request);
        MemberBulkDeleteCommand bulkCommand = memberInterfaceMapper.toMemberBulkDeleteCommand(request);
        memberFacade.removeBulkMemberInfo(bulkCommand);
    }
}
