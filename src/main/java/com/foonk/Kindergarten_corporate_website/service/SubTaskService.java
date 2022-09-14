package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskEditDto;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.SubTaskCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.SubTaskReadMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*Сервис по работе с подзадачами*/
@Service
@AllArgsConstructor
public class SubTaskService {
    private final SubTaskCreateEditMapper subTaskCreateEditMapper;

    private final SubTaskReadMapper subTaskReadMapper;

    private final SubTaskRepository subTaskRepository;
/*Метод, создающий подзадачу в системе*/
    public SubTaskReadDto create(SubTaskCreateEditDto subTaskCreateEditDto) {
        return Optional.ofNullable(subTaskCreateEditDto)
                .map(subTaskCreateEditMapper::map)
                .map(subTaskRepository::save)
                .map(subTaskReadMapper::map)
                .orElseThrow();
    }
/*Метод, находящий список подзадач по id задачи*/
    public List<SubTaskReadDto> findByTaskId(Long id) {
        return subTaskRepository.findAllByTask_Id(id).stream()
                .map(subTaskReadMapper::map)
                .sorted(new SubTaskReadDto.SubTaskReadDtoComparator())
                .collect(Collectors.toList());
    }

//    @Transactional
//    public List<SubTaskReadDto> update(SubTaskEditDto subTaskEditDto, Long id, ArrayList<SubTaskReadDto> subTasks) {
//
//        List<String> subTasksFromDto = subTasks.stream().map(SubTaskReadDto::getSubtask).collect(Collectors.toList());
//        List<Boolean> booleans = subTaskEditDto.getSubTaskCreateEditDtos().stream().map(SubTaskCreateEditDto::getStatus).toList();
//        int i = 0;
//        List<String> subTasksFromDtoFilter = new ArrayList<>();
//        for (String subTask : subTasksFromDto) {
//            if (booleans.get(i)) {
//                subTasksFromDtoFilter.add(subTask);
//            }
//            i++;
//        }
//        List<SubTask> subTasks1 = subTaskRepository.findAllByTask_Id(id).stream().filter(subTask -> subTasksFromDtoFilter.contains(subTask.getSubtask())).toList();
//        for (SubTask subTask : subTasks1) {
//            subTask.setStatus(Boolean.TRUE);
//        }
//
//        return subTasks1.stream().map(subTask -> subTaskRepository.save(subTask)).map(subTaskReadMapper::map).collect(Collectors.toList());
//    }
/*Метод, обновляющий подзадачу*/
    public SubTaskReadDto update(SubTaskCreateEditDto subTaskCreateEditDto, Long id) {

        return subTaskRepository.findBySubtaskAndTask_Id(subTaskCreateEditDto.getSubtask(), id)
                .map(subTask -> subTaskCreateEditMapper.map(subTaskCreateEditDto, subTask))
                .map(subTaskRepository::save)
                .map(subTaskReadMapper::map)
                .orElseThrow();


    }

}

