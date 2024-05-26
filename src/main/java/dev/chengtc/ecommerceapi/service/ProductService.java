package dev.chengtc.ecommerceapi.service;

import dev.chengtc.ecommerceapi.model.dto.product.ProductDTO;
import dev.chengtc.ecommerceapi.model.dto.product.ProductQueryParam;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    Page<ProductDTO> getProducts(ProductQueryParam param);
}
