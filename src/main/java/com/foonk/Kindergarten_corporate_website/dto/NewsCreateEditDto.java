package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.validation.group.CreateAction;
import lombok.Value;

import javax.validation.constraints.NotBlank;

/*Dto для операций создания или изменения сущности News.*/
@Value
public class NewsCreateEditDto {
    @NotBlank (message = "Заполните заголовок")
    private String header;

    @NotBlank (message = "Заполните текст")
    private String body;
}
