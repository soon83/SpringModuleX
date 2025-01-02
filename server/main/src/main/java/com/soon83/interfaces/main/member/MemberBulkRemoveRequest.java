package com.soon83.interfaces.main.member;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "회원 삭제요청목록은 필수값 입니다.")
    @Size(min = 1, message = "회원 삭제요청목록은 최소 1개 이상 입력해 주세요.")
    private List<@Valid MemberRemoveRequest> requestList;
}
