package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.News;
import com.foonk.Kindergarten_corporate_website.database.repository.NewsRepository;
import com.foonk.Kindergarten_corporate_website.dto.NewsCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.NewsReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.NewsCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.NewsReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class NewsServiceTest {
    @Mock
    private static NewsRepository newsRepository;
    @Mock
    private static NewsCreateEditMapper newsCreateEditMapper;
    @Mock
    private static NewsReadMapper newsReadMapper;
    @InjectMocks
    private static NewsService newsService;

    private static final News NEWS = new News(1L, "Заголовок", "Тело");

    private static final NewsReadDto NEWS_READ_DTO = new NewsReadDto(NEWS.getId(), NEWS.getHeader(), NEWS.getBody());

    private static final List<News> LIST = new ArrayList<>(Collections.singletonList(NEWS));

    private static final List<NewsReadDto> LIST_DTO = new ArrayList<>(Collections.singletonList(NEWS_READ_DTO));

    private static final NewsCreateEditDto NEWS_CREATE_EDIT_DTO = new NewsCreateEditDto(NEWS.getHeader(), NEWS.getBody());

    private static final NewsCreateEditDto NEWS_CREATE_EDIT_DTO_FOR_UPDATE = new NewsCreateEditDto("Обновление", "Новости");

    private static final News NEWS_FOR_UPDATE = new News(NEWS.getId(), NEWS_CREATE_EDIT_DTO_FOR_UPDATE.getHeader(), NEWS_CREATE_EDIT_DTO_FOR_UPDATE.getBody());

    private static final Pageable PAGEABLE = mock(Pageable.class);


    @Test
    void create() {
        doReturn(NEWS).when(newsCreateEditMapper).map(NEWS_CREATE_EDIT_DTO);
        doReturn(NEWS).when(newsRepository).save(NEWS);
        NewsReadDto map = doReturn(NEWS_READ_DTO).when(newsReadMapper).map(NEWS);
        NewsReadDto newsReadDto1 = newsService.create(NEWS_CREATE_EDIT_DTO);
        assertThat(newsReadDto1).isEqualTo(NEWS_READ_DTO);
        verify(newsCreateEditMapper).map(any(NewsCreateEditDto.class));
        verify(newsRepository).save(any(News.class));
        verifyNoMoreInteractions(newsCreateEditMapper, newsRepository);
    }

    @Test
    void findAll() {
        doReturn(new PageImpl<News>(LIST)).when(newsRepository).findAll(PAGEABLE);
        doReturn(NEWS_READ_DTO).when(newsReadMapper).map(any(News.class));
        Page<NewsReadDto> all = newsService.findAll(PAGEABLE);
        Page<NewsReadDto> result = new PageImpl<NewsReadDto>(LIST_DTO);
        assertThat(all).isEqualTo(result);
        verify(newsRepository).findAll(PAGEABLE);
        verify(newsReadMapper).map(any(News.class));
        verifyNoMoreInteractions(newsReadMapper, newsRepository);
    }

    ;

    @Test
    void findById() {
        doReturn(Optional.of(NEWS)).when(newsRepository).findById(NEWS_READ_DTO.getId());
        doReturn(Optional.of(NEWS_READ_DTO)).when(newsReadMapper).map(any(News.class));
        Optional<NewsReadDto> byId = newsService.findById(NEWS_READ_DTO.getId());
        Optional<NewsReadDto> result = Optional.of(NEWS_READ_DTO);
        assertThat(byId).isEqualTo(result);
        verify(newsRepository).findById(anyLong());
        verify(newsReadMapper).map(any(News.class));
        verifyNoMoreInteractions(newsRepository, newsReadMapper);
    }

    @Test
    void delete() {
        doReturn(Optional.of(NEWS)).when(newsRepository).findById(NEWS_READ_DTO.getId());
        doNothing().when(newsRepository).delete(any(News.class));
        doNothing().when(newsRepository).flush();
        boolean delete = newsService.delete(NEWS_READ_DTO.getId());
        assertThat(delete).isTrue();
        verify(newsRepository).delete(any(News.class));
        verify(newsRepository).flush();
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    void update() {
        doReturn(Optional.of(NEWS)).when(newsRepository).findById(NEWS_READ_DTO.getId());
        doReturn(NEWS_FOR_UPDATE).when(newsCreateEditMapper).map(NEWS_CREATE_EDIT_DTO, NEWS);
        doReturn(NEWS_FOR_UPDATE).when(newsRepository).saveAndFlush(NEWS_FOR_UPDATE);
        doReturn(new NewsReadDto(NEWS_FOR_UPDATE.getId(), NEWS_FOR_UPDATE.getHeader(), NEWS_FOR_UPDATE.getBody())).when(newsReadMapper).map(NEWS_FOR_UPDATE);
        Optional<NewsReadDto> update = newsService.update(NEWS_CREATE_EDIT_DTO, NEWS.getId());
        Optional<NewsReadDto> result = Optional.of(new NewsReadDto(NEWS.getId(), "Обновление", "Новости"));
        assertThat(update).isNotEmpty();
        assertThat(update).isEqualTo(result);
        verify(newsRepository).saveAndFlush(any(News.class));
        verify(newsRepository).findById(anyLong());
        verify(newsCreateEditMapper).map(any(NewsCreateEditDto.class), any(News.class));
        verify(newsReadMapper).map(any(News.class));
        verifyNoMoreInteractions(newsRepository, newsCreateEditMapper, newsReadMapper);
    }
}