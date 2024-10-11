package com.soon83.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon83.config.DefaultIdSort;
import com.soon83.config.ResponseHeaderUtil;
import com.soon83.config.Sortable;
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
    private final MemberResponseMapper memberResponseMapper;
    private final MemberFacade memberFacade;

    @GetMapping("/paging")
    @DefaultIdSort(entityClass = Member.class)
    public List<MemberInfoResponse> searchPageMemberInfoList(
            @ModelAttribute @Valid MemberSearchRequest request,
            Pageable pageable,
            HttpServletResponse response
    ) {
        log.debug("# searchPageMemberInfoList # request: {}, pageable: {}", request, pageable);
        MemberSearchCondition searchCondition = memberResponseMapper.toMemberSearchCondition(request);
        Page<MemberInfo> memberInfoPage = memberFacade.searchPageMemberInfoList(searchCondition, pageable);
        ResponseHeaderUtil.setPageableResponseHeader(response, objectMapper, memberInfoPage);
        return memberInfoPage.getContent().stream()
                .map(memberResponseMapper::toMemberInfoResponse)
                .toList();
    }

    @GetMapping("/all")
    public List<MemberInfoResponse> searchAllMemberInfoList(
            @ModelAttribute @Valid MemberSearchRequest request,
            Sortable sortable
    ) {
        log.debug("# searchAllMemberInfoList # request: {}, # sortable: {}", request, sortable);
        MemberSearchCondition searchCondition = memberResponseMapper.toMemberSearchCondition(request);
        List<MemberInfo> memberInfoList = memberFacade.searchAllMemberInfoList(searchCondition);
        return memberInfoList.stream()
                .map(memberResponseMapper::toMemberInfoResponse)
                .toList();
    }

    @GetMapping("/{userId}")
    public MemberInfoResponse searchMemberInfo(@PathVariable Long userId) {
        log.debug("# searchUserInfo # userId: {}", userId);
        MemberInfo memberInfo = memberFacade.searchMemberInfo(userId);
        return memberResponseMapper.toMemberInfoResponse(memberInfo);
    }
}
