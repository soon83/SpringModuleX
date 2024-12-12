package com.soon83.domain.category;

import com.soon83.dtos.category.CategoryGroupInfo;
import com.soon83.entities.category.CategoryGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryGroupDomainMapper {
    CategoryGroupInfo toCategoryGroupInfo(CategoryGroup categoryGroup);
}
