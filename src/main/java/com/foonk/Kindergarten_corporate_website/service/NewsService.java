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

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    private final NewsCreateEditMapper newsCreateEditMapper;

    private final NewsReadMapper newsReadMapper;

    @Transactional
    public NewsReadDto create(NewsCreateEditDto news) {
        return Optional.of(news)
                .map(dto -> newsCreateEditMapper.map(dto))
                .map(newsRepository::save)
                .map(newsReadMapper::map)
                .orElseThrow();
    }

    public Page<NewsReadDto> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable)
                .map(newsReadMapper::map);
    }

    public Optional<NewsReadDto> findById(Long id) {
        return newsRepository.findById(id)
                .map((newsReadMapper::map));
    }

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

    public Optional<NewsReadDto> update(NewsCreateEditDto newsCreateEditDto, Long idNews) {

        return newsRepository.findById(idNews)
                .map(news -> {
                    return newsCreateEditMapper.map(newsCreateEditDto, news);
                })
                .map(newsRepository::saveAndFlush)
                .map(newsReadMapper::map);
    }
}

