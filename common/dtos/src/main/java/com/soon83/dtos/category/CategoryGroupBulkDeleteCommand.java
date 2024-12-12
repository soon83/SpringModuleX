package com.soon83.dtos.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class CategoryGroupBulkDeleteCommand {
    private final List<CategoryGroupDeleteCommand> commandList;
}
