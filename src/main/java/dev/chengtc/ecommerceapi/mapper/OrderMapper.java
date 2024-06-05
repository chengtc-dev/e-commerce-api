package dev.chengtc.ecommerceapi.mapper;

import dev.chengtc.ecommerceapi.model.dto.order.OrderItemResponse;
import dev.chengtc.ecommerceapi.model.dto.order.OrderPlaceRequest;
import dev.chengtc.ecommerceapi.model.dto.order.OrderPlaceResponse;
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

    public static OrderPlaceResponse toDTO(Order order, List<OrderItem> orderItems) {
        List<OrderItemResponse> orderItemResponse = orderItems.stream()
                .map(OrderItemMapper::toDTO)
                .toList();

        OrderPlaceResponse response = new OrderPlaceResponse();
        response.setOrderDate(order.getOrderDate());
        response.setBuyerEmail(order.getBuyerEmail());
        response.setTotalAmount(order.getTotalAmount());
        response.setOrderNumber(order.getOrderNumber());
        response.setOrderItems(orderItemResponse);
        return response;
    }

}
