package com.jspp.service.product;

import com.jspp.domain.product.dto.ProductSearchFilter;
import com.jspp.domain.product.entity.Product;
import com.jspp.domain.product.repository.ProductQueryRepository;
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

    private final ProductQueryRepository productQueryRepository;

    public PageResponse<ProductListRes> getProductList(ProductListReq productListReq) {
        Pageable pageable = productListReq.toPageable();
        ProductSearchFilter searchFilter = ProductSearchFilter.create(productListReq.getSearchKeyword(), productListReq.getSearchType());
        Page<Product> products = productQueryRepository.searchProduct(pageable, searchFilter);
        Page<ProductListRes> dtoPage = products.map(ProductListRes::from);  // Entity -> DTO로 변경
        return PageResponse.from(dtoPage);  // Page 객체 추출하여 응답
    }
}
