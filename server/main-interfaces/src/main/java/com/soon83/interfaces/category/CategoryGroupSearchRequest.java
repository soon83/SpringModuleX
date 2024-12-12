package com.soon83.interfaces.category;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryGroupSearchRequest {
    private Long categoryGroupId;

    private Integer ordering;

    @Size(max = 31, message = "카테고리 그룹 이름의 최대 길이는 31 입니다.")
    private String name;

    @Size(max = 63, message = "카테고리 그룹 설명의 최대 길이는 63 입니다.")
    private String description;

    @Size(max = 511, message = "카테고리 그룹 아이콘의 최대 길이는 511 입니다.")
    private String icon;
}
