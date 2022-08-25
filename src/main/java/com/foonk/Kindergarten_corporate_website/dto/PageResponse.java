package com.foonk.Kindergarten_corporate_website.dto;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

/*Обертка для работы с данными в страничном режиме. Разделяет данные на основную информацию, находящуюся в List<T> content и метаинформацию во вложенном классе Metadata*/
@Value
public class PageResponse <T> {
    List<T> content;
    Metadata metadata;

    public static <T> PageResponse<T> of(Page<T> page) {
        var metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements());
        return new PageResponse<>(page.getContent(), metadata);
    }
    @Value
    public static class Metadata{
        int page;
        int size;
        long totalElements;
    }
}
