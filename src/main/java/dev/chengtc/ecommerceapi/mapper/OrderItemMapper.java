package dev.chengtc.ecommerceapi.mapper;

import dev.chengtc.ecommerceapi.model.dto.order.OrderItemRequest;
import dev.chengtc.ecommerceapi.model.dto.order.OrderItemResponse;
import dev.chengtc.ecommerceapi.model.entity.OrderItem;

public class OrderItemMapper {

    public static OrderItem toEntity(OrderItemRequest orderItemRequest) {
        OrderItem orderItem = new OrderItem();
        orderItem.setSku(orderItemRequest.getSku());
        orderItem.setQuantity(orderItemRequest.getQuantity());
        return orderItem;
    }

    public static OrderItemResponse toDTO(OrderItem orderItem) {
        OrderItemResponse response = new OrderItemResponse();
        response.setAmount(orderItem.getAmount());
        response.setSku(orderItem.getSku());
        response.setQuantity(orderItem.getQuantity());
        return response;
    }

}
