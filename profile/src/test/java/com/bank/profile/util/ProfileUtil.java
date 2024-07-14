package com.bank.profile.util;

import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ProfileEntity;

import java.util.Arrays;
import java.util.List;

public class ProfileUtil {

    public ProfileDto createProfileDto() {
        return ProfileDto.builder()
                .id(1L)
                .phoneNumber(1L)
                .email("fakeUser")
                .nameOnCard("fakeUser")
                .inn(1L)
                .snils(1L)
                .passport(new PassportUtil().createPassportDto())
                .actualRegistration(new ActualRegistrationUtil().createActualRegistrationDto())
                .build();
    }

    public ProfileEntity createProfileEntity() {
        return ProfileEntity.builder()
                .id(1L)
                .phoneNumber(1L)
                .email("fakeUser")
                .nameOnCard("fakeUser")
                .inn(1L)
                .snils(1L)
                .passport(new PassportUtil().createPassportEntity())
                .actualRegistration(new ActualRegistrationUtil().createActualRegistrationEntity())
                .build();
    }

    public List<ProfileDto> createProfileDtoList() {
        return Arrays.asList(
                ProfileDto.builder()
                        .id(1L)
                        .phoneNumber(1L)
                        .email("fakeUser")
                        .nameOnCard("fakeUser")
                        .inn(1L)
                        .snils(1L)
                        .passport(new PassportUtil().createPassportDto())
                        .actualRegistration(new ActualRegistrationUtil().createActualRegistrationDto())
                        .build(),
                ProfileDto.builder()
                        .id(2L)
                        .phoneNumber(1L)
                        .email("fakeUser")
                        .nameOnCard("fakeUser")
                        .inn(1L)
                        .snils(1L)
                        .passport(new PassportUtil().createPassportDto())
                        .actualRegistration(new ActualRegistrationUtil().createActualRegistrationDto())
                        .build());

    }

    public List<ProfileEntity> createProfileEntityList() {
        return Arrays.asList(
                ProfileEntity.builder()
                        .id(1L)
                        .phoneNumber(1L)
                        .email("fakeUser")
                        .nameOnCard("fakeUser")
                        .inn(1L)
                        .snils(1L)
                        .passport(new PassportUtil().createPassportEntity())
                        .actualRegistration(new ActualRegistrationUtil().createActualRegistrationEntity())
                        .build(),
                ProfileEntity.builder()
                        .id(2L)
                        .phoneNumber(1L)
                        .email("fakeUser")
                        .nameOnCard("fakeUser")
                        .inn(1L)
                        .snils(1L)
                        .passport(new PassportUtil().createPassportEntity())
                        .actualRegistration(new ActualRegistrationUtil().createActualRegistrationEntity())
                        .build());
    }


    public ProfileEntity createPreMergerProfileEntity() {
        return ProfileEntity.builder()
                .id(3L)
                .phoneNumber(3L)
                .email("fake")
                .nameOnCard("fake")
                .inn(3L)
                .snils(3L)
                .passport(new PassportUtil().createPassportEntity())
                .actualRegistration(new ActualRegistrationUtil().createActualRegistrationEntity())
                .build();
    }

    public ProfileEntity createPostMergerProfileEntity() {
        return ProfileEntity.builder()
                .id(3L)
                .phoneNumber(1L)
                .email("fakeUser")
                .nameOnCard("fakeUser")
                .inn(1L)
                .snils(1L)
                .passport(new PassportUtil().createPassportEntity())
                .actualRegistration(new ActualRegistrationUtil().createActualRegistrationEntity())
                .build();
    }

    public ProfileDto createUpdateProfileDto() {
        return ProfileDto.builder()
                .id(3L)
                .phoneNumber(3L)
                .email("fake")
                .nameOnCard("fake")
                .inn(3L)
                .snils(3L)
                .passport(new PassportUtil().createPassportDto())
                .actualRegistration(new ActualRegistrationUtil().createActualRegistrationDto())
                .build();

    }


}
