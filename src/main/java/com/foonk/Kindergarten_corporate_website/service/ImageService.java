package com.foonk.Kindergarten_corporate_website.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
/*Сервис по работе с изображениями*/
@Service
@RequiredArgsConstructor
public class ImageService {

    private final String bucket = "C:\\Users\\Katyusha\\IdeaProjects\\Kindergarten_corporate_website\\images";
/*Метод, загружающий картинку на сервер*/
    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        Path fullImagePath = Path.of(bucket, imagePath);
        try (content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        }
    }
/*Метод, позволяющий скачать картинку*/
    @SneakyThrows
    public Optional<byte[]> get(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);

        return Files.exists(fullImagePath)
                ? Optional.of(Files.readAllBytes(fullImagePath))
                : Optional.empty();
    }
}
