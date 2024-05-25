package dev.chengtc.ecommerceapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {

    @Schema(example = "400")
    private int status;

    @Schema(example = "Invalid argument")
    private String message;

    @Schema(example = "/api/[feat]")
    private String path;

}
