package com.soon83.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter
@ToString
@AllArgsConstructor
public class PageableResponseHeader {
    private final int totalPages;
    private final long totalElements;
    private final boolean last;
    private final int size;
    private final int page;
    private final Sort sort;
    private final int numberOfElements;
    private final boolean first;
    private final boolean empty;
}
