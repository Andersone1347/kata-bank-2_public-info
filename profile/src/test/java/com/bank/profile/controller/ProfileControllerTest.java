package com.bank.profile.controller;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.service.ProfileService;
import com.bank.profile.util.ProfileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
class ProfileControllerTest {


    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    ProfileService service;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        ProfileDto profileDto = new ProfileUtil().createProfileDto();
        doReturn(profileDto).when(service).findById(anyLong());

        ResultActions result = mockMvc.perform(get("/profile/read/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profileDto)));


        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.phoneNumber").value(1L))
                .andExpect(jsonPath("$.email").value("fakeUser"))
                .andExpect(jsonPath("$.nameOnCard").value("fakeUser"))
                .andExpect(jsonPath("$.inn").value(1L))
                .andExpect(jsonPath("$.snils").value(1L))

                .andExpect(jsonPath("$.passport.id").value(1L))
                .andExpect(jsonPath("$.passport.series").value(1))
                .andExpect(jsonPath("$.passport.number").value(1L))
                .andExpect(jsonPath("$.passport.lastName").value("fakeUser"))
                .andExpect(jsonPath("$.passport.firstName").value("fakeUser"))
                .andExpect(jsonPath("$.passport.middleName").value("fakeUser"))
                .andExpect(jsonPath("$.passport.gender").value("fakeUser"))
                .andExpect(jsonPath("$.passport.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.passport.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$.passport.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$.passport.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.passport.divisionCode").value(1))
                .andExpect(jsonPath("$.passport.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$.passport.registration.id").value(1L))
                .andExpect(jsonPath("$.passport.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.index").value(1L))

                .andExpect(jsonPath("$.actualRegistration.id").value(1L))
                .andExpect(jsonPath("$.actualRegistration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.index").value(1L));

        verify(service).findById(profileDto.getId());

    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeNest() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).findById(10000L);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .get("/profile/read/{id}", 10000L)
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findById(10000L);
    }

    @Test
    @DisplayName("создание новых данных, позитивный сценарий ")
    void createNewDataPositiveTest() throws Exception {
        ProfileDto profileDto = new ProfileUtil().createProfileDto();
        doReturn(profileDto).when(service).save(any(ProfileDto.class));


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/profile/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profileDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.phoneNumber").value(1L))
                .andExpect(jsonPath("$.email").value("fakeUser"))
                .andExpect(jsonPath("$.nameOnCard").value("fakeUser"))
                .andExpect(jsonPath("$.inn").value(1L))
                .andExpect(jsonPath("$.snils").value(1L))

                .andExpect(jsonPath("$.passport.id").value(1L))
                .andExpect(jsonPath("$.passport.series").value(1))
                .andExpect(jsonPath("$.passport.number").value(1L))
                .andExpect(jsonPath("$.passport.lastName").value("fakeUser"))
                .andExpect(jsonPath("$.passport.firstName").value("fakeUser"))
                .andExpect(jsonPath("$.passport.middleName").value("fakeUser"))
                .andExpect(jsonPath("$.passport.gender").value("fakeUser"))
                .andExpect(jsonPath("$.passport.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.passport.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$.passport.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$.passport.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.passport.divisionCode").value(1))
                .andExpect(jsonPath("$.passport.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$.passport.registration.id").value(1L))
                .andExpect(jsonPath("$.passport.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.index").value(1L))

                .andExpect(jsonPath("$.actualRegistration.id").value(1L))
                .andExpect(jsonPath("$.actualRegistration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.index").value(1L));

        verify(service).save(any(ProfileDto.class));
    }


    @Test
    @DisplayName("создание неподходящих данных, негативный сценарий")
    void createNotSuitableDataNegativeTest() throws Exception {
        ProfileDto profileDto = new ProfileUtil().createProfileDto();
        doThrow(new DataIntegrityViolationException("message")).when(service).save(any(ProfileDto.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/profile/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profileDto)));

