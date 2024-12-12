package com.soon83.infrastructure.category;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soon83.dtos.category.CategoryGroupSearchCondition;
import com.soon83.dtos.utils.Sortable;
import com.soon83.entities.category.CategoryGroup;
import com.soon83.infrastructure.utils.CustomQuerydslRepositorySupport;
import com.soon83.utils.AssertUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.soon83.entities.category.QCategoryGroup.categoryGroup;
import static com.soon83.infrastructure.utils.CustomExpressionUtil.eq;

public class CategoryGroupRepositoryQuerydslImpl extends CustomQuerydslRepositorySupport implements CategoryGroupRepositoryQuerydsl {
    private final JPAQueryFactory queryFactory;

    public CategoryGroupRepositoryQuerydslImpl(JPAQueryFactory queryFactory) {
        super(CategoryGroup.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<CategoryGroup> getPageCategoryGroupList(CategoryGroupSearchCondition searchCond, Pageable pageable) {
        return applyPagination(pageable, queryFactory -> queryFactory
                        .selectFrom(categoryGroup)
                // TODO where
                /*.where()*/
        );
    }

    @Override
    public List<CategoryGroup> getAllCategoryGroupList(CategoryGroupSearchCondition searchCond, Sortable sortable) {
        return queryFactory
                .selectFrom(categoryGroup)
                // TODO where
                /*.where()*/
                .orderBy(getOrderSpecifiers(sortable))
                .limit(sortable.getLimit())
                .fetch();
    }

    @Override
    public Optional<CategoryGroup> getCategoryGroup(Long categoryGroupId) {
        AssertUtil.notNull(categoryGroupId, "categoryGroupId");

        return Optional.ofNullable(queryFactory
                .selectFrom(categoryGroup)
                .where(eq(categoryGroup.categoryGroupId, categoryGroupId))
                .fetchOne());
    }

    /**
     * Sortable 정렬 정보를 기반으로 OrderSpecifier 배열 생성
     */
    private OrderSpecifier<?>[] getOrderSpecifiers(Sortable sortable) {
        return sortable.getSort().stream()
                .map(order -> {
                    PathBuilder<CategoryGroup> entityPath = new PathBuilder<>(CategoryGroup.class, "categoryGroup");
                    ComparablePath<?> path = entityPath.getComparable(order.getProperty(), Comparable.class);
                    return new OrderSpecifier<>(
                            order.isAscending() ? com.querydsl.core.types.Order.ASC
                                    : com.querydsl.core.types.Order.DESC,
                            path
                    );
                })
                .toArray(OrderSpecifier[]::new);
    }
}
