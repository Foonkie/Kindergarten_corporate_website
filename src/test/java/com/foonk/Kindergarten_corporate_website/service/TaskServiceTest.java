package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.*;
import com.foonk.Kindergarten_corporate_website.database.repository.SubTaskRepository;
import com.foonk.Kindergarten_corporate_website.database.repository.TaskRepository;
import com.foonk.Kindergarten_corporate_website.dto.*;
import com.foonk.Kindergarten_corporate_website.mapper.TaskCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.TaskReadMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private static TaskRepository taskRepository;
    @Mock
    private static TaskReadMapper taskReadMapper;
    @Mock
    private static TaskCreateEditMapper taskCreateEditMapper;
    @Mock
    private static SubTaskRepository subTaskRepository;
    @Mock
    private static SubTaskService subTaskService;
    @InjectMocks
    private static TaskService taskService;

    private static User user = Mockito.mock(User.class);

    private static SubTask subTask = mock(SubTask.class);

    private static final Task TASK = new Task(1L, Type.GENERAL, "Задача", LocalDateTime.MAX, user);

    private static final List<Task> LIST = new ArrayList<>(Collections.singletonList(TASK));

    private static final List<SubTask> LIST_SUB_TASKS = new ArrayList<>(Collections.singletonList(subTask));

    private static final Pageable PAGEABLE = mock(Pageable.class);

    private static final SubTaskReadDto SUB_TASK_READ_DTO = new SubTaskReadDto(1L, "Подзадача", 1L, false);

    private static final SubTaskCreateEditDto SUB_TASK_CREATE_EDIT_DTO = new SubTaskCreateEditDto("Подзадача", 1L, null);

    private static final UserReadDto USER_READ_DTO = new UserReadDto(1L, "Имя", null, null, null, null, null);

    private static final TaskReadDto TASK_READ_DTO = new TaskReadDto(new ArrayList<>(Arrays.asList(SUB_TASK_READ_DTO)), TASK.getId(), TASK.getType(), TASK.getTask_header(), TASK.getEndTime(), USER_READ_DTO);

    private static final List<TaskReadDto> LIST_TASK_READ_DTO = new ArrayList<>(Collections.singletonList(TASK_READ_DTO));

    private static final TaskCreateEditDto TASK_CREATE_EDIT_DTO = new TaskCreateEditDto(TASK.getType(), TASK.getTask_header(), TASK.getEndTime(), 1L);

    @Test
    void findAllByUser_Id() {
        doReturn(new PageImpl<Task>(LIST)).when(taskRepository).findAllByUser_Id(1L, PAGEABLE);
        doReturn(TASK_READ_DTO).when(taskReadMapper).map(TASK);
        Page<TaskReadDto> expectedPage = new PageImpl<>(LIST_TASK_READ_DTO);
        Page<TaskReadDto> allByUser_id = taskService.findAllByUser_Id(1L, PAGEABLE);
        assertThat(allByUser_id).isNotEmpty();
        assertThat(allByUser_id).isEqualTo(expectedPage);
        verify(taskRepository).findAllByUser_Id(1L, PAGEABLE);
        verify(taskReadMapper).map(TASK);
        verifyNoMoreInteractions(taskReadMapper, taskRepository);
    }

    @Test
    void findAllByUser_Username() {
        doReturn(new PageImpl<Task>(LIST)).when(taskRepository).findAllByUser_Username(USER_READ_DTO.getUsername(), PAGEABLE);
        doReturn(TASK_READ_DTO).when(taskReadMapper).map(TASK);
        Page<TaskReadDto> expectedPage = new PageImpl<>(LIST_TASK_READ_DTO);
        Page<TaskReadDto> allByUser_username = taskService.findAllByUser_Username(USER_READ_DTO.getUsername(), PAGEABLE);
        assertThat(allByUser_username).isNotEmpty();
        assertThat(expectedPage).isEqualTo(allByUser_username);
        verify(taskRepository).findAllByUser_Username("Имя", PAGEABLE);
        verify(taskReadMapper).map(TASK);
        verifyNoMoreInteractions(taskReadMapper, taskRepository);
    }

    @Test
    void create() {
        doReturn(TASK).when(taskCreateEditMapper).map(TASK_CREATE_EDIT_DTO);
        doReturn(TASK).when(taskRepository).save(TASK);
        doReturn(TASK_READ_DTO).when(taskReadMapper).map(TASK);
        TaskReadDto taskReadDtoActual = taskService.create(TASK_CREATE_EDIT_DTO);
        assertThat(taskReadDtoActual).isNotNull();
        assertThat(taskReadDtoActual).isEqualTo(TASK_READ_DTO);
        verify(taskCreateEditMapper).map(TASK_CREATE_EDIT_DTO);
        verify(taskRepository).save(TASK);
        verify(taskReadMapper).map(TASK);
        verifyNoMoreInteractions(taskCreateEditMapper, taskRepository, taskReadMapper);
    }

    @Test
    void delete() {
        doReturn(Optional.of(TASK)).when(taskRepository).findById(1L);
        doNothing().when(taskRepository).delete(TASK);
        doNothing().when(taskRepository).flush();
        boolean delete = taskService.delete(1L);
        assertThat(delete).isTrue();
        verify(taskRepository).findById(1L);
        verify(taskRepository).delete(TASK);
        verify(taskRepository).flush();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void update() {
        doReturn(Optional.of(TASK)).when(taskRepository).findById(1L);
        doReturn(TASK).when(taskCreateEditMapper).map(TASK_CREATE_EDIT_DTO, TASK);
        doReturn(TASK).when(taskRepository).saveAndFlush(TASK);
        doReturn(TASK_READ_DTO).when(taskReadMapper).map(TASK);
        Optional<TaskReadDto> update = taskService.update(TASK_CREATE_EDIT_DTO, 1L);
        Optional<TaskReadDto> taskReadDto = Optional.of(TaskServiceTest.TASK_READ_DTO);
        assertThat(update).isPresent();
        assertThat(taskReadDto).isEqualTo(update);
        verify(taskRepository).findById(1L);
        verify(taskCreateEditMapper).map(TASK_CREATE_EDIT_DTO, TASK);
        verify(taskRepository).saveAndFlush(TASK);
        verify(taskReadMapper).map(TASK);
        verifyNoMoreInteractions(taskRepository, taskCreateEditMapper, taskReadMapper);
    }

    @Test
    void findById() {
        doReturn(Optional.of(TASK)).when(taskRepository).findById(1L);
        doReturn(TASK_READ_DTO).when(taskReadMapper).map(TASK);
        Optional<TaskReadDto> byId = taskService.findById(1L);
        Optional<TaskReadDto> taskReadDto = Optional.of(TaskServiceTest.TASK_READ_DTO);
        assertThat(byId).isPresent();
        assertThat(byId).isEqualTo(taskReadDto);
        verify(taskRepository).findById(1L);
        verify(taskReadMapper).map(TASK);
        verifyNoMoreInteractions(taskReadMapper, taskRepository);
    }

    @Test
    void updateSubTask() {
        lenient().doReturn(LIST_SUB_TASKS).when(subTaskRepository).findAllByTask_Id(1L);
        doNothing().when(subTaskRepository).delete(subTask);
        doNothing().when(subTaskRepository).flush();
        doReturn(LIST_SUB_TASKS).when(subTaskRepository).findAllByTask_Id(1L);
        lenient().doReturn(SUB_TASK_READ_DTO).when(subTaskService).update(SUB_TASK_CREATE_EDIT_DTO, 1L);
        doReturn(SUB_TASK_READ_DTO).when(subTaskService).create(SUB_TASK_CREATE_EDIT_DTO);
        List<SubTaskReadDto> subTaskReadDtos = taskService.updateSubTask(new ArrayList<>(Collections.singletonList(SUB_TASK_CREATE_EDIT_DTO)), 1L);
        assertThat(subTaskReadDtos).isNotEmpty();
        verify(subTaskRepository).delete(subTask);
        verify(subTaskRepository).flush();
        verify(subTaskRepository, times(2)).findAllByTask_Id(1L);
        verify(subTaskService).create(SUB_TASK_CREATE_EDIT_DTO);
        verifyNoMoreInteractions(subTaskRepository, subTaskService);
    }

}