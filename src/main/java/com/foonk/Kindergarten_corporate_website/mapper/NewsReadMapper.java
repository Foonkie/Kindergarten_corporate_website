package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.News;
import com.foonk.Kindergarten_corporate_website.dto.NewsReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NewsReadMapper implements Mapper<News, NewsReadDto> {

    @Override
    public NewsReadDto map(News object) {
        return new NewsReadDto(
                object.getId(),
                object.getHeader(),
                object.getBody()
        );
    }
}
