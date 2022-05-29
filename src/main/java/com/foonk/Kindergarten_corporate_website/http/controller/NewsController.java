package com.foonk.Kindergarten_corporate_website.http.controller;

import com.foonk.Kindergarten_corporate_website.dto.NewsCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.NewsReadDto;
import com.foonk.Kindergarten_corporate_website.dto.PageResponse;
import com.foonk.Kindergarten_corporate_website.dto.UserCreateEditDto;
import com.foonk.Kindergarten_corporate_website.service.NewsService;
import liquibase.pro.packaged.S;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class NewsController {

   private final NewsService newsService;
    @GetMapping
    public String admin(Model model, @ModelAttribute("show_news") NewsReadDto news, Pageable pageable){
        Page<NewsReadDto> page = newsService.findAll(pageable);
        model.addAttribute("news", PageResponse.of(page));
        return "user/admin";
    }


    @GetMapping("/news/{id}")
    public String showNews(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){
        newsService.findById(id)
        .map(news->redirectAttributes.addFlashAttribute("show_news", news));
        return "redirect:/admin";
    }
    @PostMapping
    public String create(@ModelAttribute @Validated NewsCreateEditDto news) {
        newsService.create(news);
        return "redirect:/admin";
    }



}
