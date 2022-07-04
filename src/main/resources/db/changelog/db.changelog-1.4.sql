--liquibase formatted sql

--changeset foonk:1
ALTER TABLE tasks RENAME COLUMN task TO task_header;



