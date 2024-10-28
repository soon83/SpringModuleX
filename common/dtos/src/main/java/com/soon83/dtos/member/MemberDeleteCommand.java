package com.soon83.dtos.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class MemberDeleteCommand {
    private final Long memberId;
}
