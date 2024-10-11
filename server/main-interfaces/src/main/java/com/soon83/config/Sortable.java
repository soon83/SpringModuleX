package com.soon83.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@ToString
@AllArgsConstructor
public class Sortable implements Pageable {
    private final Pageable pageable;
    private final Integer limit;

    @Override
    public int getPageNumber() {
        return pageable.getPageNumber();
    }

    @Override
    public int getPageSize() {
        return pageable.getPageSize();
    }

    @Override
    public long getOffset() {
        return pageable.getOffset();
    }

    @Override
    public Sort getSort() {
        return pageable.getSort();
    }

    @Override
    public Pageable next() {
        return pageable.next();
    }

    @Override
    public Pageable previousOrFirst() {
        return pageable.previousOrFirst();
    }

    @Override
    public Pageable first() {
        return pageable.first();
    }

    @Override
    public boolean hasPrevious() {
        return pageable.hasPrevious();
    }

    @Override
    public boolean isPaged() {
        return pageable.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return pageable.isUnpaged();
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return pageable.getSortOr(sort);
    }

    @Override
    public Optional<Pageable> toOptional() {
        return pageable.toOptional();
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return pageable.withPage(pageNumber);
    }

    public List<String> getSortList() {
        List<String> sortList = new ArrayList<>();
        if (getSort().isSorted()) {
            getSort().forEach(order -> {
                sortList.add(order.getProperty() + " " + order.getDirection().name().toLowerCase());
            });
        }
        return sortList;
    }

    public Integer getLimit() {
        return limit;
    }

    public int getOffsetInt() {
        return (getPageNumber() - 1) * getPageSize();
    }

    public String getType() {
        return "sort";
    }
}
