package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.AtmDto;
import com.bank.publicinfo.dto.BranchDto;
import com.bank.publicinfo.service.AtmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AtmControllerTest {

    @Mock
    private AtmService service;

    @InjectMocks
    private AtmController controller;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private static AtmDto atmDto;
    private static BranchDto branchDto;

    @BeforeAll
    static void prepare() {
        atmDto = new AtmDto(
                1L,
                "Test address",
                LocalTime.of(9,0,0,0),
                LocalTime.of(22,0,0,0),
                false,
                branchDto
        );
        branchDto = new BranchDto(
                2L,
                "Test address",
                5L,
                "Test city",
                LocalTime.of(9,0,0,0),
                LocalTime.of(22,0,0,0)
        );
    }

    @BeforeEach
    void EachPrepare() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Чтение одного по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        Mockito.doReturn(atmDto).when(service).findById(1L);

        mockMvc.perform(get("/atm/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.address").value("Test address"))
                .andExpect(jsonPath("$.allHours").value(false));
    }

    @Test
    @DisplayName("Чтение одного по несуществующему id, негативный сценарий")
    void readByIdNegativeTest() throws Exception {
        Mockito.doReturn(null).when(service).findById(anyLong());

        mockMvc.perform(get("/atm/{id}", anyLong()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Чтение всех по id, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {
        AtmDto atmDto1 = new AtmDto();
        AtmDto atmDto2 = new AtmDto();
        atmDto1.setId(2L);
        atmDto2.setId(3L);
        List<AtmDto> atmDtoList = List.of(atmDto, atmDto1, atmDto2);

        Mockito.doReturn(atmDtoList).when(service).findAllById(List.of(1L,2L,3L));

        mockMvc.perform(get("/atm/read/all")
                        .param("ids", "1", "2", "3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id").value(Matchers.containsInAnyOrder(1, 2, 3)));
    }

    @Test
    @DisplayName("Поиск всех по некорректному листу id, негативный сценарий")
    void readAllByIdNegativeTest() throws Exception {
        Mockito.doReturn(Collections.emptyList()).when(service).findAllById(List.of(9L,45L,14L));

        mockMvc.perform(get("/atm/read/all")
                        .param("ids", "9", "45", "14"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Создание нового банкомата, позитивный сценарий")
    void createPositiveTest() throws Exception {
        String atmDtoJson = objectMapper.writeValueAsString(atmDto);

        Mockito.doReturn(atmDto).when(service).create(atmDto);

        mockMvc.perform(post("/atm/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atmDtoJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(atmDto.getId()))
                .andExpect(jsonPath("$.address").value("Test address"));
    }

    @Test
    @DisplayName("Создание нового банкомата, входной дто is null, негативный сценарий")
    void createWithNullDtoTest() throws Exception {
        String atmDtoJson = objectMapper.writeValueAsString(atmDto);

        Mockito.doReturn(null).when(service).create(atmDto);

        mockMvc.perform(post("/atm/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atmDtoJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Обновление данных о банкомате, позитивный сценарий")
    void updatePositiveTest() throws Exception {
        AtmDto atmDto1 = new AtmDto(
                2L,
                "New test address",
                LocalTime.of(9,0,0,0),
                LocalTime.of(23,0,0,0),
                false,
                branchDto
        );
        String atmDto1Json = objectMapper.writeValueAsString(atmDto1);

        Mockito.doReturn(atmDto1).when(service).update(1L, atmDto1);

        mockMvc.perform(put("/atm/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atmDto1Json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(atmDto1.getId()));
    }

    @Test
    @DisplayName("Обновление данных о несуществующем банкомате, негативный сценарий")
    void updateNegativeTest() throws Exception {
        String atmDtoJson = objectMapper.writeValueAsString(atmDto);

        Mockito.doReturn(null).when(service).update(1L, atmDto);

        mockMvc.perform(put("/atm/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(atmDtoJson))
                .andDo(print())
                .andExpect(status().isOk());
    }
}