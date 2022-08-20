package com.foonk.Kindergarten_corporate_website.mapper;

import com.foonk.Kindergarten_corporate_website.database.News;
import com.foonk.Kindergarten_corporate_website.dto.NewsCreateEditDto;
import org.springframework.stereotype.Component;

@Component
public class NewsCreateEditMapper implements Mapper<NewsCreateEditDto, News> {
    @Override
    public News map(NewsCreateEditDto object) {
        News news = new News();
        copy(object, news);
        return news;
    }

    public News map(NewsCreateEditDto fromObject, News toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(NewsCreateEditDto object, News news) {
        news.setHeader(object.getHeader());
        news.setBody(object.getBody());
    }
}
