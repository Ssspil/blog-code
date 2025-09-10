package com.jspp.service.product;

import com.jspp.domain.product.Product;
import com.jspp.domain.product.repository.ProductRepository;
import com.jspp.model.common.PageResponse;
import com.jspp.model.product.request.ProductListReq;
import com.jspp.model.product.response.ProductListRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public PageResponse<ProductListRes> getProductList(ProductListReq productListReq) {
        Pageable pageable = productListReq.toPageable();
        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductListRes> dtoPage = products.map(ProductListRes::from);  // Entity -> DTO로 변경
        return PageResponse.from(dtoPage);  // Page 객체 추출하여 응답
    }
}
