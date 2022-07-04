package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.User;
import com.foonk.Kindergarten_corporate_website.database.repository.TaskRepository;
import com.foonk.Kindergarten_corporate_website.database.repository.UserRepository;
import com.foonk.Kindergarten_corporate_website.dto.TaskReadDto;
import com.foonk.Kindergarten_corporate_website.dto.UserReadDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    TaskRepository taskRepository;


    public Page<TaskReadDto> findAllByUser_Id(Long userId, Pageable pageable){
       return  taskRepository.findAllByUser_Id(userId, pageable);
    }
}
