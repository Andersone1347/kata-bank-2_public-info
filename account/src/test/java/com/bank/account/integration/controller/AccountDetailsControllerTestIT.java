package com.bank.account.integration.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.integration.BaseIntegrationTest;
import com.bank.account.repository.AccountDetailsRepository;
import com.bank.account.util.DataUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/reset_sequences.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountDetailsControllerTestIT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    protected AccountDetailsRepository accountDetailsRepository;

    @BeforeEach
    void setUp() {
        accountDetailsRepository.deleteAll();
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        AccountDetailsEntity accountDetailsEntity = new DataUtils().createAccountDetailsEntity();
        AccountDetailsEntity entityExpected = accountDetailsRepository.save(accountDetailsEntity);

        ResultActions result = mockMvc.perform(get("/details/{id}", entityExpected.getId())
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(entityExpected.getId()))
                .andExpect(jsonPath("$.passportId").value(entityExpected.getPassportId()))
                .andExpect(jsonPath("$.accountNumber").value(entityExpected.getAccountNumber()))
                .andExpect(jsonPath("$.bankDetailsId").value(entityExpected.getBankDetailsId()))
                .andExpect(jsonPath("$.money").value(1000.50))
                .andExpect(jsonPath("$.negativeBalance").value(entityExpected.getNegativeBalance()))
                .andExpect(jsonPath("$.profileId").value(entityExpected.getProfileId()));
    }

    @Test
    @DisplayName("Чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() throws Exception {
        ResultActions result = mockMvc.perform(get("/details/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Создание, позитивный сценарий")
    void createAccountDetailsEntityPositiveTests() throws Exception {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/details/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.passportId").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(1L))
                .andExpect(jsonPath("$.bankDetailsId").value(1L))
                .andExpect(jsonPath("$.money").value(1000.50))
                .andExpect(jsonPath("$.negativeBalance").value(false))
                .andExpect(jsonPath("$.profileId").value(1L));
    }

    @Test
    @DisplayName("Создание с дублирующим passportId, негативный сценарий")
    void createAccountDetailsEntityDuplicatePassportIdNegativeTest() throws Exception {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        AccountDetailsEntity accountDetailsEntity = new DataUtils().createAccountDetailsEntity();
        accountDetailsRepository.save(accountDetailsEntity);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/details/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsDto)));

        result
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Обновление по id, позитивный сценарий")
    void updateByIdPositiveTest() throws Exception {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();
        AccountDetailsEntity accountDetailsEntity = new DataUtils().createAccountDetailsEntity();
        accountDetailsRepository.save(accountDetailsEntity);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/details/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.passportId").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(1L))
                .andExpect(jsonPath("$.bankDetailsId").value(1L))
                .andExpect(jsonPath("$.money").value(1000.50))
                .andExpect(jsonPath("$.negativeBalance").value(false))
                .andExpect(jsonPath("$.profileId").value(1L));
    }

    @Test
    @DisplayName("Обновление по несуществующему id, негативный сценарий")
    void updateByNonExistIdNegativeTest() throws Exception {
        AccountDetailsDto accountDetailsDto = new DataUtils().createAccountDetailsDto();

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/details/update/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDetailsDto)));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Чтение по списку id, позитивный сценарий")
    void readAllByIdsPositiveTest() throws Exception {
        List<AccountDetailsEntity> accountDetailsEntityList = new DataUtils().createAccountDetailsEntityList();
        accountDetailsRepository.saveAll(accountDetailsEntityList);

        ResultActions result = mockMvc.perform(get("/details/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[0].passportId").value(1L))
                .andExpect(jsonPath("$[1].passportId").value(2L))
                .andExpect(jsonPath("$[0].accountNumber").value(1L))
                .andExpect(jsonPath("$[1].accountNumber").value(2L))
                .andExpect(jsonPath("$[0].bankDetailsId").value(1L))
                .andExpect(jsonPath("$[1].bankDetailsId").value(2L))
                .andExpect(jsonPath("$[0].money").value(1000.50))
                .andExpect(jsonPath("$[1].money").value(1000.50))
                .andExpect(jsonPath("$[0].negativeBalance").value(false))
                .andExpect(jsonPath("$[1].negativeBalance").value(false))
                .andExpect(jsonPath("$[0].profileId").value(1L))
                .andExpect(jsonPath("$[1].profileId").value(2L));
    }

    @Test
    @DisplayName("Чтение по списку несуществующих id, негативный сценарий")
    void readAllByNonExistIdsNegativeTest() throws Exception {
        ResultActions result = mockMvc.perform(get("/details/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}