package com.jspp.domain.product.dto;


public record ProductSearchFilter (
        String keyword,
        String searchType
) {
    public static ProductSearchFilter create(String keyword, String searchType) {
        return new ProductSearchFilter(keyword, searchType);
    }
}
