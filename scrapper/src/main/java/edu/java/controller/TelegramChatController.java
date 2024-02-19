package edu.java.controller;

import edu.java.dto.response.APIErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/tg-chat")
public class TelegramChatController {
    @PostMapping("/{id}")
    @Operation(summary = "Зарегестрировать чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован",
                     content = @Content),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "406", description = "Пользователь уже зарегестрирован",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json"))
    })
    public ResponseEntity<?> registerChat(@PathVariable("id") long chatId) {
        log.info("Registering for charId: " + chatId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат успешно удалён",
                     content = @Content),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Чат не существует",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json"))
    })
    public ResponseEntity<?> deleteChat(@PathVariable("id") long chatId) {
        log.info("Deleting chat by id: " + chatId);
        return ResponseEntity.ok().build();
    }
}
