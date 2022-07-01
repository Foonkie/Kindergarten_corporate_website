--liquibase formatted sql

--changeset foonk:1
CREATE TABLE IF NOT EXISTS subtasks
(
    id BIGSERIAL PRIMARY KEY ,
    subtask VARCHAR(64) NOT NULL UNIQUE ,
    task_id BIGINT REFERENCES tasks(id)
);



