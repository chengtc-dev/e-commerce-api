package dev.chengtc.ecommerceapi.specification;

import dev.chengtc.ecommerceapi.model.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> containKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            String pattern = "%" + keyword + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("name"), pattern),
                    criteriaBuilder.like(root.get("description"), pattern)
            );
        };
    }

}
