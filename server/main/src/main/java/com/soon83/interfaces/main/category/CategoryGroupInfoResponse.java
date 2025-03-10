package com.soon83.interfaces.main.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CategoryGroupInfoResponse {
    private Long categoryGroupId;

    private Integer ordering;

    private String name;

    private String description;
}
