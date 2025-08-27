package com.jspp.model.product.response;

import com.jspp.domain.product.ProductStatus;
import lombok.Getter;

@Getter
public class ProductListRes {
    private Long id;
    private String productName;
    private String productCode;
    private Integer totalStock;
    private String brandName;
    private String categoryName;
    private ProductStatus productStatus;

}
