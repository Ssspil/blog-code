package com.jspp.service.product;

import com.jspp.domain.product.repository.ProductRepository;
import com.jspp.model.product.response.ProductListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductListRes getList() {
        return null;
    }
}
