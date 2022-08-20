package com.foonk.Kindergarten_corporate_website.http.controller;

import com.foonk.Kindergarten_corporate_website.database.Kind;
import com.foonk.Kindergarten_corporate_website.dto.*;
import com.foonk.Kindergarten_corporate_website.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Controller
public class TeachersController {

    private final NewsService newsService;
    private final DocumentService documentService;
    private final TaskService taskService;
    private final UserService userService;
    private final SubTaskService subTaskService;


    @GetMapping("/documents/{kind}/{id}")
    public String getDocument(Model model, @PathVariable Long id, @PathVariable String kind) {
        documentService.findDocumentById(id).map(document -> documentService.get(document.getDocument(), kind));
        return "redirect:/documents";
    }


    @GetMapping("/russian")
    public String russian(Model model, @ModelAttribute("show_news") NewsReadDto news, Pageable pageable, HttpSession httpSession) {
        if (!(pageable.getPageSize() == 20 && pageable.getPageNumber() == 0)) {
            httpSession.setAttribute("pageable_after", pageable);

            Page<NewsReadDto> page = newsService.findAll(pageable);
            model.addAttribute("news", PageResponse.of(page));
        } else {
            Pageable pageable_after1 = (Pageable) httpSession.getAttribute("pageable_after");
            if (!(pageable_after1 == null)) {
                Page<NewsReadDto> page = newsService.findAll(pageable_after1);
                model.addAttribute("news", PageResponse.of(page));
            } else {
                Page<NewsReadDto> page = newsService.findAll(pageable);
                model.addAttribute("news", PageResponse.of(page));
            }
        }
        return "user/russian";
    }

    @GetMapping("/news/{id}")
    public String showNewsForUser(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        newsService.findById(id)
                .map(news -> redirectAttributes.addFlashAttribute("show_news", news));
        return "redirect:/russian";
    }

    @GetMapping("/tasks")
    public String tasks(Model model, @ModelAttribute("subTasks") ArrayList<SubTaskReadDto> subTasks, TaskCreateEditDto taskCreateEditDto, HttpSession httpSession, Pageable pageable) {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        Page<TaskReadDto> allByUser_username = taskService.findAllByUser_Username(username, pageable);
        TaskReadDto taskReadDto = new TaskReadDto(null, null, null, null, null, null);
        model.addAttribute("tasks", PageResponse.of(allByUser_username));
        if (subTasks.isEmpty()) {
            model.addAttribute("taskCreateEditDto", taskCreateEditDto);
        }
        if (!subTasks.isEmpty()) {
            Long taskId = subTasks.get(0).getTaskId();
            taskReadDto = taskService.findById(taskId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            ;
            httpSession.setAttribute("taskId", taskId);
            TaskCreateEditDto taskCreateEditDtoAfterReading = taskService.getTaskCreateEditDto(taskReadDto);
            model.addAttribute("taskCreateEditDto", taskCreateEditDtoAfterReading);
        }
        return "user/tasks";
    }

    @GetMapping("/tasks/{id}")
    public String tasks(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        ArrayList<SubTaskReadDto> subtasks = (ArrayList<SubTaskReadDto>) subTaskService.findByTaskId(id);
        redirectAttributes.addFlashAttribute("subTasks", subtasks);
        return "redirect:/tasks";
    }

    @GetMapping("/documents")
    public String documents(Model model) {
        Kind[] values = Kind.values();
        List<Kind> kind_kinds = Arrays.asList(values);
        List<String> kinds = kind_kinds.stream().map(kind_kind -> kind_kind.toString()).collect(toList());
        model.addAttribute("kinds", kinds);
        model.addAttribute("ktp", documentService.findAllDocumentByKind("KTP"));
        model.addAttribute("work_projects", documentService.findAllDocumentByKind("WORK_PROJECTS"));
        model.addAttribute("schedule", documentService.findAllDocumentByKind("SCHEDULE"));
        model.addAttribute("event", documentService.findAllDocumentByKind("EVENT"));
        model.addAttribute("instruction", documentService.findAllDocumentByKind("INSTRUCTION"));
        return "user/documents";
    }

    @PostMapping("/tasks")
    public String updateStatus(Model model, TaskCreateEditDto taskCreateEditDto, HttpSession httpSession) {
        ArrayList<SubTaskReadDto> subTasks = (ArrayList<SubTaskReadDto>) httpSession.getAttribute("subTasks");
        Long taskId = (Long) httpSession.getAttribute("taskId");
        taskService.updateSubTask(taskCreateEditDto.getSubTaskCreateEditDtos(), taskId);
        return "redirect:/tasks";
    }

}
