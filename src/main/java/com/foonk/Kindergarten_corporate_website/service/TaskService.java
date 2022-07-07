package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.Task;
import com.foonk.Kindergarten_corporate_website.database.User;
import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.database.repository.TaskRepository;
import com.foonk.Kindergarten_corporate_website.database.repository.UserRepository;
import com.foonk.Kindergarten_corporate_website.dto.*;
import com.foonk.Kindergarten_corporate_website.mapper.SubTaskCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.SubTaskReadMapper;
import com.foonk.Kindergarten_corporate_website.mapper.TaskCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.TaskReadMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskReadMapper taskReadMapper;

    private final TaskCreateEditMapper taskCreateEditMapper;



    public Page<TaskReadDto> findAllByUser_Id(Long userId, Pageable pageable){
        return taskRepository.findAllByUser_Id(userId, pageable)
                .map(taskReadMapper::map);
    }
    TaskReadDto create(TaskCreateEditDto taskCreateEditDto){
        return Optional.of(taskCreateEditDto)
                .map(dto->taskCreateEditMapper.map(taskCreateEditDto))
                .map(taskRepository::save)
                .map(taskReadMapper::map)
                .orElseThrow();
    }

}
