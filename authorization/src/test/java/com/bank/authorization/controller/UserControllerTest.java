package com.bank.authorization.controller;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService service;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private UserDto userDto;
    private String userJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        userDto = new UserDto(1L, "admin", "testPassword", 101L);
        userJson = objectMapper.writeValueAsString(userDto);
    }

    @Test
    @DisplayName("сохранение по входящему объекту UserDto, позитивный сценарий")
    void createByUserDtoPositiveTest() throws Exception {

        when(service.save(userDto)).thenReturn(userDto);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(content().json(userJson))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.role").value("admin"))
                .andExpect(jsonPath("$.password").value("testPassword"))
                .andExpect(jsonPath("$.profileId").value(101L))
                .andExpect(status().isCreated());
        verify(service, times(1)).save(userDto);
    }

    @Test
    @DisplayName("сохранение по входящему объекту UserDto с невалидными данными, негативный сценарий")
    void сreateByUserDtoWithInvalidDataNegativeTest() throws Exception {

        UserDto invalidUser = new UserDto();
        invalidUser.setRole("");

        when(service.save(invalidUser)).thenThrow(new IllegalArgumentException("Некорректные данные"));

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUser)))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {

        when(service.findById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/read/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.role").value("admin"))
                .andExpect(jsonPath("$.password").value("testPassword"))
                .andExpect(jsonPath("$.profileId").value(101L));

        verify(service, times(1)).findById(1L);
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws Exception {

        when(service.findById(999999L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/read/{id}", 999999L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findById(999999L);
    }

    @Test
    @DisplayName("обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() throws Exception {
        Long id = 1L;
        when(service.update(id, userDto)).thenReturn(userDto);

        mockMvc.perform(put("/{id}/update", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.role").value("admin"))
                .andExpect(jsonPath("$.password").value("testPassword"))
                .andExpect(jsonPath("$.profileId").value(101L));

        verify(service, times(1)).update(id, userDto);
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeNest() throws Exception {


        when(service.update(999999L, userDto)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(put("/{id}/update", 999999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service).update(999999L, userDto);
    }

    @Test
    @DisplayName("чтение по ids, позитивный сценарий")
    void readAllByIdsPositiveTest() throws Exception {

        List<Long> ids = Arrays.asList(1L, 2L);
        UserDto userDto2 = new UserDto(2L, "admin", "testPassword2", 102L);
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto);
        userDtos.add(userDto2);

        when(service.findAllByIds(ids)).thenReturn(userDtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/read/all")
                        .param("ids", "1", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].role").value("admin"))
                .andExpect(jsonPath("$.[0].password").value("testPassword"))
                .andExpect(jsonPath("$.[0].profileId").value(101L))
                .andExpect(jsonPath("$.[1].id").value(2L))
                .andExpect(jsonPath("$.[1].role").value("admin"))
                .andExpect(jsonPath("$.[1].password").value("testPassword2"))
                .andExpect(jsonPath("$.[1].profileId").value(102L));

        verify(service, times(1)).findAllByIds(ids);
    }

    @Test
    @DisplayName("чтение по ids, негативный сценарий")
    void readAllByNonExistIdsNegativeNest() throws Exception {
        when(service.findAllByIds(List.of(1L, 2L))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/read/all")
                        .param("ids", "1,2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findAllByIds(Arrays.asList(1L, 2L));
    }
}