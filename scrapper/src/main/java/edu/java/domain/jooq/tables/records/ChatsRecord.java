/*
 * This file is generated by jOOQ.
 */

package edu.java.domain.jooq.tables.records;

import edu.java.domain.jooq.tables.Chats;
import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

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
public class ChatsRecord extends UpdatableRecordImpl<ChatsRecord> implements Record2<Long, OffsetDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Create a detached ChatsRecord
     */
    public ChatsRecord() {
        super(Chats.CHATS);
    }

    /**
     * Create a detached, initialised ChatsRecord
     */
    @ConstructorProperties({"chatId", "createdAt"})
    public ChatsRecord(@NotNull Long chatId, @Nullable OffsetDateTime createdAt) {
        super(Chats.CHATS);

        setChatId(chatId);
        setCreatedAt(createdAt);
        resetChangedOnNotNull();
    }

    /**
     * Create a detached, initialised ChatsRecord
     */
    public ChatsRecord(edu.java.domain.jooq.tables.pojos.Chats value) {
        super(Chats.CHATS);

        if (value != null) {
            setChatId(value.getChatId());
            setCreatedAt(value.getCreatedAt());
            resetChangedOnNotNull();
        }
    }

    /**
     * Getter for <code>CHATS.CHAT_ID</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public Long getChatId() {
        return (Long) get(0);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>CHATS.CHAT_ID</code>.
     */
    public void setChatId(@NotNull Long value) {
        set(0, value);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * Getter for <code>CHATS.CREATED_AT</code>.
     */
    @Nullable
    public OffsetDateTime getCreatedAt() {
        return (OffsetDateTime) get(1);
    }

    /**
     * Setter for <code>CHATS.CREATED_AT</code>.
     */
    public void setCreatedAt(@Nullable OffsetDateTime value) {
        set(1, value);
    }

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    @Override
    @NotNull
    public Row2<Long, OffsetDateTime> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row2<Long, OffsetDateTime> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Chats.CHATS.CHAT_ID;
    }

    @Override
    @NotNull
    public Field<OffsetDateTime> field2() {
        return Chats.CHATS.CREATED_AT;
    }

    @Override
    @NotNull
    public Long component1() {
        return getChatId();
    }

    @Override
    @Nullable
    public OffsetDateTime component2() {
        return getCreatedAt();
    }

    @Override
    @NotNull
    public Long value1() {
        return getChatId();
    }

    @Override
    @Nullable
    public OffsetDateTime value2() {
        return getCreatedAt();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public ChatsRecord value1(@NotNull Long value) {
        setChatId(value);
        return this;
    }

    @Override
    @NotNull
    public ChatsRecord value2(@Nullable OffsetDateTime value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    @NotNull
    public ChatsRecord values(@NotNull Long value1, @Nullable OffsetDateTime value2) {
        value1(value1);
        value2(value2);
        return this;
    }
}
