package dev.chengtc.ecommerceapi.exception.product;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String SKU) {
        super("Product with SKU " + SKU + " not found");
    }
}
