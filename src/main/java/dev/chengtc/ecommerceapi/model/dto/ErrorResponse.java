package dev.chengtc.ecommerceapi.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {

    private int status;

    private String message;

    private String path;

}
