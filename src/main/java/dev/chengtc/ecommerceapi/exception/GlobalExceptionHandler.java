package dev.chengtc.ecommerceapi.exception;

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
        log.warn(Arrays.toString(arguments));

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(Arrays.toString(arguments));
        errorResponse.setPath(((ServletWebRequest) request).getRequest().getRequestURI());
        errorResponse.setStatus(status.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ProductExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductExistsException(ProductExistsException e, ServletWebRequest request) {
        log.warn(e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getRequest().getRequestURI());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
