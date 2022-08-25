package com.foonk.Kindergarten_corporate_website.http.controller;

import com.foonk.Kindergarten_corporate_website.dto.NewsCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.NewsReadDto;
import com.foonk.Kindergarten_corporate_website.dto.PageResponse;
import com.foonk.Kindergarten_corporate_website.service.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/*Контроллер для работы адинистратора с новостями.*/
@Slf4j
@Controller
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

/*Метод возвращающий страницу со списком новостей*/
    @GetMapping("/admin/news")
    public String news(Model model, @ModelAttribute("show_news") NewsReadDto news, Pageable pageable, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (!(pageable.getPageSize() == 20 && pageable.getPageNumber() == 0)) {
            httpSession.setAttribute("pageable_after", pageable);

            Page<NewsReadDto> page = newsService.findAll(pageable);
            model.addAttribute("news", PageResponse.of(page));
        } else {
            Pageable pageable_after = (Pageable) httpSession.getAttribute("pageable_after");
            if (!(pageable_after == null)) {
                Page<NewsReadDto> page = newsService.findAll(pageable_after);
                model.addAttribute("news", PageResponse.of(page));
            } else {
                Page<NewsReadDto> page = newsService.findAll(pageable);
                model.addAttribute("news", PageResponse.of(page));
            }
            PageResponse<NewsReadDto> newsForEdit = (PageResponse<NewsReadDto>) model.getAttribute("news");
            redirectAttributes.addFlashAttribute("news", newsForEdit);
        }
        return "user/admin_news";
    }

/*Метод возвращающий страницу со списком новостей и открытой выбранной новостью*/
    @GetMapping("/admin/news/{id}")
    public String showNews(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        newsService.findById(id)
                .map(news -> redirectAttributes.addFlashAttribute("show_news", news));
        return "redirect:/admin/news";
    }

/*Метод для редакттирования новости*/
    @GetMapping("/admin/news/edit/{id}")
    public String editNews(Model model, @PathVariable Long id, PageResponse<NewsReadDto> news) {
        Optional<NewsReadDto> newsReadDto = newsService.findById(id);
        return newsReadDto.map(newsForEdit -> {
            model.addAttribute("newsReadDto", newsForEdit);
            return "user/admin_news_edit";
        })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

/*Метод для создания новости*/
    @PostMapping("/admin/news")
    public String create(@ModelAttribute @Validated NewsCreateEditDto news) {
        newsService.create(news);
        return "redirect:/admin/news";
    }
/*Метод для редактирования или удаления новости*/
    @PostMapping("/admin/news/edit")
    public String admin_news_delete(Model model, Long id, @RequestParam(name = "action") String action, Pageable pageable) {
        if (action.equals("Edit")) {
            return "redirect:/admin/news/edit/" + id;
        }
        if (action.equals("Delete")) {
            newsService.delete(id);
        }
        return "redirect:/admin/news";
    }
/*Метод для редактирования новости*/
    @PostMapping("/admin/news/edit/{id}")
    public String admin_news_edit(NewsCreateEditDto newsCreateEditDto, @PathVariable Long id) {
        newsService.update(newsCreateEditDto, id);
        return "redirect:/admin/news";
    }


}
