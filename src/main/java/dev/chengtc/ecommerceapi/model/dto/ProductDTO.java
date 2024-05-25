package dev.chengtc.ecommerceapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@Schema(name = "Product")
public class ProductDTO {

    @NotBlank
    @Schema(example = "13-inch MacBook Air with M3 chip - Midnight")
    private String name;

    @NotBlank
    @Schema(example = "MB-A-13-M3-MID", description = "SKU（庫存單位）是一種唯一的產品編碼系統，用於識別、追蹤和管理庫存及銷售數據")
    private String sku;

    @Schema(example = "MacBook Air sails through work and play — and the M3 chip brings even greater capabilities to the world’s most popular laptop. With up to 18 hours of battery life, you can take the super portable MacBook Air anywhere and blaze through whatever you’re into.")
    private String description;

    @NotNull
    @Positive
    @Schema(example = "1299.00")
    private BigDecimal price;

    @NotNull
    @Positive
    @Schema(example = "10000000")
    private Integer stock;

}
