package com.soon83.dtos.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DefaultIdSortAspect {
    @Before("@annotation(defaultIdSort)")
    public void applyDefaultIdSort(JoinPoint joinPoint, DefaultIdSort defaultIdSort) {
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof Pageable pageable) {
                if (!pageable.getSort().isSorted()) {
                    // 엔티티 클래스의 @Id 필드를 찾아서 기본 정렬 적용
                    String idFieldName = EntityIdCacheInitializer.getCachedIdFieldName(defaultIdSort.entityClass());
                    Pageable sortedPageable = PageRequest.of(
                            pageable.getPageNumber(),
                            pageable.getPageSize(),
                            Sort.by(Sort.Direction.DESC, idFieldName)
                    );
                    args[i] = sortedPageable;
                }
            }
        }
    }
}
