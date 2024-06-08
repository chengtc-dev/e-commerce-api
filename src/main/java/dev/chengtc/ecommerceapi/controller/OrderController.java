package dev.chengtc.ecommerceapi.controller;

import dev.chengtc.ecommerceapi.model.dto.ErrorResponse;
import dev.chengtc.ecommerceapi.model.dto.order.OrderQueryParam;
import dev.chengtc.ecommerceapi.model.dto.order.OrderPlaceRequest;
import dev.chengtc.ecommerceapi.model.dto.order.OrderResponse;
import dev.chengtc.ecommerceapi.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order Service", description = "REST APIs for PLACE and QUERY orders")
@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
@Validated
public class OrderController {

    private OrderService orderService;

    @Operation(summary = "Place order", description = "Place an order")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order placed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid argument",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody @Valid OrderPlaceRequest request) {
        OrderResponse response = orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get Orders", description = "Get orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get orders successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid argument",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getOrders(@Valid OrderQueryParam param) {
        Page<OrderResponse> orders = orderService.getOrders(param);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
