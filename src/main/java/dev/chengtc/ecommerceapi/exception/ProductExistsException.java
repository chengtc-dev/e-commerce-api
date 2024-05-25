package dev.chengtc.ecommerceapi.exception;

public class ProductExistsException extends RuntimeException {

    public ProductExistsException(String SKU) {
        super("Product with SKU " + SKU + " already exists");
    }
}
