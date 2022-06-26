package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.Document;
import com.foonk.Kindergarten_corporate_website.database.News;
import com.foonk.Kindergarten_corporate_website.dto.DocumentCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.NewsCreateEditDto;
import liquibase.pro.packaged.D;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static java.util.function.Predicate.not;

@Component
public class DocumentCreateEditMapper implements Mapper<DocumentCreateEditDto, Document>{
    @Override
    public Document map(DocumentCreateEditDto object) {
        Document document = new Document();
        copy(object, document);
        return document;
    }

    @Override
    public Document map(DocumentCreateEditDto fromObject, Document toObject) {
        copy(fromObject, toObject);
        return toObject;
    }
    private void copy(DocumentCreateEditDto object, Document document) {
        document.setKind(object.getKind());
        Optional.ofNullable(object.getDocument())
                .filter(not(MultipartFile::isEmpty))
                .ifPresent(obj -> document.setDocument(obj.getOriginalFilename()));
    }
}
