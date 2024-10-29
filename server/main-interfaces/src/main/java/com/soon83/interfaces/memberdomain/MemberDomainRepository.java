package com.soon83.interfaces.memberdomain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MemberDomainRepository {
    private final Map<Long, MemberDomain> memberDomains = new HashMap<>();
    private Long nextId = 1L;

    public void save(MemberDomain member) {
        // 회원 저장
        member.assignId(nextId++);
        memberDomains.put(member.getMemberId(), member);
    }

    public List<MemberDomain> findAll() {
        return new ArrayList<>(memberDomains.values());
    }
}
