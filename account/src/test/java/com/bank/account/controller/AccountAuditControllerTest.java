package com.bank.account.controller;

import com.bank.account.dto.AuditDto;
import com.bank.account.service.AccountAuditService;
import com.bank.account.util.DataUtils;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountAuditController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class AccountAuditControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private AccountAuditService service;

    @Test
    @DisplayName("Должен вернуть 200 OK и AuditDto: успешный запрос по id")
    void read_shouldReturnAuditDto_whenExists() throws Exception {
        AuditDto auditDto = new DataUtils().createAuditDto();
        String timeStamp = new DataUtils().createTimeStamp();

        when(service.findById(1L)).thenReturn(auditDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(auditDto.getId().intValue()))
                .andExpect(jsonPath("$.entityType").value(auditDto.getEntityType()))
                .andExpect(jsonPath("$.operationType").value(auditDto.getOperationType()))
                .andExpect(jsonPath("$.createdBy").value(auditDto.getCreatedBy()))
                .andExpect(jsonPath("$.modifiedBy").value(auditDto.getModifiedBy()))
                .andExpect(jsonPath("$.createdAt").value(timeStamp))
                .andExpect(jsonPath("$.modifiedAt").value(timeStamp))
                .andExpect(jsonPath("$.newEntityJson").value(auditDto.getNewEntityJson()))
                .andExpect(jsonPath("$.entityJson").value(auditDto.getEntityJson()));
        verify(service).findById(auditDto.getId());
    }

    @Test
    @DisplayName("Должен вернуть 404 NOT FOUND: id не существует")
    void read_shouldReturnErrorResponse_whenIdDoesNotExist() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).findById(10000L);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", 10000L)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findById(10000L);
    }
}