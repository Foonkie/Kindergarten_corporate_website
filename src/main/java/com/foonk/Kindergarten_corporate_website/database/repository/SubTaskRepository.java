package com.foonk.Kindergarten_corporate_website.database.repository;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.database.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    public List<SubTask> findAllByTask(Task task);

    public List<SubTask> findAllByTask_Id(Long id);

    public Optional<SubTask> findBySubtaskAndTask_Id(String subtask, Long id);
}
