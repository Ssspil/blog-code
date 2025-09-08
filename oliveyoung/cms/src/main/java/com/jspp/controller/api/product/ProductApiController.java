package com.jspp.controller.api.product;

import com.jspp.model.common.PageResponse;
import com.jspp.model.product.request.ProductListReq;
import com.jspp.model.product.response.ProductListRes;
import com.jspp.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductApiController {

    private final ProductService productService;

    @GetMapping
    public PageResponse<ProductListRes> getProducts(ProductListReq productListReq) {
        return productService.getProductList(productListReq);
    }
}