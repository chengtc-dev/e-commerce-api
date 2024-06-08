package dev.chengtc.ecommerceapi.model.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Schema(name = "OrderPlaceRequest")
public class OrderPlaceRequest {

    @NotBlank
    @Schema(example = "normal_member@e-commerce.org")
    private String buyerEmail;

    @NotEmpty
    private List<@Valid OrderItemRequest> orderItems;

}
