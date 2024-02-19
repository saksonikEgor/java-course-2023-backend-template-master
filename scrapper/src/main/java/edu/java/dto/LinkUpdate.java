package edu.java.dto;

import java.util.List;

public record LinkUpdate(
    int id,
    String url,
    String description,
    List<Integer> tgChatIds
) {
}
