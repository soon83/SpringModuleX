package com.soon83.dtos.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class MemberBulkDeleteCommand {
    private final List<MemberDeleteCommand> commandList;
}
