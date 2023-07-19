package com.gbsoft.util;

import com.gbsoft.dto.ErrorResponse;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(IllegalStateException.class)
    @ApiResponse(responseCode = "409", description ="중복된 회원 존재")
    public ResponseEntity<ErrorResponse> illegalStateException(Exception ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ApiResponse(responseCode = "400", description ="잘못된 파라미터")
    public ResponseEntity<ErrorResponse> illegalArgumentException(Exception ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(JwtException.class)
    @ApiResponse(responseCode = "403", description ="잘못된 JWT")
    public ResponseEntity<ErrorResponse> jwtException(Exception ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description ="Internal Server Error")
    public ResponseEntity<ErrorResponse> globalException(Exception ex, WebRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
