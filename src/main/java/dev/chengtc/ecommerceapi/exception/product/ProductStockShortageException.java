package dev.chengtc.ecommerceapi.exception.product;

public class ProductStockShortageException extends RuntimeException {

    public ProductStockShortageException(String name, Integer remainingStock) {
        super("Product stock shortage with name " + name + ", remaining stock " + remainingStock);
    }
}
