package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.database.Task;
import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskReadDto;
import com.foonk.Kindergarten_corporate_website.dto.TaskReadDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Component
public class SubTaskReadMapper implements Mapper<SubTask, SubTaskReadDto> {


    @Override
    public SubTaskReadDto map(SubTask object) {
//       TaskReadDto task= Optional.ofNullable(object.getTask())
//               .map(taskReadMapper::map)
//               .orElse(null);
        return new SubTaskReadDto(object.getId(), object.getSubtask(), object.getTask().getId());
    }

}
