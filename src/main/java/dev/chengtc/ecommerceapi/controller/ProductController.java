package dev.chengtc.ecommerceapi.controller;

import dev.chengtc.ecommerceapi.model.dto.ErrorResponse;
import dev.chengtc.ecommerceapi.model.dto.product.ProductDTO;
import dev.chengtc.ecommerceapi.model.dto.product.ProductQueryParam;
import dev.chengtc.ecommerceapi.service.ProductService;
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

@Tag(name = "Product Service", description = "REST APIs for CREATE, READ, UPDATE and DELETE products")
@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create Product", description = "Create a new product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid argument",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO productDTO) {
        productDTO = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    @Operation(summary = "Get Products", description = "Get products")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get products successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid argument",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getProducts(@Valid ProductQueryParam param) {
        Page<ProductDTO> productDTOList = productService.getProducts(param);
        return ResponseEntity.status(HttpStatus.OK).body(productDTOList);
    }

}
