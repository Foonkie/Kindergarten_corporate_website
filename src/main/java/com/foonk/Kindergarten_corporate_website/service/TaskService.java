package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.database.Task;
import com.foonk.Kindergarten_corporate_website.database.User;

import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.database.repository.TaskRepository;
import com.foonk.Kindergarten_corporate_website.database.repository.UserRepository;
import com.foonk.Kindergarten_corporate_website.dto.*;

import com.foonk.Kindergarten_corporate_website.mapper.TaskCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.TaskReadMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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




    public Page<TaskReadDto> findAllByUser_Id(Long userId, Pageable pageable){
        return taskRepository.findAllByUser_Id(userId, pageable)
                .map(taskReadMapper::map);
    }

    public Page<TaskReadDto> findAllByUser_Username(String username, Pageable pageable){
        return taskRepository.findAllByUser_Username(username, pageable)
                .map(taskReadMapper::map);
    }

    public TaskReadDto create(TaskCreateEditDto taskCreateEditDto){
        return Optional.of(taskCreateEditDto)
                .map(dto->taskCreateEditMapper.map(taskCreateEditDto))
                .map(taskRepository::save)
                .map(taskReadMapper::map)
                .orElseThrow();
    }
   @Transactional
    public boolean delete(Long id){
        return taskRepository.findById(id)
                .map(task->{
                    taskRepository.delete(task);
                    taskRepository.flush();
                    return true;})
                .orElse(false);
    }
    @Transactional
    public Optional<TaskReadDto> update(TaskCreateEditDto taskCreateEditDto,Long id){
        return taskRepository.findById(id)
                .map(task->{
                 return taskCreateEditMapper.map(taskCreateEditDto, task);
                })
                .map(taskRepository::saveAndFlush)
                .map(taskReadMapper::map);
    }
    public Optional<TaskReadDto> findById(Long id) {
        return taskRepository.findById(id)
                .map(taskReadMapper::map);

    }


   public List<SubTaskReadDto> updateSubTask(List<SubTaskCreateEditDto> subTaskCreateEditDtos, Long taskId){
       List<String> list = subTaskCreateEditDtos.stream().filter(subtask->!subtask.getSubtask().isBlank()).map(dto -> dto.getSubtask()).toList();
       subTaskRepository.findAllByTask_Id(taskId).stream().filter(subTask->!list.contains(subTask.getSubtask()))
                 .forEach(subTask-> {
                     subTaskRepository.delete(subTask);
                     subTaskRepository.flush();
                 });
       List<String> list1 = subTaskRepository.findAllByTask_Id(taskId).stream().map(subtask -> subtask.getSubtask()).toList();
       List<SubTaskReadDto> subTaskReadDtos=new ArrayList<>();
       for (SubTaskCreateEditDto dto:subTaskCreateEditDtos){
           if (list1.contains(dto.getSubtask())){
              subTaskReadDtos.add(subTaskService.update(dto, taskId));
           }
           else if (!dto.getSubtask().isEmpty())
           {
               subTaskReadDtos.add(subTaskService.create(dto));
           }
       }
     return  subTaskReadDtos;
   }

}
