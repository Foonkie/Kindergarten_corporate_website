--liquibase formatted sql

--changeset foonk:1
ALTER TABLE tasks DROP CONSTRAINT IF EXISTS tasks_type_key;




