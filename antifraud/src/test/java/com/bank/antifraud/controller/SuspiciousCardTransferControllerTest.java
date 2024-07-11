package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import com.bank.antifraud.util.service.TestSuspiciousCardTransferUtil;
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

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * тест для контроллера {@link SuspiciousCardTransferController}
 */
@WebMvcTest(SuspiciousCardTransferController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class SuspiciousCardTransferControllerTest {

    @MockBean
    private SuspiciousCardTransferService service;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        SuspiciousCardTransferDto expectedDto = TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDto();

        Mockito.when(service.findById(1L)).thenReturn(expectedDto);
        mockMvc.perform(get("/suspicious/card/transfer/{id}", 1L))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardTransferId").value(123456789L))
                .andExpect(jsonPath("$.isBlocked").value(true))
                .andExpect(jsonPath("$.isSuspicious").value(true))
                .andExpect(jsonPath("$.blockedReason").value("Счет заблокирован" +
                        " из-за превышения лимита транзакций"))
                .andExpect(jsonPath("$.suspiciousReason").value("На счету обнаружено" +
                        " множество мелких транзакций в течение короткого промежутка времени"))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("чтение SuspiciousCardTransfer по несуществующему id, негативный сценарий")
    void readByIdNonExistNegativeTest() throws Exception {
        Mockito.when(service.findById(Long.MAX_VALUE)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/suspicious/card/transfer/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).findById(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("чтение SuspiciousCardTransfer по ряду id, позитивный сценарий")
    void readByAllIdPositiveTest() throws Exception {
        List<SuspiciousCardTransferDto> expectedDtos =
                TestSuspiciousCardTransferUtil.createListOfSuspiciousCardTransferDtos();

        Mockito.when(service.findAllById(Mockito.anyList())).thenReturn(expectedDtos);

        mockMvc.perform(get("/suspicious/card/transfer?ids=1&ids=2"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].cardTransferId").value(987654321L))
                .andExpect(jsonPath("$[0].isBlocked").value(false))
                .andExpect(jsonPath("$[0].isSuspicious").value(true))
                .andExpect(jsonPath("$[0].blockedReason").doesNotExist())
                .andExpect(jsonPath("$[0].suspiciousReason").value("Подозрительная операция"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].cardTransferId").value(123456789L))
                .andExpect(jsonPath("$[1].isBlocked").value(true))
                .andExpect(jsonPath("$[1].isSuspicious").value(true))
                .andExpect(jsonPath("$[1].blockedReason").value("Счет заблокирован" +
                        " из-за превышения лимита транзакций"))
                .andExpect(jsonPath("$[1].suspiciousReason").value("На счету обнаружено" +
                        " множество мелких транзакций в течение короткого промежутка времени"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("чтение SuspiciousCardTransfer по ряду несуществующих id, негативный сценарий")
    void readByAllIdNonExistNegativeTest() throws Exception {
        Mockito.when(service.findAllById(List.of(1L, 2L))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/suspicious/card/transfer?ids=1&ids=2", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).findAllById(List.of(1L, 2L));
    }

    @Test
    @DisplayName("создание (сохранение) SuspiciousCardTransfer, позитивный сценарий")
    void createSuspiciousCardTransferPositiveTest() throws Exception {
        SuspiciousCardTransferDto savingDto = TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDto();
        String dtoJson = objectMapper.writeValueAsString(savingDto);

        Mockito.when(service.save(savingDto)).thenReturn(savingDto);

        mockMvc.perform(post("/suspicious/card/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardTransferId").value(123456789L))
                .andExpect(jsonPath("$.isBlocked").value(true))
                .andExpect(jsonPath("$.isSuspicious").value(true))
                .andExpect(jsonPath("$.blockedReason").value("Счет заблокирован" +
                        " из-за превышения лимита транзакций"))
                .andExpect(jsonPath("$.suspiciousReason").value("На счету обнаружено" +
                        " множество мелких транзакций в течение короткого промежутка времени"));


        Mockito.verify(service, Mockito.times(1)).save(savingDto);
    }

    @Test
    @DisplayName("создание (сохранение) SuspiciousCardTransfer, негативный сценарий")
    void createSuspiciousCardTransferNegativeTest() throws Exception {
        SuspiciousCardTransferDto savingDto = TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDto();
        String dtoJson = objectMapper.writeValueAsString(savingDto);

        Mockito.when(service.save(savingDto)).thenThrow(new DataIntegrityViolationException("Ошибка сохранения" +
                " объекта: нарушение целостности данных"));

        mockMvc.perform(post("/suspicious/card/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isConflict());

        Mockito.verify(service, Mockito.times(1)).save(savingDto);
    }

    @Test
    @DisplayName("обновить поля SuspiciousCardTransfer, позитивный сценарий")
    void updateSuspiciousCardTransferPositiveTest() throws Exception {
        SuspiciousCardTransferDto dtoForUpdate = TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDtoForMerge();
        String dtoForUpdateJson = objectMapper.writeValueAsString(dtoForUpdate);

        SuspiciousCardTransferDto afterUpdate = TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDtoForMerge();
        afterUpdate.setId(1L);

        Mockito.when(service.update(1L, dtoForUpdate)).thenReturn(afterUpdate);

        mockMvc.perform(put("/suspicious/card/transfer/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoForUpdateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardTransferId").value(987654321L))
                .andExpect(jsonPath("$.isBlocked").value(true))
                .andExpect(jsonPath("$.isSuspicious").value(false))
                .andExpect(jsonPath("$.blockedReason").value("Счет заблокирован"))
                .andExpect(jsonPath("$.suspiciousReason").doesNotExist());

        Mockito.verify(service, Mockito.times(1)).update(1L, dtoForUpdate);
    }

    @Test
    @DisplayName("обновить поля SuspiciousCardTransfer, негативный сценарий")
    void updateSuspiciousCardTransferNegativeTest() throws Exception {
        SuspiciousCardTransferDto dtoForUpdate =
                TestSuspiciousCardTransferUtil.createSuspiciousCardTransferDtoForMerge();
        String dtoForUpdateJson = objectMapper.writeValueAsString(dtoForUpdate);

        Mockito.when(service.update(1L, dtoForUpdate)).
                thenThrow(new EntityNotFoundException("SuspiciousAccountTransfer для обновления не найден"));

        mockMvc.perform(put("/suspicious/card/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoForUpdateJson))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).update(1L, dtoForUpdate);
    }
}