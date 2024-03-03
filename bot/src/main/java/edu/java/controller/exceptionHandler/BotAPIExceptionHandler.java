package edu.java.controller.exceptionHandler;

import edu.java.dto.response.APIErrorResponse;
import edu.java.exception.LinkIsNotExistException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BotAPIExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIErrorResponse> messageNotReadable(HttpMessageNotReadableException exception) {
        return handleException(exception, HttpStatus.BAD_REQUEST, "Invalid query parameters");
    }

    @ExceptionHandler(LinkIsNotExistException.class)
    public ResponseEntity<APIErrorResponse> linkIsNotExist(LinkIsNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Link is not exist");
    }

    private @NotNull ResponseEntity<APIErrorResponse> handleException(
        @NotNull Exception exception,
        @NotNull HttpStatus status,
        String description
    ) {
        return ResponseEntity.badRequest().body(new APIErrorResponse(
            description,
            String.valueOf(status.value()),
            exception.getClass().getSimpleName(),
            exception.getMessage()
        ));
    }
}
