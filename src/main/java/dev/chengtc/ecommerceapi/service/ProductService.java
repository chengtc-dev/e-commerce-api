package dev.chengtc.ecommerceapi.service;

import dev.chengtc.ecommerceapi.model.dto.product.ProductDTO;
import dev.chengtc.ecommerceapi.model.dto.product.ProductQueryParam;
import dev.chengtc.ecommerceapi.model.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    Page<ProductDTO> getProducts(ProductQueryParam param);

    ProductDTO updateProduct(ProductDTO productDTO);

    void deleteProduct(String sku);

    Product getProductBySKU(String sku);

    void updateStock(Integer remainingStock, String sku);
}
