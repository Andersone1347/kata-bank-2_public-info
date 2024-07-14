package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.RegistrationService;
import com.bank.profile.util.RegistrationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class RegistrationControllerTest {


    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    RegistrationService service;


    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        doReturn(registrationDto).when(service).findById(anyLong());

        ResultActions result = mockMvc.perform(get("/registration/read/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.country").value("fakeAddress"))
                .andExpect(jsonPath("$.region").value("fakeAddress"))
                .andExpect(jsonPath("$.city").value("fakeAddress"))
                .andExpect(jsonPath("$..district").value("fakeAddress"))
                .andExpect(jsonPath("$.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.street").value("fakeAddress"))
                .andExpect(jsonPath("$.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.index").value(1L));

        verify(service).findById(registrationDto.getId());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).findById(10000L);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/registration/read/{id}", 10000L)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findById(10000L);
    }

    @Test
    @DisplayName("создание новых данных, позитивный сценарий ")
    void createNewDataPositiveTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        doReturn(registrationDto).when(service).save(any(RegistrationDto.class));


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/registration/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.country").value("fakeAddress"))
                .andExpect(jsonPath("$.region").value("fakeAddress"))
                .andExpect(jsonPath("$.city").value("fakeAddress"))
                .andExpect(jsonPath("$..district").value("fakeAddress"))
                .andExpect(jsonPath("$.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.street").value("fakeAddress"))
                .andExpect(jsonPath("$.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.index").value(1L));

        verify(service).save(any(RegistrationDto.class));
    }


    @Test
    @DisplayName("создание неподходящих данных, негативный сценарий")
    void createNotSuitableDataNegativeTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        doThrow(new DataIntegrityViolationException("message")).when(service).save(any(RegistrationDto.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/registration/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDto)));

        result
                .andDo(print())
                .andExpect(status().isConflict());
        verify(service).save(any(RegistrationDto.class));

    }


    @Test
    @DisplayName("обновление данных по id, позитивный сценарий")
    void updateByIdPositiveTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        doReturn(registrationDto).when(service).update(anyLong(), any(RegistrationDto.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/registration/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.country").value("fakeAddress"))
                .andExpect(jsonPath("$.region").value("fakeAddress"))
                .andExpect(jsonPath("$.city").value("fakeAddress"))
                .andExpect(jsonPath("$..district").value("fakeAddress"))
                .andExpect(jsonPath("$.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.street").value("fakeAddress"))
                .andExpect(jsonPath("$.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.index").value(1L));

        verify(service).update(anyLong(), any(RegistrationDto.class));
    }


    @Test
    @DisplayName("обновление данных по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() throws Exception {
        RegistrationDto registrationDto = new RegistrationUtil().createRegistrationDto();
        doThrow(new EntityNotFoundException()).when(service).update(Long.MAX_VALUE, registrationDto);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/registration/update/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationDto)));

        result
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service).update(Long.MAX_VALUE, registrationDto);
    }


    @Test
    @DisplayName("чтение списка по id, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {

        List<RegistrationDto> registrationDtoList = new RegistrationUtil().createRegistrationDtoList();
        when(service.findAllById(List.of(1L, 2L))).thenReturn(registrationDtoList);

        ResultActions result = mockMvc.perform(get("/registration/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].country").value("fakeAddress"))
                .andExpect(jsonPath("$[0].region").value("fakeAddress"))
                .andExpect(jsonPath("$[0].city").value("fakeAddress"))
                .andExpect(jsonPath("$[0]..district").value("fakeAddress"))
                .andExpect(jsonPath("$[0].locality").value("fakeAddress"))
                .andExpect(jsonPath("$[0].street").value("fakeAddress"))
                .andExpect(jsonPath("$[0].houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[0].flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].index").value(1L))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].country").value("fakeAddress"))
                .andExpect(jsonPath("$[1].region").value("fakeAddress"))
                .andExpect(jsonPath("$[1].city").value("fakeAddress"))
                .andExpect(jsonPath("$[1]..district").value("fakeAddress"))
                .andExpect(jsonPath("$[1].locality").value("fakeAddress"))
                .andExpect(jsonPath("$[1].street").value("fakeAddress"))
                .andExpect(jsonPath("$[1].houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[1].flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].index").value(1L));

        verify(service).findAllById(Arrays.asList(1L, 2L));
    }

    @Test
    @DisplayName("чтение списка по несуществующему id, негативный сценарий")
    void readAllNonExistIdNegativeTest() throws Exception {
        when(service.findAllById(List.of(1L, 2L))).thenThrow(new EntityNotFoundException());
        ResultActions result = mockMvc.perform(get("/registration/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findAllById(Arrays.asList(1L, 2L));
    }
}
