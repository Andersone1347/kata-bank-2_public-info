package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.service.SuspiciousPhoneTransferService;
import com.bank.antifraud.util.service.TestSuspiciousPhoneTransferUtil;
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
 * тест для контроллера {@link SuspiciousPhoneTransferController}
 */
@WebMvcTest(SuspiciousPhoneTransferController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class SuspiciousPhoneTransferControllerTest {
    @MockBean
    private SuspiciousPhoneTransferService service;

    private final MockMvc mockMvc;

    private final ObjectMapper objectMapper;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        SuspiciousPhoneTransferDto expectedDto = TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDto();

        Mockito.when(service.findById(1L)).thenReturn(expectedDto);
        mockMvc.perform(get("/suspicious/phone/transfer/{id}", 1L))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.phoneTransferId").value(123456789L))
                .andExpect(jsonPath("$.isBlocked").value(true))
                .andExpect(jsonPath("$.isSuspicious").value(true))
                .andExpect(jsonPath("$.blockedReason").value("Номер телефона заблокирован" +
                        " из-за подозрительной активности"))
                .andExpect(jsonPath("$.suspiciousReason").value("Номер телефона используется" +
                        " для мошенничества"))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("чтение SuspiciousPhoneTransfer по несуществующему id, негативный сценарий")
    void readByIdNonExistNegativeTest() throws Exception {
        Mockito.when(service.findById(Long.MAX_VALUE)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/suspicious/phone/transfer/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).findById(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("чтение SuspiciousPhoneTransfer по ряду id, позитивный сценарий")
    void readByAllIdPositiveTest() throws Exception {
        List<SuspiciousPhoneTransferDto> expectedDtos =
                TestSuspiciousPhoneTransferUtil.createListOfSuspiciousPhoneTransferDtos();

        Mockito.when(service.findAllById(Mockito.anyList())).thenReturn(expectedDtos);

        mockMvc.perform(get("/suspicious/phone/transfer?ids=1&ids=2"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].phoneTransferId").value(111222333L))
                .andExpect(jsonPath("$[0].isBlocked").value(true))
                .andExpect(jsonPath("$[0].isSuspicious").value(false))
                .andExpect(jsonPath("$[0].blockedReason").value("Номер телефона заблокирован" +
                        " из-за превышения лимита звонков"))
                .andExpect(jsonPath("$[0].suspiciousReason").doesNotExist())
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].phoneTransferId").value(444555666L))
                .andExpect(jsonPath("$[1].isBlocked").value(false))
                .andExpect(jsonPath("$[1].isSuspicious").value(true))
                .andExpect(jsonPath("$[1].blockedReason").doesNotExist())
                .andExpect(jsonPath("$[1].suspiciousReason").value("Номер телефона используется" +
                        " для мошенничества с использованием автоматизированных звонков"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("чтение SSuspiciousPhoneTransfer по ряду несуществующих id, негативный сценарий")
    void readByAllIdNonExistNegativeTest() throws Exception {
        Mockito.when(service.findAllById(List.of(1L, 2L))).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/suspicious/phone/transfer?ids=1&ids=2", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).findAllById(List.of(1L, 2L));
    }

    @Test
    @DisplayName("создание (сохранение) SuspiciousPhoneTransfer, позитивный сценарий")
    void createSuspiciousPhoneTransferPositiveTest() throws Exception {
        SuspiciousPhoneTransferDto savingDto = TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDto();
        String dtoJson = objectMapper.writeValueAsString(savingDto);

        Mockito.when(service.save(savingDto)).thenReturn(savingDto);

        mockMvc.perform(post("/suspicious/phone/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.phoneTransferId").value(123456789L))
                .andExpect(jsonPath("$.isBlocked").value(true))
                .andExpect(jsonPath("$.isSuspicious").value(true))
                .andExpect(jsonPath("$.blockedReason").value("Номер телефона заблокирован" +
                        " из-за подозрительной активности"))
                .andExpect(jsonPath("$.suspiciousReason").value("Номер телефона используется" +
                        " для мошенничества"));


        Mockito.verify(service, Mockito.times(1)).save(savingDto);
    }

    @Test
    @DisplayName("создание (сохранение) SuspiciousPhoneTransfer, негативный сценарий")
    void createSuspiciousPhoneTransferNegativeTest() throws Exception {
        SuspiciousPhoneTransferDto savingDto = TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDto();
        String dtoJson = objectMapper.writeValueAsString(savingDto);

        Mockito.when(service.save(savingDto)).thenThrow(new DataIntegrityViolationException("Ошибка сохранения" +
                " объекта: нарушение целостности данных"));

        mockMvc.perform(post("/suspicious/phone/transfer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isConflict());

        Mockito.verify(service, Mockito.times(1)).save(savingDto);
    }

    @Test
    @DisplayName("обновить поля SuspiciousPhoneTransfer, позитивный сценарий")
    void updateSuspiciousPhoneTransferPositiveTest() throws Exception {
        SuspiciousPhoneTransferDto dtoForUpdate =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDtoForMerge();
        String dtoForUpdateJson = objectMapper.writeValueAsString(dtoForUpdate);

        SuspiciousPhoneTransferDto afterUpdate =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDtoForMerge();
        afterUpdate.setId(1L);

        Mockito.when(service.update(1L, dtoForUpdate)).thenReturn(afterUpdate);

        mockMvc.perform(put("/suspicious/phone/transfer/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoForUpdateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.phoneTransferId").value(987654321L))
                .andExpect(jsonPath("$.isBlocked").value(false))
                .andExpect(jsonPath("$.isSuspicious").value(true))
                .andExpect(jsonPath("$.blockedReason").value("Номер телефона используется" +
                        " для рассылки спама"))
                .andExpect(jsonPath("$.suspiciousReason").value("Номер телефона используется" +
                        " для мошенничества"));

        Mockito.verify(service, Mockito.times(1)).update(1L, dtoForUpdate);
    }

    @Test
    @DisplayName("обновить поля SuspiciousPhoneTransfer, негативный сценарий")
    void updateSuspiciousPhoneTransferNegativeTest() throws Exception {
        SuspiciousPhoneTransferDto dtoForUpdate =
                TestSuspiciousPhoneTransferUtil.createSuspiciousPhoneTransferDtoForMerge();
        String dtoForUpdateJson = objectMapper.writeValueAsString(dtoForUpdate);

        Mockito.when(service.update(1L, dtoForUpdate)).
                thenThrow(new EntityNotFoundException("SuspiciousAccountTransfer для обновления не найден"));

        mockMvc.perform(put("/suspicious/phone/transfer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoForUpdateJson))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).update(1L, dtoForUpdate);
    }
}