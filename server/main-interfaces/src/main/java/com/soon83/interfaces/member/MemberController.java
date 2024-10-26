package com.soon83.interfaces.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon83.application.member.MemberFacade;
import com.soon83.dtos.member.MemberCreateCommand;
import com.soon83.dtos.member.MemberInfo;
import com.soon83.dtos.member.MemberSearchCondition;
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
    public MemberRegisterResponse registerMemberInfo(@RequestBody @Valid MemberRegisterRequest request) {
        log.debug("# registerMemberInfo # request: {}", request);
        MemberCreateCommand command = memberMapper.toMemberCreateCommand(request);
        Long registeredMemberId = memberFacade.registerMember(command);
        return memberMapper.toMemberRegisterResponse(registeredMemberId);
    }
}
