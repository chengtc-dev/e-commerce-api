package dev.chengtc.ecommerceapi.model.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@Schema(name = "OrderItemResponse")
public class OrderItemResponse {

    @Schema(example = "100.00")
    private BigDecimal amount;

    @Schema(example = "10")
    private Integer quantity;

    @Schema(example = "MB-A-13-M3-MID", description = "SKU（庫存單位）是一種唯一的產品編碼系統，用於識別、追蹤和管理庫存及銷售數據")
    private String sku;

}
