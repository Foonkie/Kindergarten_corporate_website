--liquibase formatted sql

--changeset foonk:1
CREATE TABLE IF NOT EXISTS news
(
    id BIGSERIAL PRIMARY KEY ,
    header VARCHAR(64) NOT NULL UNIQUE ,
    body VARCHAR(64) NOT NULL UNIQUE,
    created_at TIMESTAMP
);
--changeset foonk:2
CREATE TABLE IF NOT EXISTS documents
(
    id BIGSERIAL PRIMARY KEY ,
    type VARCHAR(32) NOT NULL UNIQUE,
    document VARCHAR(64) NOT NULL UNIQUE,
    created_at TIMESTAMP
);
--changeset foonk:3
CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(64) NOT NULL UNIQUE ,
    password VARCHAR(128) DEFAULT '{noop}123',
    birth_date DATE,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    role VARCHAR(32),
    image VARCHAR(64)
);
--changeset foonk:4
CREATE TABLE IF NOT EXISTS tasks
(
    id BIGSERIAL PRIMARY KEY ,
    type VARCHAR(32) NOT NULL UNIQUE,
    task VARCHAR(64) NOT NULL UNIQUE,
    end_time TIMESTAMP,
    created_at TIMESTAMP,
    user_id BIGINT REFERENCES users(id)
);



