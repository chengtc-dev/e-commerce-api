package dev.chengtc.ecommerceapi.model.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Schema(name = "OrderResponse")
public class OrderResponse {

    @Schema(example = "1000.00")
    private BigDecimal totalAmount;

    @Schema(example = "2007-12-03")
    private LocalDate orderDate;

    @Schema(example = "MA2024060523220331763")
    private String orderNumber;

    private List<OrderItemResponse> orderItems;

}
