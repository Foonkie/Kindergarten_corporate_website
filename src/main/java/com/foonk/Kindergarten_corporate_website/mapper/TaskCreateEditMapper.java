package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.News;
import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.database.Task;
import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.dto.NewsCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.TaskCreateEditDto;
import liquibase.pro.packaged.T;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TaskCreateEditMapper implements Mapper<TaskCreateEditDto, Task>{
   private final SubTaskRepository subTaskRepository;
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
        task.setSubTask(object.getSubTask());
        task.setEndTime(object.getEndTime());
        task.setType(object.getType());
        task.setUser(object.getUser());
        task.setType(object.getType());


    }
    public List<SubTask> getSubTask(Long id){
     return subTaskRepository.findAllByTask_Id(id);
    }
}
