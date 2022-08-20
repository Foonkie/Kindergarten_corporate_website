package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.Document;
import com.foonk.Kindergarten_corporate_website.dto.DocumentReadDto;
import org.springframework.stereotype.Component;

@Component
public class DocumentReadMapper implements Mapper<Document, DocumentReadDto> {

    @Override
    public DocumentReadDto map(Document object) {
        return new DocumentReadDto(object.getKind(), object.getDocument(), object.getId());
    }
}

