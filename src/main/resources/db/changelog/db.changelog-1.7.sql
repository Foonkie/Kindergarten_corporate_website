--liquibase formatted sql

--changeset foonk:1
ALTER TABLE subtasks
ADD COLUMN status BOOLEAN DEFAUlT FALSE;




