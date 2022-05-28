package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.validation.group.CreateAction;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class NewsCreateEditDto {
    @NotBlank(groups = CreateAction.class)
    private String header;

    @NotBlank(groups = CreateAction.class)
    private String body;
}
