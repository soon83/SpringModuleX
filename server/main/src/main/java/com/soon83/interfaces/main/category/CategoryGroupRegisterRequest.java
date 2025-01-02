package com.soon83.interfaces.main.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryGroupRegisterRequest {
    @NotNull(message = "카테고리 그룹 순서는 필수값 입니다.")
    private Integer ordering;

    @NotBlank(message = "카테고리 그룹 이름은 필수값 입니다.")
    @Size(max = 31, message = "카테고리 그룹 이름의 최대 길이는 31 입니다.")
    private String name;

    @NotBlank(message = "카테고리 그룹 설명은 필수값 입니다.")
    @Size(max = 63, message = "카테고리 그룹 설명의 최대 길이는 63 입니다.")
    private String description;

    @NotBlank(message = "카테고리 그룹 아이콘은 필수값 입니다.")
    @Size(max = 511, message = "카테고리 그룹 아이콘의 최대 길이는 511 입니다.")
    private String icon;
}
