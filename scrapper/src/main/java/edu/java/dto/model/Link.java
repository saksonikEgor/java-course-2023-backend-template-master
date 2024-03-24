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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.URL;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id", nullable = false)
    private Long linkId;

    @NotBlank(message = "url should be not empty")
    @Column(name = "url", nullable = false, unique = true)
    @URL
    private String url;

    @NotNull(message = "lastUpdate should not be null")
    @Column(name = "last_update", nullable = false)
    private OffsetDateTime lastUpdate = OffsetDateTime.now();

    @NotNull(message = "lastCheck should not be null")
    @Column(name = "last_check", nullable = false)
    private OffsetDateTime lastCheck = OffsetDateTime.now();

    @NotNull(message = "baseURL should not be null")
    @Column(name = "base_url", nullable = false)
    @Enumerated(EnumType.STRING)
    private BaseURL baseURL;

    @NotNull(message = "info should not be null")
    @Column(name = "info", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> info = new HashMap<>();

    @NotNull(message = "links should not be null")
    @ToString.Exclude
    @ManyToMany(mappedBy = "links")
    private List<Chat> chats = new ArrayList<>();

    public Link(
        String url,
        OffsetDateTime lastUpdate,
        OffsetDateTime lastCheck,
        BaseURL baseURL
    ) {
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.lastCheck = lastCheck;
        this.baseURL = baseURL;
    }

    public Link(
        String url,
        BaseURL baseURL,
        Map<String, String> info
    ) {
        this.url = url;
        this.baseURL = baseURL;
        this.info = info;
    }

    public Link(
        long linkId,
        String url,
        OffsetDateTime lastUpdate,
        OffsetDateTime lastCheck,
        BaseURL baseURL,
        Map<String, String> info
    ) {
        this.linkId = linkId;
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.lastCheck = lastCheck;
        this.baseURL = baseURL;
        this.info = info;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        Class<?> oEffectiveClass = o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        Link link = (Link) o;
        return getLinkId() != null && Objects.equals(getLinkId(), link.getLinkId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
            : getClass().hashCode();
    }
}
