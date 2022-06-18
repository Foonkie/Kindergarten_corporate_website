package com.foonk.Kindergarten_corporate_website.http.controller;

import com.foonk.Kindergarten_corporate_website.dto.NewsCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.NewsReadDto;
import com.foonk.Kindergarten_corporate_website.dto.PageResponse;
import com.foonk.Kindergarten_corporate_website.dto.UserCreateEditDto;
import com.foonk.Kindergarten_corporate_website.service.DocumentService;
import com.foonk.Kindergarten_corporate_website.service.NewsService;
import liquibase.pro.packaged.C;
import liquibase.pro.packaged.P;
import liquibase.pro.packaged.S;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class NewsController {
   private final DocumentService documentService;
   private final NewsService newsService;
    @GetMapping
    public String admin(Model model, @ModelAttribute("show_news") NewsReadDto news, Pageable pageable, HttpSession httpSession ) {
        if (!(pageable.getPageSize()==20 && pageable.getPageNumber()==0)){
        httpSession.setAttribute("pageable_after", pageable);

        Page<NewsReadDto> page = newsService.findAll(pageable);
        model.addAttribute("news", PageResponse.of(page));}
        else {
            Pageable pageable_after1 = (Pageable) httpSession.getAttribute("pageable_after");
            if (!(pageable_after1 ==null))
            {
                Page<NewsReadDto> page = newsService.findAll(pageable_after1);
                model.addAttribute("news", PageResponse.of(page));
            }
            else {
                Page<NewsReadDto> page = newsService.findAll(pageable);
                model.addAttribute("news", PageResponse.of(page));
            }
        }

        return "user/admin";
    }
    @GetMapping("/first_page")
    public String admin_first(){
        return "user/admin_first_page";
    }
    @GetMapping("/russian")
    public String findAllByKind(Model model) {
        model.addAttribute("ktp", documentService.findAllDocumentByKind("KTP"));
        model.addAttribute("work_projects", documentService.findAllDocumentByKind("WORK_PROJECTS"));
        model.addAttribute("schedule", documentService.findAllDocumentByKind("SCHEDULE"));
        model.addAttribute("event", documentService.findAllDocumentByKind("EVENT"));
        model.addAttribute("instruction", documentService.findAllDocumentByKind("INSTRUCTION"));
        model.addAttribute("general_works", documentService.findAllDocumentByKind("GENERAL_WORKS"));
        return "user/admin_russian";
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
