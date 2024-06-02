package dev.chengtc.ecommerceapi.exception;

import dev.chengtc.ecommerceapi.exception.member.MemberExistsException;
import dev.chengtc.ecommerceapi.exception.member.RoleNotFoundException;
import dev.chengtc.ecommerceapi.exception.product.ProductExistsException;
import dev.chengtc.ecommerceapi.exception.product.ProductNotFoundException;
import dev.chengtc.ecommerceapi.model.dto.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Objects;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Object[] arguments = Objects.requireNonNull(e.getDetailMessageArguments());
        arguments = Arrays.stream(arguments).skip(1).toArray();
        log.warn("Validation failed: {}", Arrays.toString(arguments));
        ErrorResponse errorResponse = createErrorResponse(Arrays.toString(arguments), status.value(), ((ServletWebRequest) request).getRequest().getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({ProductExistsException.class, ProductNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleProductException(Exception e, ServletWebRequest request) {
        return handleCustomException(e, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MemberExistsException.class, RoleNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleMemberException(Exception e, ServletWebRequest request) {
        return handleCustomException(e, request, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> handleCustomException(Exception e, ServletWebRequest request, HttpStatus status) {
        log.warn("Exception: {}", e.getMessage());
        ErrorResponse errorResponse = createErrorResponse(e.getMessage(), status.value(), request.getRequest().getRequestURI());
        return ResponseEntity.status(status).body(errorResponse);
    }

    private ErrorResponse createErrorResponse(String message, int status, String path) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(message);
        errorResponse.setStatus(status);
        errorResponse.setPath(path);
        return errorResponse;
    }
}
