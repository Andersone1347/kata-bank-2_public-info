package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.service.AccountDetailsServiceImpl;
import com.bank.account.util.DataUtils;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountDetailsController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class AccountDetailsControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private AccountDetailsServiceImpl service;

    @Test
    @DisplayName("Должен вернуть 200 OK и AccountDetailsDto: успешный запрос по id")
    void read_shouldReturnAccountDetailsDto_whenExists() throws Exception {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();

        doReturn(accountDetailsDto).when(service).findById(anyLong());

        ResultActions result = mockMvc.perform(get("/details/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.passportId").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(1L))
                .andExpect(jsonPath("$.bankDetailsId").value(1L))
                .andExpect(jsonPath("$.money").value(1000.50))
                .andExpect(jsonPath("$.negativeBalance").value(false))
                .andExpect(jsonPath("$.profileId").value(1L));
        verify(service).findById(accountDetailsDto.getId());
    }

    @Test
    @DisplayName("Должен вернуть 404 Not Found: id не существует")
    void read_shouldThrowException_whenAccountDetailsDtoDoesNotExists() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).findById(Long.MAX_VALUE);

        ResultActions result = mockMvc.perform(get("/details/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findById(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("Должен вернуть 200 OK и AccountDetailsDto: успешно создана сущность")
    void create_shouldReturnAccountDetailsDto_whenCreateSuccess() throws Exception {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        doReturn(accountDetailsDto).when(service).save(any(AccountDetailsDto.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/details/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.passportId").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(1L))
                .andExpect(jsonPath("$.bankDetailsId").value(1L))
                .andExpect(jsonPath("$.money").value(1000.50))
                .andExpect(jsonPath("$.negativeBalance").value(false))
                .andExpect(jsonPath("$.profileId").value(1L));
        verify(service).save(any(AccountDetailsDto.class));
    }

    @Test
    @DisplayName("Должен вернуть 409 CONFLICT: ошибка сохранения в базу данных")
    void create_shouldReturnAccountDetailsDto_whenPassportIdIsDuplicate() throws Exception {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        when(service.save(any(AccountDetailsDto.class)))
                .thenThrow(new DataIntegrityViolationException("message"));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/details/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsDto)));

        result
                .andDo(print())
                .andExpect(status().isConflict());
        verify(service).save(any(AccountDetailsDto.class));
    }

    @Test
    @DisplayName("Должен вернуть 200 ОК и AccountDetailsDto: успешное обновление AccountDetailsDto по id")
    void update_shouldReturnAccountDetailsDto_whenAccountDetailsDtoUpdate() throws Exception {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        doReturn(accountDetailsDto).when(service).update(anyLong(), any(AccountDetailsDto.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/details/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.passportId").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(1L))
                .andExpect(jsonPath("$.bankDetailsId").value(1L))
                .andExpect(jsonPath("$.money").value(1000.50))
                .andExpect(jsonPath("$.negativeBalance").value(false))
                .andExpect(jsonPath("$.profileId").value(1L));
        verify(service).update(anyLong(), any(AccountDetailsDto.class));
    }

    @Test
    @DisplayName("Должен вернуть 404 NOT FOUND: id не существует")
    void update_shouldThrowException_whenIdDoesNotExist() throws Exception {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        doThrow(new EntityNotFoundException()).when(service).update(Long.MAX_VALUE, accountDetailsDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/details/update/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsDto)));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).update(Long.MAX_VALUE, accountDetailsDto);
    }

    @Test
    @DisplayName(("Должен вернуть 200 ОК и список AccountDetailsDto: успешный запрос по списку ID"))
    void readAll_shouldReturnAccountDetailsDtoList_whenAllIdExists() throws Exception {
        List<AccountDetailsDto> accountDetailsDtoList = new DataUtils().createAccountDetailsDtoList();
        when(service.findAllById(List.of(1L, 2L))).thenReturn(accountDetailsDtoList);

        ResultActions result = mockMvc.perform(get("/details/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[0].passportId").value(1L))
                .andExpect(jsonPath("$[1].passportId").value(2L))
                .andExpect(jsonPath("$[0].accountNumber").value(1L))
                .andExpect(jsonPath("$[1].accountNumber").value(2L))
                .andExpect(jsonPath("$[0].bankDetailsId").value(1L))
                .andExpect(jsonPath("$[1].bankDetailsId").value(2L))
                .andExpect(jsonPath("$[0].money").value(1000.50))
                .andExpect(jsonPath("$[1].money").value(1000.50))
                .andExpect(jsonPath("$[0].negativeBalance").value(false))
                .andExpect(jsonPath("$[1].negativeBalance").value(false))
                .andExpect(jsonPath("$[0].profileId").value(1L))
                .andExpect(jsonPath("$[1].profileId").value(2L));
        verify(service).findAllById(Arrays.asList(1L, 2L));
    }

    @Test
    @DisplayName("Должен вернуть 404: id не существует")
    void readAll_shouldThrowException_whenIdDoesNotExist() throws Exception {
        when(service.findAllById(List.of(1L, 2L))).thenThrow(new EntityNotFoundException());

        ResultActions result = mockMvc.perform(get("/details/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findAllById(Arrays.asList(1L, 2L));
    }

    @Test
    @DisplayName("Должен вернуть 500: пустой список id")
    void readAll_shouldThrowException_whenEmptyIdList() throws Exception {
        when(service.findAllById(Collections.emptyList())).thenThrow(new IllegalArgumentException());

        ResultActions result = mockMvc.perform(get("/details/read/all")
                .param("ids", "")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isInternalServerError());
        verify(service).findAllById(Collections.emptyList());
    }
}