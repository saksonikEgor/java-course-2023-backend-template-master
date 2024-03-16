package edu.java.dto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.URL;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id", nullable = false)
    private Long linkId;

    @Column(name = "url", nullable = false, unique = true)
    @URL
    private String url;

    @Column(name = "last_update", nullable = false)
    private OffsetDateTime lastUpdate = OffsetDateTime.now();

    @Column(name = "last_check", nullable = false)
    private OffsetDateTime lastCheck = OffsetDateTime.now();

    @Column(name = "base_url", nullable = false)
    @Enumerated(EnumType.STRING)
    private BaseURL baseURL;

    @Column(name = "info", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> info = new HashMap<>();

    @NotNull
    @ToString.Exclude
    @ManyToMany(mappedBy = "links")
    private List<Chat> chats = new ArrayList<>();

    public Link(
        String url,
        @NotNull OffsetDateTime lastUpdate,
        @NotNull OffsetDateTime lastCheck,
        @NotNull BaseURL baseURL
    ) {
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.lastCheck = lastCheck;
        this.baseURL = baseURL;
    }

    public Link(
        String url,
        @NotNull BaseURL baseURL,
        @NotNull Map<String, String> info
    ) {
        this.url = url;
        this.baseURL = baseURL;
        this.info = info;
    }

    public Link(
        long linkId,
        String url,
        @NotNull OffsetDateTime lastUpdate,
        @NotNull OffsetDateTime lastCheck,
        @NotNull BaseURL baseURL,
        @NotNull Map<String, String> info
    ) {
        this.linkId = linkId;
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.lastCheck = lastCheck;
        this.baseURL = baseURL;
        this.info = info;
    }
}
