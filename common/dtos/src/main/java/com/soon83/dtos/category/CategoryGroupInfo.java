package com.soon83.dtos.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CategoryGroupInfo {
    private final Long categoryGroupId;
    private final Integer ordering;
    private final String name;
    private final String description;
    private final String icon;
}
