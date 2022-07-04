package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.Task;
import com.foonk.Kindergarten_corporate_website.dto.TaskReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskReadMapper implements Mapper<Task, TaskReadDto>{
    public TaskReadDto map(Task task){
        return new TaskReadDto(task.getId(),task.getSubTask(),task.getType(),task.getTask_header(),task.getEndTime(),task.getUser());
    }
}
