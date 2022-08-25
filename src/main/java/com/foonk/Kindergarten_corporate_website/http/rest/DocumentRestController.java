package com.foonk.Kindergarten_corporate_website.http.rest;

import com.foonk.Kindergarten_corporate_website.dto.DocumentReadDto;
import com.foonk.Kindergarten_corporate_website.service.DocumentService;
import com.foonk.Kindergarten_corporate_website.util.MediaTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

/*REST контроллер*/
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentRestController {


    private final DocumentService documentService;

    private final ServletContext servletContext;

    private final String bucket = "C:\\Users\\Katyusha\\IdeaProjects\\Kindergarten_corporate_website\\documents";
/*Метод возращающий картинку (Аватар)*/
    @RequestMapping(value = "/{kind}/{id}")
    public ResponseEntity<InputStreamResource> findAvatar(@PathVariable Long id, @PathVariable String kind) throws IOException {

        Optional<DocumentReadDto> documentById = documentService.findDocumentById(id);
        Optional<String> s = documentById.map(doc -> doc.getDocument());

        if (s.isPresent()) {
            String s1 = s.get();
            MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(servletContext, s1);
            File file = new File(bucket + "\\" + kind + "\\" + s1);
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                    .contentType(mediaType)
                    .contentLength(file.length())
                    .body(inputStreamResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


//    return documentService.findDocumentById(id).flatMap(document -> documentService.get(document.getDocument(), kind))
//            .map(content -> ok()
//                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
//                    .contentLength(content.length)
//                    .body(content))
//            .orElseGet(notFound()::build);

//    File file = new File("C:\\" + s1);
//    Path path= Paths.get(DIRECTORY+ "/" + s1);
//    byte[] data = Files.readAllBytes(path);








