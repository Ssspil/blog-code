package com.jspp.controller.web.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    // 상품 목록
    @GetMapping
    public String products() {
        return "/page/product/list";
    }

}
