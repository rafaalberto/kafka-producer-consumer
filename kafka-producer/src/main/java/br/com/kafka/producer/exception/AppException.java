package br.com.kafka.producer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class AppException extends Exception {
    private HttpStatus httpStatus;
    private final String field;
    private final String message;

    public AppException(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