        result
                .andDo(print())
                .andExpect(status().isConflict());
        verify(service).save(any(ProfileDto.class));

    }


    @Test
    @DisplayName("обновление данных по id, позитивный сценарий")
    void updateByIdPositiveTest() throws Exception {
        ProfileDto profileDto = new ProfileUtil().createProfileDto();
        doReturn(profileDto).when(service).update(anyLong(), any(ProfileDto.class));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/profile/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profileDto)));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.phoneNumber").value(1L))
                .andExpect(jsonPath("$.email").value("fakeUser"))
                .andExpect(jsonPath("$.nameOnCard").value("fakeUser"))
                .andExpect(jsonPath("$.inn").value(1L))
                .andExpect(jsonPath("$.snils").value(1L))

                .andExpect(jsonPath("$.passport.id").value(1L))
                .andExpect(jsonPath("$.passport.series").value(1))
                .andExpect(jsonPath("$.passport.number").value(1L))
                .andExpect(jsonPath("$.passport.lastName").value("fakeUser"))
                .andExpect(jsonPath("$.passport.firstName").value("fakeUser"))
                .andExpect(jsonPath("$.passport.middleName").value("fakeUser"))
                .andExpect(jsonPath("$.passport.gender").value("fakeUser"))
                .andExpect(jsonPath("$.passport.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.passport.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$.passport.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$.passport.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$.passport.divisionCode").value(1))
                .andExpect(jsonPath("$.passport.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$.passport.registration.id").value(1L))
                .andExpect(jsonPath("$.passport.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.passport.registration.index").value(1L))

                .andExpect(jsonPath("$.actualRegistration.id").value(1L))
                .andExpect(jsonPath("$.actualRegistration.country").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.region").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.city").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.district").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.street").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$.actualRegistration.index").value(1L));

        verify(service).update(anyLong(), any(ProfileDto.class));
    }

    @Test
    @DisplayName("обновление данных по несуществующему id, негативный сценарий")
    void updateNonExistIdNegativeTest() throws Exception {
        ProfileDto profileDto = new ProfileUtil().createProfileDto();
        doThrow(new EntityNotFoundException()).when(service).update(Long.MAX_VALUE, profileDto);


        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/profile/update/{id}", Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(profileDto)));

        result
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service).update(Long.MAX_VALUE, profileDto);
    }


    @Test
    @DisplayName("чтение списка по id, позитивный сценарий")
    void readAllByIdPositiveTest() throws Exception {

        List<ProfileDto> profileDtoList = new ProfileUtil().createProfileDtoList();
        when(service.findAllById(List.of(1L, 2L))).thenReturn(profileDtoList);

        ResultActions result = mockMvc.perform(get("/profile/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("[0].phoneNumber").value(1L))
                .andExpect(jsonPath("[0].email").value("fakeUser"))
                .andExpect(jsonPath("[0].nameOnCard").value("fakeUser"))
                .andExpect(jsonPath("$[0].inn").value(1L))
                .andExpect(jsonPath("$[0].snils").value(1L))

                .andExpect(jsonPath("$[0].passport.id").value(1L))
                .andExpect(jsonPath("$[0].passport.series").value(1))
                .andExpect(jsonPath("$[0].passport.number").value(1L))
                .andExpect(jsonPath("$[0].passport.lastName").value("fakeUser"))
                .andExpect(jsonPath("$[0].passport.firstName").value("fakeUser"))
                .andExpect(jsonPath("[0].passport.middleName").value("fakeUser"))
                .andExpect(jsonPath("$[0].passport.gender").value("fakeUser"))
                .andExpect(jsonPath("$[0].passport.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[0].passport.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$[0].passport.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$[0].passport.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[0].passport.divisionCode").value(1))
                .andExpect(jsonPath("$[0].passport.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$[0].passport.registration.id").value(1L))
                .andExpect(jsonPath("$[0].passport.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$[0].passport.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$[0].passport.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$[0].passport.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$[0].passport.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$[0].passport.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$[0].passport.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].passport.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[0].passport.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].passport.registration.index").value(1L))

                .andExpect(jsonPath("$[0].actualRegistration.id").value(1L))
                .andExpect(jsonPath("$[0].actualRegistration.country").value("fakeAddress"))
                .andExpect(jsonPath("$[0].actualRegistration.region").value("fakeAddress"))
                .andExpect(jsonPath("$[0].actualRegistration.city").value("fakeAddress"))
                .andExpect(jsonPath("$[0].actualRegistration.district").value("fakeAddress"))
                .andExpect(jsonPath("$[0].actualRegistration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$[0].actualRegistration.street").value("fakeAddress"))
                .andExpect(jsonPath("$[0].actualRegistration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].actualRegistration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[0].actualRegistration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[0].actualRegistration.index").value(1L))


                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].phoneNumber").value(1L))
                .andExpect(jsonPath("$[1].email").value("fakeUser"))
                .andExpect(jsonPath("$[1].nameOnCard").value("fakeUser"))
                .andExpect(jsonPath("$[1].inn").value(1L))
                .andExpect(jsonPath("$[1].snils").value(1L))

                .andExpect(jsonPath("$[1].passport.id").value(1L))
                .andExpect(jsonPath("$[1].passport.series").value(1))
                .andExpect(jsonPath("$[1].passport.number").value(1L))
                .andExpect(jsonPath("$[1].passport.lastName").value("fakeUser"))
                .andExpect(jsonPath("$[1].passport.firstName").value("fakeUser"))
                .andExpect(jsonPath("$[1].passport.middleName").value("fakeUser"))
                .andExpect(jsonPath("$[1].passport.gender").value("fakeUser"))
                .andExpect(jsonPath("$[1].passport.birthDate").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[1].passport.birthPlace").value("fakeUser"))
                .andExpect(jsonPath("$[1].passport.issuedBy").value("fakeUser"))
                .andExpect(jsonPath("$[1].passport.dateOfIssue").value(String.valueOf(LocalDate.now())))
                .andExpect(jsonPath("$[1].passport.divisionCode").value(1))
                .andExpect(jsonPath("$[1].passport.expirationDate").value(String.valueOf(LocalDate.now())))

                .andExpect(jsonPath("$[1].passport.registration.id").value(1L))
                .andExpect(jsonPath("$[1].passport.registration.country").value("fakeAddress"))
                .andExpect(jsonPath("$[1].passport.registration.region").value("fakeAddress"))
                .andExpect(jsonPath("$[1].passport.registration.city").value("fakeAddress"))
                .andExpect(jsonPath("$[1].passport.registration.district").value("fakeAddress"))
                .andExpect(jsonPath("$[1].passport.registration.locality").value("fakeAddress"))
                .andExpect(jsonPath("[1].passport.registration.street").value("fakeAddress"))
                .andExpect(jsonPath("$[1].passport.registration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].passport.registration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[1].passport.registration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].passport.registration.index").value(1L))

                .andExpect(jsonPath("$[1].actualRegistration.id").value(1L))
                .andExpect(jsonPath("$[1].actualRegistration.country").value("fakeAddress"))
                .andExpect(jsonPath("$[1].actualRegistration.region").value("fakeAddress"))
                .andExpect(jsonPath("$[1].actualRegistration.city").value("fakeAddress"))
                .andExpect(jsonPath("$[1].actualRegistration.district").value("fakeAddress"))
                .andExpect(jsonPath("$[1].actualRegistration.locality").value("fakeAddress"))
                .andExpect(jsonPath("$[1].actualRegistration.street").value("fakeAddress"))
                .andExpect(jsonPath("$[1].actualRegistration.houseNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].actualRegistration.houseBlock").value("fakeAddress"))
                .andExpect(jsonPath("$[1].actualRegistration.flatNumber").value("fakeAddress"))
                .andExpect(jsonPath("$[1].actualRegistration.index").value(1L));

        verify(service).findAllById(Arrays.asList(1L, 2L));
    }


    @Test
    @DisplayName("чтение списка по несуществующему id, негативный сценарий")
    void readAllNonExistIdNegativeTest() throws Exception {
        when(service.findAllById(List.of(1L, 2L))).thenThrow(new EntityNotFoundException());
        ResultActions result = mockMvc.perform(get("/profile/read/all")
                .param("ids", "1,2")
                .contentType(MediaType.APPLICATION_JSON));

        result
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(service).findAllById(Arrays.asList(1L, 2L));
    }
}
