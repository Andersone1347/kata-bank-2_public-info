package com.bank.antifraud.controller;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.service.AuditService;
import com.bank.antifraud.util.service.TestAuditUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * тест для {@link AuditController}
 */
@WebMvcTest(AuditController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class AuditControllerTest {
    @MockBean
    private AuditService service;

    private final MockMvc mockMvc;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        AuditDto expectedDto = TestAuditUtil.createAuditDto();

        Mockito.when(service.findById(1L)).thenReturn(expectedDto);

        mockMvc.perform(get("/audit/{id}", 1))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.entityType").value("Account"))
                .andExpect(jsonPath("$.operationType").value("UPDATE"))
                .andExpect(jsonPath("$.createdBy").value("admin"))
                .andExpect(jsonPath("$.modifiedBy").value("admin"))
                .andExpect(jsonPath("$.newEntityJson").value("{\"id\":1,\"name\":\"New Account Name\",\"balance\":1000}"))
                .andExpect(jsonPath("$.entityJson").value("{\"id\":1,\"name\":\"Old Account Name\",\"balance\":500}"))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("чтение AuditEntity по несуществующему id, негативный сценарий")
    void readByIdNonExistNegativeTest() throws Exception {
        Mockito.when(service.findById(Long.MAX_VALUE)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/audit/{id}", Long.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Mockito.verify(service, Mockito.times(1)).findById(Long.MAX_VALUE);
    }
}