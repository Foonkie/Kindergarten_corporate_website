package com.foonk.Kindergarten_corporate_website.database.repository;

import com.foonk.Kindergarten_corporate_website.database.News;
import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.database.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
  List<SubTask> findAllByTask_Id(Long task_id);
}
