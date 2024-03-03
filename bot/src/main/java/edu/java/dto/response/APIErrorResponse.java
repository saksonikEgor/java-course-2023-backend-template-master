package edu.java.dto.response;

public record APIErrorResponse(
    String description,
    String code,
    String exceptionName,
    String exceptionMessage
) {
}
