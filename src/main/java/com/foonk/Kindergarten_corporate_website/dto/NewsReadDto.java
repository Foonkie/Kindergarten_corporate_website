package com.foonk.Kindergarten_corporate_website.dto;

import lombok.Value;

@Value
public class NewsReadDto {
    private Long id;

    private String header;

    private String body;
}
