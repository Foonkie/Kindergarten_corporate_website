package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.database.Type;
import com.foonk.Kindergarten_corporate_website.database.User;
import com.foonk.Kindergarten_corporate_website.validation.group.CreateAction;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Value
public class TaskReadDto {
    private Long id;

    private List<SubTask> subTask;

    private Type type;

    private String task_header;

    private LocalDateTime endTime;

    private User user;
}
