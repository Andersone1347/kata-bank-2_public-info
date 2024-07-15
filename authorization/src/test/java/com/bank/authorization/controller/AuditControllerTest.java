package com.bank.authorization.controller;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.service.AuditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditController.class)
class AuditControllerTest {

    @MockBean
    private AuditService service;

    @Autowired
    private MockMvc mockMvc;

    public static String formateTimeStamp() {
        Timestamp timestamp = Timestamp.valueOf("2024-07-01 17:27:19");
        ZonedDateTime utcZonedDateTime = timestamp.toLocalDateTime().atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String timesTamp = utcZonedDateTime.format(formatter) + "+00:00";
        return timesTamp;
    }

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {

        AuditDto auditDto = new AuditDto();
        auditDto.setId(1L);
        auditDto.setEntityType("USER");
        auditDto.setOperationType("CREATE");
        auditDto.setCreatedBy("user1");
        auditDto.setModifiedBy("user2");
        auditDto.setCreatedAt(Timestamp.valueOf("2024-07-01 17:27:19"));
        auditDto.setModifiedAt(Timestamp.valueOf("2024-07-01 17:27:19"));
        auditDto.setNewEntityJson("{\"name\":\"John\"}");
        auditDto.setEntityJson("{\"name\":\"Mary\"}");

        String timeStamp = formateTimeStamp();

        when(service.findById(1L)).thenReturn(auditDto);

        mockMvc.perform(get("/audit/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(auditDto.getId().intValue()))
                .andExpect(jsonPath("$.operationType").value(auditDto.getOperationType()))
                .andExpect(jsonPath("$.entityType").value(auditDto.getEntityType()))
                .andExpect(jsonPath("$.createdBy").value(auditDto.getCreatedBy()))
                .andExpect(jsonPath("$.modifiedBy").value(auditDto.getModifiedBy()))
                .andExpect(jsonPath("$.createdAt").value(timeStamp))
                .andExpect(jsonPath("$.modifiedAt").value(timeStamp))
                .andExpect(jsonPath("$.newEntityJson").value(auditDto.getNewEntityJson()))
                .andExpect(jsonPath("$.entityJson").value(auditDto.getEntityJson()));

        verify(service).findById(auditDto.getId());
    }

    @Test
    @DisplayName("чтение по id, негативный сценарий")
    void readByNonExistIdNegativeNest() throws Exception {
        when(service.findById(999999L)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/audit/{id}", 999999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service).findById(999999L);
    }
}