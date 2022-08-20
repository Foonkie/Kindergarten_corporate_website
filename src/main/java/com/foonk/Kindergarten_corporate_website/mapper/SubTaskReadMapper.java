package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class SubTaskReadMapper implements Mapper<SubTask, SubTaskReadDto> {


    @Override
    public SubTaskReadDto map(SubTask object) {
//       TaskReadDto task= Optional.ofNullable(object.getTask())
//               .map(taskReadMapper::map)
//               .orElse(null);
        return new SubTaskReadDto(object.getId(), object.getSubtask(), object.getTask().getId(), object.getStatus());
    }

}
