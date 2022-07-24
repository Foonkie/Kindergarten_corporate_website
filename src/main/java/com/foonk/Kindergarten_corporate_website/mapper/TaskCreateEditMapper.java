package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.News;
import com.foonk.Kindergarten_corporate_website.database.Task;
import com.foonk.Kindergarten_corporate_website.database.User;
import com.foonk.Kindergarten_corporate_website.database.repository.UserRepository;
import com.foonk.Kindergarten_corporate_website.dto.NewsCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.TaskCreateEditDto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class TaskCreateEditMapper implements Mapper<TaskCreateEditDto, Task>{
   private final UserRepository userRepository;


    @Override
    public Task map(TaskCreateEditDto object) {
        Task task=new Task();
        copy(object, task);
        return task;
    }

    public Task map(TaskCreateEditDto fromObject, Task toObject) {
        copy(fromObject, toObject);
        return toObject;
    }


    private void copy(TaskCreateEditDto object, Task task) {
        task.setTask_header(object.getTask_header());
        task.setEndTime(object.getEndTime());
        task.setType(object.getType());
        task.setUser(getUser(object.getUserId()));
        task.setType(object.getType());
    }


    public User getUser(Long userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
