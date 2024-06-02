package dev.chengtc.ecommerceapi.controller;

import dev.chengtc.ecommerceapi.model.dto.ErrorResponse;
import dev.chengtc.ecommerceapi.model.dto.member.MemberDTO;
import dev.chengtc.ecommerceapi.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member Service", description = "REST APIs for REGISTER, LOGIN members")
@RestController
@RequestMapping("/api/members")
@AllArgsConstructor
@Validated
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "Register Member", description = "Register a new member")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Member registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid argument",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<MemberDTO> register(@RequestBody @Valid MemberDTO memberDTO) {
        memberDTO = memberService.register(memberDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberDTO);
    }
}
