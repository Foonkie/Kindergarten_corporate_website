package com.foonk.Kindergarten_corporate_website.http.controller;

import com.foonk.Kindergarten_corporate_website.dto.SubTaskReadDto;
import com.foonk.Kindergarten_corporate_website.dto.TaskCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.TaskReadDto;
import com.foonk.Kindergarten_corporate_website.dto.UserReadDto;
import com.foonk.Kindergarten_corporate_website.service.SubTaskService;
import com.foonk.Kindergarten_corporate_website.service.TaskService;
import com.foonk.Kindergarten_corporate_website.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Controller
@AllArgsConstructor
public class TaskController {
  private final TaskService taskService;

  private final UserService userService;

  private final SubTaskService subTaskService;

    @GetMapping("/admin/tasks")
    public String tasksByUser(Model model){
        List<UserReadDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/admin_tasks";
    }
    @GetMapping("/admin/tasks/{id}")
   public String tasksByUserId (Model model, @PathVariable Long id, Pageable pageable){

        Page<TaskReadDto> allByUser_id = taskService.findAllByUser_Id(id, pageable);
     model.addAttribute("allByUser_id", allByUser_id);
        return "user/admin_tasks_id";
    }
    @GetMapping("/admin/tasks/{id1}/{id2}")
    public String tasksByUserId (Model model, @PathVariable Long id1, @PathVariable Long id2, Pageable pageable){
        return "user/admin_tasks_id";
    }
    @PostMapping("/admin/tasks/{id}/create")
    public String create (TaskCreateEditDto taskCreateEditDto, Model model, @PathVariable Long id, Pageable pageable){
        List<SubTaskReadDto> allByTask_id = subTaskService.findAllByTask_Id(id);

        model.addAttribute("subtasks", allByTask_id);

        return "redirect:/admin/tasks/{id}";
    }
}
