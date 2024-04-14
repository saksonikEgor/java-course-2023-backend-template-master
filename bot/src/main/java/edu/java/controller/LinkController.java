package edu.java.controller;

import edu.java.bot.TelegramBotWrapper;
import edu.java.dto.request.LinkUpdateRequest;
import edu.java.dto.response.APIErrorResponse;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class LinkController {
    private final TelegramBotWrapper telegramBotWrapper;
    private final MeterRegistry meterRegistry;

    @PostMapping("/updates")
    @Operation(summary = "Отправить обновление")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Обновление обработано"),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json"))
    })
    public ResponseEntity<?> updateLink(@RequestBody LinkUpdateRequest request) {
        telegramBotWrapper.sendMessages(request);
        meterRegistry.counter("proceeded_request_count", Collections.emptyList())
            .increment();
        return ResponseEntity.ok().build();
    }
}
