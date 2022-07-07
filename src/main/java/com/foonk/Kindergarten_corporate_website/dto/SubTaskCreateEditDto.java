package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.database.Task;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Value
public class SubTaskCreateEditDto {
    @NotBlank
    private String subtask;
    @NotBlank
    private Task task;
}
