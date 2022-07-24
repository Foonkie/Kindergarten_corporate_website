package com.foonk.Kindergarten_corporate_website.http.controller;

import com.foonk.Kindergarten_corporate_website.database.Type;
import com.foonk.Kindergarten_corporate_website.database.User;
import com.foonk.Kindergarten_corporate_website.dto.*;
import com.foonk.Kindergarten_corporate_website.service.SubTaskService;
import com.foonk.Kindergarten_corporate_website.service.TaskService;
import com.foonk.Kindergarten_corporate_website.service.UserService;
import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Controller
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    private final UserService userService;

    private final SubTaskService subTaskService;

    @GetMapping("/admin/tasks")
    public String tasksByUser(Model model) {
        List<UserReadDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "user/admin_tasks";
    }

    @GetMapping("/admin/tasks/{id1}")
    public String tasksByUserId(Model model, @PathVariable Long id1, Pageable pageable) {

        Page<TaskReadDto> allByUser_id = taskService.findAllByUser_Id(id1, pageable);
        model.addAttribute("allByUser_id", allByUser_id);
        return "user/admin_tasks_id";
    }

    @GetMapping("/admin/tasks/{id1}/{id2:\\d+}")
    public String tasksByUserId(Model model, @PathVariable Long id1, @PathVariable Long id2, Pageable pageable) {
        return "user/admin_tasks_id";
    }

    @GetMapping("/admin/tasks/{id1}/delete")
    public String deleteTask(@PathVariable Long id1){
        if(!taskService.delete(id1)){
     throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        return "redirect:/admin/tasks/{id1}";
    }


    @GetMapping("/admin/tasks/create")
    public String preСreate(Model model,TaskCreateEditDto taskCreateEditDto, Pageable pageable) {
        String modify="create";
        model.addAttribute("modify", modify);
        List<UserReadDto> users = userService.findAll();
        List<String> types = Arrays.asList(Type.values()).stream().map(list -> list.toString()).collect(toList());
        model.addAttribute("users", users);
        model.addAttribute("types", types);
        model.addAttribute("taskCreateEditDto", taskCreateEditDto);
        TaskReadDto taskReadDto = new TaskReadDto(new ArrayList<>(Arrays.asList(new SubTaskReadDto(null,null, null), new SubTaskReadDto(null,null, null), new SubTaskReadDto(null,null, null), new SubTaskReadDto(null,null, null), new SubTaskReadDto(null,null, null) )), null, null, null, null, null);
        model.addAttribute("taskReadDto", taskReadDto);
        List<String> listForUpdate;
        listForUpdate =taskReadDto.getSubTaskReadDtos().stream().map(subTaskReadDto -> subTaskReadDto.getSubtask()).toList();
        int size = taskReadDto.getSubTaskReadDtos().size();
        for (int i = 0; i < 5-size; i++) {
            listForUpdate.add("");
        }
        model.addAttribute("listForUpdate", listForUpdate);
        return "user/admin_tasks_id";
    }

    @PostMapping("/admin/tasks/create")
    public String create(Model model, TaskCreateEditDto taskCreateEditDto, Pageable pageable) {
        TaskReadDto taskReadDto = taskService.create(taskCreateEditDto);
        List<SubTaskCreateEditDto> subTaskCreateEditDtos = taskCreateEditDto.getSubTaskCreateEditDtos();
        Long id = taskReadDto.getId();
        for (int i = 0; i < 5; i++) {
            if (!subTaskCreateEditDtos.get(i).getSubtask().isBlank()){
            subTaskCreateEditDtos.get(i).setTaskId(id);}
        }
        List<SubTaskReadDto> subTaskReadDtos = subTaskCreateEditDtos.stream().filter(subTaskCreateEditDto -> !subTaskCreateEditDto.getSubtask().isBlank()).map(subTaskService::create).toList();
        return "redirect:/admin/tasks";
    }
    @GetMapping("/admin/tasks/{id1}/update")
    public String preUpdate(Model model,TaskCreateEditDto taskCreateEditDto,@PathVariable("id1") Long id1, Pageable pageable){
        String modify=id1.toString().concat("/update");
        model.addAttribute("modify", modify);
        List<UserReadDto> users = userService.findAll();
        List<String> types = Arrays.asList(Type.values()).stream().map(list -> list.toString()).collect(toList());
        model.addAttribute("users", users);
        model.addAttribute("types", types);
        model.addAttribute("taskCreateEditDto", taskCreateEditDto);
        TaskReadDto taskReadDto = taskService.findById(id1)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("taskReadDto", taskReadDto);
        List<String> listForUpdate;
        listForUpdate =taskReadDto.getSubTaskReadDtos().stream().map(subTaskReadDto -> subTaskReadDto.getSubtask()).toList();
        int size = taskReadDto.getSubTaskReadDtos().size();
        for (int i = 0; i < 5-size; i++) {
            listForUpdate.add("");
        }
        model.addAttribute("listForUpdate", listForUpdate);
        return "user/admin_tasks_id";
    }
    @PostMapping("/admin/tasks/{id1}/update")
    public String update(TaskCreateEditDto taskCreateEditDto, @PathVariable("id1") Long id1){
        String link = taskService.update(taskCreateEditDto, id1).
                map(it -> {
                    Long id = it.getId();
                    List<SubTaskCreateEditDto> subTaskCreateEditDtos = taskCreateEditDto.getSubTaskCreateEditDtos();
                    for (int i = 0; i < 5; i++) {
                        if (!subTaskCreateEditDtos.get(i).getSubtask().isBlank()){
                            subTaskCreateEditDtos.get(i).setTaskId(id);}
                    }
                   taskService.updateSubTask(subTaskCreateEditDtos, id1);
                    return "redirect:/admin/tasks";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return link;
    }

}
