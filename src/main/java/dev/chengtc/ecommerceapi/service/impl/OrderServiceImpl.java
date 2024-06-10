package dev.chengtc.ecommerceapi.service.impl;

import dev.chengtc.ecommerceapi.mapper.OrderMapper;
import dev.chengtc.ecommerceapi.model.dto.order.OrderPlaceRequest;
import dev.chengtc.ecommerceapi.model.dto.order.OrderResponse;
import dev.chengtc.ecommerceapi.model.dto.order.OrderQueryParam;
import dev.chengtc.ecommerceapi.model.entity.Order;
import dev.chengtc.ecommerceapi.model.entity.OrderItem;
import dev.chengtc.ecommerceapi.model.entity.Product;
import dev.chengtc.ecommerceapi.repository.OrderItemRepository;
import dev.chengtc.ecommerceapi.repository.OrderRepository;
import dev.chengtc.ecommerceapi.service.OrderService;
import dev.chengtc.ecommerceapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final ProductService productService;

    @Transactional
    @Override
    public OrderResponse placeOrder(OrderPlaceRequest request) {
        Order order = OrderMapper.toEntity(request);
        order.setOrderDate(LocalDate.now());
        order.setOrderNumber(generateOrderNumber());
        // 為了取得 orderId
        order = orderRepository.saveAndFlush(order);

        List<OrderItem> orderItems = order.getOrderItems();
        // 計算總金額及扣除庫存數量
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            Product product = productService.getProductBySKU(orderItem.getSku());
            Integer remainingStock = product.getStock() - orderItem.getQuantity();
            productService.updateStock(remainingStock, product.getSku());

            BigDecimal amount = product.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
            totalAmount = totalAmount.add(amount);
            orderItem.setAmount(amount);
            orderItem.setOrderId(order.getId());
            orderItem.setSku(orderItem.getSku());
            orderItem.setName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setImageSrc(product.getImageSrc());
        }

        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);
        orderItems = orderItemRepository.saveAll(orderItems);
        order.setOrderItems(orderItems);

        return OrderMapper.toDTO(order);
    }

    @Override
    public Page<OrderResponse> getOrders(OrderQueryParam param) {
        Specification<Order> specification = Specification.where(null);

        Page<Order> orders = orderRepository.findAll(specification, param.getPageRequest());

        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getId());
            order.setOrderItems(orderItems);
        }

        return orders.map(OrderMapper::toDTO);
    }

    private String generateOrderNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        int randomNum = new Random().nextInt(99999);
        return "MA" + timestamp + String.format("%05d", randomNum);
    }

}
