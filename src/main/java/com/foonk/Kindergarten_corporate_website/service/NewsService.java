package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.repository.NewsRepository;
import com.foonk.Kindergarten_corporate_website.dto.NewsCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.NewsReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.NewsCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.NewsReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
/*Сервис по работе с новостями*/
@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    private final NewsCreateEditMapper newsCreateEditMapper;

    private final NewsReadMapper newsReadMapper;
/*Метод, создающий новость в системе*/
    @Transactional
    public NewsReadDto create(NewsCreateEditDto news) {
        return Optional.of(news)
                .map(dto -> newsCreateEditMapper.map(dto))
                .map(newsRepository::save)
                .map(newsReadMapper::map)
                .orElseThrow();
    }
/*Метод, возвращающий страницу с новостями*/
    public Page<NewsReadDto> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(newsReadMapper::map);
    }
/*Метод находящий новость по ее id*/
    public Optional<NewsReadDto> findById(Long id) {
        return newsRepository.findById(id)
                .map((newsReadMapper::map));
    }
/*Метод, удаляющий новость из системы*/
    @Transactional
    public boolean delete(Long id) {
        return newsRepository.findById(id)
                .map(entity -> {
                    newsRepository.delete(entity);
                    newsRepository.flush();
                    return true;
                })
                .orElse(false);
    }
/*Метод, обновляющий новость*/
    public Optional<NewsReadDto> update(NewsCreateEditDto newsCreateEditDto, Long idNews) {

        return newsRepository.findById(idNews)
                .map(news -> {
                    return newsCreateEditMapper.map(newsCreateEditDto, news);
                })
                .map(newsRepository::saveAndFlush)
                .map(newsReadMapper::map);
    }
}

