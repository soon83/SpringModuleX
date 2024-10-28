package com.soon83.interfaces.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class MemberBulkEditRequest {
    private final List<MemberEditRequest> requestList;
}
