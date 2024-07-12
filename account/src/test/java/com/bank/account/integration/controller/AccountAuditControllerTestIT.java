package com.bank.account.integration.controller;

import com.bank.account.entity.AuditEntity;
import com.bank.account.integration.BaseIntegrationTest;
import com.bank.account.repository.AccountAuditRepository;
import com.bank.account.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(properties = {"eureka.client.enabled=false"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountAuditControllerTestIT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected AccountAuditRepository auditRepository;

    @BeforeEach
    void setUp() {
        auditRepository.deleteAll();
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        AuditEntity auditEntity = new DataUtils().createAuditEntity();
        auditRepository.save(auditEntity);
        String timeStamp = new DataUtils().createTimeStamp();


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", auditEntity.getId())
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(auditEntity.getId().intValue()))
                .andExpect(jsonPath("$.entityType").value(auditEntity.getEntityType()))
                .andExpect(jsonPath("$.operationType").value(auditEntity.getOperationType()))
                .andExpect(jsonPath("$.createdBy").value(auditEntity.getCreatedBy()))
                .andExpect(jsonPath("$.modifiedBy").value(auditEntity.getModifiedBy()))
                .andExpect(jsonPath("$.createdAt").value(timeStamp))
                .andExpect(jsonPath("$.modifiedAt").value(timeStamp))
                .andExpect(jsonPath("$.newEntityJson").value(auditEntity.getNewEntityJson()))
                .andExpect(jsonPath("$.entityJson").value(auditEntity.getEntityJson()));
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() throws Exception {
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/audit/{id}", 10000L)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}