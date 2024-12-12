package com.soon83.infrastructure.category;

import com.soon83.entities.category.CategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long>, CategoryGroupRepositoryQuerydsl {
}
