package com.foonk.Kindergarten_corporate_website.http.controller;

import com.foonk.Kindergarten_corporate_website.database.Kind;
import com.foonk.Kindergarten_corporate_website.dto.*;
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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


@Slf4j
@Controller
@RequiredArgsConstructor
public class NewsController {
   private final DocumentService documentService;
   private final NewsService newsService;
    @GetMapping("/admin")
    public String admin(){
        return "user/admin";
    }

    @GetMapping("/admin/news")
    public String news(Model model, @ModelAttribute("show_news") NewsReadDto news, Pageable pageable, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (!(pageable.getPageSize()==20 && pageable.getPageNumber()==0)){
        httpSession.setAttribute("pageable_after", pageable);

        Page<NewsReadDto> page = newsService.findAll(pageable);
        model.addAttribute("news", PageResponse.of(page));
        }
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
            PageResponse<NewsReadDto> newsForEdit = (PageResponse<NewsReadDto>) model.getAttribute("news");
            redirectAttributes.addFlashAttribute("news", newsForEdit);
        }
        return "user/admin_news";
    }
    @GetMapping("/admin/first_page")
    public String admin_first()
    {
        return "user/admin_first_page";
    }

    @GetMapping("/admin/documents")
    public String admin_documents(Model model){
        Kind[] values = Kind.values();
        List<Kind> kind_kinds = Arrays.asList(values);
        List<String> kinds = kind_kinds.stream().map(kind_kind -> kind_kind.toString()).collect(toList());
        model.addAttribute("kinds", kinds);
        model.addAttribute("ktp", documentService.findAllDocumentByKind("KTP"));
        model.addAttribute("work_projects", documentService.findAllDocumentByKind("WORK_PROJECTS"));
        model.addAttribute("schedule", documentService.findAllDocumentByKind("SCHEDULE"));
        model.addAttribute("event", documentService.findAllDocumentByKind("EVENT"));
        model.addAttribute("instruction", documentService.findAllDocumentByKind("INSTRUCTION"));
        model.addAttribute("general_works", documentService.findAllDocumentByKind("GENERAL_WORKS"));
        return "user/admin_documents";
    }

    @GetMapping("/admin/russian")
    public String findAllByKind(Model model) {
        model.addAttribute("ktp", documentService.findAllDocumentByKind("KTP"));
        model.addAttribute("work_projects", documentService.findAllDocumentByKind("WORK_PROJECTS"));
        model.addAttribute("schedule", documentService.findAllDocumentByKind("SCHEDULE"));
        model.addAttribute("event", documentService.findAllDocumentByKind("EVENT"));
        model.addAttribute("instruction", documentService.findAllDocumentByKind("INSTRUCTION"));
        model.addAttribute("general_works", documentService.findAllDocumentByKind("GENERAL_WORKS"));
        return "user/admin_russian";
    }
    @GetMapping("/admin/news/{id}")
    public String showNews(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){
        newsService.findById(id)
        .map(news->redirectAttributes.addFlashAttribute("show_news", news));
        return "redirect:/admin/news";
    }

    @GetMapping("/admin/documents/{kind}/{id}")
    public String getDocument(Model model, @PathVariable Long id, @PathVariable String kind){
        documentService.findDocumentById(id).map(document -> documentService.get(document.getDocument(),kind));
        return "redirect:/admin/documents";
    }

    @GetMapping("/admin/news/edit/{id}")
    public String editNews(Model model, @PathVariable Long id,PageResponse<NewsReadDto> news){
        Optional<NewsReadDto> newsReadDto = newsService.findById(id);
       return newsReadDto.map(newsForEdit->{
        model.addAttribute("newsReadDto",newsForEdit);
        return "user/admin_news_edit";})
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/admin/news")
    public String create(@ModelAttribute @Validated NewsCreateEditDto news) {
        newsService.create(news);
        return "redirect:/admin/news";
    }

    @PostMapping("/admin/documents/create")
    public String admin_documents_create(Model model, DocumentCreateEditDto dto)
    {
        documentService.create(dto);
        return "redirect:/admin/documents";
    }

    @PostMapping("/admin/documents/delete")
    public String admin_documents_delete(Model model, Long idDocument)
    {
        documentService.delete(idDocument);
        return "redirect:/admin/documents";
    }


    @PostMapping("/admin/news/edit")
    public String admin_news_delete(Model model, Long id, @RequestParam(name = "action") String action, Pageable pageable)
    {
        if (action.equals("Edit")) {
            return "redirect:/admin/news/edit/"+id;
        }

        if (action.equals("Delete")) {
            newsService.delete(id);
        }

        return "redirect:/admin/news";
    }
    @PostMapping("/admin/news/edit/{id}")
    public String admin_news_edit(NewsCreateEditDto newsCreateEditDto, @PathVariable Long id)
    {
        newsService.update(newsCreateEditDto, id);
      return "redirect:/admin/news";
    }




}
