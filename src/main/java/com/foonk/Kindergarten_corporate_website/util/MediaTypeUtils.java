package com.foonk.Kindergarten_corporate_website.util;

import lombok.experimental.UtilityClass;

import javax.servlet.ServletContext;

import org.springframework.http.MediaType;

@UtilityClass
public class MediaTypeUtils {
    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        String mineType = servletContext.getMimeType(fileName);
        try {
            MediaType mediaType = MediaType.parseMediaType(mineType);
            return mediaType;
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
