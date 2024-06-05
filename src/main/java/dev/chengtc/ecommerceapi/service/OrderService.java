package dev.chengtc.ecommerceapi.service;

import dev.chengtc.ecommerceapi.model.dto.order.OrderPlaceRequest;
import dev.chengtc.ecommerceapi.model.dto.order.OrderPlaceResponse;

public interface OrderService {
    OrderPlaceResponse placeOrder(OrderPlaceRequest request);
}
