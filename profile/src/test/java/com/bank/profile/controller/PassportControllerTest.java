package com.bank.profile.controller;


import com.bank.profile.dto.PassportDto;
import com.bank.profile.service.PassportService;
import com.bank.profile.util.PassportUtil;
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
import java.time.LocalDate;
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

@WebMvcTest(PassportController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class PassportControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    PassportService service;


    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        PassportDto passportDto = new PassportUtil().createPassportDto();

        doReturn(passportDto).when(service).findById(anyLong());

        ResultActions result = mockMvc.perform(get("/passport/read/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passportDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.series").value(1))
                .andExpect(jsonPath("$.number").value(1L))
                .andExpect(jsonPath("$.lastName").value("fakeUser"))
                .andExpect(jsonPath("$.firstName").value("fakeUser"))
                .andExpect(jsonPath("$.middleName").value("fakeUser"))
                .andExpect(jsonPath("$.gender").value("fakeUser"))
                .andExpect(jsonPath("$.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.divisionCode").value(1))
                .andExpect(jsonPath("$.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$.registration.id").value(1L))
                .andExpect(jsonPath("$.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.index").value(1L));

        verify(service).findById(passportDto.getId());

    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).findById(10000L);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/passport/read/{id}", 10000L)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findById(10000L);

    }

    @Test
    @DisplayName("создание новых данных, позитивный сценарий ")
    void createNewDataPositiveTest() throws Exception {
        PassportDto passportDto = new PassportUtil().createPassportDto();
        doReturn(passportDto).when(service).save(any(PassportDto.class));


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/passport/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passportDto)));


        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.series").value(1))
                .andExpect(jsonPath("$.number").value(1L))
                .andExpect(jsonPath("$.lastName").value("fakeUser"))
                .andExpect(jsonPath("$.firstName").value("fakeUser"))
                .andExpect(jsonPath("$.middleName").value("fakeUser"))
                .andExpect(jsonPath("$.gender").value("fakeUser"))
                .andExpect(jsonPath("$.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.divisionCode").value(1))
                .andExpect(jsonPath("$.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$.registration.id").value(1L))
                .andExpect(jsonPath("$.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.index").value(1L));

        verify(service).save(any(PassportDto.class));

    }

    @Test
    @DisplayName("создание неподходящих данных, негативный сценарий")
    void createNotSuitableDataNegativeTest() throws Exception {
        PassportDto passportDto = new PassportUtil().createPassportDto();
        doThrow(new DataIntegrityViolationException("message")).when(service).save(any(PassportDto.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/passport/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passportDto)));

        result
                .andDo(print())
                .andExpect(status().isConflict());
        verify(service).save(any(PassportDto.class));

    }


    @Test
    @DisplayName("    @DisplayName(\"обновление данных по id, позитивный сценарий\")\n объекта по id, позитивный сценарий")
    void updateByIdPositiveTest() throws Exception {
        PassportDto passportDto = new PassportUtil().createPassportDto();
        doReturn(passportDto).when(service).update(anyLong(), any(PassportDto.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/passport/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passportDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.series").value(1))
                .andExpect(jsonPath("$.number").value(1L))
                .andExpect(jsonPath("$.lastName").value("fakeUser"))
                .andExpect(jsonPath("$.firstName").value("fakeUser"))
                .andExpect(jsonPath("$.middleName").value("fakeUser"))
                .andExpect(jsonPath("$.gender").value("fakeUser"))
                .andExpect(jsonPath("$.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.divisionCode").value(1))
                .andExpect(jsonPath("$.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$.registration.id").value(1L))
                .andExpect(jsonPath("$.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.registration.index").value(1L));

        verify(service).update(anyLong(), any(PassportDto.class));
    }

    @Test
    @DisplayName("обновление данных по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() throws Exception {
        PassportDto passportDto = new PassportUtil().createPassportDto();
        doThrow(new EntityNotFoundException()).when(service).update(Long.MAX_VALUE, passportDto);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/passport/update/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(passportDto)));

        result
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service).update(Long.MAX_VALUE, passportDto);

    }

    @Test
    @DisplayName("чтение списка по id, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {

        List<PassportDto> passportDtoList = new PassportUtil().createPassportDtoList();
        when(service.findAllById(List.of(1L, 2L))).thenReturn(passportDtoList);

        ResultActions result = mockMvc.perform(get("/passport/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));


        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("[0].id").value(1L))
                .andExpect(jsonPath("$[0].series").value(1))
                .andExpect(jsonPath("$[0].number").value(1L))
                .andExpect(jsonPath("$[0].lastName").value("fakeUser"))
                .andExpect(jsonPath("$[0].firstName").value("fakeUser"))
                .andExpect(jsonPath("$[0].middleName").value("fakeUser"))
                .andExpect(jsonPath("$[0].gender").value("fakeUser"))
                .andExpect(jsonPath("$[0].birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[0].birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$[0].issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$[0].dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[0].divisionCode").value(1))
                .andExpect(jsonPath("$[0].expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$[0].registration.id").value(1L))
                .andExpect(jsonPath("$[0].registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$[0].registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$[0].registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$[0].registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$[0].registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$[0].registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$[0].registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[0].registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].registration.index").value(1L))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].series").value(1))
                .andExpect(jsonPath("$[1].number").value(1L))
                .andExpect(jsonPath("$[1].lastName").value("fakeUser"))
                .andExpect(jsonPath("$[1].firstName").value("fakeUser"))
                .andExpect(jsonPath("$[1].middleName").value("fakeUser"))
                .andExpect(jsonPath("$[1].gender").value("fakeUser"))
                .andExpect(jsonPath("$[1].birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[1].birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$[1].issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$[1].dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[1].divisionCode").value(1))
                .andExpect(jsonPath("$[1].expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$[1].registration.id").value(1L))
                .andExpect(jsonPath("$[1].registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$[1].registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$[1].registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$[1].registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$[1].registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$[1].registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$[1].registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[1].registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].registration.index").value(1L));

        verify(service).findAllById(Arrays.asList(1L, 2L));
    }

    @Test
    @DisplayName("чтение списка по несуществующему id, негативный сценарий")
    void readAllNonExistIdNegativeTest() throws Exception {
        when(service.findAllById(List.of(1L, 2L))).thenThrow(new EntityNotFoundException());
        ResultActions result = mockMvc.perform(get("/passport/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findAllById(Arrays.asList(1L, 2L));
    }
}
