package edu.java.controller.exceptionHandler;

import edu.java.dto.response.APIErrorResponse;
import edu.java.exception.chat.ChatIsAlreadyRegisteredException;
import edu.java.exception.chat.ChatIsNotExistException;
import edu.java.exception.link.LinkIsAlreadyTrackedException;
import edu.java.exception.link.LinkIsNotTrackingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ScrapperAPIExceptionHandler {
    @ExceptionHandler({HttpMessageNotReadableException.class, ClassCastException.class})
    public ResponseEntity<APIErrorResponse> messageNotReadable(RuntimeException exception) {
        return handleException(exception, HttpStatus.BAD_REQUEST, "Некорректные праметры запроса");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIErrorResponse> messageNotReadable(MethodArgumentNotValidException exception) {
        return handleException(exception, HttpStatus.BAD_REQUEST, "Некорректный формат ссылки");
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<APIErrorResponse> notFound(HttpClientErrorException.NotFound exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Не удалось найти ресурс");
    }

    @ExceptionHandler(ChatIsNotExistException.class)
    public ResponseEntity<APIErrorResponse> chatIsNotExist(ChatIsNotExistException exception) {
        return handleException(exception, HttpStatus.NOT_FOUND, "Пользователь не зарегестрирован");
    }

    @ExceptionHandler(LinkIsAlreadyTrackedException.class)
    public ResponseEntity<APIErrorResponse> linkIsAlreadyTracked(LinkIsAlreadyTrackedException exception) {
        return handleException(exception, HttpStatus.NOT_ACCEPTABLE, "Ссылка уже была добавлена ранее");
    }

    @ExceptionHandler(LinkIsNotTrackingException.class)
    public ResponseEntity<APIErrorResponse> linkIsNotTracking(LinkIsNotTrackingException exception) {
        return handleException(exception, HttpStatus.NOT_ACCEPTABLE, "Ссылка не отслеживается");
    }

    @ExceptionHandler(ChatIsAlreadyRegisteredException.class)
    public ResponseEntity<APIErrorResponse> userIsAlreadyRegistered(ChatIsAlreadyRegisteredException exception) {
        return handleException(exception, HttpStatus.NOT_ACCEPTABLE, "Пользователь уже зарегестрирован");
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<APIErrorResponse> ds(TransactionException exception) {
        return handleException(exception, HttpStatus.SERVICE_UNAVAILABLE, "Сервис временно недоступен");
    }

    private @NotNull ResponseEntity<APIErrorResponse> handleException(
        @NotNull Exception exception,
        @NotNull HttpStatus status,
        String description
    ) {
        return ResponseEntity.status(status).body(new APIErrorResponse(
            description,
            String.valueOf(status.value()),
            exception.getClass().getSimpleName(),
            exception.getMessage()
        ));
    }
}
