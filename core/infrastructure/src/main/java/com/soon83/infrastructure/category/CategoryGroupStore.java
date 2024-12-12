package com.soon83.infrastructure.category;

import com.soon83.entities.category.CategoryGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryGroupStore {
    private final CategoryGroupRepository categoryGroupRepository;

    public CategoryGroup create(CategoryGroup categoryGroup) {
        return categoryGroupRepository.save(categoryGroup);
    }

    public void delete(CategoryGroup categoryGroup) {
        if (categoryGroup != null) {
            categoryGroupRepository.delete(categoryGroup);
        }
    }
}
