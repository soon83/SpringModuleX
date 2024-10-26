package com.soon83.dtos.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class MemberBulkCreateCommand {
    private List<MemberCreateCommand> commandList;
}
