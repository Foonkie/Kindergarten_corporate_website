package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskCreateEditDto;
import org.springframework.stereotype.Component;

@Component
public class SubTaskCreateEditMapper implements Mapper<SubTaskCreateEditDto, SubTask> {

    @Override
    public SubTask map(SubTaskCreateEditDto object) {
        SubTask subTask=new SubTask();
        copy(object,subTask);
        return subTask;
    }

    @Override
    public SubTask map(SubTaskCreateEditDto fromObject, SubTask toObject) {
        copy(fromObject,toObject);
        return toObject;
    }
    private void copy(SubTaskCreateEditDto subTaskCreateEditDto, SubTask subTask){
subTask.setTask(subTaskCreateEditDto.getTask());
subTask.setSubtask(subTaskCreateEditDto.getSubtask());
    }
}
