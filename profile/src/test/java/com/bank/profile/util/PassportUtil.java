package com.bank.profile.util;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.entity.PassportEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class PassportUtil {

    public PassportDto createPassportDto(){
        return PassportDto.builder()
                .id(1L)
                .series(1)
                .number(1L)
                .lastName("fakeUser")
                .firstName("fakeUser")
                .middleName("fakeUser")
                .gender("fakeUser")
                .birthDate(LocalDate.now())
                .birthPlace("fakeUser")
                .issuedBy("fakeUser")
                .dateOfIssue(LocalDate.now())
                .divisionCode(1)
                .expirationDate(LocalDate.now())
                .registration(new RegistrationUtil().createRegistrationDto())
                .build();
    }


    public PassportEntity createPassportEntity(){
        return PassportEntity.builder()
                .id(1L)
                .series(1)
                .number(1L)
                .lastName("fakeUser")
                .firstName("fakeUser")
                .middleName("fakeUser")
                .gender("fakeUser")
                .birthDate(LocalDate.now())
                .birthPlace("fakeUser")
                .issuedBy("fakeUser")
                .dateOfIssue(LocalDate.now())
                .divisionCode(1)
                .expirationDate(LocalDate.now())
                .registration(new RegistrationUtil().createRegistrationEntity())
                .build();
    }

    public List<PassportDto> createPassportDtoList() {
        return Arrays.asList(
                PassportDto.builder()
                        .id(1L)
                        .series(1)
                        .number(1L)
                        .lastName("fakeUser")
                        .firstName("fakeUser")
                        .middleName("fakeUser")
                        .gender("fakeUser")
                        .birthDate(LocalDate.now())
                        .birthPlace("fakeUser")
                        .issuedBy("fakeUser")
                        .dateOfIssue(LocalDate.now())
                        .divisionCode(1)
                        .expirationDate(LocalDate.now())
                        .registration(new RegistrationUtil().createRegistrationDto())
                        .build(),
                PassportDto.builder()
                        .id(2L)
                        .series(1)
                        .number(1L)
                        .lastName("fakeUser")
                        .firstName("fakeUser")
                        .middleName("fakeUser")
                        .gender("fakeUser")
                        .birthDate(LocalDate.now())
                        .birthPlace("fakeUser")
                        .issuedBy("fakeUser")
                        .dateOfIssue(LocalDate.now())
                        .divisionCode(1)
                        .expirationDate(LocalDate.now())
                        .registration(new RegistrationUtil().createRegistrationDto())
                        .build()
        );
    }

    public List<PassportEntity> createPassportEntityList() {
        return Arrays.asList(
                PassportEntity.builder()
                        .id(1L)
                        .series(1)
                        .number(1L)
                        .lastName("fakeUser")
                        .firstName("fakeUser")
                        .middleName("fakeUser")
                        .gender("fakeUser")
                        .birthDate(LocalDate.now())
                        .birthPlace("fakeUser")
                        .issuedBy("fakeUser")
                        .dateOfIssue(LocalDate.now())
                        .divisionCode(1)
                        .expirationDate(LocalDate.now())
                        .registration(new RegistrationUtil().createRegistrationEntity())
                        .build(),
                PassportEntity.builder()
                        .id(2L)
                        .series(1)
                        .number(1L)
                        .lastName("fakeUser")
                        .firstName("fakeUser")
                        .middleName("fakeUser")
                        .gender("fakeUser")
                        .birthDate(LocalDate.now())
                        .birthPlace("fakeUser")
                        .issuedBy("fakeUser")
                        .dateOfIssue(LocalDate.now())
                        .divisionCode(1)
                        .expirationDate(LocalDate.now())
                        .registration(new RegistrationUtil().createRegistrationEntity())
                        .build()
        );
    }

    public PassportEntity createPreMergerPassportEntity() {
       return PassportEntity.builder()
                .id(3L)
                .series(3)
                .number(3L)
                .lastName("fake")
                .firstName("fake")
                .middleName("fake")
                .gender("fake")
                .birthDate(LocalDate.now())
                .birthPlace("fake")
                .issuedBy("fake")
                .dateOfIssue(LocalDate.now())
                .divisionCode(3)
                .expirationDate(LocalDate.now())
                .registration(new RegistrationUtil().createRegistrationEntity())
                .build();
    }


    public PassportEntity createPostMergerPassportEntity() {
        return PassportEntity.builder()
                .id(3L)
                .series(1)
                .number(1L)
                .lastName("fakeUser")
                .firstName("fakeUser")
                .middleName("fakeUser")
                .gender("fakeUser")
                .birthDate(LocalDate.now())
                .birthPlace("fakeUser")
                .issuedBy("fakeUser")
                .dateOfIssue(LocalDate.now())
                .divisionCode(1)
                .expirationDate(LocalDate.now())
                .registration(new RegistrationUtil().createRegistrationEntity())
                .build();
    }

    public PassportDto updatePassportDto() {
        return PassportDto.builder()
                .id(3L)
                .series(3)
                .number(3L)
                .lastName("fake")
                .firstName("fake")
                .middleName("fake")
                .gender("fake")
                .birthDate(LocalDate.now())
                .birthPlace("fake")
                .issuedBy("fake")
                .dateOfIssue(LocalDate.now())
                .divisionCode(3)
                .expirationDate(LocalDate.now())
                .registration(new RegistrationUtil().createRegistrationDto())
                .build();
    }


}
