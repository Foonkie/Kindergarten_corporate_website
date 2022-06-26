--liquibase formatted sql

--changeset foonk:1
ALTER TABLE documents
DROP CONSTRAINT IF EXISTS type;
ALTER TABLE documents
DROP CONSTRAINT IF EXISTS document;



