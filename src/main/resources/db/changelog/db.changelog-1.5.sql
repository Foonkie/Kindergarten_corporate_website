--liquibase formatted sql

--changeset foonk:1
ALTER TABLE subtasks
DROP CONSTRAINT IF EXISTS subtasks_task_id_fkey;
--changeset foonk:2
ALTER TABLE subtasks
ADD CONSTRAINT subtasks_task_id_fkey
FOREIGN KEY (task_id) REFERENCES tasks(id)
ON DELETE CASCADE;



