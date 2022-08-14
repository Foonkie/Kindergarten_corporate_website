package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.database.Task;
import com.foonk.Kindergarten_corporate_website.database.repository.TaskRepository;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskCreateEditDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SubTaskCreateEditMapper implements Mapper<SubTaskCreateEditDto, SubTask> {
    private final TaskRepository taskRepository;

    @Override
    public SubTask map(SubTaskCreateEditDto object) {
        SubTask subTask = new SubTask();
        copy(object, subTask);
        return subTask;
    }

    @Override
    public SubTask map(SubTaskCreateEditDto fromObject, SubTask toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(SubTaskCreateEditDto subTaskCreateEditDto, SubTask subTask) {
        subTask.setTask(getTask(subTaskCreateEditDto.getTaskId()));
        subTask.setSubtask(subTaskCreateEditDto.getSubtask());
        subTask.setStatus(subTaskCreateEditDto.getStatus());
    }

    public Task getTask(Long taskId) {
        return Optional.ofNullable(taskId)
                .flatMap(taskRepository::findById)
                .orElseThrow();
    }
}