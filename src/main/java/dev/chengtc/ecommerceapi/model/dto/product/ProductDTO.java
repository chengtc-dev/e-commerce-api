package dev.chengtc.ecommerceapi.model.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

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

    @NotNull
    @URL
    @Schema(example = "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/mba13-midnight-select-202402?wid=904&hei=840&fmt=jpeg&qlt=90&.v=1708367688034")
    private String imageSrc;

}
