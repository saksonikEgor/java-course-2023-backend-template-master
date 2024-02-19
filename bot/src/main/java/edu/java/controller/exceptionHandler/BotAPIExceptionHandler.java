package edu.java.controller.exceptionHandler;

import edu.java.dto.response.APIErrorResponse;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BotAPIExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIErrorResponse> messageNotReadable(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(new APIErrorResponse(
            "Invalid query parameters",
            HttpStatus.BAD_REQUEST.toString(),
            exception.getClass().getSimpleName(),
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new)
        ));
    }
}
