package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.database.Kind;
import lombok.Value;

@Value
public class DocumentReadDto {
     Kind kind;

     String document;

     Long id;
}
