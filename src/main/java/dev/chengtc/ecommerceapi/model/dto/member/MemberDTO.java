package dev.chengtc.ecommerceapi.model.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(name = "Member")
public class MemberDTO {

    @NotBlank
    @Schema(example = "Ian Cheng")
    private String name;

    @Email
    @NotBlank
    @Schema(example = "spring-boot@e-commerce.org")
    private String email;

    @NotBlank
    @Schema(example = "P@ssw0rd")
    private String password;

    @NotBlank
    @Schema(example = "0912345678")
    private String phone;
}
