package com.jspp.model.common;

import java.util.List;
import org.springframework.data.domain.Page;


public record PageResponse<T> (
    List<T> content,
    int page,
    int size,
    long totalElements,
    int totalPages,
    boolean isLast,
    boolean isFirst
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }
}