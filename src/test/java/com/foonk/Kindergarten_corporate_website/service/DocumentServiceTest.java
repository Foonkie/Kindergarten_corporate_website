package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.Document;
import com.foonk.Kindergarten_corporate_website.database.Kind;
import com.foonk.Kindergarten_corporate_website.database.repository.DocumentRepository;
import com.foonk.Kindergarten_corporate_website.dto.DocumentCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.DocumentReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.DocumentCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.DocumentReadMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    private final String bucket = "C:\\Users\\Katyusha\\IdeaProjects\\Kindergarten_corporate_website\\documents";
    @Mock
    private static DocumentReadMapper documentReadMapper;
    @Mock
    private static DocumentRepository documentRepository;
    @Mock
    private static DocumentCreateEditMapper documentCreateEditMapper;

    @InjectMocks
    private static DocumentService documentService;

    private final static InputStream stubInputStream =
            IOUtils.toInputStream("some test data for my input stream", "UTF-8");

    private static final MultipartFile mockFile = mock(MultipartFile.class, "Имя_файла.txt");

    private static final long DOCUMENT_ID = 1l;

    private static final Kind KIND = Kind.SCHEDULE;

    private static final Document DOCUMENT = new Document(DOCUMENT_ID, KIND, "Имя_файла.txt");

    private static final DocumentCreateEditDto DOCUMENT_CREATE_EDIT_DTO = new DocumentCreateEditDto(KIND, mockFile);

    private static final DocumentReadDto DOCUMENT_READ_DTO = new DocumentReadDto(KIND, "Имя_файла.txt", DOCUMENT_ID);


    @ParameterizedTest
    @MethodSource("getArgumetsForUploadTest")
    void upload(String documentPath, InputStream content, String kind) {
        documentService.upload(documentPath, content, kind);
        Optional<byte[]> file = documentService.get(documentPath, kind);
        assertThat(file).isNotEmpty();
    }

    public static Stream<Arguments> getArgumetsForUploadTest() {
        return Stream.of(Arguments.of("Документ.doc", stubInputStream, "Расписание"));
    }

    @ParameterizedTest
    @MethodSource("getArgumetsForGetTest")
    public void get(String documentPath, String kind) {
        documentService.upload(documentPath, stubInputStream, kind);
        Optional<byte[]> file = documentService.get(documentPath, kind);
        assertThat(file).isNotEmpty();
    }

    public static Stream<Arguments> getArgumetsForGetTest() {
        when(mockFile.getOriginalFilename()).thenReturn("Имя_файла.txt");
        return Stream.of(Arguments.of(mockFile.getOriginalFilename(), "Расписание"));
    }


    @ParameterizedTest
    @MethodSource("getArgumetsForUploadDocumentTest")
    void uploadDocument(MultipartFile document, String kind) {
        when(document.getOriginalFilename()).thenReturn("Имя_файла.txt");
        when(document.isEmpty()).thenReturn(false);
        try {
            when(document.getInputStream()).thenReturn(stubInputStream);
        } catch (IOException e) {
            assertThat(e).doesNotThrowAnyException();
        }
        documentService.uploadDocument(document, kind);
        Optional<byte[]> file = documentService.get(document.getOriginalFilename(), kind);
        assertThat(file).isNotEmpty();
    }


    public static Stream<Arguments> getArgumetsForUploadDocumentTest() {
        return Stream.of(Arguments.of(mockFile, "Расписание"));
    }


    @Test
    void delete() {
        doReturn(Optional.of(DOCUMENT)).when(documentRepository).findById(DOCUMENT_ID);
        doNothing().when(documentRepository).delete(DOCUMENT);
        doNothing().when(documentRepository).flush();
        boolean delete = documentService.delete(DOCUMENT.getId());
        assertThat(delete).isTrue();
    }

    @Test
    void create() {
        doReturn(DOCUMENT).when(documentCreateEditMapper).map(DOCUMENT_CREATE_EDIT_DTO);
        doReturn(DOCUMENT).when(documentRepository).save(DOCUMENT);
        doReturn(DOCUMENT_READ_DTO).when(documentReadMapper).map(DOCUMENT);
        doReturn("Имя_файла.txt").when(mockFile).getOriginalFilename();

        try {
            doReturn(stubInputStream).when(mockFile).getInputStream();
        } catch (IOException e) {
            assertThat(e).doesNotThrowAnyException();
        }
        DocumentReadDto documentReadDto = documentService.create(DOCUMENT_CREATE_EDIT_DTO);
        Path fullDocumentPath = Path.of(bucket, KIND.toString(), "Имя_файла.txt");
        boolean exists = Files.exists(fullDocumentPath);
        assertThat(exists).isTrue();
    }


    @Test
    void findAllDocumentByKind() {
        doReturn(new ArrayList<>(Collections.singletonList(DOCUMENT))).when(documentRepository).findAllByKind(KIND);
        doReturn(DOCUMENT_READ_DTO).when(documentReadMapper).map(DOCUMENT);
        List<DocumentReadDto> allDocumentByKind = documentService.findAllDocumentByKind(KIND.toString());
        assertThat(allDocumentByKind).hasSize(1);
    }

    @Test
    void findDocumentById() {
        doReturn(Optional.of(DOCUMENT)).when(documentRepository).findById(DOCUMENT.getId());
        doReturn(DOCUMENT_READ_DTO).when(documentReadMapper).map(DOCUMENT);
        Optional<DocumentReadDto> documentById = documentService.findDocumentById(DOCUMENT_ID);
        assertThat(documentById).isNotEmpty();
        assertThat(documentById.get()).isEqualTo(DOCUMENT_READ_DTO);
    }
}