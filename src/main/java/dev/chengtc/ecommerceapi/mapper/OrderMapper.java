package dev.chengtc.ecommerceapi.mapper;

import dev.chengtc.ecommerceapi.model.dto.order.OrderItemResponse;
import dev.chengtc.ecommerceapi.model.dto.order.OrderPlaceRequest;
import dev.chengtc.ecommerceapi.model.dto.order.OrderResponse;
import dev.chengtc.ecommerceapi.model.entity.Order;
import dev.chengtc.ecommerceapi.model.entity.OrderItem;

import java.util.List;

public class OrderMapper {

    public static Order toEntity(OrderPlaceRequest request) {
        List<OrderItem> orderItems = request.getOrderItems().stream()
                .map(OrderItemMapper::toEntity)
                .toList();

        Order order = new Order();
        order.setBuyerEmail(request.getBuyerEmail());
        order.setOrderItems(orderItems);
        return order;
    }

    public static OrderResponse toDTO(Order order) {
        List<OrderItemResponse> orderItemResponse = order.getOrderItems().stream()
                .map(OrderItemMapper::toDTO)
                .toList();

        OrderResponse response = new OrderResponse();
        response.setOrderDate(order.getOrderDate());
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderNumber(order.getOrderNumber());
        response.setOrderItems(orderItemResponse);
        return response;
    }

}
