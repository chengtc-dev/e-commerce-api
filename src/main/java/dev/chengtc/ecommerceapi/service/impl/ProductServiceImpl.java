package dev.chengtc.ecommerceapi.service.impl;

import dev.chengtc.ecommerceapi.exception.ProductExistsException;
import dev.chengtc.ecommerceapi.mapper.ProductMapper;
import dev.chengtc.ecommerceapi.model.dto.product.ProductDTO;
import dev.chengtc.ecommerceapi.model.dto.product.ProductQueryParam;
import dev.chengtc.ecommerceapi.model.entity.Product;
import dev.chengtc.ecommerceapi.repository.ProductRepository;
import dev.chengtc.ecommerceapi.service.ProductService;
import dev.chengtc.ecommerceapi.specification.ProductSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = ProductMapper.toEntity(productDTO);

        if (productRepository.existsBySku(product.getSku()))
            throw new ProductExistsException(product.getSku());

        product = productRepository.save(product);
        return ProductMapper.toDTO(product);
    }

    @Override
    public Page<ProductDTO> getProducts(ProductQueryParam param) {
        Specification<Product> specification = Specification.where(null);

        if (!param.getKeyword().isBlank()) {
            specification = specification.and(ProductSpecification.containKeyword(param.getKeyword()));
        }

        Page<Product> products = productRepository.findAll(specification, param.getPageRequest());
        return products.map(ProductMapper::toDTO);
    }
}
