package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.Task;
import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.SubTaskCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.SubTaskReadMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubTaskService {
    private final SubTaskRepository subTaskRepository;

    private final SubTaskReadMapper subTaskReadMapper;

    private final SubTaskCreateEditMapper subTaskCreateEditMapper;


    public List<SubTaskReadDto> findAllByTask_Id(Long id){
        return subTaskRepository.findAllByTask_Id(id).stream().map(subTask -> subTaskReadMapper.map(subTask)).toList();
        }

    SubTaskReadDto create(SubTaskCreateEditDto subTaskCreateEditDto){
        return Optional.of(subTaskCreateEditDto)
                .map(subTaskCreateEditMapper::map)
                .map(subTaskRepository::save)
                .map(subTaskReadMapper::map)
                .orElseThrow();
    }}

