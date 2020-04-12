package br.com.kafka.producer.resource.handler;

import br.com.kafka.producer.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static br.com.kafka.producer.resource.handler.ErrorResponse.ApiError;
import static br.com.kafka.producer.resource.handler.ErrorResponse.of;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({AppException.class})
    public ResponseEntity<ErrorResponse> handleAppException(AppException exception) {
        final HttpStatus httpStatus = exception.getHttpStatus() != null ? exception.getHttpStatus() : HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = of(httpStatus, toApiError(exception.getField(), exception.getMessage()));
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private ApiError toApiError(String field, String message) {
        return ApiError.builder().field(field).message(message).build();
    }


}
