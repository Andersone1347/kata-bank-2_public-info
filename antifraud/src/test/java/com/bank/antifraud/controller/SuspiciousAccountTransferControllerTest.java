package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.service.SuspiciousAccountTransferService;
import com.bank.antifraud.util.service.TestSuspiciousAccountTransferUtil;
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
 * тест для контроллера {@link SuspiciousAccountTransferController}
 */
@WebMvcTest(SuspiciousAccountTransferController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class SuspiciousAccountTransferControllerTest {

    @MockBean
    private SuspiciousAccountTransferService service;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        SuspiciousAccountTransferDto expectedDto = TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDto();

        Mockito.when(service.findById(1L)).thenReturn(expectedDto);
        mockMvc.perform(get("/suspicious/account/transfer/{id}", 1L))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountTransferId").value(1L))
                .andExpect(jsonPath("$.isBlocked").value(true))
                .andExpect(jsonPath("$.isSuspicious").value(false))
                .andExpect(jsonPath("$.blockedReason").value("Счет отправителя заблокирован"))
                .andExpect(jsonPath("$.suspiciousReason").doesNotExist())
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("чтение SuspiciousAccountTransfer по несуществующему id, негативный сценарий")
    void readByIdNonExistNegativeTest() throws Exception {
        Mockito.when(service.findById(Long.MAX_VALUE)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/suspicious/account/transfer/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).findById(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("чтение SuspiciousAccountTransfer по ряду id, позитивный сценарий")
    void readByAllIdPositiveTest() throws Exception {
        List<SuspiciousAccountTransferDto> expectedDtos =
                TestSuspiciousAccountTransferUtil.createListOfSuspiciousAccountTransferDtos();

        Mockito.when(service.findAllById(Mockito.anyList())).thenReturn(expectedDtos);

        mockMvc.perform(get("/suspicious/account/transfer?ids=1&ids=2"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].accountTransferId").value(2L))
                .andExpect(jsonPath("$[0].isBlocked").value(false))
                .andExpect(jsonPath("$[0].isSuspicious").value(true))
                .andExpect(jsonPath("$[0].blockedReason").doesNotExist())
                .andExpect(jsonPath("$[0].suspiciousReason").value("На счету обнаружено" +
                        " множество подозрительных транзакций"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].accountTransferId").value(3L))
                .andExpect(jsonPath("$[1].isBlocked").value(true))
                .andExpect(jsonPath("$[1].isSuspicious").value(true))
                .andExpect(jsonPath("$[1].blockedReason").value("Счет получателя заблокирован" +
                        " из-за превышения лимита транзакций"))
                .andExpect(jsonPath("$[1].suspiciousReason").value("На счету обнаружено" +
                        " множество подозрительных транзакций"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("чтение SuspiciousAccountTransfer по ряду несуществующих id, негативный сценарий")
    void readByAllIdNonExistNegativeTest() throws Exception {
        Mockito.when(service.findAllById(List.of(1L, 2L))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/suspicious/account/transfer?ids=1&ids=2", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).findAllById(List.of(1L, 2L));
    }

    @Test
    @DisplayName("создание (сохранение) SuspiciousAccountTransfer, позитивный сценарий")
    void createSuspiciousAccountTransferPositiveTest() throws Exception {
        SuspiciousAccountTransferDto savingDto = TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDto();
        String dtoJson = objectMapper.writeValueAsString(savingDto);

        Mockito.when(service.save(savingDto)).thenReturn(savingDto);

        mockMvc.perform(post("/suspicious/account/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountTransferId").value(1L))
                .andExpect(jsonPath("$.isBlocked").value(true))
                .andExpect(jsonPath("$.isSuspicious").value(false))
                .andExpect(jsonPath("$.blockedReason").value("Счет отправителя заблокирован"))
                .andExpect(jsonPath("$.suspiciousReason").doesNotExist());


        Mockito.verify(service, Mockito.times(1)).save(savingDto);
    }

    @Test
    @DisplayName("создание (сохранение) SuspiciousAccountTransfer, негативный сценарий")
    void createSuspiciousAccountTransferNegativeTest() throws Exception {
        SuspiciousAccountTransferDto savingDto = TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDto();
        String dtoJson = objectMapper.writeValueAsString(savingDto);

        Mockito.when(service.save(savingDto)).thenThrow(new DataIntegrityViolationException("Ошибка сохранения" +
                " объекта: нарушение целостности данных"));

        mockMvc.perform(post("/suspicious/account/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isConflict());

        Mockito.verify(service, Mockito.times(1)).save(savingDto);
    }


    @Test
    @DisplayName("обновить поля SuspiciousAccountTransferDto, позитивный сценарий")
    void updateSuspiciousAccountTransferPositiveTest() throws Exception {
        SuspiciousAccountTransferDto dtoForUpdate =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDtoForMerge();
        String dtoForUpdateJson = objectMapper.writeValueAsString(dtoForUpdate);

        SuspiciousAccountTransferDto afterUpdate =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDtoForMerge();
        afterUpdate.setId(1L);

        Mockito.when(service.update(1L, dtoForUpdate)).thenReturn(afterUpdate);

        mockMvc.perform(put("/suspicious/account/transfer/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoForUpdateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountTransferId").value(987654321L))
                .andExpect(jsonPath("$.isBlocked").value(true))
                .andExpect(jsonPath("$.isSuspicious").value(false))
                .andExpect(jsonPath("$.blockedReason").value("Счет заблокирован"))
                .andExpect(jsonPath("$.suspiciousReason").doesNotExist());

        Mockito.verify(service, Mockito.times(1)).update(1L, dtoForUpdate);
    }

    @Test
    @DisplayName("обновить поля SuspiciousAccountTransferDto, негативный сценарий")
    void updateSuspiciousAccountTransferNegativeTest() throws Exception {
        SuspiciousAccountTransferDto dtoForUpdate =
                TestSuspiciousAccountTransferUtil.createSuspiciousAccountTransferDtoForMerge();
        String dtoForUpdateJson = objectMapper.writeValueAsString(dtoForUpdate);

        Mockito.when(service.update(1L, dtoForUpdate)).
                thenThrow(new EntityNotFoundException("SuspiciousAccountTransfer для обновления не найден"));

        mockMvc.perform(put("/suspicious/account/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoForUpdateJson))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).update(1L, dtoForUpdate);
    }
}