package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.database.repository.TaskRepository;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskReadDto;
import com.foonk.Kindergarten_corporate_website.dto.TaskCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.TaskReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.TaskCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.TaskReadMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final TaskReadMapper taskReadMapper;

    private final TaskCreateEditMapper taskCreateEditMapper;

    private final SubTaskRepository subTaskRepository;

    private final SubTaskService subTaskService;


    public Page<TaskReadDto> findAllByUser_Id(Long userId, Pageable pageable) {
        return taskRepository.findAllByUser_Id(userId, pageable)
                .map(taskReadMapper::map);
    }

    public Page<TaskReadDto> findAllByUser_Username(String username, Pageable pageable) {
        return taskRepository.findAllByUser_Username(username, pageable)
                .map(taskReadMapper::map);
    }

    public TaskReadDto create(TaskCreateEditDto taskCreateEditDto) {
        return Optional.of(taskCreateEditDto)
                .map(dto -> taskCreateEditMapper.map(taskCreateEditDto))
                .map(taskRepository::save)
                .map(taskReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public boolean delete(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    taskRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<TaskReadDto> update(TaskCreateEditDto taskCreateEditDto, Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    return taskCreateEditMapper.map(taskCreateEditDto, task);
                })
                .map(taskRepository::saveAndFlush)
                .map(taskReadMapper::map);
    }

    public Optional<TaskReadDto> findById(Long id) {
        return taskRepository.findById(id)
                .map(taskReadMapper::map);

    }


    public List<SubTaskReadDto> updateSubTask(List<SubTaskCreateEditDto> subTaskCreateEditDtos, Long taskId) {
        List<String> list = subTaskCreateEditDtos.stream().filter(subtask -> !subtask.getSubtask().isBlank()).map(dto -> dto.getSubtask()).toList();
        subTaskRepository.findAllByTask_Id(taskId).stream().filter(subTask -> !list.contains(subTask.getSubtask()))
                .forEach(subTask -> {
                    subTaskRepository.delete(subTask);
                    subTaskRepository.flush();
                });
        List<String> list1 = subTaskRepository.findAllByTask_Id(taskId).stream().map(subtask -> subtask.getSubtask()).toList();
        List<SubTaskReadDto> subTaskReadDtos = new ArrayList<>();
        for (SubTaskCreateEditDto dto : subTaskCreateEditDtos) {
            if (list1.contains(dto.getSubtask())) {
                subTaskReadDtos.add(subTaskService.update(dto, taskId));
            } else if (!dto.getSubtask().isEmpty()) {
                subTaskReadDtos.add(subTaskService.create(dto));
            }
        }
        return subTaskReadDtos;
    }

    public List<String> getSubtaskListForUpdate(TaskReadDto taskReadDto) {
        List<String> listForUpdate = taskReadDto.getSubTaskReadDtos().stream().map(subTaskReadDto -> subTaskReadDto.getSubtask()).toList();
        int size = taskReadDto.getSubTaskReadDtos().size();
        for (int i = 0; i < 5-size; i++) {
            listForUpdate.add("");
        }
        return listForUpdate;
    }

    public TaskReadDto taskCreation(TaskCreateEditDto taskCreateEditDto) {
        TaskReadDto taskReadDto = create(taskCreateEditDto);
        List<SubTaskCreateEditDto> subTaskCreateEditDtos = taskCreateEditDto.getSubTaskCreateEditDtos();
        Long id = taskReadDto.getId();
        for (int i = 0; i < 5; i++) {
            if (!subTaskCreateEditDtos.get(i).getSubtask().isBlank()){
                subTaskCreateEditDtos.get(i).setTaskId(id);}
        }
        List<SubTaskReadDto> subTaskReadDtos = subTaskCreateEditDtos.stream().filter(subTaskCreateEditDto -> !subTaskCreateEditDto.getSubtask().isBlank()).map(subTaskService::create).toList();
        return taskReadDto;
    }

    public TaskCreateEditDto getTaskCreateEditDto(TaskReadDto taskReadDto) {
        TaskCreateEditDto taskCreateEditDtoAfterReading=new TaskCreateEditDto(taskReadDto.getType(), taskReadDto.getTask_header(), taskReadDto.getEndTime(), taskReadDto.getId());
        taskReadDto.getSubTaskReadDtos().stream().forEach(subTaskReadDto -> taskCreateEditDtoAfterReading.add(new SubTaskCreateEditDto(subTaskReadDto.getSubtask(), subTaskReadDto.getId(), subTaskReadDto.getStatus())));
        for (int i = 0; i < taskCreateEditDtoAfterReading.getSubTaskCreateEditDtos().size()-1; i++) {
            if (taskCreateEditDtoAfterReading.getSubTaskCreateEditDtos().get(i).getSubtask().isEmpty()){
                taskCreateEditDtoAfterReading.getSubTaskCreateEditDtos().remove(i);
                i--;
            };
        }
        return taskCreateEditDtoAfterReading;
    }

    public String fullTaskUpdate(TaskCreateEditDto taskCreateEditDto, Long id1) {
       String link = update(taskCreateEditDto, id1).
                map(it -> {
                    Long id = it.getId();
                    List<SubTaskCreateEditDto> subTaskCreateEditDtos = taskCreateEditDto.getSubTaskCreateEditDtos();
                    for (int i = 0; i < 5; i++) {
                        if (!subTaskCreateEditDtos.get(i).getSubtask().isBlank()){
                            subTaskCreateEditDtos.get(i).setTaskId(id);}
                    }
                    updateSubTask(subTaskCreateEditDtos, id1);
                    return "redirect:/admin/tasks";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return link;
    }
}
