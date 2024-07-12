package com.bank.account.integration.service;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.integration.BaseIntegrationTest;
import com.bank.account.repository.AccountAuditRepository;
import com.bank.account.service.AccountAuditServiceImpl;
import com.bank.account.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@ActiveProfiles("test")
@Sql(scripts = "/reset_sequences.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(properties = {"eureka.client.enabled=false"}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountAuditServiceImplTestIT extends BaseIntegrationTest {
    @Autowired
    private AccountAuditRepository repository;

    @Autowired
    private AccountAuditServiceImpl service;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        AuditEntity auditEntity = new DataUtils().createAuditEntity();
        AuditDto auditDto = new DataUtils().createAuditDto();
        repository.save(auditEntity);

        AuditDto actualResult = service.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(auditDto);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(Long.MAX_VALUE));
    }
}