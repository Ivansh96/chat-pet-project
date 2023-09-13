CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id        UUID PRIMARY KEY,
    username  VARCHAR(64) NOT NULL,
    password  VARCHAR(64) NOT NULL
);

CREATE TABLE chat
(
    id      UUID PRIMARY KEY,
    name    VARCHAR(64),
    user_id UUID REFERENCES users (id)
);

CREATE TABLE message
(
    id          UUID PRIMARY KEY,
    description VARCHAR(256),
    chat_id     UUID REFERENCES chat (id) NOT NULL ,
    created_at TIMESTAMP NOT NULL
);