package com.soon83.interfaces.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberRemoveRequest {
    private final Long memberId;
}
