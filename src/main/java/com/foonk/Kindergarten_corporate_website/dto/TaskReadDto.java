package com.foonk.Kindergarten_corporate_website.dto;


import com.foonk.Kindergarten_corporate_website.database.Type;
import com.foonk.Kindergarten_corporate_website.database.User;
import com.foonk.Kindergarten_corporate_website.validation.group.CreateAction;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Dto для операций чтения сущности Task из базы.*/
@Value
@AllArgsConstructor
public class TaskReadDto {

    private List<SubTaskReadDto> subTaskReadDtos;

    private Long id;

    private Type type;

    private String task_header;

    private LocalDateTime endTime;

    private UserReadDto user;
}
