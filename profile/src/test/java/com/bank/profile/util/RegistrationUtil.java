package com.bank.profile.util;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;

import java.util.Arrays;
import java.util.List;

public class RegistrationUtil {
    public RegistrationDto createRegistrationDto() {
        return RegistrationDto.builder()
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

    public RegistrationEntity createRegistrationEntity() {
        return RegistrationEntity.builder()
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

    public List<RegistrationDto> createRegistrationDtoList() {
        return Arrays.asList(
                RegistrationDto.builder()
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
                RegistrationDto.builder()
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

    public List<RegistrationEntity> createRegistrationEntityList() {
        return Arrays.asList(
                RegistrationEntity.builder()
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
                RegistrationEntity.builder()
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

    public RegistrationEntity createPreMergerRegistrationEntity() {
        return RegistrationEntity.builder()
                .id(3L)
                .country("fake")
                .region("fake")
                .city("fake")
                .district("fake")
                .locality("fake")
                .street("fake")
                .houseNumber("fake")
                .houseBlock("fake")
                .flatNumber("fake")
                .index(3L)
                .build();
    }

    public RegistrationEntity createPostMergerRegistrationEntity() {
        return RegistrationEntity.builder()
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


    public RegistrationDto createUpdateRegistrationDto() {
        return RegistrationDto.builder()
                .id(3L)
                .country("fake")
                .region("fake")
                .city("fake")
                .district("fake")
                .locality("fake")
                .street("fake")
                .houseNumber("fake")
                .houseBlock("fake")
                .flatNumber("fake")
                .index(3L)
                .build();
    }


}
