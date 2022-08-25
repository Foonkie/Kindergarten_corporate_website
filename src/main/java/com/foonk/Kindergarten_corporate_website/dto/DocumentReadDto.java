package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.database.Kind;
import lombok.Value;

/*Dto для операций чтения сущности Document из базы.*/
@Value
public class DocumentReadDto {
    private Kind kind;

    private String document;

    private Long id;
}
