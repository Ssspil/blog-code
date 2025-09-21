package com.jspp.domain.product.repository.query;

import static com.jspp.domain.product.entity.QProduct.product;

import com.jspp.domain.product.dto.ProductSearchFilter;
import com.jspp.domain.product.entity.Product;
import com.jspp.domain.product.repository.ProductQueryRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> searchProduct(Pageable pageable, ProductSearchFilter productSearchFilter) {

        List<Product> results = queryFactory
                .selectFrom(product)
                .where(searchCondition(
                        productSearchFilter.keyword(),
                        productSearchFilter.searchType()
                ))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(product.id.desc())
                .fetch();

        long totalCount = Optional.ofNullable(
                queryFactory
                        .select(product.count())
                        .from(product)
                        .where(searchCondition(
                                productSearchFilter.keyword(),
                                productSearchFilter.searchType()
                        ))
                        .fetchOne()
                ).orElse(0L);

        return new PageImpl<>(results, pageable, totalCount);
    }

    private BooleanExpression searchCondition(String keyword, String searchType) {
        if (keyword == null || keyword.isBlank()) {
            return null; // 조건 없으면 전체 조회
        }

        return switch (searchType) {
            case "productName" -> product.productName.containsIgnoreCase(keyword);
            case "category" -> product.category.categoryName.containsIgnoreCase(keyword);
            case "brand" -> product.brand.brandName.containsIgnoreCase(keyword);
            case "all" -> product.productName.containsIgnoreCase(keyword)
                    .or(product.category.categoryName.containsIgnoreCase(keyword))
                    .or(product.brand.brandName.containsIgnoreCase(keyword));
            default -> null;
        };
    }
}
