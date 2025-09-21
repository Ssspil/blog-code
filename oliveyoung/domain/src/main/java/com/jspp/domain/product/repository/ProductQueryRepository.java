package com.jspp.domain.product.repository;

import com.jspp.domain.product.dto.ProductSearchFilter;
import com.jspp.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductQueryRepository {
    Page<Product> searchProduct(Pageable pageable, ProductSearchFilter productSearchFilter);

}
