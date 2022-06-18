package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.Document;
import com.foonk.Kindergarten_corporate_website.database.Kind;
import com.foonk.Kindergarten_corporate_website.database.repository.DocumentRepository;
import com.foonk.Kindergarten_corporate_website.dto.DocumentReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.DocumentReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentReadMapper documentReadMapper;
    private final DocumentRepository documentRepository;
public List<DocumentReadDto> findAllDocumentByKind(String kind){
   documentRepository.findAllByKind(Kind.valueOf(kind));

    return documentRepository.findAllByKind(Kind.valueOf(kind)).stream()
            .map(documentReadMapper::map)
            .collect(Collectors.toList());
    }


}
