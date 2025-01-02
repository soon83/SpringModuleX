package com.soon83.interfaces.main.configs;

import com.soon83.dtos.utils.Sortable;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.ArrayList;
import java.util.List;

@Component
public class SortableArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Sortable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            org.springframework.web.bind.support.WebDataBinderFactory binderFactory
    ) {
        String page = webRequest.getParameter("page");
        String size = webRequest.getParameter("size");
        String limit = webRequest.getParameter("limit");
        String[] sort = webRequest.getParameterValues("sort");

        if (limit != null && !limit.isBlank() && !isNumeric(limit)) {
            throw new NumberFormatException("limit 파라미터는 숫자만 가능합니다.");
        }

        int pageNumber = page != null ? Integer.parseInt(page) - 1 : 0;
        int pageSize = size != null ? Integer.parseInt(size) : 10;
        Integer _limit = limit != null && !limit.isBlank() ? Integer.parseInt(limit) : null;

        Sort sortOrder = Sort.unsorted();
        if (sort != null && sort.length > 0) {
            sortOrder = Sort.by(parseSortParameters(sort));
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOrder);
        return new Sortable(pageable, _limit);
    }

    private List<Sort.Order> parseSortParameters(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String sortParam : sort) {
            String[] parts = sortParam.split(",");
            if (parts.length > 1) {
                orders.add(new Sort.Order(Sort.Direction.fromString(parts[1]), parts[0]));
            } else {
                orders.add(Sort.Order.by(parts[0]));
            }
        }
        return orders;
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?");
    }
}
