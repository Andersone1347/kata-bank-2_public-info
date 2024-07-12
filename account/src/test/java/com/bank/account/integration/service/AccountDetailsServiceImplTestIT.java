package com.bank.account.integration.service;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.integration.BaseIntegrationTest;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.service.AccountDetailsServiceImpl;
import com.bank.account.util.DataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@Transactional
@ActiveProfiles("test")
@Sql(scripts = "/reset_sequences.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountDetailsServiceImplTestIT extends BaseIntegrationTest {

    @Autowired
    private AccountDetailsRepository repository;

    @Autowired
    private AccountDetailsServiceImpl service;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        AccountDetailsEntity accountDetailsEntity = new DataUtils().createAccountDetailsEntity();
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        repository.save(accountDetailsEntity);

        AccountDetailsDto actualResult = service.findById(1L);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(accountDetailsDto);
    }

    @Test
    @DisplayName("Поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {
        assertThrows(EntityNotFoundException.class, () -> service.findById(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("Поиск по списку id, позитивный сценарий")
    void findAllByIdsPositiveTest() {
        List<AccountDetailsEntity> entityList = new DataUtils().createAccountDetailsEntityList();
        List<AccountDetailsDto> dtoList = new DataUtils().createAccountDetailsDtoList();
        repository.saveAll(entityList);

        List<AccountDetailsDto> resultList = service.findAllById(List.of(1L, 2L));

        assertThat(resultList).isEqualTo(dtoList);
    }

    @Test
    @DisplayName("Поиск по списку несуществующих id, негативный сценарий")
    void findAllByNonExistIdsNegativeTest() {
        assertThrows(EntityNotFoundException.class, () -> service.findAllById(List.of(1L, 2L)));
    }

    @Test
    @DisplayName("Сохранение, позитивный сценарий")
    void saveAccountDetailsEntityPositiveTest() {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();

        AccountDetailsDto actualResult = service.save(accountDetailsDto);

        assertThat(actualResult).isEqualTo(accountDetailsDto);
    }

    @Test
    @DisplayName("Сохранение с дублирующим passportId, негативный сценарий")
    void saveAccountDetailsEntityDuplicatePassportIdNegativeTest() {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        AccountDetailsEntity accountDetailsEntity = new DataUtils().createAccountDetailsEntity();
        repository.save(accountDetailsEntity);

        assertThrows(DataIntegrityViolationException.class, () -> service.save(accountDetailsDto));
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        AccountDetailsEntity accountDetailsEntity = new DataUtils().createPreMergerAccountDetailsEntity();
        repository.save(accountDetailsEntity);

        AccountDetailsDto actualResult = service.update(1L, accountDetailsDto);

        assertThat(actualResult).isEqualTo(accountDetailsDto);
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, accountDetailsDto));
    }
}