package com.jspp.model.product.response;

import com.jspp.domain.product.Product;
import com.jspp.domain.product.ProductStatus;
import lombok.Getter;

@Getter
public class ProductListRes {
    private Long id;
    private String productName;
    private String productCode;
    private String brandName;
    private String categoryName;
    private ProductStatus productStatus;

    // Builder 패턴
    public ProductListRes(Long id, String productName, String productCode,
                          String brandName, String categoryName,
                          ProductStatus productStatus) {
        this.id = id;
        this.productName = productName;
        this.productCode = productCode;
        this.brandName = brandName;
        this.categoryName = categoryName;
        this.productStatus = productStatus;
    }

    // 변환 메서드
    public static ProductListRes from(Product product) {
        return new ProductListRes(
                product.getId(),
                product.getProductName(),
                product.getProductCode(),
                product.getBrand() != null ? product.getBrand().getBrandName() : null,
                product.getCategory() != null ? product.getCategory().getCategoryName() : null,
                product.getStatus()
        );
    }
}