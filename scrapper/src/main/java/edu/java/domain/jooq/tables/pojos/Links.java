/*
 * This file is generated by jOOQ.
 */

package edu.java.domain.jooq.tables.pojos;

import edu.java.domain.jooq.enums.BaseUrlType;
import jakarta.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.JSON;

/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes", "this-escape"})
public class Links implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long linkId;
    private String url;
    private OffsetDateTime lastUpdate;
    private OffsetDateTime lastCheck;
    private BaseUrlType baseUrl;
    private JSON info;

    public Links() {
    }

    public Links(Links value) {
        this.linkId = value.linkId;
        this.url = value.url;
        this.lastUpdate = value.lastUpdate;
        this.lastCheck = value.lastCheck;
        this.baseUrl = value.baseUrl;
        this.info = value.info;
    }

    @ConstructorProperties({"linkId", "url", "lastUpdate", "lastCheck", "baseUrl", "info"})
    public Links(
        @Nullable Long linkId,
        @NotNull String url,
        @Nullable OffsetDateTime lastUpdate,
        @Nullable OffsetDateTime lastCheck,
        @NotNull BaseUrlType baseUrl,
        @NotNull JSON info
    ) {
        this.linkId = linkId;
        this.url = url;
        this.lastUpdate = lastUpdate;
        this.lastCheck = lastCheck;
        this.baseUrl = baseUrl;
        this.info = info;
    }

    /**
     * Getter for <code>LINKS.LINK_ID</code>.
     */
    @Nullable
    public Long getLinkId() {
        return this.linkId;
    }

    /**
     * Setter for <code>LINKS.LINK_ID</code>.
     */
    public void setLinkId(@Nullable Long linkId) {
        this.linkId = linkId;
    }

    /**
     * Getter for <code>LINKS.URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 2048)
    @NotNull
    public String getUrl() {
        return this.url;
    }

    /**
     * Setter for <code>LINKS.URL</code>.
     */
    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    /**
     * Getter for <code>LINKS.LAST_UPDATE</code>.
     */
    @Nullable
    public OffsetDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    /**
     * Setter for <code>LINKS.LAST_UPDATE</code>.
     */
    public void setLastUpdate(@Nullable OffsetDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for <code>LINKS.LAST_CHECK</code>.
     */
    @Nullable
    public OffsetDateTime getLastCheck() {
        return this.lastCheck;
    }

    /**
     * Setter for <code>LINKS.LAST_CHECK</code>.
     */
    public void setLastCheck(@Nullable OffsetDateTime lastCheck) {
        this.lastCheck = lastCheck;
    }

    /**
     * Getter for <code>LINKS.BASE_URL</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public BaseUrlType getBaseUrl() {
        return this.baseUrl;
    }

    /**
     * Setter for <code>LINKS.BASE_URL</code>.
     */
    public void setBaseUrl(@NotNull BaseUrlType baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Getter for <code>LINKS.INFO</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public JSON getInfo() {
        return this.info;
    }

    /**
     * Setter for <code>LINKS.INFO</code>.
     */
    public void setInfo(@NotNull JSON info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Links other = (Links) obj;
        if (this.linkId == null) {
            if (other.linkId != null) {
                return false;
            }
        } else if (!this.linkId.equals(other.linkId)) {
            return false;
        }
        if (this.url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!this.url.equals(other.url)) {
            return false;
        }
        if (this.lastUpdate == null) {
            if (other.lastUpdate != null) {
                return false;
            }
        } else if (!this.lastUpdate.equals(other.lastUpdate)) {
            return false;
        }
        if (this.lastCheck == null) {
            if (other.lastCheck != null) {
                return false;
            }
        } else if (!this.lastCheck.equals(other.lastCheck)) {
            return false;
        }
        if (this.baseUrl == null) {
            if (other.baseUrl != null) {
                return false;
            }
        } else if (!this.baseUrl.equals(other.baseUrl)) {
            return false;
        }
        if (this.info == null) {
            if (other.info != null) {
                return false;
            }
        } else if (!this.info.equals(other.info)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.linkId == null) ? 0 : this.linkId.hashCode());
        result = prime * result + ((this.url == null) ? 0 : this.url.hashCode());
        result = prime * result + ((this.lastUpdate == null) ? 0 : this.lastUpdate.hashCode());
        result = prime * result + ((this.lastCheck == null) ? 0 : this.lastCheck.hashCode());
        result = prime * result + ((this.baseUrl == null) ? 0 : this.baseUrl.hashCode());
        result = prime * result + ((this.info == null) ? 0 : this.info.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Links (");

        sb.append(linkId);
        sb.append(", ").append(url);
        sb.append(", ").append(lastUpdate);
        sb.append(", ").append(lastCheck);
        sb.append(", ").append(baseUrl);
        sb.append(", ").append(info);

        sb.append(")");
        return sb.toString();
    }
}
