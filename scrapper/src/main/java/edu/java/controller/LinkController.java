package edu.java.controller;

import edu.java.dto.model.Link;
import edu.java.dto.request.AddLinkRequest;
import edu.java.dto.request.RemoveLinkRequest;
import edu.java.dto.response.APIErrorResponse;
import edu.java.dto.response.LinkResponse;
import edu.java.dto.response.ListLinksResponse;
import edu.java.service.LinkService;
import edu.java.util.LinkFactory;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;
    private final LinkFactory linkFactory;
    private final MeterRegistry meterRegistry;

    @SuppressWarnings("MultipleStringLiterals")
    @GetMapping
    @Operation(summary = "Получить все отслеживаемые ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылки успешно получены",
                     content = @Content(schema = @Schema(implementation = ListLinksResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Чат отсутствует",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json"))
    })
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") long chatId) {
        log.info("Getting links for chat: " + chatId);

        List<Link> links = linkService.listAll(chatId);

        meterRegistry.counter("proceeded_request_count", Collections.emptyList())
            .increment();
        return ResponseEntity.ok(new ListLinksResponse(
            links.stream()
                .map(l -> new LinkResponse(l.getLinkId(), l.getUrl()))
                .toList(),
            links.size()
        ));
    }

    @SuppressWarnings("MultipleStringLiterals")
    @PostMapping
    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена",
                     content = @Content(schema = @Schema(implementation = LinkResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Чат не найден",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "406", description = "Ссылка уже отслеживается",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json"))
    })
    public ResponseEntity<LinkResponse> addLink(
        @RequestHeader("Tg-Chat-Id") long chatId,
        @Valid @RequestBody AddLinkRequest request
    ) {
        log.info("Adding link for chat: " + chatId + ", and request: " + request);

        String url = request.link();
        linkService.addLinkToChat(chatId, linkFactory.createLink(url));

        meterRegistry.counter("proceeded_request_count", Collections.emptyList())
            .increment();
        return ResponseEntity.ok(new LinkResponse(chatId, url));
    }

    @DeleteMapping
    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана",
                     content = @Content(schema = @Schema(implementation = LinkResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "Чат не найден",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json")),
        @ApiResponse(responseCode = "406", description = "Ссылка уже удалена",
                     content = @Content(schema = @Schema(implementation = APIErrorResponse.class),
                                        mediaType = "application/json"))
    })
    public ResponseEntity<LinkResponse> deleteLink(
        @RequestHeader("Tg-Chat-Id") long chatId,
        @Valid @RequestBody RemoveLinkRequest request
    ) {
        log.info("Deleting link for chat: " + chatId + ", and request: " + request);

        String url = request.link();
        linkService.removeLinkFromChat(chatId, url);

        meterRegistry.counter("proceeded_request_count", Collections.emptyList())
            .increment();
        return ResponseEntity.ok(new LinkResponse(chatId, url));
    }
}
