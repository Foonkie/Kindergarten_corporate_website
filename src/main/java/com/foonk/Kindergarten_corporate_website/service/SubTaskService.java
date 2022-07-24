package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.SubTaskCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.SubTaskReadMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SubTaskService {
    private final SubTaskCreateEditMapper subTaskCreateEditMapper;
    private final SubTaskReadMapper subTaskReadMapper;

    SubTaskRepository subTaskRepository;
    public SubTaskReadDto create(SubTaskCreateEditDto subTaskCreateEditDto){
        return Optional.ofNullable(subTaskCreateEditDto)
                .map(subTaskCreateEditMapper::map)
                .map(subTaskRepository::save)
                .map(subTaskReadMapper::map)
                .orElseThrow();
    }}

