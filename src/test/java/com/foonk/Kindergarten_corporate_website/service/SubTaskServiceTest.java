package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.SubTask;
import com.foonk.Kindergarten_corporate_website.database.Task;
import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.SubTaskReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.SubTaskCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.SubTaskReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubTaskServiceTest {
    @Mock
    private static SubTaskCreateEditMapper subTaskCreateEditMapper;
    @Mock
    private static SubTaskReadMapper subTaskReadMapper;
    @Mock
    private static SubTaskRepository subTaskRepository;

    @InjectMocks
    private static SubTaskService subTaskService;

    private static final Task TASK = mock(Task.class);

    private static final SubTaskCreateEditDto DTO = new SubTaskCreateEditDto("Подзадача", 1L, false);

    private static final SubTask SUB_TASK = new SubTask(1L, DTO.getSubtask(), TASK, DTO.getStatus());

    private static final SubTaskReadDto SUB_TASK_READ_DTO = new SubTaskReadDto(SUB_TASK.getId(), SUB_TASK.getSubtask(), 1L, SUB_TASK.getStatus());

    @Test
    void create() {
        doReturn(SUB_TASK).when(subTaskCreateEditMapper).map(DTO);
        doReturn(SUB_TASK).when(subTaskRepository).save(SUB_TASK);
        doReturn(SUB_TASK_READ_DTO).when(subTaskReadMapper).map(SUB_TASK);
        SubTaskReadDto subTaskReadDtoResult = subTaskService.create(DTO);
        assertThat(subTaskReadDtoResult).isNotNull();
        assertThat(subTaskReadDtoResult).isEqualTo(SUB_TASK_READ_DTO);
        verify(subTaskCreateEditMapper).map(any(SubTaskCreateEditDto.class));
        verify(subTaskRepository).save(any(SubTask.class));
        verify(subTaskReadMapper).map(any(SubTask.class));
        verifyNoMoreInteractions(subTaskCreateEditMapper, subTaskRepository, subTaskReadMapper);
    }

    @Test
    void findByTaskId() {
        List<SubTask> subTasks = new ArrayList<>(Collections.singletonList(SUB_TASK));
        doReturn(subTasks).when(subTaskRepository).findAllByTask_Id(SUB_TASK_READ_DTO.getTaskId());
        doReturn(SUB_TASK_READ_DTO).when(subTaskReadMapper).map(SUB_TASK);
        List<SubTaskReadDto> byTaskId = subTaskService.findByTaskId(SUB_TASK_READ_DTO.getTaskId());
        List<SubTaskReadDto> result = new ArrayList<>(Collections.singletonList(SUB_TASK_READ_DTO));
        assertThat(byTaskId).isNotEmpty();
        assertThat(byTaskId).isEqualTo(result);
        verify(subTaskRepository).findAllByTask_Id(anyLong());
        verify(subTaskReadMapper).map(any(SubTask.class));
        verifyNoMoreInteractions(subTaskRepository, subTaskReadMapper);
    }

    @Test
    void update() {
        doReturn(Optional.of(SUB_TASK)).when(subTaskRepository).findBySubtaskAndTask_Id(DTO.getSubtask(), SUB_TASK_READ_DTO.getTaskId());
        doReturn(SUB_TASK).when(subTaskCreateEditMapper).map(DTO, SUB_TASK);
        doReturn(SUB_TASK).when(subTaskRepository).save(SUB_TASK);
        doReturn(SUB_TASK_READ_DTO).when(subTaskReadMapper).map(SUB_TASK);
        SubTaskReadDto update = subTaskService.update(DTO, SUB_TASK_READ_DTO.getTaskId());
        assertThat(update).isNotNull();
        assertThat(update).isEqualTo(SUB_TASK_READ_DTO);
        verify(subTaskRepository).save(any(SubTask.class));
        verify(subTaskCreateEditMapper).map(any(SubTaskCreateEditDto.class), any(SubTask.class));
        verify(subTaskRepository).save(any(SubTask.class));
        verify(subTaskReadMapper).map(any(SubTask.class));
        verifyNoMoreInteractions(subTaskRepository, subTaskCreateEditMapper, subTaskRepository, subTaskReadMapper);
    }
}