package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.Document;
import com.foonk.Kindergarten_corporate_website.database.Kind;
import com.foonk.Kindergarten_corporate_website.database.repository.DocumentRepository;
import com.foonk.Kindergarten_corporate_website.dto.DocumentCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.DocumentReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.DocumentCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.DocumentReadMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final String bucket="C:\\Users\\Katyusha\\IdeaProjects\\Kindergarten_corporate_website\\documents";
    private final DocumentReadMapper documentReadMapper;
    private final DocumentRepository documentRepository;
    private final DocumentCreateEditMapper documentCreateEditMapper;

    @Transactional
    @SneakyThrows
    public void upload (String documentPath, InputStream content, String kind){
        Path fullDocumentPath = Path.of(bucket, kind, documentPath);
        try(content) {
            Files.createDirectories(fullDocumentPath.getParent());
            Files.write(fullDocumentPath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }
    @Transactional
    @SneakyThrows
    public Optional<byte[]> get(String documentPath, String kind){
        Path fullDocumentPath = Path.of(bucket, kind, documentPath);

        return Files.exists(fullDocumentPath)
                ? Optional.of(Files.readAllBytes(fullDocumentPath))
                :Optional.empty();
    }
    @Transactional
    @SneakyThrows
    private void uploadDocument(MultipartFile document, String kind) {
        if (!document.isEmpty()) {
            upload(document.getOriginalFilename(), document.getInputStream(), kind);
        }
    }

    @Transactional
    public boolean delete(Long id) {
        return documentRepository.findById(id)
                .map(entity -> {
                    documentRepository.delete(entity);
                    documentRepository.flush();
                    return true;
                })
                .orElse(false);
    }
    @Transactional
    public DocumentReadDto create(DocumentCreateEditDto documentDto) {
         return Optional.of(documentDto)
                .map(dto->{
                    uploadDocument(dto.getDocument(), dto.getKind().toString());
                    return documentCreateEditMapper.map(dto);
                })
                .map(documentRepository::save)
                .map(documentReadMapper::map)
                .orElseThrow();
    }
    @Transactional
public List<DocumentReadDto> findAllDocumentByKind(String kind){
   documentRepository.findAllByKind(Kind.valueOf(kind));

    return documentRepository.findAllByKind(Kind.valueOf(kind)).stream()
            .map(documentReadMapper::map)
            .collect(Collectors.toList());
    }
    @Transactional
public Optional<DocumentReadDto> findDocumentById(Long id)
    {
        return documentRepository.findById(id).map(documentReadMapper::map);
    }



}
