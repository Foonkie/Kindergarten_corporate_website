package com.foonk.Kindergarten_corporate_website.dto;

import com.foonk.Kindergarten_corporate_website.database.Kind;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.web.multipart.MultipartFile;

/*Dto для операций создания или изменения сущности Document.*/
@Value
public class DocumentCreateEditDto {
    private Kind kind;

    private MultipartFile document;
}
