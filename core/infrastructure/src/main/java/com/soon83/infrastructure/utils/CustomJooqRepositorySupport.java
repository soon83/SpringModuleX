package com.soon83.infrastructure.utils;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectLimitStep;
import org.jooq.SelectOrderByStep;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.function.Function;

public abstract class CustomJooqRepositorySupport {
	private final DSLContext dslContext;

	public CustomJooqRepositorySupport(DSLContext dslContext) {
		this.dslContext = dslContext;
	}

	protected DSLContext getDslContext() {
		return dslContext;
	}

	protected <T> Page<T> applyPagination(Pageable pageable,
		Function<DSLContext, SelectLimitStep<Record>> contentQuery) {
		// 쿼리 실행하여 결과 가져오기
		List<T> content = contentQuery.apply(dslContext)
			.limit(pageable.getPageSize())
			.offset((int)pageable.getOffset())
			.fetchInto((Class<T>)Object.class); // 원하는 클래스로 변환

		// 총 레코드 수를 계산하는 쿼리 (Optional)
		long total = dslContext.fetchCount(contentQuery.apply(dslContext));

		// 페이징된 결과 반환
		return PageableExecutionUtils.getPage(content, pageable, () -> total);
	}

	// 페이징 처리 외에 정렬 처리 기능 추가
	protected <T> SelectOrderByStep<Record> applySorting(SelectOrderByStep<Record> query, Sort sort) {
		if (sort != null) {
			for (Sort.Order order : sort) {
				String property = order.getProperty();
				boolean ascending = order.isAscending();

				// 예시: 필드를 jOOQ의 테이블 필드에 맞게 변환 (필요시 커스터마이징)
				query.orderBy(ascending ? DSL.field(property).asc() : DSL.field(property).desc());
			}
		}
		return query;
	}
}
