CREATE TYPE base_url_type AS ENUM ('GITHUB', 'STACKOVERFLOW');


CREATE TABLE links
(
    link_id     BIGSERIAL PRIMARY KEY,
    url         VARCHAR(2048) UNIQUE                   NOT NULL,
    last_update TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    last_check  TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    base_url    base_url_type                          NOT NULL,
    info        JSON                                   NOT NULL
);


CREATE TABLE chats
(
    chat_id    BIGINT PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()        NOT NULL
);


CREATE TABLE links_chats
(
    chat_id BIGINT NOT NULL,
    link_id BIGINT NOT NULL,
    PRIMARY KEY (chat_id, link_id),
    CONSTRAINT chat_fkey FOREIGN KEY (chat_id)
        REFERENCES chats (chat_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT link_fkey FOREIGN KEY (link_id)
        REFERENCES links (link_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
