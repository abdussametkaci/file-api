CREATE TABLE file
(
    id               UUID DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
    extension        VARCHAR(10)                     NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE     NOT NULL
);
