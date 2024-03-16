package edu.java.dto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    @NotNull
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
        name = "links_chats",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "link_id")
    )
    private List<Link> links = new ArrayList<>();

    public Chat(long chatId) {
        this.chatId = chatId;
    }
}
