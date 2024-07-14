package com.bank.profile.util;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;

import java.util.Arrays;
import java.util.List;

public class ActualRegistrationUtil {

    public ActualRegistrationDto createActualRegistrationDto() {
        return ActualRegistrationDto.builder()
                .id(1L)
                .country("fakeAddress")
                .region("fakeAddress")
                .city("fakeAddress")
                .district("fakeAddress")
                .locality("fakeAddress")
                .street("fakeAddress")
                .houseNumber("fakeAddress")
                .houseBlock("fakeAddress")
                .flatNumber("fakeAddress")
                .index(1L)
                .build();
    }

    public ActualRegistrationEntity createActualRegistrationEntity() {
        return ActualRegistrationEntity.builder()
                .id(1L)
                .country("fakeAddress")
                .region("fakeAddress")
                .city("fakeAddress")
                .district("fakeAddress")
                .locality("fakeAddress")
                .street("fakeAddress")
                .houseNumber("fakeAddress")
                .houseBlock("fakeAddress")
                .flatNumber("fakeAddress")
                .index(1L)
                .build();
    }


    public List<ActualRegistrationDto> createActualRegistrationDtoList(){
        return Arrays.asList(
                ActualRegistrationDto.builder()
                        .id(1L)
                        .country("fakeAddress")
                        .region("fakeAddress")
                        .city("fakeAddress")
                        .district("fakeAddress")
                        .locality("fakeAddress")
                        .street("fakeAddress")
                        .houseNumber("fakeAddress")
                        .houseBlock("fakeAddress")
                        .flatNumber("fakeAddress")
                        .index(1L)
                        .build(),
                ActualRegistrationDto.builder()
                        .id(2L)
                        .country("fakeAddress")
                        .region("fakeAddress")
                        .city("fakeAddress")
                        .district("fakeAddress")
                        .locality("fakeAddress")
                        .street("fakeAddress")
                        .houseNumber("fakeAddress")
                        .houseBlock("fakeAddress")
                        .flatNumber("fakeAddress")
                        .index(1L)
                        .build()
        );
    }

    public List<ActualRegistrationEntity> createActualRegistrationEntityList(){
        return Arrays.asList(
                ActualRegistrationEntity.builder()
                        .id(1L)
                        .country("fakeAddress")
                        .region("fakeAddress")
                        .city("fakeAddress")
                        .district("fakeAddress")
                        .locality("fakeAddress")
                        .street("fakeAddress")
                        .houseNumber("fakeAddress")
                        .houseBlock("fakeAddress")
                        .flatNumber("fakeAddress")
                        .index(1L)
                        .build(),
                ActualRegistrationEntity.builder()
                        .id(2L)
                        .country("fakeAddress")
                        .region("fakeAddress")
                        .city("fakeAddress")
                        .district("fakeAddress")
                        .locality("fakeAddress")
                        .street("fakeAddress")
                        .houseNumber("fakeAddress")
                        .houseBlock("fakeAddress")
                        .flatNumber("fakeAddress")
                        .index(1L)
                        .build()
        );
    }

    public ActualRegistrationEntity createPreMergerActualRegistrationEntity() {
        return ActualRegistrationEntity.builder()
                .id(3L)
                .country("PreAddress")
                .region("PreAddress")
                .city("PreAddress")
                .district("PreAddress")
                .locality("PreAddress")
                .street("PreAddress")
                .houseNumber("PreAddress")
                .houseBlock("PreAddress")
                .flatNumber("PreAddress")
                .index(3L)
                .build();
    }

    public ActualRegistrationEntity createPostMergerActualRegistrationEntity() {
        return ActualRegistrationEntity.builder()
                .id(3L)
                .country("fakeAddress")
                .region("fakeAddress")
                .city("fakeAddress")
                .district("fakeAddress")
                .locality("fakeAddress")
                .street("fakeAddress")
                .houseNumber("fakeAddress")
                .houseBlock("fakeAddress")
                .flatNumber("fakeAddress")
                .index(1L)
                .build();
    }

    public ActualRegistrationDto createUpdateActualRegistrationEntity() {
        return ActualRegistrationDto.builder()
                .id(3L)
                .country("fakeAddress")
                .region("fakeAddress")
                .city("fakeAddress")
                .district("fakeAddress")
                .locality("fakeAddress")
                .street("fakeAddress")
                .houseNumber("fakeAddress")
                .houseBlock("fakeAddress")
                .flatNumber("fakeAddress")
                .index(3L)
                .build();
    }


}
