package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskReadDto;
import org.springframework.stereotype.Component;

@Component
public class SubTaskReadMapper implements Mapper<SubTask, SubTaskReadDto> {

    @Override
    public SubTaskReadDto map(SubTask object) {
        return new SubTaskReadDto(object.getId(), object.getSubtask(), object.getTask());
    }
}
