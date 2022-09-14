package com.foonk.Kindergarten_corporate_website.service;

import com.foonk.Kindergarten_corporate_website.database.Role;
import com.foonk.Kindergarten_corporate_website.database.User;
import com.foonk.Kindergarten_corporate_website.database.repository.UserRepository;
import com.foonk.Kindergarten_corporate_website.dto.UserCreateEditDto;
import com.foonk.Kindergarten_corporate_website.dto.UserReadDto;
import com.foonk.Kindergarten_corporate_website.mapper.UserCreateEditMapper;
import com.foonk.Kindergarten_corporate_website.mapper.UserReadMapper;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private static UserRepository userRepository;
    @Mock
    private static UserReadMapper userReadMapper;
    @Mock
    private static UserCreateEditMapper userCreateEditMapper;
    @Mock
    private static ImageService imageService;
    @InjectMocks
    private static UserService userService;

    private static final MultipartFile IMAGE = mock(MultipartFile.class);

    private final static InputStream STUB_INPUT_STREAM =
            IOUtils.toInputStream("some test data for my input stream", "UTF-8");

    private static final User USER = new User(1L, "Имя", "Пользователя", null, null, null, IMAGE.getOriginalFilename(), Role.ADMIN, null);

    private static final UserReadDto USER_READ_DTO = new UserReadDto(USER.getId(), USER.getUsername(), null, null, null, IMAGE.getOriginalFilename(), Role.ADMIN);

    private static final UserCreateEditDto USER_CREATE_EDIT_DTO = new UserCreateEditDto(USER.getUsername(), null, null, null, null, Role.ADMIN, IMAGE);

    private static final List<User> USERS = new ArrayList<>(Collections.singletonList(USER));

    @Test
    void findAll() {
        doReturn(USERS).when(userRepository).findAll();
        doReturn(USER_READ_DTO).when(userReadMapper).map(USER);
        List<UserReadDto> all = userService.findAll();
        assertThat(all).isNotEmpty();
        verify(userReadMapper).map(USER);
        verify(userRepository).findAll();
        verifyNoMoreInteractions(userReadMapper);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findById() {
        doReturn(Optional.of(USER)).when(userRepository).findById(USER.getId());
        doReturn(USER_READ_DTO).when(userReadMapper).map(USER);
        Optional<UserReadDto> byId = userService.findById(USER.getId());
        assertThat(byId).isPresent();
        assertThat(byId).isEqualTo(Optional.of(USER_READ_DTO));
        verify(userRepository).findById(USER.getId());
        verify(userReadMapper).map(USER);
        verifyNoMoreInteractions(userRepository, userReadMapper);
    }


    @SneakyThrows
    @Test
    void create() {
        doReturn(USER).when(userCreateEditMapper).map(USER_CREATE_EDIT_DTO);
        doReturn(USER).when(userRepository).save(USER);
        doReturn(USER_READ_DTO).when(userReadMapper).map(USER);
        doNothing().when(imageService).upload(any(String.class), any(InputStream.class));
        doReturn("Аватар").when(IMAGE).getOriginalFilename();
        doReturn(STUB_INPUT_STREAM).when(IMAGE).getInputStream();
        UserReadDto userReadDtoResult = userService.create(USER_CREATE_EDIT_DTO);
        assertThat(userReadDtoResult).isNotNull();
        assertThat(userReadDtoResult).isEqualTo(USER_READ_DTO);
        verify(userCreateEditMapper).map(USER_CREATE_EDIT_DTO);
        verify(userRepository).save(USER);
        verify(userReadMapper).map(USER);
        verifyNoMoreInteractions(userCreateEditMapper, userRepository, userReadMapper);
    }

    @SneakyThrows
    @Test
    void update() {
        doReturn(Optional.of(USER)).when(userRepository).findById(USER.getId());
        doReturn(USER).when(userCreateEditMapper).map(USER_CREATE_EDIT_DTO, USER);
        doReturn("Аватар").when(IMAGE).getOriginalFilename();
        doReturn(STUB_INPUT_STREAM).when(IMAGE).getInputStream();
        Optional<UserReadDto> update = userService.update(USER.getId(), USER_CREATE_EDIT_DTO);
        assertThat(update).isPresent();
        assertThat(update).isEqualTo(Optional.of(USER_READ_DTO));
        verify(userRepository).findById(USER.getId());
        verify(userCreateEditMapper).map(USER_CREATE_EDIT_DTO, USER);
        verifyNoMoreInteractions(userRepository, userCreateEditMapper);
    }

    @Test
    void delete() {
        doReturn(Optional.of(USER)).when(userRepository).findById(USER.getId());
        doNothing().when(userRepository).delete(USER);
        doNothing().when(userRepository).flush();
        boolean delete = userService.delete(USER.getId());
        assertThat(delete).isTrue();
        verify(userRepository).findById(USER.getId());
        verify(userRepository).delete(USER);
        verify(userRepository).flush();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void loadUserByUsername() {
        doReturn(Optional.of(USER)).when(userRepository).findByUsername(USER.getUsername());
        UserDetails userDetails = userService.loadUserByUsername(USER.getUsername());
        assertThat(userDetails).isNotNull();
        verify(userRepository).findByUsername(USER.getUsername());
    }
}