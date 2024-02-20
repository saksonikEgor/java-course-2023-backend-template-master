CREATE TYPE chat_state AS ENUM ('registered', 'waiting_for_track', 'waiting_for_untrack');


CREATE TABLE links
(
    link_id     BIGINT PRIMARY KEY,
    url         VARCHAR(2048) UNIQUE                      NOT NULL,
    last_update TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL,
    last_check  TIMESTAMP WITHOUT TIME ZONE DEFAULT now() NOT NULL
);


CREATE TABLE chats
(
    chat_id    BIGINT PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT now()        NOT NULL,
    state      chat_state                  DEFAULT 'registered' NOT NULL
);


CREATE TABLE links_chats
(
    chat_id BIGINT PRIMARY KEY,
    link_id BIGINT PRIMARY KEY,
    CONSTRAINT chat_fkey FOREIGN KEY (chat_id)
        REFERENCES chats (chat_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT link_fkey FOREIGN KEY (link_id)
        REFERENCES links (link_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
