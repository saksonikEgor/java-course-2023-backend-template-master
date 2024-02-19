package edu.java.controller.exceptionHandler;

import edu.java.dto.response.APIErrorResponse;
import edu.java.exception.ChatIsNotExistException;
import edu.java.exception.LinkIsAlreadyTrackedException;
import edu.java.exception.LinkIsNotTrackingException;
import edu.java.exception.UserIsAlreadyRegisteredException;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ScrapperAPIExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIErrorResponse> messageNotReadable(HttpMessageNotReadableException exception) {
        return handleException(exception, HttpStatus.BAD_REQUEST, "Invalid query parameters");
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<APIErrorResponse> notFound(HttpClientErrorException.NotFound exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Resource is not found");
    }

    @ExceptionHandler(ChatIsNotExistException.class)
    public ResponseEntity<APIErrorResponse> chatIsNotExist(ChatIsNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Chat is not exist");
    }

    @ExceptionHandler(LinkIsAlreadyTrackedException.class)
    public ResponseEntity<APIErrorResponse> linkIsAlreadyTracked(LinkIsAlreadyTrackedException exception) {
        return handleException(exception, HttpStatus.NOT_ACCEPTABLE, "Link is already tracked");
    }

    @ExceptionHandler(LinkIsNotTrackingException.class)
    public ResponseEntity<APIErrorResponse> linkIsNotTracking(LinkIsNotTrackingException exception) {
        return handleException(exception, HttpStatus.NOT_ACCEPTABLE, "Link is not tracking");
    }

    @ExceptionHandler(UserIsAlreadyRegisteredException.class)
    public ResponseEntity<APIErrorResponse> userIsAlreadyRegistered(UserIsAlreadyRegisteredException exception) {
        return handleException(exception, HttpStatus.NOT_ACCEPTABLE, "User is already registered");
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
            exception.getMessage(),
            Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new)
        ));
    }
}