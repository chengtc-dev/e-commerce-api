package dev.chengtc.ecommerceapi.service;

import dev.chengtc.ecommerceapi.model.dto.order.OrderPlaceRequest;
import dev.chengtc.ecommerceapi.model.dto.order.OrderResponse;
import dev.chengtc.ecommerceapi.model.dto.order.OrderQueryParam;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderResponse placeOrder(OrderPlaceRequest request);

    Page<OrderResponse> getOrders(OrderQueryParam param);
}
