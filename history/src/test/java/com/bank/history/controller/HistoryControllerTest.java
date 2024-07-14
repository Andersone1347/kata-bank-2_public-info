package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.service.HistoryService;
import com.bank.history.util.DataHistory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(HistoryController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class HistoryControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper mapper;

    @MockBean
    private HistoryService historyService;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        HistoryDto historyDto = DataHistory.createDto();
        Mockito.when(historyService.readById(1L)).thenReturn(historyDto);
        ResultActions resultActions = mockMvc.perform(get("/api/history/{id}", 1L).contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(historyDto.getId()))
                .andExpect(jsonPath("$.accountAuditId").value(historyDto.getAccountAuditId()))
                .andExpect(jsonPath("$.antiFraudAuditId").value(historyDto.getAntiFraudAuditId()))
                .andExpect(jsonPath("$.profileAuditId").value(historyDto.getProfileAuditId()))
                .andExpect(jsonPath("$.authorizationAuditId").value(historyDto.getAuthorizationAuditId()))
                .andExpect(jsonPath("$.transferAuditId").value(historyDto.getTransferAuditId()))
                .andExpect(jsonPath("$.publicBankInfoAuditId").value(historyDto.getPublicBankInfoAuditId()));
        verify(historyService).readById(1L);
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByIdNonExistIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException()).when(historyService).readById(Long.MAX_VALUE);

        ResultActions result = mockMvc.perform(get("/api/history/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(historyService).readById(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("поиск по списку , позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        List<HistoryDto> historyDto = DataHistory.listDto();
        Mockito.when(historyService.readAllById(List.of(1L, 2L))).thenReturn(historyDto);
        ResultActions resultActions = mockMvc.perform(get("/api/history?id=1&id=2").contentType(MediaType.APPLICATION_JSON));

        resultActions.andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id").value(historyDto.get(0).getId()))
                .andExpect(jsonPath("$[1].id").value(historyDto.get(1).getId()))
                .andExpect(jsonPath("$[0].accountAuditId").value(historyDto.get(0).getAccountAuditId()))
                .andExpect(jsonPath("$[1].accountAuditId").value(historyDto.get(1).getAccountAuditId()))
                .andExpect(jsonPath("$[0].antiFraudAuditId").value(historyDto.get(0).getAntiFraudAuditId()))
                .andExpect(jsonPath("$[1].antiFraudAuditId").value(historyDto.get(1).getAntiFraudAuditId()))
                .andExpect(jsonPath("$[0].profileAuditId").value(historyDto.get(0).getProfileAuditId()))
                .andExpect(jsonPath("$[1].profileAuditId").value(historyDto.get(1).getProfileAuditId()))
                .andExpect(jsonPath("$[0].authorizationAuditId").value(historyDto.get(0).getAuthorizationAuditId()))
                .andExpect(jsonPath("$[1].authorizationAuditId").value(historyDto.get(1).getAuthorizationAuditId()))
                .andExpect(jsonPath("$[0].transferAuditId").value(historyDto.get(0).getTransferAuditId()))
                .andExpect(jsonPath("$[1].transferAuditId").value(historyDto.get(1).getTransferAuditId()))
                .andExpect(jsonPath("$[0].publicBankInfoAuditId").value(historyDto.get(0).getPublicBankInfoAuditId()))
                .andExpect(jsonPath("$[1].publicBankInfoAuditId").value(historyDto.get(1).getPublicBankInfoAuditId()));
        verify(historyService).readAllById(List.of(1L, 2L));
    }

    @Test
    @DisplayName("поиск по списку, негативный сценарий")
    void readAllByIdNonExistIdNegativeTest() throws Exception {
        doThrow(new EntityNotFoundException()).when(historyService).readAllById(List.of(1L, 2L));
        ResultActions resultActions = mockMvc.perform(get("/api/history?id=1&id=2").contentType(MediaType.APPLICATION_JSON));
        resultActions.andDo(print())
                .andExpect(status().isNotFound());
        verify(historyService).readAllById(List.of(1L, 2L));
    }

    @Test
    @DisplayName("сохранение , позитивный сценарий")
    void createPositiveTest() throws Exception {
        HistoryDto historyDto = DataHistory.createDto();
        String historyDtoJson = mapper.writeValueAsString(historyDto);
        doReturn(historyDto).when(historyService).create(any(HistoryDto.class));
        ResultActions resultActions = mockMvc.perform(post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(historyDtoJson))
                .andExpect(status().isOk());

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(historyDto.getId()))
                .andExpect(jsonPath("$.accountAuditId").value(historyDto.getAccountAuditId()))
                .andExpect(jsonPath("$.antiFraudAuditId").value(historyDto.getAntiFraudAuditId()))
                .andExpect(jsonPath("$.profileAuditId").value(historyDto.getProfileAuditId()))
                .andExpect(jsonPath("$.authorizationAuditId").value(historyDto.getAuthorizationAuditId()))
                .andExpect(jsonPath("$.transferAuditId").value(historyDto.getTransferAuditId()))
                .andExpect(jsonPath("$.publicBankInfoAuditId").value(historyDto.getPublicBankInfoAuditId()));
        verify(historyService).create(historyDto);

        verify(historyService, Mockito.times(1)).create(historyDto);
    }

    @Test
    @DisplayName("сохранение, негативный тест")
    void createInvalidDataNegativeTest() throws Exception {
        HistoryDto historyDto = DataHistory.createDto();
        String historyDtoJson = mapper.writeValueAsString(historyDto);

        Mockito.when(historyService.create(historyDto))
                .thenThrow(new DataIntegrityViolationException("не корректные данные"));

        ResultActions resultActions = mockMvc.perform(post("/api/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(historyDtoJson))
                .andExpect(status().isConflict());


        Mockito.verify(historyService, Mockito.times(1)).create(historyDto);
    }

    @Test
    @DisplayName("обновление по id, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        HistoryDto historyDto = DataHistory.createDto();
        String historyDtoJson = mapper.writeValueAsString(historyDto);
        doReturn(historyDto).when(historyService).update(1L, historyDto);
        ResultActions resultActions = mockMvc.perform(put("/api/history/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(historyDtoJson))
                .andExpect(status().isOk());

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(historyDto.getId()))
                .andExpect(jsonPath("$.accountAuditId").value(historyDto.getAccountAuditId()))
                .andExpect(jsonPath("$.antiFraudAuditId").value(historyDto.getAntiFraudAuditId()))
                .andExpect(jsonPath("$.profileAuditId").value(historyDto.getProfileAuditId()))
                .andExpect(jsonPath("$.authorizationAuditId").value(historyDto.getAuthorizationAuditId()))
                .andExpect(jsonPath("$.transferAuditId").value(historyDto.getTransferAuditId()))
                .andExpect(jsonPath("$.publicBankInfoAuditId").value(historyDto.getPublicBankInfoAuditId()));
        verify(historyService).update(1L, historyDto);
    }

    @Test
    @DisplayName("обновление по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() throws Exception {
        HistoryDto historyDto = DataHistory.createDto();
        doThrow(new EntityNotFoundException()).when(historyService).update(Long.MAX_VALUE, historyDto);

        ResultActions result = mockMvc.perform(put("/api/history/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(historyDto)));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(historyService).update(Long.MAX_VALUE, historyDto);
    }
}