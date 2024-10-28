package com.soon83.interfaces.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberBulkRemoveRequest {
    private List<MemberRemoveRequest> requestList;
}
