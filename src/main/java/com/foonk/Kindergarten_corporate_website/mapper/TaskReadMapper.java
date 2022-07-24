package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.database.Task;
import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskReadDto;
import com.foonk.Kindergarten_corporate_website.dto.TaskReadDto;
import com.foonk.Kindergarten_corporate_website.dto.UserReadDto;
import com.foonk.Kindergarten_corporate_website.service.SubTaskService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TaskReadMapper implements Mapper<Task, TaskReadDto>{
    private final UserReadMapper userReadMapper;
    private final SubTaskReadMapper subTaskReadMapper;
    private final SubTaskRepository subTaskRepository;



    public TaskReadDto map(Task task){
        UserReadDto user = Optional.ofNullable(userReadMapper.map(task.getUser())).orElse(null);
        return new TaskReadDto(subtasks(task),task.getId(),task.getType(),task.getTask_header(),task.getEndTime(),user);
    }
        public List<SubTaskReadDto> subtasks (Task task){
        return subTaskRepository.findAllByTask(task).stream().map(subTaskReadMapper::map).toList();
    }

}
