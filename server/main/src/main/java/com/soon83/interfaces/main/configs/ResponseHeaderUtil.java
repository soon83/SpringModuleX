package com.soon83.interfaces.main.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soon83.dtos.utils.PageableResponseHeader;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;

public class ResponseHeaderUtil {
    @SneakyThrows
    public static <T> void setPageableResponseHeader(
            HttpServletResponse response,
            ObjectMapper objectMapper,
            Page<T> page
    ) {
        response.setHeader("pageable", objectMapper.writeValueAsString(new PageableResponseHeader(
                page.getTotalPages(),
                page.getTotalElements(),
                page.isLast(),
                page.getSize(),
                page.getNumber() + 1,
                page.getSort(),
                page.getNumberOfElements(),
                page.isFirst(),
                page.isEmpty()
        )));
    }
}