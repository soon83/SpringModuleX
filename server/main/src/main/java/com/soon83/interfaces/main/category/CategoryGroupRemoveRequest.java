package com.soon83.interfaces.main.category;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryGroupRemoveRequest {
    @NotNull(message = "카테고리 그룹 아이디는 필수값 입니다.")
    private Long categoryGroupId;
}
