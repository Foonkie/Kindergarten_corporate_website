package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.database.Task;
import lombok.Value;

import javax.validation.constraints.NotBlank;

    @Value
    public class SubTaskReadDto {
        private Long id;
        @NotBlank
        private String subtask;
        @NotBlank
        private Long taskId;
    }

