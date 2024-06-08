package dev.chengtc.ecommerceapi.model.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter @Setter
@Schema(name = "OrderQueryParam")
public class OrderQueryParam {

    @Email
    @NotBlank
    @Schema(example = "normal_member@e-commerce.org")
    private String email;

    @Pattern(regexp = "createdAt|totalAmount")
    @Schema(example = "createdAt")
    private String orderBy = "createdAt";

    @Pattern(regexp = "desc|asc")
    @Schema(example = "desc")
    private String sortBy = "desc";

    @Min(0)
    @Schema(example = "0")
    private Integer pageNumber = 0;

    @Min(0) @Max(1000)
    @Schema(example = "10")
    private Integer pageSize = 10;

    @JsonIgnore
    private Pageable pageRequest;

    public Pageable getPageRequest() {
        Sort.Direction direction = Sort.Direction.valueOf(this.sortBy.toUpperCase());
        Sort sort = Sort.by(direction, this.orderBy);
        return PageRequest.of(pageNumber, pageSize, sort);
    }

}
