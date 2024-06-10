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

    @Schema(example = "13-inch MacBook Air with M3 chip - Midnight")
    private String name;

    @Schema(example = "1299.00")
    private BigDecimal price;

    @Schema(example = "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/mba13-midnight-select-202402?wid=904&hei=840&fmt=jpeg&qlt=90&.v=1708367688034")
    private String imageSrc;

}
