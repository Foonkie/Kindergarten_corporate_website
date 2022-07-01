package com.foonk.Kindergarten_corporate_website.database.repository;


import com.foonk.Kindergarten_corporate_website.database.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
