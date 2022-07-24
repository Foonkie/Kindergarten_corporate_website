package com.foonk.Kindergarten_corporate_website.http.controller;

import com.foonk.Kindergarten_corporate_website.dto.NewsReadDto;
import com.foonk.Kindergarten_corporate_website.dto.PageResponse;
import com.foonk.Kindergarten_corporate_website.dto.TaskReadDto;
import com.foonk.Kindergarten_corporate_website.dto.UserReadDto;
import com.foonk.Kindergarten_corporate_website.service.DocumentService;
import com.foonk.Kindergarten_corporate_website.service.NewsService;
import com.foonk.Kindergarten_corporate_website.service.TaskService;
import com.foonk.Kindergarten_corporate_website.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TeachersController {

    private final NewsService newsService;
    private final DocumentService documentService;
    private final TaskService taskService;
    private final UserService userService;

    @GetMapping("/news")
    public String newsForUser(Model model, @ModelAttribute("show_news") NewsReadDto news, Pageable pageable, HttpSession httpSession ){
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
        return "user/news";
    }

    @GetMapping("/news/{id}")
    public String showNewsForUser(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes){
        newsService.findById(id)
                .map(news->redirectAttributes.addFlashAttribute("show_news", news));
        return "redirect:/news";
    }

    @GetMapping("/documents/{kind}/{id}")
    public String getDocument(Model model, @PathVariable Long id, @PathVariable String kind){
        documentService.findDocumentById(id).map(document -> documentService.get(document.getDocument(),kind));
        return "redirect:/documents";
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

    @GetMapping("/tasks")
    public String tasksByUser(Model model) {
        List<UserReadDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/admin_tasks";
    }

    @GetMapping("/tasks/{id1}")
    public String tasksByUserId(Model model, @PathVariable Long id1, Pageable pageable) {

        Page<TaskReadDto> allByUser_id = taskService.findAllByUser_Id(id1, pageable);
        model.addAttribute("allByUser_id", allByUser_id);
        return "user/admin_tasks_id";
    }

    @GetMapping("/tasks/{id1}")
    public String tasksByUserId(Model model, @PathVariable Long id1, Pageable pageable) {

        Page<TaskReadDto> allByUser_id = taskService.findAllByUser_Id(id1, pageable);
        model.addAttribute("allByUser_id", allByUser_id);
        return "user/admin_tasks_id";
    }



}
