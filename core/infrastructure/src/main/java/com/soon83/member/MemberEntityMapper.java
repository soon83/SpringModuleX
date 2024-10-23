package com.soon83.member;

import com.soon83.tables.records.MemberRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberEntityMapper {
    Member toEntity(MemberRecord memberRecord);
}