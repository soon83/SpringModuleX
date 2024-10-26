package com.soon83.application.member;

import com.soon83.domain.member.MemberService;
import com.soon83.dtos.member.*;
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
public class MemberFacade {
    private final MemberService memberService;

    /**
     * 회원 목록 조회 - 페이징
     */
    public Page<MemberInfo> searchPageMemberInfoList(MemberSearchCondition searchCondition, Pageable pageable) {
        return memberService.getPageMemberInfoList(searchCondition, pageable);
    }

    /**
     * 회원 목록 조회 - 전체
     */
    public List<MemberInfo> searchAllMemberInfoList(MemberSearchCondition searchCondition, Sortable sortable) {
        return memberService.getAllMemberInfoList(searchCondition, sortable);
    }

    /**
     * 회원 단건 조회
     */
    public MemberInfo searchMemberInfo(Long memberId) {
        return memberService.getMemberInfo(memberId);
    }

    /**
     * 회원 단건 등록
     */
    public Long registerMember(MemberCreateCommand command) {
        return memberService.createMember(command);
    }

    /**
     * 회원 대량 등록
     */
    public void registerBulkMember(MemberBulkCreateCommand bulkCommand) {
        memberService.createBulkMember(bulkCommand);
    }

    /**
     * 회원 단건 수정
     */
    public void editMember(MemberUpdateCommand command) {
        memberService.updateMember(command);
    }

    /**
     * 회원 대량 수정
     */
    public void editBulkMember(MemberBulkUpdateCommand bulkCommand) {
        memberService.updateBulkMember(bulkCommand);
    }

    /**
     * 회원 단건 삭제
     */
    public void removeMember(MemberDeleteCommand command) {
        memberService.deleteMember(command);
    }

    /**
     * 회원 대량 삭제
     */
    public void removeBulkMember(MemberBulkDeleteCommand bulkCommand) {
        memberService.deleteBulkMember(bulkCommand);
    }
}
