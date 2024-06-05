package dev.chengtc.ecommerceapi.model.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(name = "OrderItem")
public class OrderItemRequest {

    @NotNull
    @Positive
    @Schema(example = "10")
    private Integer quantity;

    @NotBlank
    @Schema(example = "MB-A-13-M3-MID", description = "SKU（庫存單位）是一種唯一的產品編碼系統，用於識別、追蹤和管理庫存及銷售數據")
    private String sku;

}
