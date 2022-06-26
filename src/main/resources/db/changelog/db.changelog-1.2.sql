--liquibase formatted sql

--changeset foonk:1
ALTER TABLE documents
DROP CONSTRAINT IF EXISTS documents_type_key;
ALTER TABLE documents
DROP CONSTRAINT IF EXISTS documents_document_key;



