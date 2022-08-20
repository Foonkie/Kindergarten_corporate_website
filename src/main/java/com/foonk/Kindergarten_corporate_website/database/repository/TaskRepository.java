package com.foonk.Kindergarten_corporate_website.database.repository;


import com.foonk.Kindergarten_corporate_website.database.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findAllByUser_Id(Long id, Pageable pageable);

    Page<Task> findAllByUser_Username(String username, Pageable pageable);
}
