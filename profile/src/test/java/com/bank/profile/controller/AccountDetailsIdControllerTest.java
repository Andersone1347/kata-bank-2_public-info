package com.bank.profile.controller;


import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.service.AccountDetailsIdService;
import com.bank.profile.util.AccountDetailsIdUtil;
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

@WebMvcTest(AccountDetailsIdController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class AccountDetailsIdControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    AccountDetailsIdService service;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();
        doReturn(accountDetailsIdDto).when(service).findById(anyLong());

        ResultActions result = mockMvc.perform(get("/account/details/read/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsIdDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountId").value(1L))

                .andExpect(jsonPath("$.profile.id").value(1L))
                .andExpect(jsonPath("$.profile.phoneNumber").value(1L))
                .andExpect(jsonPath("$.profile.email").value("fakeUser"))
                .andExpect(jsonPath("$.profile.nameOnCard").value("fakeUser"))
                .andExpect(jsonPath("$.profile.inn").value(1L))
                .andExpect(jsonPath("$.profile.snils").value(1L))

                .andExpect(jsonPath("$.profile.passport.id").value(1L))
                .andExpect(jsonPath("$.profile.passport.series").value(1))
                .andExpect(jsonPath("$.profile.passport.number").value(1L))
                .andExpect(jsonPath("$.profile.passport.lastName").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.firstName").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.middleName").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.gender").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.profile.passport.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.profile.passport.divisionCode").value(1))
                .andExpect(jsonPath("$.profile.passport.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$.profile.passport.registration.id").value(1L))
                .andExpect(jsonPath("$.profile.passport.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.index").value(1L))

                .andExpect(jsonPath("$.profile.actualRegistration.id").value(1L))
                .andExpect(jsonPath("$.profile.actualRegistration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.index").value(1L));

        verify(service).findById(accountDetailsIdDto.getId());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).findById(10000L);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/account/details/read/{id}", 10000L)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findById(10000L);
    }


    @Test
    @DisplayName("создание новых данных, позитивный сценарий ")
    void createNewDataPositiveTest() throws Exception {
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();
        doReturn(accountDetailsIdDto).when(service).save(any(AccountDetailsIdDto.class));


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/account/details/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsIdDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountId").value(1L))

                .andExpect(jsonPath("$.profile.id").value(1L))
                .andExpect(jsonPath("$.profile.phoneNumber").value(1L))
                .andExpect(jsonPath("$.profile.email").value("fakeUser"))
                .andExpect(jsonPath("$.profile.nameOnCard").value("fakeUser"))
                .andExpect(jsonPath("$.profile.inn").value(1L))
                .andExpect(jsonPath("$.profile.snils").value(1L))

                .andExpect(jsonPath("$.profile.passport.id").value(1L))
                .andExpect(jsonPath("$.profile.passport.series").value(1))
                .andExpect(jsonPath("$.profile.passport.number").value(1L))
                .andExpect(jsonPath("$.profile.passport.lastName").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.firstName").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.middleName").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.gender").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.profile.passport.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.profile.passport.divisionCode").value(1))
                .andExpect(jsonPath("$.profile.passport.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$.profile.passport.registration.id").value(1L))
                .andExpect(jsonPath("$.profile.passport.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.index").value(1L))

                .andExpect(jsonPath("$.profile.actualRegistration.id").value(1L))
                .andExpect(jsonPath("$.profile.actualRegistration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.index").value(1L));

        verify(service).save(any(AccountDetailsIdDto.class));

    }

    @Test
    @DisplayName("создание неподходящих данных, негативный сценарий")
    void createNotSuitableDataNegativeTest() throws Exception {
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();
        doThrow(new DataIntegrityViolationException("message")).when(service).save(any(AccountDetailsIdDto.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/account/details/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsIdDto)));

        result
                .andDo(print())
                .andExpect(status().isConflict());
        verify(service).save(any(AccountDetailsIdDto.class));

    }


    @Test
    @DisplayName("обновление данных по id, позитивный сценарий")
    void updateByIdPositiveTest() throws Exception {
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();
        doReturn(accountDetailsIdDto).when(service).update(anyLong(), any(AccountDetailsIdDto.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/account/details/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsIdDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountId").value(1L))

                .andExpect(jsonPath("$.profile.id").value(1L))
                .andExpect(jsonPath("$.profile.phoneNumber").value(1L))
                .andExpect(jsonPath("$.profile.email").value("fakeUser"))
                .andExpect(jsonPath("$.profile.nameOnCard").value("fakeUser"))
                .andExpect(jsonPath("$.profile.inn").value(1L))
                .andExpect(jsonPath("$.profile.snils").value(1L))

                .andExpect(jsonPath("$.profile.passport.id").value(1L))
                .andExpect(jsonPath("$.profile.passport.series").value(1))
                .andExpect(jsonPath("$.profile.passport.number").value(1L))
                .andExpect(jsonPath("$.profile.passport.lastName").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.firstName").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.middleName").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.gender").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.profile.passport.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$.profile.passport.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.profile.passport.divisionCode").value(1))
                .andExpect(jsonPath("$.profile.passport.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$.profile.passport.registration.id").value(1L))
                .andExpect(jsonPath("$.profile.passport.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.passport.registration.index").value(1L))

                .andExpect(jsonPath("$.profile.actualRegistration.id").value(1L))
                .andExpect(jsonPath("$.profile.actualRegistration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.profile.actualRegistration.index").value(1L));


        verify(service).update(anyLong(), any(AccountDetailsIdDto.class));
    }


    @Test
    @DisplayName("обновление данных по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() throws Exception {
        AccountDetailsIdDto accountDetailsIdDto = new AccountDetailsIdUtil().createAccountDetailsIdDto();
        doThrow(new EntityNotFoundException()).when(service).update(Long.MAX_VALUE, accountDetailsIdDto);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/account/details/update/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsIdDto)));

        result
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service).update(Long.MAX_VALUE, accountDetailsIdDto);

    }

    @Test
    @DisplayName("чтение списка по id, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {

        List<AccountDetailsIdDto> accountDetailsIdDtoList = new AccountDetailsIdUtil().createAccountDetailsIdDtoList();
        when(service.findAllById(List.of(1L, 2L))).thenReturn(accountDetailsIdDtoList);

        ResultActions result = mockMvc.perform(get("/account/details/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));


        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].accountId").value(1L))

                .andExpect(jsonPath("$[0].profile.id").value(1L))
                .andExpect(jsonPath("$[0].profile.phoneNumber").value(1L))
                .andExpect(jsonPath("$[0].profile.email").value("fakeUser"))
                .andExpect(jsonPath("$[0].profile.nameOnCard").value("fakeUser"))
                .andExpect(jsonPath("$[0].profile.inn").value(1L))
                .andExpect(jsonPath("$[0].profile.snils").value(1L))

                .andExpect(jsonPath("$[0].profile.passport.id").value(1L))
                .andExpect(jsonPath("$[0].profile.passport.series").value(1))
                .andExpect(jsonPath("$[0].profile.passport.number").value(1L))
                .andExpect(jsonPath("$[0].profile.passport.lastName").value("fakeUser"))
                .andExpect(jsonPath("$[0].profile.passport.firstName").value("fakeUser"))
                .andExpect(jsonPath("$[0].profile.passport.middleName").value("fakeUser"))
                .andExpect(jsonPath("$[0].profile.passport.gender").value("fakeUser"))
                .andExpect(jsonPath("$[0].profile.passport.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[0].profile.passport.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$[0].profile.passport.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$[0].profile.passport.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[0].profile.passport.divisionCode").value(1))
                .andExpect(jsonPath("$[0].profile.passport.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$[0].profile.passport.registration.id").value(1L))
                .andExpect(jsonPath("$[0].profile.passport.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.passport.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.passport.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.passport.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.passport.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.passport.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.passport.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.passport.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.passport.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.passport.registration.index").value(1L))

                .andExpect(jsonPath("$[0].profile.actualRegistration.id").value(1L))
                .andExpect(jsonPath("$[0].profile.actualRegistration.country").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.actualRegistration.region").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.actualRegistration.city").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.actualRegistration.district").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.actualRegistration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.actualRegistration.street").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.actualRegistration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.actualRegistration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.actualRegistration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].profile.actualRegistration.index").value(1L))


                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].accountId").value(1L))

                .andExpect(jsonPath("$[1].profile.id").value(1L))
                .andExpect(jsonPath("$[1].profile.phoneNumber").value(1L))
                .andExpect(jsonPath("$[1].profile.email").value("fakeUser"))
                .andExpect(jsonPath("$[1].profile.nameOnCard").value("fakeUser"))
                .andExpect(jsonPath("$[1].profile.inn").value(1L))
                .andExpect(jsonPath("$[1].profile.snils").value(1L))

                .andExpect(jsonPath("$[1].profile.passport.id").value(1L))
                .andExpect(jsonPath("$[1].profile.passport.series").value(1))
                .andExpect(jsonPath("$[1].profile.passport.number").value(1L))
                .andExpect(jsonPath("$[1].profile.passport.lastName").value("fakeUser"))
                .andExpect(jsonPath("$[1].profile.passport.firstName").value("fakeUser"))
                .andExpect(jsonPath("$[1].profile.passport.middleName").value("fakeUser"))
                .andExpect(jsonPath("$[1].profile.passport.gender").value("fakeUser"))
                .andExpect(jsonPath("$[1].profile.passport.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[1].profile.passport.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$[1].profile.passport.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$[1].profile.passport.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[1].profile.passport.divisionCode").value(1))
                .andExpect(jsonPath("$[1].profile.passport.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$[1].profile.passport.registration.id").value(1L))
                .andExpect(jsonPath("$[1].profile.passport.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.passport.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.passport.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.passport.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.passport.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.passport.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.passport.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.passport.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.passport.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.passport.registration.index").value(1L))

                .andExpect(jsonPath("$[1].profile.actualRegistration.id").value(1L))
                .andExpect(jsonPath("$[1].profile.actualRegistration.country").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.actualRegistration.region").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.actualRegistration.city").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.actualRegistration.district").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.actualRegistration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.actualRegistration.street").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.actualRegistration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.actualRegistration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.actualRegistration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].profile.actualRegistration.index").value(1L));

        verify(service).findAllById(Arrays.asList(1L, 2L));

    }

    @Test
    @DisplayName("чтение списка по несуществующему id, негативный сценарий")
    void readAllNonExistIdNegativeTest() throws Exception {
        when(service.findAllById(List.of(1L, 2L))).thenThrow(new EntityNotFoundException());
        ResultActions result = mockMvc.perform(get("/account/details/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findAllById(Arrays.asList(1L, 2L));
    }
}
